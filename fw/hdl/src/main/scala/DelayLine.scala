package tofperipheral

import spinal.core._

class DelayLine(numElements: Int) extends BlackBox{

  // define verilog generics
  val generic = new Generic {
    val num_elements = DelayLine.this.numElements
  }

  // define verilog ios
  val io = new Bundle {
    val clk = in Bool
    val in_signal= in Bool
    val delay_value = out Bits(numElements bits)
  }

  mapClockDomain(clock=io.clk)
  noIoPrefix()
  addRTLPath("src/main/verilog/DelayLine.v")
}
