package tofperipheral

import spinal.core._
import spinal.lib.bus.amba3.apb.{Apb3, Apb3SlaveFactory}
import spinal.lib.slave // custom apb peripheral

class TofPeripheral (sim : Boolean = false) extends Component {
  val io = new Bundle {
    val apb = slave(Apb3(
      addressWidth = 8,
      dataWidth = 32
    ))
    val led = out Bool
    val led2 = out Bool
    val trigsOut = out Bits(1 bits)
    val trigsIn = in Bits(2 bits)
    val delay = out Bits(8 bits)
  }
  io.led2 := False
  val busCtrl = Apb3SlaveFactory(io.apb)
  io.led := (busCtrl.createReadAndWrite(Bits(1 bits), 0x00, 0, "this is the led2") init (0)) === B"1"
  io.led2.setWhen(busCtrl.isWriting(0x00 + 4))
  busCtrl.read(U"h1a", 0x00 + 8)
  busCtrl.createReadAndWrite(Bits(32 bits), 0x00+12, 0, "this is a test register") init (0)
  io.delay := (busCtrl.createReadAndWrite(Bits(io.delay.getWidth bits), 0x18, 0, "delay chip setting") init (0))


   // when this happens: [error] COMBINATORIAL LOOP :
  //right, basicaly, the combinatorial loop analysis threat the whole signal as one
  //  If you want, you can disable the combinatorial loop check on that specific signal
  //val a = Bits(2 bits).noCombLoopCheck
  //Or
  //Instead using a Bits, use a Vec(Bool, 2)


  var tofLen = 64

  val tofRegs = Reg(Bits(tofLen bits)) init(0)

  val trig = Bool

  trig := io.trigsIn(0) || io.trigsIn(1)

  // delayline
  val dl = if (sim) new DelayLineSim(tofLen)
  else new DelayLineSim(tofLen)

  dl.io.in_signal := trig
  tofRegs := dl.io.delay_value

  busCtrl.read(tofRegs(0 until 32), 0x10)

   // implement  test trigger counter
  val trigTestPeriod = Reg(UInt(5 bits)) init(20)
  busCtrl.readAndWrite(trigTestPeriod, 0x1c, 0,"trig test period")
  val trigTestCounter = Reg(UInt(5 bits))
  val trigTestValue = Reg(Bool)
  trigTestCounter := trigTestCounter + 1
  when (trigTestCounter >= trigTestPeriod) {
    trigTestCounter := 0
    trigTestValue := !trigTestValue
  }
  io.trigsOut(0) := trigTestValue


  // trigger finder
  val tf = new TriggerFinder(tofLen)
  val tfRegs = Reg(Bits(tofLen bits))
  tfRegs := tofRegs
  tf.io.pattern := tfRegs
  busCtrl.driveAndRead(tf.io.edge,0x28,4)
  //tf.io.edge := False
  busCtrl.read(tf.io.trigPosition, 0x24, 0)
  busCtrl.read(tf.io.trigFound, 0x24, 16)

  // histogram
  val hist = new Histogram(tofLen,32)

  // input
  hist.io.values.payload := tf.io.trigPosition.resized
  hist.io.values.valid := tf.io.trigFound
  // set mode
  busCtrl.driveAndRead(hist.io.mode,0x28,0)
  //read
  busCtrl.read(hist.io.readValues, 0x2c, 0)
  hist.io.readValid := False
  busCtrl.onRead(0x2c) {hist.io.readValid := True}

  busCtrl.printDataModel()

}