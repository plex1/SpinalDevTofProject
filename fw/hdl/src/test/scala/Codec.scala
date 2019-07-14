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
  def R = 0
  def W = 1

  // enums

  // functions
  def byteToInt(d: Byte) : Int = if (d>= 0) {d.toInt} else {d.toInt & 0xff}
  def intToByteArray(d: Int): Array[Byte] = for (i <- (n_bw-1 to 0 by -1).toArray) yield ((d >>> w_byte*i) & 0xff).toByte
  def byteArrayToInt(d: Array[Byte]): Int = d.foldLeft(0)((1<< w_byte) * _ + byteToInt(_))
  def intArrayToByteArray(d: Array[Int]) : Array[Byte] = d.flatMap(intToByteArray(_))
  def byteArrayToIntArray(d: Array[Byte]) : Array[Int] = d.grouped(4).toArray.map(byteArrayToInt(_))

  // constructor with byte array
  def apply(in: Array[Byte]): CodecFormat = {
    val Array(idcontrol, addr, len) = in.grouped(n_bw).take(n_header).toArray.map(x => CodecFormat.byteArrayToInt(x))
    val incr = if ((idcontrol & 2) == 0) false else true
    val command = (idcontrol & (1<<CodecFormat.w_byte)) >> CodecFormat.w_byte
    val request = (idcontrol & 1)
    val data = byteArrayToIntArray(in.drop(n_header * n_bw))
    new CodecFormat(command, addr, len, data, incr, request)
  }

}

case class CodecFormat(command:Int, addr:Int, len:Int, data: Array[Int] = Array.empty[Int], incr: Boolean=true, request: Int = 1) {

  def toByteArray: Array[Byte] = {
    // construct header and control bytes
    val idcval = (CodecFormat.id << (CodecFormat.w_word - CodecFormat.w_byte))+ (command << CodecFormat.w_byte)+ 2*(if (incr) 1 else 0) +(request)
    val idcontrol = CodecFormat.intToByteArray(idcval)

    // construct frame
    val frame = idcontrol ++ CodecFormat.intToByteArray(addr) ++ CodecFormat.intToByteArray(len) ++
      CodecFormat.intArrayToByteArray(data)
    println("raw: " + frame.map("%02x".format(_)).mkString(" "))
    frame
  }

  def mkString: String = {
    "request: " +   request.toString + ", command: " + command.toString+", len: " + len.toString + ", addr: " + addr.toHexString +
      ", data (hex): " + (if (data.length == 0) "none" else data.map("%01x".format(_)).mkString(" "))
  }
}

case class Codec(Tx: UartEncoderSim, Rx: UartDecoderSim){

  def sendMessage(data: Array[Byte]): Unit = {
    data.foreach(Tx.writeQueue.enqueue(_))
  }

  def getMessage(len: Int): Array[Byte]@suspendable = {
    while (Rx.readQueue.length<len) {sleep(10000000)} //cycles to be defined
    val ret = for (i <- Array.range(0,len)) yield Rx.readQueue.dequeue().toByte
    ret
  }

  def MessageAvailable( ) : Boolean = {
    Rx.readQueue.length>=CodecFormat.n_header*CodecFormat.w_byte
  }

  def transceive(request:CodecFormat):CodecFormat@suspendable = {

    // send tx frame
    val bb = request.toByteArray
    sendMessage(bb)
    println("[INFO] sent     -> " + request.mkString)

    // get rx frame
    val rxheader = getMessage(CodecFormat.n_header*CodecFormat.n_bw) // first part
    println("[INFO] header received")
    val data = if (CodecFormat(rxheader).command == 0) {
      getMessage(CodecFormat(rxheader).len * CodecFormat.n_bw) // data part for the read command
    } else {Array.empty[Byte]}
    var response = rxheader ++ data
    println("[INFO] received -> " + CodecFormat(response).mkString)

    CodecFormat(response) //return

  }

}


