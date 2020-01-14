package tofperipheral

import spinal.sim._
import spinal.core._
import spinal.core.sim._

import org.scalatest.FunSuite

class TriggerFinderSim extends FunSuite {

  // RTL to simulate
  val numTaps = 32
  val compiledRTL = SimConfig.compile(new TriggerFinder(numTaps))


  /**
    * Test 1
    */
  test("TriggerFinder") {

    compiledRTL.doSim { dut =>

      dut.io.edge #= true
      dut.io.pattern #= BigInt("10101010101010101010101010101011", 2)
      sleep(1)
      println(dut.io.trigPosition.toInt)
      println(dut.io.trigFound.toBoolean)
      assert(dut.io.trigFound.toBoolean == true)
      assert(dut.io.trigPosition.toInt == 1)
      dut.io.edge #= false
      dut.io.pattern #= BigInt("01010101010101010101010101010100", 2)
      sleep(1)
      assert(dut.io.trigFound.toBoolean == true)
      assert(dut.io.trigPosition.toInt == 1)
      dut.io.pattern #= BigInt("01010101010101010101010101010110", 2)
      sleep(1)
      assert(dut.io.trigFound.toBoolean == true)
      assert(dut.io.trigPosition.toInt == 2)
      dut.io.pattern #= BigInt("01010101010101010101010101010010", 2)
      sleep(1)
      assert(dut.io.trigFound.toBoolean == true)
      assert(dut.io.trigPosition.toInt == 3)
      dut.io.pattern #= BigInt("01010101010100101010101010101010", 2)
      sleep(1)
      assert(dut.io.trigFound.toBoolean == true)
      assert(dut.io.trigPosition.toInt == 19)
      dut.io.pattern #= BigInt("01010101010110101010101010101010", 2)
      sleep(1)
      assert(dut.io.trigFound.toBoolean == true)
      assert(dut.io.trigPosition.toInt == 20)
    }
  }
}


