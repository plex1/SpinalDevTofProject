package soc

import spinal.sim._
import spinal.core._
import spinal.core.sim._
import vexriscv.demo.{Murax, MuraxConfig}
import java.awt.Graphics
import javax.swing.{JFrame, JPanel}

import spinal.lib.com.jtag.sim.JtagTcp
import spinal.lib.com.uart.sim.{UartDecoder, UartEncoder}



object MuraxSim2 {
  def main(args: Array[String]): Unit = {
    //def config = MuraxConfig.default.copy(onChipRamSize = 256 kB)
    //def config = MuraxConfig.default.copy(onChipRamSize = 4 kB, onChipRamHexFile = "src/main/ressource/hex/muraxDemo.hex")
    def config = MuraxConfig.default.copy(onChipRamSize = 4 kB, onChipRamHexFile = "../../sw/example_uart/build/uart.hex")


    SimConfig.allOptimisation.compile(new MuraxSoc(config)).doSimUntilVoid{dut =>
      val mainClkPeriod = (1e12/dut.config.coreFrequency.toDouble).toLong
      val jtagClkPeriod = mainClkPeriod*4
      val uartBaudRate = 115200
      val uartBaudPeriod = (1e12/uartBaudRate).toLong

      val clockDomain = ClockDomain(dut.io.mainClk, dut.io.asyncReset)
      clockDomain.forkStimulus(mainClkPeriod)

      val tcpJtag = JtagTcp(
        jtag = dut.io.jtag,
        jtagClkPeriod = jtagClkPeriod
      )

      val uartTx = UartDecoder(
        uartPin = dut.io.uart.txd,
        baudPeriod = uartBaudPeriod
      )

      val uartRx = UartEncoder(
        uartPin = dut.io.uart.rxd,
        baudPeriod = uartBaudPeriod
      )


      val leds = fork{
        var ledsValue = 0l
        val ledsFrame = new JFrame{
          setContentPane(new DrawPane());
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          setSize(400, 400);
          setVisible(true);

          //create a component that you can actually draw on.
          class DrawPane extends JPanel{
            override def paintComponent(g : Graphics) : Unit = {
              for(i <- 0 to 7) {
                if (((ledsValue >> i) & 1) != 0) {
                  g.fillRect(20*i, 20, 20, 20)
                }
              }
            }
          }
        }

        while(true){
          sleep(mainClkPeriod*100000)
          ledsValue = dut.io.gpioA.write.toLong
          ledsFrame.repaint()
        }
      }
    }
  }
}
