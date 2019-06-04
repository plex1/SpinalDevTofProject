package tofperipheral

import spinal.core._

class DelayLineSim(numElements: Int) extends Component{

    // define verilog ios
  val io = new Bundle {
    val clk = in Bool
    val in_signal= in Bool
    val delay_value = out Bits(numElements bits)
  }

  io.delay_value := B"32'xa9555555"

}
