package tofperipheral

import spinal.core._
import spinal.lib.bus.amba3.apb.{Apb3, Apb3SlaveFactory}
import spinal.lib.slave // custom apb peripheral

class TofPeripheral extends Component {
  val io = new Bundle {
    val apb = slave(Apb3(
      addressWidth = 8,
      dataWidth = 32
    ))
    val led = out Bool
    val led2 = out Bool
    val trigsOut = out Bits(3 bits)
    val trigsIn = in Bits(2 bits)
    val delay = out Bits(6 bits)
  }
  io.led2 := False
  val busCtrl = Apb3SlaveFactory(io.apb)
  io.led := (busCtrl.createReadAndWrite(Bits(1 bits), 0x00, 0, "this is the led2") init (0)) === B"1"
  io.led2.setWhen(busCtrl.isWriting(0x00 + 4))
  busCtrl.read(U"h1a", 0x00 + 8)
  busCtrl.createReadAndWrite(Bits(32 bits), 0x00+12, 0, "this is a test register") init (0)
  io.delay := (busCtrl.createReadAndWrite(Bits(io.delay.getWidth bits), 0x00 + 24, 0, "delay chip setting") init (0))


  busCtrl.printDataModel()

  val toggleReg = Reg(Bool)
  toggleReg := !toggleReg
  io.trigsOut(0) := toggleReg
  io.trigsOut(1) := toggleReg
  io.trigsOut(2) := io.trigsIn(0) && !io.trigsIn(1)

   // when this happens: [error] COMBINATORIAL LOOP :
  //right, basicaly, the combinatorial loop analysis threat the whole signal as one
  //  If you want, you can disable the combinatorial loop check on that specific signal
  //val a = Bits(2 bits).noCombLoopCheck
  //Or
  //Instead using a Bits, use a Vec(Bool, 2)


  var tofLen = 64

  val tofVals = Bits(tofLen bits).noCombLoopCheck
  val tofRegs = Reg(Bits(tofLen bits)) init(0)

  tofVals(0) := io.trigsIn(1)

  for( i <- 1 to tofVals.getWidth-2){
    tofVals(i+1) := !tofVals(i)
  }
  tofVals(1) := !tofVals(0)

  when((tofVals(0) =/= tofRegs(0)) && (tofVals(0) === False)) {
    tofRegs := tofVals
  }

  busCtrl.read(tofRegs(0 until 32), 0x00 + 16)
  busCtrl.read(tofRegs(32 until 64), 0x00 + 20)

}