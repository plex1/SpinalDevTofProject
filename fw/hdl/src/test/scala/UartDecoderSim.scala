package soc

import spinal.core.sim._
import spinal.core.{Bool, assert}
import spinal.sim._

import scala.collection.mutable


case class UartDecoderSim(uartPin : Bool, baudPeriod : Long) {

  var readQueue: mutable.Queue[Int] = mutable.Queue[Int]()

  var pFork = fork {
      sleep(1) //Wait boot signals propagation
      waitUntil(uartPin.toBoolean == true)
      while (true) {
        fill_queue()
      }
    }

  def fill_queue() : Unit@suspendable = {
    waitUntil(uartPin.toBoolean == false)
    sleep(baudPeriod/2)

    assert(uartPin.toBoolean == false)
    sleep(baudPeriod)

    var buffer = 0
    (0 to 7).suspendable.foreach{ bitId =>
      if(uartPin.toBoolean)
        buffer |= 1 << bitId
      sleep(baudPeriod)
    }

    assert(uartPin.toBoolean == true)
    readQueue.enqueue(buffer)
  }
}