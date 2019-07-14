package tofperipheral

import spinal.core._
import spinal.lib._

object histogramMode extends SpinalEnum() {
  val RESET_MEM , RECORD, READ, RESET_ADDR= newElement()
}



class Histogram(numBins: Int, bitWidth : Int) extends Component{

  val io = new Bundle {
    val mode =  in(histogramMode()) //UInt(2 bits)
    val values = slave Flow UInt(log2Up(numBins) bits)
    val readValues = out Bits(bitWidth bits)
    val readValid = in Bool
  }

  io.readValues.clearAll()
  val mem = Mem(Bits(bitWidth bits), wordCount = numBins)
  val address = Reg(UInt(log2Up(numBins) bits))
  val valid = Reg(Bool)
  when (io.mode === histogramMode.RECORD) {
    address := io.values.payload
    valid := io.values.valid
    val readData = Bits(bitWidth bits)
    readData := mem.readSync(address = io.values.payload)
    // todo: when consecutive reads from same address in consecutive clock cycles, upate required
    when (valid) {
      val lastCount = Bits(bitWidth bits)
      mem.write(address = address,   data = (readData.asUInt + 1).asBits)
    }

  } elsewhen(io.mode === histogramMode.READ) {
    io.readValues := mem.readSync(address = address)
    when(io.readValid) {
      address := address + 1
    }
  } elsewhen (io.mode === histogramMode.RESET_ADDR) {
      address := 0
  }elsewhen (io.mode === histogramMode.RESET_MEM) {
    address := address + 1
    mem.write(address = address, data = B(0, bitWidth bits))
  }

}

class HistogramFilter(bitWidth : Int)  extends Component {

  val io = new Bundle {
    val filterOn = in Bool
    val validIn = in Bool
    val validOut = out Bool
    val internalCount = in UInt(bitWidth bits)
    val interestedCount = in UInt(bitWidth bits)
  }

  when ((io.internalCount === io.interestedCount) || !io.filterOn) {
    io.validOut := io.validIn
  } otherwise {
    io.validOut := False
  }

}

class AverageFilter(bitWidth : Int, decimationFactor : Int)  extends Component {

  val io = new Bundle {
    val valid = in Bool
    val dataIn = in UInt(bitWidth bits)
    val dataOut = out UInt(bitWidth bits)
  }

  val averageCalc = Reg(UInt((log2Up(decimationFactor) + bitWidth) bits))
  val averageSaved = Reg(UInt((log2Up(decimationFactor) + bitWidth) bits))
  val counter = Reg(UInt(log2Up(decimationFactor) bits)) init(0)
  when (io.valid) {
    averageCalc := averageCalc + io.dataIn
    counter := counter + 1
    when (counter === 0) {
      averageSaved := averageCalc
      averageCalc := io.dataIn.resized
    }
  }
  io.dataOut := averageSaved(log2Up(decimationFactor) until averageSaved.getWidth);

}
