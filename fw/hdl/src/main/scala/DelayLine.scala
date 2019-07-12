package tofperipheral

import spinal.core._

class DelayIo(numElements: Int) extends Bundle{
  //val io = new Bundle {
    val clk = in Bool
    val in_signal= in Bool
    val delay_value = out Bits(numElements bits)
    val a = in Bool
    val b = in Bool
  //}
}

trait DelayBase{
  def io : DelayIo
}

class DelayLine(numElements: Int) extends BlackBox with DelayBase{

  // define verilog generics
  val generic = new Generic {
    val num_elements = DelayLine.this.numElements
  }

  // define verilog ios
  override val io = new DelayIo(DelayLine.this.numElements)
 /* val io = new Bundle {
    val clk = in Bool
    val in_signal= in Bool
    val delay_value = out Bits(numElements bits)
    val a = in Bool
    val b = in Bool
  }*/

  mapClockDomain(clock=io.clk)
  noIoPrefix()
  def filename = "src/main/verilog/DelayLine.v"
  CreateDelayLineVerilog.create(filename, numElements, true, false, constr = true)
  addRTLPath(filename)
}
