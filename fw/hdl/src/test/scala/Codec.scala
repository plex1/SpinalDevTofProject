package soc

import spinal.core.sim._
import spinal.sim._

object CodecFormat {

  // constants
  val id = 0xcf
  def w_byte = 8
  def w_word = 32
  def n_bw = w_word  / w_byte
  def n_header = 3 // in words

  // enums

  // functions
  def intToByteArray(d: Int): Array[Byte] = for (i <- (0 to n_bw-1).toArray) yield ((d >> w_byte*i) & 0xff).toByte
  def ByteArrayToInt(d: Array[Byte]): Int = d.foldRight(0)(_.toInt + (1<< w_byte) * _ )

  // constructor with byte array
  def apply(in: Array[Byte]): CodecFormat = {
    val Array(idcontrol, addr, len) = in.grouped(n_bw).take(n_header).toArray.map(x => CodecFormat.ByteArrayToInt(x))
    val incr = if ((idcontrol & 1) == 0) false else true
    val command = (idcontrol & (1<<CodecFormat.w_byte)) >> CodecFormat.w_byte
    val request = (idcontrol & (1 << 1)) >> 1
    val data = in.drop(n_header * n_bw)
    new CodecFormat(command, addr, len, data, incr, request)
  }

}

case class CodecFormat(command:Int, addr:Int, len:Int, data: Array[Byte] = Array.empty[Byte], incr: Boolean=true, request: Int = 1) {

  def toByteArray: Array[Byte] = {
    // construct header and control bytes
    val idcval = (CodecFormat.id << (CodecFormat.w_word - CodecFormat.w_byte))+ (command << CodecFormat.w_byte)+ (request <<1) +(if (incr) 1 else 0)
    val idcontrol = CodecFormat.intToByteArray(idcval)

    // construct frame
    val frame = idcontrol ++ CodecFormat.intToByteArray(addr) ++ CodecFormat.intToByteArray(len) ++ data
    frame
  }

  def mkString: String = {
    "request: " +   request.toString + ", command: " + command.toString+", len: " + len.toString + ", addr: " + addr.toHexString +
      ", data (hex): " + (if (data.length == 0) "none" else data.map("%02x".format(_)).mkString(" "))
  }
}

case class Codec(Tx: UartEncoderSim, Rx: UartDecoderSim){

  def sendMessage(data: Array[Byte]): Unit = {
    data.foreach(Tx.writeQueue.enqueue(_))
  }

  def getMessage(len: Int): Array[Byte]@suspendable = {
    while (Rx.readQueue.length<len) {sleep(1000)}
    val ret = for (i <- Array.range(0,len)) yield Rx.readQueue.dequeue().toByte
    ret
  }

  def MessageAvailable( ) : Boolean ={
    Rx.readQueue.length>=CodecFormat.n_header*CodecFormat.w_byte
  }

  def transceive(request:CodecFormat):CodecFormat@suspendable = {

    // send tx frame
    val bb = request.toByteArray
    sendMessage(bb)
    println("[INFO] sent     -> " + request.mkString)

    // get rx frame
    val rxheader = getMessage(CodecFormat.n_header*CodecFormat.n_bw) // first part
    val data = if (CodecFormat(rxheader).command == 0) {
      getMessage(CodecFormat(rxheader).len * CodecFormat.n_bw) // data part for the read command
    } else {Array.empty[Byte]}
    var response = rxheader ++ data
    println("[INFO] received -> " + CodecFormat(response).mkString)

    CodecFormat(response) //return

  }

}


