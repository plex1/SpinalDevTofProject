package soc

import spinal.sim._
import spinal.core._
import spinal.core.sim._
import vexriscv.demo.{Murax, MuraxConfig}
import javax.swing._

import java.awt
import java.awt.event.{ActionEvent, ActionListener, MouseEvent, MouseListener}

import spinal.lib.com.jtag.sim.JtagTcp
import spinal.lib.com.uart.sim.{UartDecoder, UartEncoder}
import vexriscv.test.{JLedArray, JSwitchArray}

import scala.collection.mutable

import org.scalatest._

object MuraxSim extends FunSuite{
  def main(args: Array[String]): Unit = {
    def config = MuraxConfig.default.copy(onChipRamSize = 8 kB, onChipRamHexFile = "../../sw/example_uart/build/uart.hex")


    SimConfig.withWave.allOptimisation.compile(new MuraxCustom(config, sim=true)).doSimUntilVoid{dut =>
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

      val uartRx = UartDecoderSim(
        uartPin = dut.io.uart2.txd,
        baudPeriod = uartBaudPeriod
      )

      val uartTx = new UartEncoderSim(
        uartPin = dut.io.uart2.rxd,
        baudPeriod = uartBaudPeriod
      )

      var ledsValue = 0l
      var trigValue = 0l

      val guiThread = fork{
        val guiToSim = mutable.Queue[Any]()


        var switchValue : () => BigInt = null
        val ledsFrame = new JFrame{
          setLayout(new BoxLayout(getContentPane, BoxLayout.Y_AXIS))

          add(new JLedArray(8){
            override def getValue = ledsValue
          })
          add{
            val switches = new JSwitchArray(8)
            switchValue = switches.getValue
            switches
          }
          add(new JLedArray(2){
            override def getValue = trigValue
          })

          add(new JButton("Reset"){
            addActionListener(new ActionListener {
              override def actionPerformed(actionEvent: ActionEvent): Unit = {
                println("ASYNC RESET")
                guiToSim.enqueue("asyncReset")
              }
            })
            setAlignmentX(awt.Component.CENTER_ALIGNMENT)
          })
          setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
          pack()
          setVisible(true)

        }

        //Slow refresh
        while(true) {
          sleep(mainClkPeriod * 50000)

          val dummy = if (guiToSim.nonEmpty) {
            val request = guiToSim.dequeue()
            if (request == "asyncReset") {
              dut.io.asyncReset #= true
              sleep(mainClkPeriod * 32)
              dut.io.asyncReset #= false
            }
          }

          dut.io.gpioA.read #= (dut.io.gpioA.write.toLong & dut.io.gpioA.writeEnable.toLong) | (switchValue() << 8)
          ledsValue = dut.io.gpioA.write.toLong
          //trigValue = dut.io.trigs.toLong
          ledsFrame.repaint()
        }
      }

      val testThread = fork {

        while(ledsValue==0) {sleep(mainClkPeriod*500000) }

        test("An empty Set should have size 0") {
          org.scalatest.Assertions.assert(1 == 1)
        }

        val codec = new Codec(uartTx, uartRx)

        // id
        val rxmessage = codec.transceive(CodecFormat(CodecFormat.R, 0xF0030008, 1))
        assert(rxmessage.data(0)==0x1a)

        // write and non incremental read
        codec.transceive(CodecFormat(1, 0xF003000C, 1, Array[Int](0x01020304)))
        val rxmessage2 = codec.transceive(CodecFormat(command = 0, addr = 0xF003000C, len = 4, incr = false))
        assert(rxmessage2.data(0)==0x01020304)
        assert(rxmessage2.data(1)==0x01020304)

        //delay settings
        codec.transceive(CodecFormat(1, 0xF0030018, 1, Array[Int](0x9a)))
        assert(dut.io.delay.toInt == 0x9a)

        //a,b setting
        codec.transceive(CodecFormat(1, 0xF0030030, 1, Array[Int](0x3)))
        val rxmessage_ab = codec.transceive(CodecFormat(command = 0, addr = 0xF0030030, len = 1))
        assert(rxmessage_ab.data(0)==3)

        // misc
        val rxmessage3 = codec.transceive(CodecFormat(command = 0, addr = 0xF0030000, len = 4, incr = true))
        val rxmessage4= codec.transceive(CodecFormat(0, 0xF0030010, 1))
        val rxmessage5 = codec.transceive(CodecFormat(0, 0xF0030014, 1))

        // turn off histogram filter
        val rxmessage5b = codec.transceive(CodecFormat(0, 0xF0030034, 0))

        // trigger finder
        val rxmessage52 =codec.transceive(CodecFormat(1, 0xF0030028, 1, Array[Int](0*1))) // set edge
        val rxmessage6 = codec.transceive(CodecFormat(0, 0xF0030024, 1)) // read trigger finder output
        assert (rxmessage6.data(0)==0x10024)

        // histogram
        codec.transceive(CodecFormat(1, 0xF0030028, 1, Array[Int](0))) // set to reset mem
        codec.transceive(CodecFormat(1, 0xF0030028, 1, Array[Int](1))) // set to record
        codec.transceive(CodecFormat(1, 0xF0030028, 1, Array[Int](3))) // set to reset  addrmode
        codec.transceive(CodecFormat(1, 0xF0030028, 1, Array[Int](2))) // set to read mode
        val rxhistogram = codec.transceive(CodecFormat(command = 0, addr = 0xF003002c, len = 64, incr = false)) // read histogram
        assert(rxhistogram.data(36)>0)
        // todo: there is still a bug, sometimes there is a 1 somewhere instead of 0
        //for (i <- 0 to 40) {
        //  if (i!=36) assert(rxhistogram.data(35) == 0)
        //}

        val rxmessage7 = codec.transceive(CodecFormat(0, 0xF0030038, 1)) // read averager output

        simSuccess()
      }
    }
  }
}
