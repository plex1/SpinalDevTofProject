package CsrProcessing

import org.json4s._
import org.json4s.jackson.Serialization  // alternative circe
//import org.json4s.jackson.Serialization.read


import java.io.{File, PrintWriter}

import spinal.lib.bus.amba3.apb.Apb3SlaveFactory
import spinal.lib.bus.misc.BusSlaveFactoryRead
import spinal.lib.bus.misc._

import scala.collection.mutable.ListBuffer

case class Access(read: Boolean, write: Boolean) {
  override def toString(): String = {
    if (read && !write) "ro"
    else if (!read && write) "wo"
    else if (read && write) "rw"
    else ""
  }

  def orCombine(other: Access): Access = {
    Access(this.read || other.read, this.write || other.write)
  }
}


case class Field(bitOffset: Int, bitWidth: Int, name: String, description: String, access: Access, comment: String = "")

case class Register(address: BigInt, name: String, fields : List[Field], access: Access)

case class CsrDefinition(name: String, description: String, offset: BigInt = 0, registers: List[Register])

abstract class CsrStrings(){
  def addHeaderString(builder: StringBuilder, name: String, description: String)
  def addRegisterStartString(builder: StringBuilder, register: Register)
  def addRegisterEndString(builder: StringBuilder, register: Register)
  def addFieldString(builder: StringBuilder, field: Field)
  def addFooterString(builder: StringBuilder)
  def addFieldsStartString(builder: StringBuilder, register: Register)
  def addFieldsEndString(builder: StringBuilder, register: Register)
}

case class CsrChebyStrings(reg_width : Int) extends CsrStrings{

  def addHeaderString(builder: StringBuilder, name: String = "None", description: String = "None") = {
    builder ++= s"#  Configuration and status register definitions for ${name}\n"
    builder ++= s"memory-map:\n"
    builder ++= s" bus: wb-32-be\n"
    builder ++= s" name: ${name}\n"
    builder ++= s" description: ${'"'}${description}${'"'}\n"
    builder ++= s" children:\n"

  }

  def addRegisterStartString(builder: StringBuilder, register: Register): Unit ={
    builder ++= s"  - reg:\n"
    builder ++= s"      name: ${register.name}\n"
    builder ++= s"      #adddress: ${register.address}\n"
    builder ++= s"      width: ${reg_width}\n"
    builder ++= s"      access: ${register.access}\n"

  }

  def addRegisterEndString(builder: StringBuilder, register: Register): Unit ={}

  def addFieldsStartString(builder: StringBuilder, register: Register): Unit = {
    builder ++= s"      children:\n"
  }

  def addFieldsEndString(builder: StringBuilder, register: Register): Unit = {}

  def addFieldString(builder: StringBuilder, field: Field): Unit = {
    builder ++= s"        - field:\n"
    builder ++= s"            name: ${field.name}\n"
    builder ++= s"            description: ${'"'}${field.description}${'"'}\n"
    builder ++= s"            #offset: ${field.bitOffset}\n"
    val range = if (field.bitWidth==1) s"${field.bitOffset}" else
      s"${field.bitWidth+field.bitOffset-1}-${field.bitOffset}"
    builder ++= s"            range: ${range}\n"
    builder ++= s"            #access: ${field.access}\n"
    if (field.comment != "") {
      builder ++= s"            comment: ${'"'}${field.comment}${'"'}\n"
    }
  }

  def addFooterString(builder: StringBuilder){
    builder ++="#  Configuration and status register definitions end\n"
  }
}

case class CsrProcessingConfig(name: String = "None", description: String = "None", offset: BigInt = 0, addr_inc: Int = 4,
                               fill_gaps: Boolean = true, nofield_keep : Boolean = false, add_nodocu: Boolean = true)

class CsrProcessing(val busCtrl: BusSlaveFactoryDelayed,config : CsrProcessingConfig) {

  val reg_width = 32
  var outputFormat : String = "json"

  def addRegisterString(csrStrings: CsrStrings, builder: StringBuilder, register: Register): Unit = {

    csrStrings.addRegisterStartString(builder, register)

    if (!register.fields.isEmpty || config.nofield_keep) csrStrings.addFieldsStartString(builder, register)

    // add fields
    for (field <- register.fields) {
      // add field
      csrStrings.addFieldString(builder, field)
    }

    if (!register.fields.isEmpty || config.nofield_keep) csrStrings.addFieldsEndString(builder, register)
    csrStrings.addRegisterEndString(builder, register)
  }


  def csrFileString(): String = {
    val builder = new StringBuilder()
    val csrStrings = new CsrChebyStrings(reg_width)

    csrStrings.addHeaderString(builder, config.name , config.description)

    var registers = new ListBuffer[Register]()

    case class DocumentationParts(RegName : String, FieldName: String, FieldDescription: String, FieldComment: String = "")

    def extract_docu(documentation: String) : Option[DocumentationParts] = {
      if (documentation != null) {
        val b = documentation.split("-").map(_.trim)
        if (b != null && b.length>=3) {
          if (b.length==3)
            Some(DocumentationParts(b(0), b(1), b(2)))
          else
            Some(DocumentationParts(b(0), b(1), b(2), b(3)))
        } else None
      } else None
    }

    var current_addr = 0
    val regs = (for ((address, jobs) <- busCtrl.elementsPerAddress.toList.sortBy(_._1.lowerBound)) yield {

      var regName : String = ""

      // convert to list of Field
      val fields = (for (job <- jobs.filter(j => j.isInstanceOf[BusSlaveFactoryRead] || j.isInstanceOf[BusSlaveFactoryWrite]))
        yield job match {
          case job: BusSlaveFactoryRead => {
            extract_docu(job.documentation) match {
              case Some(docu) => {
                if (docu.RegName.length()>0) regName = docu.RegName
                Some(Field(job.bitOffset, job.that.getBitsWidth, docu.FieldName, docu.FieldDescription, Access(true, false), docu.FieldComment))
              }
              case None =>
                if (config.add_nodocu) Some(Field(job.bitOffset, job.that.getBitsWidth,
                  if (job.that.getName()=="") s"NoFieldName${job.bitOffset}" else job.that.getName(), job.documentation, Access(true, false)))
                else None
            }
          }
          case job: BusSlaveFactoryWrite => {
            extract_docu(job.documentation) match {
              case Some(docu) => {
                if (docu.RegName.length()>0) regName = docu.RegName
                Some(Field(job.bitOffset, job.that.getBitsWidth, docu.FieldName, docu.FieldDescription, Access(true, true), docu.FieldComment))
              }
              case None =>
                if (config.add_nodocu) Some(Field(job.bitOffset, job.that.getBitsWidth,
                  if (job.that.getName()=="") s"NoFieldName${job.bitOffset}" else job.that.getName(), job.documentation, Access(true, true)))
                else None
            }
          }
          case _ => None
        }).toList.flatten

      // combine read and write fields
      val fields_comb = fields.groupBy(_.bitOffset).map{
        case (k,v) =>
          if (v.size > 1)
            v(0).copy(access = (v(0).access orCombine v(1).access))// Access(v(0).access.read || v(1).access.read, v(0).access.write || v(1).access.write))
          else v(0)
      }.toList.sortBy(_.bitOffset)


      if (fields_comb.nonEmpty) {
        // find a valid register name if there is none
        if (regName == "") regName = fields_comb(0).name
        if (regName == "NoFieldName" || regName == "" || (regName contains "NoFieldName")) regName = s"NoRegName${address.lowerBound.toInt}"

        // fill gaps between registers with dummy registers (required for cheby format)
        if (config.fill_gaps) {
          val addr_diff = address.lowerBound.toInt - current_addr
          if (addr_diff > config.addr_inc) {
            for (i <- 1 to (addr_diff - config.addr_inc) / config.addr_inc) {
              addRegisterString(csrStrings, builder, Register(current_addr + i * config.addr_inc,
                s"Reserved${current_addr + i * config.addr_inc}",
                List[Field](), Access(true, false)))
              //List(Field(0, reg_width, "Reserved", "Reserved", Access(false, false))), Access(false, false)))
            }
          }
        }
        current_addr = address.lowerBound.toInt
        addRegisterString(csrStrings, builder, Register(current_addr, regName, fields_comb, fields_comb(0).access))
      }

      csrStrings.addFooterString(builder)

      if (fields_comb.nonEmpty)
        Some(Register(address.lowerBound, regName, fields_comb,
        if(fields_comb.nonEmpty)
          (fields_comb.map(_.access).reduceLeft(_ orCombine _))
        else Access(true, false)))
      else
        None

    }).flatten



    val csrDefintion = CsrDefinition(config.name, config.description, config.offset, regs)

    if (outputFormat == "json")
    {
      //json
      implicit val formats = DefaultFormats
      val json_string = Serialization.writePretty(csrDefintion)

      json_string
    } else if (outputFormat == "cheby")
    {
      //cheby
      builder.toString
    } else {"format not defined"}

  }

  def toCsrFile(filename:String): Unit ={
    val pw = new PrintWriter(new File(filename ))
    pw.write(csrFileString)
    pw.close()
  }
}
