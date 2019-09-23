package tofperipheral

import spinal.core._
import spinal.lib.bus.amba3.apb.{Apb3, Apb3SlaveFactory}
import spinal.lib.bus.misc.{BusSlaveFactoryRead, BusSlaveFactoryWrite}
import spinal.lib.slave // custom apb peripheral
import CsrProcessing.CsrProcessing
import CsrProcessing.CsrProcessingConfig



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
    val ro_clk = out Bool
  }
  io.led2 := False
  val busCtrl = Apb3SlaveFactory(io.apb)
  io.led := (busCtrl.createReadAndWrite(Bits(1 bits), 0x00, 0, "LED - LED - 1> switch LED on") init (0)) === B"1"
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


  var tofLen = if (sim) 64 else 128 //64

  val tofRegs = Reg(Bits(tofLen bits)) init(0)

  val trig = Bool

  trig := io.trigsIn(0) || io.trigsIn(1)


  // ring oscillator
  val ro_clk = Bool
  if (!sim) {
    val ro = new RingOscillator(5)
    ro_clk := ro.io.clk_out
  } else {
    ro_clk := False
  }
  io.ro_clk := ro_clk

  // delayline
  val dl = if (sim) {new DelayLineSim(tofLen)} else {new DelayLine(tofLen)}

  val ro_switch = Bool
  ro_switch := (busCtrl.createReadAndWrite(Bits(1 bits), 0x30, 2, "ring osc input selection") init (0)).asBool

  dl.io.in_signal := ro_switch ? ro_clk | trig
  tofRegs := dl.io.delay_value
  dl.io.a := (busCtrl.createReadAndWrite(Bits(1 bits), 0x30, 0, "delayline a") init (0)).asBool
  dl.io.b := (busCtrl.createReadAndWrite(Bits(1 bits), 0x30, 1, "delayline b") init (0)).asBool

  busCtrl.read(tofRegs(0 until List(32, tofLen).min), 0x10)

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
  busCtrl.read(tf.io.trigPosition, 0x24, 0)
  busCtrl.read(tf.io.trigFound, 0x24, 16)

  // filter
  val hf = new HistogramFilter(trigTestCounter.getWidth)
  hf.io.validIn := tf.io.trigFound
  hf.io.internalCount := trigTestCounter
  busCtrl.driveAndRead(hf.io.interestedCount, 0x34, 0,"interested cout")
  busCtrl.driveAndRead(hf.io.filterOn, 0x34, 16,"filterOn")

  // histogram
  val hist = new Histogram(tofLen,32)

  // input
  hist.io.values.payload := tf.io.trigPosition.resized
  hist.io.values.valid := hf.io.validOut
  // set mode
  busCtrl.driveAndRead(hist.io.mode,0x28,0)
  //read
  busCtrl.read(hist.io.readValues, 0x2c, 0, "ReadValues - ReadValues - ")
  hist.io.readValid := False
  busCtrl.onRead(0x2c) {hist.io.readValid := True}

  // averager
  val av = new AverageFilter(trigTestCounter.getWidth, 128)
  av.io.valid := tf.io.trigFound
  av.io.dataIn := trigTestCounter
  busCtrl.read(av.io.dataOut, 0x38, 0,"Reg Name - Field Name - Description\\nLine2") //averager


  val csrp = new CsrProcessing(busCtrl, CsrProcessingConfig("TofPeripheral", "Peripheral for measuring time-of-flight with a TDC implemented in FPGA logic"))
  print(csrp.csrFileString())
  csrp.toCsrFile("TofPeripheral.json")
  busCtrl.printDataModel()


  //todo: modify BusSlaveFactory.scala to have a name for each register, per default use the name of the first field
  //todo: add possibility to at manual name for each field,
  //  1) with parameter after documentation, 2) sepearte function to set name of field
  //todo: put csrPythonString in separate package
  //todo: create yaml csr file according to cern cheby specification
  //todo: second possibility is to add register name and field name into the documention with predefined fromat


}