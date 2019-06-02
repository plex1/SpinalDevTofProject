package soc

import spinal.core._
import spinal.lib.bus.amba3.apb.{Apb3, Apb3SlaveFactory}
import spinal.lib.com.jtag.Jtag
import spinal.lib.com.uart.Uart
import spinal.lib.io.TriStateArray
import spinal.lib.{master, slave}
import vexriscv.demo._
import tofperipheral.TofPeripheral

case class MuraxCustom(config : MuraxConfig) extends Component{
  import config._

  val io = new Bundle {
    //Clocks / reset
    val asyncReset = in Bool
    val mainClk = in Bool

    //Main components IO
    val jtag = slave(Jtag())

    //Peripherals IO
    val gpioA = master(TriStateArray(gpioWidth bits))
    val uart = master(Uart())
    val uart2 = master(Uart())

    //Custom Peripheral
    val led = out Bool
    val trigsOut = out Bits(1
      bits)
    val trigsIn = in Bits(2 bits)

    val delay = out Bits(6 bits)

  }

  // Murax Sytem On Chip
  val murrax = new soc.MuraxSoC(config)

  val system = new ClockingArea(murrax.systemClockDomain) {

    // Connect Soc to toplevel
    murrax.io.asyncReset := io.asyncReset
    murrax.io.mainClk := io.mainClk
    murrax.io.jtag <> io.jtag
    murrax.io.gpioA <> io.gpioA
    murrax.io.uart <> io.uart
    murrax.io.uart2 <> io.uart2

    val tof = new TofPeripheral()

    // Connect led to toplevel
    io.led := tof.io.led

    io.trigsOut := tof.io.trigsOut
    tof.io.trigsIn := io.trigsIn
    io.delay := tof.io.delay

    // connect apb
    tof.io.apb <> murrax.io.apbExternal

  }

}

object MuraxCustom{
  def main(args: Array[String]) {
    val config = SpinalConfig()
    config.generateVerilog({
      val toplevel = new MuraxCustom(MuraxConfig.default)
      toplevel
    })
    println("DONE")
  }
}

