package tofperipheral

import spinal.core._
import spinal.core.sim._



class DelayLineSim(numElements: Int) extends Component with DelayBase {

  // define verilog ios
  override val io = new DelayIo(numElements)
  /*val io = new Bundle {
    val clk = in Bool
    val in_signal= in Bool
    val delay_value = out Bits(numElements bits)
    val a = in Bool
    val b = in Bool
  }*/


  io.delay_value := B"0101010101010101010101010101101010101010101010101010101010101010"
  //val delay_value =  Reg(Bits(numElements bits)) init(  B"0101010101010101010101010101101010101010101010101010101010101010" ) simPublic()

}
