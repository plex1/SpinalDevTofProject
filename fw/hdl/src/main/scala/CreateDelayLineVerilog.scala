package tofperipheral

import java.io._

object CreateDelayLineVerilog {

  // see SiliconBlue, ICE Technology Library for definition of primitives
  // constraints for desired behaviour with yosys:
  //  - carry chain which has no inputs especially if its just forwarding the CI it will be optimized away.
  //    See  yosys/techlibs/ice40/ice40_opt.cc
  //  - Carries only pack with luts if the two carry inputs (I0 and I1) are also present on the lut inputs
  //     See nextpnr/ice40/pack.cc pack_carries
  // Issues with nextpnr:
  //  -for carry and reg and stages>16, nextpnr yields legalizing error if constraints are activated, seems distance in
  //   placement algorithm never goes below legalise_dia=4 and thus never performing the legalisation step
  //    See nextpnr/common/placer1.c -> if (diameter < legalise_dia && require_legal) {..
  def create(filename : String, stages: Int, add_reg: Boolean = false, carry : Boolean = false, constr : Boolean = true) = {

    val pw = new PrintWriter(new File(filename ))
    val ident = "    "

    pw.write("//automatically created by CreateDelineLineVerilog.scala\n")

    pw.write("(* keep *)\n")
    pw.write("module DelayLine #(parameter num_elements = "+stages.toString+") (\n")
    pw.write(ident+"input clk,\n")
    pw.write(ident+"input in_signal,\n")
    pw.write(ident+"input a, // a needs to be set to 1\n")
    pw.write(ident+"input b, // b needs to be set to 0\n")
    pw.write(ident+"output [num_elements-1:0]delay_value);\n")
    pw.write("\n")
    //pw.write(ident+"(* keep *) wire a;\n")
    //pw.write(ident+"(* keep *) wire b;\n")
    //pw.write(ident+"assign a=1;\n")
    //pw.write(ident+"assign b=0;\n")
    pw.write(ident+"wire [num_elements-1:0] x;\n")
    pw.write(ident+"wire [num_elements-1:0] y;\n")
    pw.write(ident+"wire [num_elements-1:0] z;\n")
    pw.write(ident+"assign x[0] = in_signal;\n")
    pw.write("\n")

    val cellsPerTile = 8
    for (j <- 0 until stages/8) {
      for (i <- 0 until cellsPerTile) {
        if (j!=stages/8-1 || i!=cellsPerTile-1) {
          val n = j*8+i

          if (!carry) {
            if (constr) {pw.write(ident + "(* keep=\"true\"  *)\n"+ident+"(* BEL=\"X2/Y" + (j + 1).toString + "/lc" + i.toString + "\" *)\n")}
            pw.write(ident + "SB_LUT4 #(.LUT_INIT(1)) lut_i" + n.toString + "(.I0(x[" + n.toString + "]), .I1(), .I2(), .I3(), .O(x[" + (n + 1).toString + "]));\n")
            pw.write(ident + "assign y["+(n+1).toString+"] = x["+(n+1).toString+"];\n")
            pw.write("\n")

          } else{
            if (constr) {pw.write(ident + "(* keep=\"true\"  *)\n"+ident+"(* BEL=\"X2/Y" + (j + 1).toString + "/lc" + i.toString + "\" *)\n")}
            pw.write(ident + "SB_CARRY carry_i" + n.toString + "(.CO(x[" + (n + 1).toString+"]), .I0(a), .I1(b), .CI(x[" + n.toString + "]));\n")
            //pw.write(ident + "SB_CARRY carry_i" + n.toString + "(.CO(x[" + (n + 1).toString+"]), .I0(1), .I1(0), .CI(x[" + n.toString + "]));\n")
            pw.write("\n")
            if (constr) {pw.write(ident + "(* keep=\"true\"  *)\n"+ident+"(* BEL=\"X2/Y" + (j + 1).toString + "/lc" + i.toString + "\" *)\n")}
            //pw.write(ident + "SB_LUT4 #(.LUT_INIT(2048)) lut_i" + n.toString + "(.I0(1), .I1(1), .I2(0), .I3(x[" + n.toString + "]), .O(y[" + (n + 1).toString + "]));\n")
            pw.write(ident + "SB_LUT4 #(.LUT_INIT(2048)) lut_i" + n.toString + "(.I0(1), .I1(a), .I2(b), .I3(x[" + n.toString + "]), .O(y[" + (n + 1).toString + "]));\n")

            pw.write("\n")
          }

          if (!add_reg) {
            pw.write(ident + "assign z["+(n+1).toString+"] = y["+(n+1).toString+"];\n")
          }
          else {
            val col = if (carry) 2 else 3
            if (constr) {pw.write(ident+"(* keep=\"true\"  *)\n"+ident+"(* BEL=\"X"+col.toString+"/Y"+(j+1).toString+"/lc" + i.toString + "\" *)\n")}
            pw.write(ident + "SB_DFF reg_i"+n.toString +"(.Q(z["+(n+1).toString+"]), .C(clk), .D(y["+(n+1).toString+"]));\n")
            pw.write("\n")
          }
          pw.write("\n")
        }
      }
    }
    pw.write("\n")

    pw.write(ident + "assign y[0] = x[0];\n")
    pw.write(ident + "assign z[0] = y[0];\n")

    pw.write(ident + "assign delay_value = z;\n")


    pw.write("\n")
    pw.write("endmodule")

    pw.close()
    print("written file: " + filename + "\n")
  }

  def main(args: Array[String]): Unit = {
    def filename = "src/main/verilog/DelayLine3.v"
    create(filename, 128, true, true)
  }
}