package tofperipheral

import spinal.core._

class TriggerFinder(numElements: Int) extends Component{

  val io = new Bundle {
    val pattern= in Bits(numElements bits)
    val edge = in Bool
    val trigStateBottom = out Bool
    val trigStateTop = out Bool
    val trigFound = out Bool
    val trigPosition = out UInt(log2Up(numElements) bits)
  }

  io.trigStateBottom := io.pattern(0)
  io.trigStateTop := !io.pattern(numElements-1)
  io.trigPosition := 0
  if (numElements == 2) {
    io.trigFound := False
    when (io.pattern(0) === io.pattern(1) && io.pattern(0) === io.edge) {
      io.trigFound := True
      io.trigPosition := 1
    }
  } else {
    val tf1 = new TriggerFinder(numElements/2)
    val tf2 = new TriggerFinder(numElements/2)
    tf1.io.edge := io.edge
    tf2.io.edge := io.edge
    tf1.io.pattern := io.pattern(0 until numElements/2)
    tf2.io.pattern := io.pattern(numElements/2 until numElements)
    io.trigFound := tf1.io.trigFound || tf2.io.trigFound
    when (tf1.io.trigFound) {
      io.trigPosition := tf1.io.trigPosition.resized
    } elsewhen tf2.io.trigFound {
      io.trigPosition := tf2.io.trigPosition.resized
      io.trigPosition(log2Up(numElements/2)) := True //+ numElements/2
    } elsewhen (tf1.io.trigStateTop =/= tf2.io.trigStateBottom && tf1.io.trigStateTop=== io.edge) {
      io.trigFound := True
      io.trigPosition := numElements/2
    }
  }


}
