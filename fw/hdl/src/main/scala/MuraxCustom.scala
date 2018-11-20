package soc

import spinal.core._
import spinal.lib.bus.amba3.apb.{Apb3, Apb3SlaveFactory}
import spinal.lib.com.jtag.Jtag
import spinal.lib.com.uart.Uart
import spinal.lib.io.TriStateArray
import spinal.lib.{master, slave}
import vexriscv.demo._
import ledperipherals.TwoLedPeripheral

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

    //Custom Peripheral
    val led = out Bool
  }

  // Murax Sytem On Chip
  val murrax = new MuraxSoc(config)

  val system = new ClockingArea(murrax.systemClockDomain) {

    // Connect Soc to toplevel
    murrax.io.asyncReset := io.asyncReset
    murrax.io.mainClk := io.mainClk
    murrax.io.jtag <> io.jtag
    murrax.io.gpioA <> io.gpioA
    murrax.io.uart <> io.uart

    val twoled = new TwoLedPeripheral()

    // Connect led to toplevel
    io.led := twoled.io.led

    // connect apb
    twoled.io.apb <> murrax.io.apbExternal

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

/*

//Will blink led and echo UART RX to UART TX   (in the verilator sim, type some text and press enter to send UART frame to the Murax RX pin)
object MuraxWithRamInit{
  def main(args: Array[String]) {
    SpinalVerilog(Murax(MuraxConfig.default.copy(onChipRamSize = 4 kB, onChipRamHexFile = "src/main/ressource/hex/muraxDemo.hex")))
  }
}
*/