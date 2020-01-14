package tofperipheral
import spinal.core._

class Sequencer(numBits: Int) extends Component{

  val io = new Bundle {
    val period = in UInt(numBits bits)
    val lockin_sync1_start = in UInt(numBits bits)
    val lockin_sync1_end = in UInt(numBits bits)
    val lockin_sync2_start = in UInt(numBits bits)
    val lockin_sync2_end = in UInt(numBits bits)
    val trigOn = in Bool
    val syncOn = in Bool
    val trig = out Bool
    val sync1 = out Bool
    val sync2 = out Bool
    val seqeunceCounter = out UInt(numBits bits)
  }


  // generate internal trigger
  val trigTestCounter = Reg(UInt(numBits bits))
  val trigTestValue = Reg(Bool)
  val trigTestValueInc = UInt(numBits bits)

  trigTestValueInc := trigTestCounter + 1
  trigTestCounter := trigTestValueInc

  when (trigTestValueInc >= io.period) {
    trigTestCounter := 0
  }

  when (trigTestCounter === 0) {
    trigTestValue := True
  }
  when (trigTestCounter === (io.period>>1)) {
    trigTestValue := False
  }

  val sync1 = RegInit(False)
  when (trigTestCounter === io.lockin_sync1_start) {
    sync1:=True
  }
  when (trigTestCounter === io.lockin_sync1_end) {
    sync1:=False
  }

  val sync2 = RegInit(False)
  when (trigTestCounter === io.lockin_sync2_start) {
    sync2:=True
  }
  when (trigTestCounter === io.lockin_sync2_end) {
    sync2:=False
  }

  io.sync1 := Mux(io.syncOn, sync1, False)
  io.sync2 := Mux(io.syncOn, sync2, False)
  io.trig := Mux(io.trigOn, trigTestValue, False)
  io.seqeunceCounter := trigTestCounter


}
