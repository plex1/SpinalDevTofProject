package tofperipheral

import spinal.core._

class RingOscillator(divider: Int) extends BlackBox {

  // define verilog generics
  val generic = new Generic {
    val divider = RingOscillator.this.divider
  }

  // define verilog ios
  val io = new Bundle {
    val clk_out = out Bool
  }

  noIoPrefix()
  def filename = "src/main/verilog/RingOscillator.v"
  addRTLPath(filename)
}

