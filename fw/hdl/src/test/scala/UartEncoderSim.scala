package soc

import spinal.core.Bool
import spinal.core.sim._
import spinal.sim._

import scala.collection.mutable

case class UartEncoderSim(uartPin: Bool, baudPeriod: Long){

  var writeQueue: mutable.Queue[Int] = mutable.Queue[Int]()

  val pFork =  fork {
    uartPin #= true

    while (true) {
      send_queue()
    }
  }

  def send_queue() : Unit@suspendable = {
    if (writeQueue.nonEmpty) {
      val buffer = writeQueue.dequeue()
      uartPin #= false
      sleep(baudPeriod)

      (0 to 7).suspendable.foreach { bitId =>
        uartPin #= ((buffer >> bitId) & 1) != 0
        sleep(baudPeriod)
      }
      uartPin #= true
      sleep(baudPeriod)
    } else {
      sleep(baudPeriod * 10)
    }
  }
}