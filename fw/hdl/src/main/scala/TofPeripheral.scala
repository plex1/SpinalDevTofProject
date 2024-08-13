package tofperipheral

import spinal.core._
import spinal.lib.bus.amba3.apb.{Apb3, Apb3SlaveFactory}
import spinal.lib.bus.misc.{BusSlaveFactoryRead, BusSlaveFactoryWrite}
import spinal.lib.slave // custom apb peripheral
import csrProcessing.CsrProcessing
import csrProcessing.CsrProcessingConfig



class TofPeripheral (sim : Boolean = false) extends Component {
  val io = new Bundle {
    val apb = slave(Apb3(
      addressWidth = 8,
      dataWidth = 32
    ))
    val led = out Bool
    val led2 = out Bool
    val trigsOut = out Bits(1 bits)
    val lockin1Out = out Bool
    val lockin2Out = out Bool
    val trigsIn = in Bits(2 bits)
    val delay = out Bits(8 bits)
    val ro_clk = out Bool
  }

  // some teststs
  io.led2 := False
  val busCtrl = Apb3SlaveFactory(io.apb)
  io.led := (busCtrl.createReadAndWrite(Bits(1 bits), 0x00, 0, "led | led1 | 0: LED 1 off\n1: LED 1 on") init (0)) === B"1"
  io.led2.setWhen(busCtrl.isWriting(0x00 + 4))
  busCtrl.read(U"h1a", 0x00 + 8, 0, "| id | ID of this module (default: 0x1a)")
  busCtrl.createReadAndWrite(Bits(32 bits), 0x00+12, 0, "| test | test regiser for read and write operation| the content of this register is not processed") init (0)

  // delay settings
  io.delay := (busCtrl.createReadAndWrite(Bits(io.delay.getWidth bits), 0x18, 0, "| delay | delay chip configuraiton | 0-255, Step size is 0.25ns") init (0))

  // definitions
  var tofLen = if (sim) 64 else 128
  val tofRegs = Reg(Bits(tofLen bits)) init(0)

  // trigger input
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

  // switch between ring oscillator and external trigger input
  val ro_switch = Bool
  ro_switch := (busCtrl.createReadAndWrite(Bits(1 bits), 0x30, 2,
    "ringOscSetting | ringOscSetting | delay line input selection | 0: from internal ring oscillator\n 1: from external trig input") init (0)).asBool
  dl.io.in_signal := ro_switch ? ro_clk | trig

  tofRegs := dl.io.delay_value
  dl.io.a := (busCtrl.createReadAndWrite(Bits(1 bits), 0x30, 0,"ringOscSetting | delaylineA | delayline a parameter, keep 0") init (0)).asBool
  dl.io.b := (busCtrl.createReadAndWrite(Bits(1 bits), 0x30, 1,"ringOscSetting | delaylineB | delayline b parameter, keep 0") init (0)).asBool
  busCtrl.read(tofRegs(0 until List(32, tofLen).min), 0x10, 0, "| tofReg | First 32 taps of delay line | LSB is first tap in delayline")

   // implement  test trigger counter

  val numBitsSync = 12
  val sq = new Sequencer(numBitsSync)

  val trigTestPeriod = Reg(UInt(numBitsSync bits)) init(20)
  busCtrl.readAndWrite(trigTestPeriod, 0x1c, 0,"| trigTestPeriod | period of the trigger signal output | 0-2**12-1 : step size (LSB) = 25ns")

  busCtrl.driveAndRead(sq.io.lockin_sync1_start, 0x40, 0,"| lockin_sync1_start | start position of Sync1 | 0-2**12-1  : step size (LSB) = 25ns")
  busCtrl.driveAndRead(sq.io.lockin_sync1_end, 0x44, 0,"| lockin_sync1_end | end position of Sync1 | 0-2**12-1  : step size (LSB) = 25ns")
  busCtrl.driveAndRead(sq.io.lockin_sync2_start, 0x48, 0,"| lockin_sync2_start | start position of Sync2 | 0-2**12-1  : step size (LSB) = 25ns")
  busCtrl.driveAndRead(sq.io.lockin_sync2_end, 0x4c, 0,"| lockin_sync2_end | end position of Sync2  | 0-2**12-1  : step size (LSB) = 25ns")
  busCtrl.driveAndRead(sq.io.trigOn,0x28, 6, "control | trigOn | enable trigger output | 0: Off\n1: On")
  busCtrl.driveAndRead(sq.io.syncOn,0x28, 7, "control | syncOn | enable sync1 and sync2 output | 0: Off\n1: On")
  busCtrl.driveAndRead(sq.io.trig_length, 0x50, 0,"| trig_length | length of trig signal | 0: 50% duty cycle, 1-2**12-1  : step size (LSB) = 25ns")
  busCtrl.driveAndRead(sq.io.trigInvert,0x28, 5, "control | trigInvert | ivnert trigger output | 0: Off\n1: On")

  sq.io.period := trigTestPeriod


  io.trigsOut(0) := sq.io.trig
  io.lockin1Out := sq.io.sync1
  io.lockin2Out := sq.io.sync2

  // trigger finder
  val tf = new TriggerFinder(tofLen)
  val tfRegs = Reg(Bits(tofLen bits))
  tfRegs := tofRegs
  tf.io.pattern := tfRegs
  busCtrl.driveAndRead(tf.io.edge,0x28,4, "control | edge | Set on which edge the trigIn signal is triggered | 0: positive edge\n1: negative edge")
  busCtrl.read(tf.io.trigPosition, 0x24, 0, "trigPosition | position | Position of the trigger in the delay line")
  busCtrl.read(tf.io.trigFound, 0x24, 16, "trigPosition | trigPositionFound | A trigger has been captured | 1: found\n0: not found")

  // filter
  val hf = new HistogramFilter(sq.io.seqeunceCounter.getWidth)
  hf.io.validIn := tf.io.trigFound
  hf.io.internalCount := sq.io.seqeunceCounter
  busCtrl.driveAndRead(hf.io.interestedCount, 0x34, 0,
    "histogramFilter | interestedCout | Select which slot should be gated\nNumber of 25ns periods after the trigger output\n only enabled if filterOn=1")
  busCtrl.driveAndRead(hf.io.filterOn, 0x34, 16,"histogramFilter | filterOn | gating active (see interestedCout)")
  // drive And Read, apparently not initialized to 0
  val reg3 = RegInit(U"0000")

  // histogram
  val hist = new Histogram(tofLen,32)
  hist.io.values.payload := tf.io.trigPosition.resized
  hist.io.values.valid := hf.io.validOut
  busCtrl.driveAndRead(hist.io.mode,0x28,0, "control | histMode | histogram mode | 0: RESET_MEM\n1: RECORD\n2: READ\n3: RESET_ADD")
  busCtrl.read(hist.io.readValues, 0x2c, 0, "| histValues | fifo to read histogram value")

  hist.io.readValid := False
  busCtrl.onRead(0x2c) {hist.io.readValid := True}

  // averager
  val av = new AverageFilter(sq.io.seqeunceCounter.getWidth, 128)
  av.io.valid := tf.io.trigFound
  av.io.dataIn := sq.io.seqeunceCounter
  busCtrl.read(av.io.dataOut, 0x38, 0," | averageFilter | Slot position of the received trigger signal (averaged)")

  // generate documentation output
  val csrp = new CsrProcessing(busCtrl, CsrProcessingConfig("TofPeripheral",
    "Peripheral for measuring time-of-flight with a TDC implemented in FPGA logic",0xF0030000l))
  csrp.writeCsrFile("TofPeripheral.cheby", "cheby")
  csrp.writeCsrFile("TofPeripheral.json", "json_csrp")
  csrp.writeCsrFile("TofPeripheral.yaml", "yaml_csrp")
  csrp.writeCsrFile("TofPeripheral_cheby.json", "json_cheby")
  csrp.writeCsrFile("TofPeripheral_cheby.yaml", "yaml_cheby")


  busCtrl.printDataModel()


  //todo: modify BusSlaveFactory.scala to have a name for each register, per default use the name of the first field
  //todo: add possibility to at manual name for each field,
  //  1) with parameter after documentation, 2) sepearte function to set name of field
  //todo: put csrPythonString in separate package
  //todo: create yaml csr file according to cern cheby specification
  //todo: second possibility is to add register name and field name into the documention with predefined fromat


  // when this happens: [error] COMBINATORIAL LOOP :
  //right, basicaly, the combinatorial loop analysis threat the whole signal as one
  //  If you want, you can disable the combinatorial loop check on that specific signal
  //val a = Bits(2 bits).noCombLoopCheck
  //Or
  //Instead using a Bits, use a Vec(Bool, 2)


}