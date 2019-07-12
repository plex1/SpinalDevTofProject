//automatically created by CreateDelineLineVerilog.scala
(* keep *)
module DelayLine #(parameter num_elements = 128) (
    input clk,
    input in_signal,
    input a, // a needs to be set to 1
    input b, // b needs to be set to 0
    output [num_elements-1:0]delay_value);

    wire [num_elements-1:0] x;
    wire [num_elements-1:0] y;
    wire [num_elements-1:0] z;
    assign x[0] = in_signal;

    (* keep="true"  *)
    (* BEL="X2/Y1/lc0" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i0(.I0(x[0]), .I1(), .I2(), .I3(), .O(x[1]));
    assign y[1] = x[1];

    (* keep="true"  *)
    (* BEL="X3/Y1/lc0" *)
    SB_DFF reg_i0(.Q(z[1]), .C(clk), .D(y[1]));


    (* keep="true"  *)
    (* BEL="X2/Y1/lc1" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i1(.I0(x[1]), .I1(), .I2(), .I3(), .O(x[2]));
    assign y[2] = x[2];

    (* keep="true"  *)
    (* BEL="X3/Y1/lc1" *)
    SB_DFF reg_i1(.Q(z[2]), .C(clk), .D(y[2]));


    (* keep="true"  *)
    (* BEL="X2/Y1/lc2" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i2(.I0(x[2]), .I1(), .I2(), .I3(), .O(x[3]));
    assign y[3] = x[3];

    (* keep="true"  *)
    (* BEL="X3/Y1/lc2" *)
    SB_DFF reg_i2(.Q(z[3]), .C(clk), .D(y[3]));


    (* keep="true"  *)
    (* BEL="X2/Y1/lc3" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i3(.I0(x[3]), .I1(), .I2(), .I3(), .O(x[4]));
    assign y[4] = x[4];

    (* keep="true"  *)
    (* BEL="X3/Y1/lc3" *)
    SB_DFF reg_i3(.Q(z[4]), .C(clk), .D(y[4]));


    (* keep="true"  *)
    (* BEL="X2/Y1/lc4" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i4(.I0(x[4]), .I1(), .I2(), .I3(), .O(x[5]));
    assign y[5] = x[5];

    (* keep="true"  *)
    (* BEL="X3/Y1/lc4" *)
    SB_DFF reg_i4(.Q(z[5]), .C(clk), .D(y[5]));


    (* keep="true"  *)
    (* BEL="X2/Y1/lc5" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i5(.I0(x[5]), .I1(), .I2(), .I3(), .O(x[6]));
    assign y[6] = x[6];

    (* keep="true"  *)
    (* BEL="X3/Y1/lc5" *)
    SB_DFF reg_i5(.Q(z[6]), .C(clk), .D(y[6]));


    (* keep="true"  *)
    (* BEL="X2/Y1/lc6" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i6(.I0(x[6]), .I1(), .I2(), .I3(), .O(x[7]));
    assign y[7] = x[7];

    (* keep="true"  *)
    (* BEL="X3/Y1/lc6" *)
    SB_DFF reg_i6(.Q(z[7]), .C(clk), .D(y[7]));


    (* keep="true"  *)
    (* BEL="X2/Y1/lc7" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i7(.I0(x[7]), .I1(), .I2(), .I3(), .O(x[8]));
    assign y[8] = x[8];

    (* keep="true"  *)
    (* BEL="X3/Y1/lc7" *)
    SB_DFF reg_i7(.Q(z[8]), .C(clk), .D(y[8]));


    (* keep="true"  *)
    (* BEL="X2/Y2/lc0" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i8(.I0(x[8]), .I1(), .I2(), .I3(), .O(x[9]));
    assign y[9] = x[9];

    (* keep="true"  *)
    (* BEL="X3/Y2/lc0" *)
    SB_DFF reg_i8(.Q(z[9]), .C(clk), .D(y[9]));


    (* keep="true"  *)
    (* BEL="X2/Y2/lc1" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i9(.I0(x[9]), .I1(), .I2(), .I3(), .O(x[10]));
    assign y[10] = x[10];

    (* keep="true"  *)
    (* BEL="X3/Y2/lc1" *)
    SB_DFF reg_i9(.Q(z[10]), .C(clk), .D(y[10]));


    (* keep="true"  *)
    (* BEL="X2/Y2/lc2" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i10(.I0(x[10]), .I1(), .I2(), .I3(), .O(x[11]));
    assign y[11] = x[11];

    (* keep="true"  *)
    (* BEL="X3/Y2/lc2" *)
    SB_DFF reg_i10(.Q(z[11]), .C(clk), .D(y[11]));


    (* keep="true"  *)
    (* BEL="X2/Y2/lc3" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i11(.I0(x[11]), .I1(), .I2(), .I3(), .O(x[12]));
    assign y[12] = x[12];

    (* keep="true"  *)
    (* BEL="X3/Y2/lc3" *)
    SB_DFF reg_i11(.Q(z[12]), .C(clk), .D(y[12]));


    (* keep="true"  *)
    (* BEL="X2/Y2/lc4" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i12(.I0(x[12]), .I1(), .I2(), .I3(), .O(x[13]));
    assign y[13] = x[13];

    (* keep="true"  *)
    (* BEL="X3/Y2/lc4" *)
    SB_DFF reg_i12(.Q(z[13]), .C(clk), .D(y[13]));


    (* keep="true"  *)
    (* BEL="X2/Y2/lc5" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i13(.I0(x[13]), .I1(), .I2(), .I3(), .O(x[14]));
    assign y[14] = x[14];

    (* keep="true"  *)
    (* BEL="X3/Y2/lc5" *)
    SB_DFF reg_i13(.Q(z[14]), .C(clk), .D(y[14]));


    (* keep="true"  *)
    (* BEL="X2/Y2/lc6" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i14(.I0(x[14]), .I1(), .I2(), .I3(), .O(x[15]));
    assign y[15] = x[15];

    (* keep="true"  *)
    (* BEL="X3/Y2/lc6" *)
    SB_DFF reg_i14(.Q(z[15]), .C(clk), .D(y[15]));


    (* keep="true"  *)
    (* BEL="X2/Y2/lc7" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i15(.I0(x[15]), .I1(), .I2(), .I3(), .O(x[16]));
    assign y[16] = x[16];

    (* keep="true"  *)
    (* BEL="X3/Y2/lc7" *)
    SB_DFF reg_i15(.Q(z[16]), .C(clk), .D(y[16]));


    (* keep="true"  *)
    (* BEL="X2/Y3/lc0" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i16(.I0(x[16]), .I1(), .I2(), .I3(), .O(x[17]));
    assign y[17] = x[17];

    (* keep="true"  *)
    (* BEL="X3/Y3/lc0" *)
    SB_DFF reg_i16(.Q(z[17]), .C(clk), .D(y[17]));


    (* keep="true"  *)
    (* BEL="X2/Y3/lc1" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i17(.I0(x[17]), .I1(), .I2(), .I3(), .O(x[18]));
    assign y[18] = x[18];

    (* keep="true"  *)
    (* BEL="X3/Y3/lc1" *)
    SB_DFF reg_i17(.Q(z[18]), .C(clk), .D(y[18]));


    (* keep="true"  *)
    (* BEL="X2/Y3/lc2" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i18(.I0(x[18]), .I1(), .I2(), .I3(), .O(x[19]));
    assign y[19] = x[19];

    (* keep="true"  *)
    (* BEL="X3/Y3/lc2" *)
    SB_DFF reg_i18(.Q(z[19]), .C(clk), .D(y[19]));


    (* keep="true"  *)
    (* BEL="X2/Y3/lc3" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i19(.I0(x[19]), .I1(), .I2(), .I3(), .O(x[20]));
    assign y[20] = x[20];

    (* keep="true"  *)
    (* BEL="X3/Y3/lc3" *)
    SB_DFF reg_i19(.Q(z[20]), .C(clk), .D(y[20]));


    (* keep="true"  *)
    (* BEL="X2/Y3/lc4" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i20(.I0(x[20]), .I1(), .I2(), .I3(), .O(x[21]));
    assign y[21] = x[21];

    (* keep="true"  *)
    (* BEL="X3/Y3/lc4" *)
    SB_DFF reg_i20(.Q(z[21]), .C(clk), .D(y[21]));


    (* keep="true"  *)
    (* BEL="X2/Y3/lc5" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i21(.I0(x[21]), .I1(), .I2(), .I3(), .O(x[22]));
    assign y[22] = x[22];

    (* keep="true"  *)
    (* BEL="X3/Y3/lc5" *)
    SB_DFF reg_i21(.Q(z[22]), .C(clk), .D(y[22]));


    (* keep="true"  *)
    (* BEL="X2/Y3/lc6" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i22(.I0(x[22]), .I1(), .I2(), .I3(), .O(x[23]));
    assign y[23] = x[23];

    (* keep="true"  *)
    (* BEL="X3/Y3/lc6" *)
    SB_DFF reg_i22(.Q(z[23]), .C(clk), .D(y[23]));


    (* keep="true"  *)
    (* BEL="X2/Y3/lc7" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i23(.I0(x[23]), .I1(), .I2(), .I3(), .O(x[24]));
    assign y[24] = x[24];

    (* keep="true"  *)
    (* BEL="X3/Y3/lc7" *)
    SB_DFF reg_i23(.Q(z[24]), .C(clk), .D(y[24]));


    (* keep="true"  *)
    (* BEL="X2/Y4/lc0" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i24(.I0(x[24]), .I1(), .I2(), .I3(), .O(x[25]));
    assign y[25] = x[25];

    (* keep="true"  *)
    (* BEL="X3/Y4/lc0" *)
    SB_DFF reg_i24(.Q(z[25]), .C(clk), .D(y[25]));


    (* keep="true"  *)
    (* BEL="X2/Y4/lc1" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i25(.I0(x[25]), .I1(), .I2(), .I3(), .O(x[26]));
    assign y[26] = x[26];

    (* keep="true"  *)
    (* BEL="X3/Y4/lc1" *)
    SB_DFF reg_i25(.Q(z[26]), .C(clk), .D(y[26]));


    (* keep="true"  *)
    (* BEL="X2/Y4/lc2" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i26(.I0(x[26]), .I1(), .I2(), .I3(), .O(x[27]));
    assign y[27] = x[27];

    (* keep="true"  *)
    (* BEL="X3/Y4/lc2" *)
    SB_DFF reg_i26(.Q(z[27]), .C(clk), .D(y[27]));


    (* keep="true"  *)
    (* BEL="X2/Y4/lc3" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i27(.I0(x[27]), .I1(), .I2(), .I3(), .O(x[28]));
    assign y[28] = x[28];

    (* keep="true"  *)
    (* BEL="X3/Y4/lc3" *)
    SB_DFF reg_i27(.Q(z[28]), .C(clk), .D(y[28]));


    (* keep="true"  *)
    (* BEL="X2/Y4/lc4" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i28(.I0(x[28]), .I1(), .I2(), .I3(), .O(x[29]));
    assign y[29] = x[29];

    (* keep="true"  *)
    (* BEL="X3/Y4/lc4" *)
    SB_DFF reg_i28(.Q(z[29]), .C(clk), .D(y[29]));


    (* keep="true"  *)
    (* BEL="X2/Y4/lc5" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i29(.I0(x[29]), .I1(), .I2(), .I3(), .O(x[30]));
    assign y[30] = x[30];

    (* keep="true"  *)
    (* BEL="X3/Y4/lc5" *)
    SB_DFF reg_i29(.Q(z[30]), .C(clk), .D(y[30]));


    (* keep="true"  *)
    (* BEL="X2/Y4/lc6" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i30(.I0(x[30]), .I1(), .I2(), .I3(), .O(x[31]));
    assign y[31] = x[31];

    (* keep="true"  *)
    (* BEL="X3/Y4/lc6" *)
    SB_DFF reg_i30(.Q(z[31]), .C(clk), .D(y[31]));


    (* keep="true"  *)
    (* BEL="X2/Y4/lc7" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i31(.I0(x[31]), .I1(), .I2(), .I3(), .O(x[32]));
    assign y[32] = x[32];

    (* keep="true"  *)
    (* BEL="X3/Y4/lc7" *)
    SB_DFF reg_i31(.Q(z[32]), .C(clk), .D(y[32]));


    (* keep="true"  *)
    (* BEL="X2/Y5/lc0" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i32(.I0(x[32]), .I1(), .I2(), .I3(), .O(x[33]));
    assign y[33] = x[33];

    (* keep="true"  *)
    (* BEL="X3/Y5/lc0" *)
    SB_DFF reg_i32(.Q(z[33]), .C(clk), .D(y[33]));


    (* keep="true"  *)
    (* BEL="X2/Y5/lc1" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i33(.I0(x[33]), .I1(), .I2(), .I3(), .O(x[34]));
    assign y[34] = x[34];

    (* keep="true"  *)
    (* BEL="X3/Y5/lc1" *)
    SB_DFF reg_i33(.Q(z[34]), .C(clk), .D(y[34]));


    (* keep="true"  *)
    (* BEL="X2/Y5/lc2" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i34(.I0(x[34]), .I1(), .I2(), .I3(), .O(x[35]));
    assign y[35] = x[35];

    (* keep="true"  *)
    (* BEL="X3/Y5/lc2" *)
    SB_DFF reg_i34(.Q(z[35]), .C(clk), .D(y[35]));


    (* keep="true"  *)
    (* BEL="X2/Y5/lc3" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i35(.I0(x[35]), .I1(), .I2(), .I3(), .O(x[36]));
    assign y[36] = x[36];

    (* keep="true"  *)
    (* BEL="X3/Y5/lc3" *)
    SB_DFF reg_i35(.Q(z[36]), .C(clk), .D(y[36]));


    (* keep="true"  *)
    (* BEL="X2/Y5/lc4" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i36(.I0(x[36]), .I1(), .I2(), .I3(), .O(x[37]));
    assign y[37] = x[37];

    (* keep="true"  *)
    (* BEL="X3/Y5/lc4" *)
    SB_DFF reg_i36(.Q(z[37]), .C(clk), .D(y[37]));


    (* keep="true"  *)
    (* BEL="X2/Y5/lc5" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i37(.I0(x[37]), .I1(), .I2(), .I3(), .O(x[38]));
    assign y[38] = x[38];

    (* keep="true"  *)
    (* BEL="X3/Y5/lc5" *)
    SB_DFF reg_i37(.Q(z[38]), .C(clk), .D(y[38]));


    (* keep="true"  *)
    (* BEL="X2/Y5/lc6" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i38(.I0(x[38]), .I1(), .I2(), .I3(), .O(x[39]));
    assign y[39] = x[39];

    (* keep="true"  *)
    (* BEL="X3/Y5/lc6" *)
    SB_DFF reg_i38(.Q(z[39]), .C(clk), .D(y[39]));


    (* keep="true"  *)
    (* BEL="X2/Y5/lc7" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i39(.I0(x[39]), .I1(), .I2(), .I3(), .O(x[40]));
    assign y[40] = x[40];

    (* keep="true"  *)
    (* BEL="X3/Y5/lc7" *)
    SB_DFF reg_i39(.Q(z[40]), .C(clk), .D(y[40]));


    (* keep="true"  *)
    (* BEL="X2/Y6/lc0" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i40(.I0(x[40]), .I1(), .I2(), .I3(), .O(x[41]));
    assign y[41] = x[41];

    (* keep="true"  *)
    (* BEL="X3/Y6/lc0" *)
    SB_DFF reg_i40(.Q(z[41]), .C(clk), .D(y[41]));


    (* keep="true"  *)
    (* BEL="X2/Y6/lc1" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i41(.I0(x[41]), .I1(), .I2(), .I3(), .O(x[42]));
    assign y[42] = x[42];

    (* keep="true"  *)
    (* BEL="X3/Y6/lc1" *)
    SB_DFF reg_i41(.Q(z[42]), .C(clk), .D(y[42]));


    (* keep="true"  *)
    (* BEL="X2/Y6/lc2" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i42(.I0(x[42]), .I1(), .I2(), .I3(), .O(x[43]));
    assign y[43] = x[43];

    (* keep="true"  *)
    (* BEL="X3/Y6/lc2" *)
    SB_DFF reg_i42(.Q(z[43]), .C(clk), .D(y[43]));


    (* keep="true"  *)
    (* BEL="X2/Y6/lc3" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i43(.I0(x[43]), .I1(), .I2(), .I3(), .O(x[44]));
    assign y[44] = x[44];

    (* keep="true"  *)
    (* BEL="X3/Y6/lc3" *)
    SB_DFF reg_i43(.Q(z[44]), .C(clk), .D(y[44]));


    (* keep="true"  *)
    (* BEL="X2/Y6/lc4" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i44(.I0(x[44]), .I1(), .I2(), .I3(), .O(x[45]));
    assign y[45] = x[45];

    (* keep="true"  *)
    (* BEL="X3/Y6/lc4" *)
    SB_DFF reg_i44(.Q(z[45]), .C(clk), .D(y[45]));


    (* keep="true"  *)
    (* BEL="X2/Y6/lc5" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i45(.I0(x[45]), .I1(), .I2(), .I3(), .O(x[46]));
    assign y[46] = x[46];

    (* keep="true"  *)
    (* BEL="X3/Y6/lc5" *)
    SB_DFF reg_i45(.Q(z[46]), .C(clk), .D(y[46]));


    (* keep="true"  *)
    (* BEL="X2/Y6/lc6" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i46(.I0(x[46]), .I1(), .I2(), .I3(), .O(x[47]));
    assign y[47] = x[47];

    (* keep="true"  *)
    (* BEL="X3/Y6/lc6" *)
    SB_DFF reg_i46(.Q(z[47]), .C(clk), .D(y[47]));


    (* keep="true"  *)
    (* BEL="X2/Y6/lc7" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i47(.I0(x[47]), .I1(), .I2(), .I3(), .O(x[48]));
    assign y[48] = x[48];

    (* keep="true"  *)
    (* BEL="X3/Y6/lc7" *)
    SB_DFF reg_i47(.Q(z[48]), .C(clk), .D(y[48]));


    (* keep="true"  *)
    (* BEL="X2/Y7/lc0" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i48(.I0(x[48]), .I1(), .I2(), .I3(), .O(x[49]));
    assign y[49] = x[49];

    (* keep="true"  *)
    (* BEL="X3/Y7/lc0" *)
    SB_DFF reg_i48(.Q(z[49]), .C(clk), .D(y[49]));


    (* keep="true"  *)
    (* BEL="X2/Y7/lc1" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i49(.I0(x[49]), .I1(), .I2(), .I3(), .O(x[50]));
    assign y[50] = x[50];

    (* keep="true"  *)
    (* BEL="X3/Y7/lc1" *)
    SB_DFF reg_i49(.Q(z[50]), .C(clk), .D(y[50]));


    (* keep="true"  *)
    (* BEL="X2/Y7/lc2" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i50(.I0(x[50]), .I1(), .I2(), .I3(), .O(x[51]));
    assign y[51] = x[51];

    (* keep="true"  *)
    (* BEL="X3/Y7/lc2" *)
    SB_DFF reg_i50(.Q(z[51]), .C(clk), .D(y[51]));


    (* keep="true"  *)
    (* BEL="X2/Y7/lc3" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i51(.I0(x[51]), .I1(), .I2(), .I3(), .O(x[52]));
    assign y[52] = x[52];

    (* keep="true"  *)
    (* BEL="X3/Y7/lc3" *)
    SB_DFF reg_i51(.Q(z[52]), .C(clk), .D(y[52]));


    (* keep="true"  *)
    (* BEL="X2/Y7/lc4" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i52(.I0(x[52]), .I1(), .I2(), .I3(), .O(x[53]));
    assign y[53] = x[53];

    (* keep="true"  *)
    (* BEL="X3/Y7/lc4" *)
    SB_DFF reg_i52(.Q(z[53]), .C(clk), .D(y[53]));


    (* keep="true"  *)
    (* BEL="X2/Y7/lc5" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i53(.I0(x[53]), .I1(), .I2(), .I3(), .O(x[54]));
    assign y[54] = x[54];

    (* keep="true"  *)
    (* BEL="X3/Y7/lc5" *)
    SB_DFF reg_i53(.Q(z[54]), .C(clk), .D(y[54]));


    (* keep="true"  *)
    (* BEL="X2/Y7/lc6" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i54(.I0(x[54]), .I1(), .I2(), .I3(), .O(x[55]));
    assign y[55] = x[55];

    (* keep="true"  *)
    (* BEL="X3/Y7/lc6" *)
    SB_DFF reg_i54(.Q(z[55]), .C(clk), .D(y[55]));


    (* keep="true"  *)
    (* BEL="X2/Y7/lc7" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i55(.I0(x[55]), .I1(), .I2(), .I3(), .O(x[56]));
    assign y[56] = x[56];

    (* keep="true"  *)
    (* BEL="X3/Y7/lc7" *)
    SB_DFF reg_i55(.Q(z[56]), .C(clk), .D(y[56]));


    (* keep="true"  *)
    (* BEL="X2/Y8/lc0" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i56(.I0(x[56]), .I1(), .I2(), .I3(), .O(x[57]));
    assign y[57] = x[57];

    (* keep="true"  *)
    (* BEL="X3/Y8/lc0" *)
    SB_DFF reg_i56(.Q(z[57]), .C(clk), .D(y[57]));


    (* keep="true"  *)
    (* BEL="X2/Y8/lc1" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i57(.I0(x[57]), .I1(), .I2(), .I3(), .O(x[58]));
    assign y[58] = x[58];

    (* keep="true"  *)
    (* BEL="X3/Y8/lc1" *)
    SB_DFF reg_i57(.Q(z[58]), .C(clk), .D(y[58]));


    (* keep="true"  *)
    (* BEL="X2/Y8/lc2" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i58(.I0(x[58]), .I1(), .I2(), .I3(), .O(x[59]));
    assign y[59] = x[59];

    (* keep="true"  *)
    (* BEL="X3/Y8/lc2" *)
    SB_DFF reg_i58(.Q(z[59]), .C(clk), .D(y[59]));


    (* keep="true"  *)
    (* BEL="X2/Y8/lc3" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i59(.I0(x[59]), .I1(), .I2(), .I3(), .O(x[60]));
    assign y[60] = x[60];

    (* keep="true"  *)
    (* BEL="X3/Y8/lc3" *)
    SB_DFF reg_i59(.Q(z[60]), .C(clk), .D(y[60]));


    (* keep="true"  *)
    (* BEL="X2/Y8/lc4" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i60(.I0(x[60]), .I1(), .I2(), .I3(), .O(x[61]));
    assign y[61] = x[61];

    (* keep="true"  *)
    (* BEL="X3/Y8/lc4" *)
    SB_DFF reg_i60(.Q(z[61]), .C(clk), .D(y[61]));


    (* keep="true"  *)
    (* BEL="X2/Y8/lc5" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i61(.I0(x[61]), .I1(), .I2(), .I3(), .O(x[62]));
    assign y[62] = x[62];

    (* keep="true"  *)
    (* BEL="X3/Y8/lc5" *)
    SB_DFF reg_i61(.Q(z[62]), .C(clk), .D(y[62]));


    (* keep="true"  *)
    (* BEL="X2/Y8/lc6" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i62(.I0(x[62]), .I1(), .I2(), .I3(), .O(x[63]));
    assign y[63] = x[63];

    (* keep="true"  *)
    (* BEL="X3/Y8/lc6" *)
    SB_DFF reg_i62(.Q(z[63]), .C(clk), .D(y[63]));


    (* keep="true"  *)
    (* BEL="X2/Y8/lc7" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i63(.I0(x[63]), .I1(), .I2(), .I3(), .O(x[64]));
    assign y[64] = x[64];

    (* keep="true"  *)
    (* BEL="X3/Y8/lc7" *)
    SB_DFF reg_i63(.Q(z[64]), .C(clk), .D(y[64]));


    (* keep="true"  *)
    (* BEL="X2/Y9/lc0" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i64(.I0(x[64]), .I1(), .I2(), .I3(), .O(x[65]));
    assign y[65] = x[65];

    (* keep="true"  *)
    (* BEL="X3/Y9/lc0" *)
    SB_DFF reg_i64(.Q(z[65]), .C(clk), .D(y[65]));


    (* keep="true"  *)
    (* BEL="X2/Y9/lc1" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i65(.I0(x[65]), .I1(), .I2(), .I3(), .O(x[66]));
    assign y[66] = x[66];

    (* keep="true"  *)
    (* BEL="X3/Y9/lc1" *)
    SB_DFF reg_i65(.Q(z[66]), .C(clk), .D(y[66]));


    (* keep="true"  *)
    (* BEL="X2/Y9/lc2" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i66(.I0(x[66]), .I1(), .I2(), .I3(), .O(x[67]));
    assign y[67] = x[67];

    (* keep="true"  *)
    (* BEL="X3/Y9/lc2" *)
    SB_DFF reg_i66(.Q(z[67]), .C(clk), .D(y[67]));


    (* keep="true"  *)
    (* BEL="X2/Y9/lc3" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i67(.I0(x[67]), .I1(), .I2(), .I3(), .O(x[68]));
    assign y[68] = x[68];

    (* keep="true"  *)
    (* BEL="X3/Y9/lc3" *)
    SB_DFF reg_i67(.Q(z[68]), .C(clk), .D(y[68]));


    (* keep="true"  *)
    (* BEL="X2/Y9/lc4" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i68(.I0(x[68]), .I1(), .I2(), .I3(), .O(x[69]));
    assign y[69] = x[69];

    (* keep="true"  *)
    (* BEL="X3/Y9/lc4" *)
    SB_DFF reg_i68(.Q(z[69]), .C(clk), .D(y[69]));


    (* keep="true"  *)
    (* BEL="X2/Y9/lc5" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i69(.I0(x[69]), .I1(), .I2(), .I3(), .O(x[70]));
    assign y[70] = x[70];

    (* keep="true"  *)
    (* BEL="X3/Y9/lc5" *)
    SB_DFF reg_i69(.Q(z[70]), .C(clk), .D(y[70]));


    (* keep="true"  *)
    (* BEL="X2/Y9/lc6" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i70(.I0(x[70]), .I1(), .I2(), .I3(), .O(x[71]));
    assign y[71] = x[71];

    (* keep="true"  *)
    (* BEL="X3/Y9/lc6" *)
    SB_DFF reg_i70(.Q(z[71]), .C(clk), .D(y[71]));


    (* keep="true"  *)
    (* BEL="X2/Y9/lc7" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i71(.I0(x[71]), .I1(), .I2(), .I3(), .O(x[72]));
    assign y[72] = x[72];

    (* keep="true"  *)
    (* BEL="X3/Y9/lc7" *)
    SB_DFF reg_i71(.Q(z[72]), .C(clk), .D(y[72]));


    (* keep="true"  *)
    (* BEL="X2/Y10/lc0" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i72(.I0(x[72]), .I1(), .I2(), .I3(), .O(x[73]));
    assign y[73] = x[73];

    (* keep="true"  *)
    (* BEL="X3/Y10/lc0" *)
    SB_DFF reg_i72(.Q(z[73]), .C(clk), .D(y[73]));


    (* keep="true"  *)
    (* BEL="X2/Y10/lc1" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i73(.I0(x[73]), .I1(), .I2(), .I3(), .O(x[74]));
    assign y[74] = x[74];

    (* keep="true"  *)
    (* BEL="X3/Y10/lc1" *)
    SB_DFF reg_i73(.Q(z[74]), .C(clk), .D(y[74]));


    (* keep="true"  *)
    (* BEL="X2/Y10/lc2" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i74(.I0(x[74]), .I1(), .I2(), .I3(), .O(x[75]));
    assign y[75] = x[75];

    (* keep="true"  *)
    (* BEL="X3/Y10/lc2" *)
    SB_DFF reg_i74(.Q(z[75]), .C(clk), .D(y[75]));


    (* keep="true"  *)
    (* BEL="X2/Y10/lc3" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i75(.I0(x[75]), .I1(), .I2(), .I3(), .O(x[76]));
    assign y[76] = x[76];

    (* keep="true"  *)
    (* BEL="X3/Y10/lc3" *)
    SB_DFF reg_i75(.Q(z[76]), .C(clk), .D(y[76]));


    (* keep="true"  *)
    (* BEL="X2/Y10/lc4" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i76(.I0(x[76]), .I1(), .I2(), .I3(), .O(x[77]));
    assign y[77] = x[77];

    (* keep="true"  *)
    (* BEL="X3/Y10/lc4" *)
    SB_DFF reg_i76(.Q(z[77]), .C(clk), .D(y[77]));


    (* keep="true"  *)
    (* BEL="X2/Y10/lc5" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i77(.I0(x[77]), .I1(), .I2(), .I3(), .O(x[78]));
    assign y[78] = x[78];

    (* keep="true"  *)
    (* BEL="X3/Y10/lc5" *)
    SB_DFF reg_i77(.Q(z[78]), .C(clk), .D(y[78]));


    (* keep="true"  *)
    (* BEL="X2/Y10/lc6" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i78(.I0(x[78]), .I1(), .I2(), .I3(), .O(x[79]));
    assign y[79] = x[79];

    (* keep="true"  *)
    (* BEL="X3/Y10/lc6" *)
    SB_DFF reg_i78(.Q(z[79]), .C(clk), .D(y[79]));


    (* keep="true"  *)
    (* BEL="X2/Y10/lc7" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i79(.I0(x[79]), .I1(), .I2(), .I3(), .O(x[80]));
    assign y[80] = x[80];

    (* keep="true"  *)
    (* BEL="X3/Y10/lc7" *)
    SB_DFF reg_i79(.Q(z[80]), .C(clk), .D(y[80]));


    (* keep="true"  *)
    (* BEL="X2/Y11/lc0" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i80(.I0(x[80]), .I1(), .I2(), .I3(), .O(x[81]));
    assign y[81] = x[81];

    (* keep="true"  *)
    (* BEL="X3/Y11/lc0" *)
    SB_DFF reg_i80(.Q(z[81]), .C(clk), .D(y[81]));


    (* keep="true"  *)
    (* BEL="X2/Y11/lc1" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i81(.I0(x[81]), .I1(), .I2(), .I3(), .O(x[82]));
    assign y[82] = x[82];

    (* keep="true"  *)
    (* BEL="X3/Y11/lc1" *)
    SB_DFF reg_i81(.Q(z[82]), .C(clk), .D(y[82]));


    (* keep="true"  *)
    (* BEL="X2/Y11/lc2" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i82(.I0(x[82]), .I1(), .I2(), .I3(), .O(x[83]));
    assign y[83] = x[83];

    (* keep="true"  *)
    (* BEL="X3/Y11/lc2" *)
    SB_DFF reg_i82(.Q(z[83]), .C(clk), .D(y[83]));


    (* keep="true"  *)
    (* BEL="X2/Y11/lc3" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i83(.I0(x[83]), .I1(), .I2(), .I3(), .O(x[84]));
    assign y[84] = x[84];

    (* keep="true"  *)
    (* BEL="X3/Y11/lc3" *)
    SB_DFF reg_i83(.Q(z[84]), .C(clk), .D(y[84]));


    (* keep="true"  *)
    (* BEL="X2/Y11/lc4" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i84(.I0(x[84]), .I1(), .I2(), .I3(), .O(x[85]));
    assign y[85] = x[85];

    (* keep="true"  *)
    (* BEL="X3/Y11/lc4" *)
    SB_DFF reg_i84(.Q(z[85]), .C(clk), .D(y[85]));


    (* keep="true"  *)
    (* BEL="X2/Y11/lc5" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i85(.I0(x[85]), .I1(), .I2(), .I3(), .O(x[86]));
    assign y[86] = x[86];

    (* keep="true"  *)
    (* BEL="X3/Y11/lc5" *)
    SB_DFF reg_i85(.Q(z[86]), .C(clk), .D(y[86]));


    (* keep="true"  *)
    (* BEL="X2/Y11/lc6" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i86(.I0(x[86]), .I1(), .I2(), .I3(), .O(x[87]));
    assign y[87] = x[87];

    (* keep="true"  *)
    (* BEL="X3/Y11/lc6" *)
    SB_DFF reg_i86(.Q(z[87]), .C(clk), .D(y[87]));


    (* keep="true"  *)
    (* BEL="X2/Y11/lc7" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i87(.I0(x[87]), .I1(), .I2(), .I3(), .O(x[88]));
    assign y[88] = x[88];

    (* keep="true"  *)
    (* BEL="X3/Y11/lc7" *)
    SB_DFF reg_i87(.Q(z[88]), .C(clk), .D(y[88]));


    (* keep="true"  *)
    (* BEL="X2/Y12/lc0" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i88(.I0(x[88]), .I1(), .I2(), .I3(), .O(x[89]));
    assign y[89] = x[89];

    (* keep="true"  *)
    (* BEL="X3/Y12/lc0" *)
    SB_DFF reg_i88(.Q(z[89]), .C(clk), .D(y[89]));


    (* keep="true"  *)
    (* BEL="X2/Y12/lc1" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i89(.I0(x[89]), .I1(), .I2(), .I3(), .O(x[90]));
    assign y[90] = x[90];

    (* keep="true"  *)
    (* BEL="X3/Y12/lc1" *)
    SB_DFF reg_i89(.Q(z[90]), .C(clk), .D(y[90]));


    (* keep="true"  *)
    (* BEL="X2/Y12/lc2" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i90(.I0(x[90]), .I1(), .I2(), .I3(), .O(x[91]));
    assign y[91] = x[91];

    (* keep="true"  *)
    (* BEL="X3/Y12/lc2" *)
    SB_DFF reg_i90(.Q(z[91]), .C(clk), .D(y[91]));


    (* keep="true"  *)
    (* BEL="X2/Y12/lc3" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i91(.I0(x[91]), .I1(), .I2(), .I3(), .O(x[92]));
    assign y[92] = x[92];

    (* keep="true"  *)
    (* BEL="X3/Y12/lc3" *)
    SB_DFF reg_i91(.Q(z[92]), .C(clk), .D(y[92]));


    (* keep="true"  *)
    (* BEL="X2/Y12/lc4" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i92(.I0(x[92]), .I1(), .I2(), .I3(), .O(x[93]));
    assign y[93] = x[93];

    (* keep="true"  *)
    (* BEL="X3/Y12/lc4" *)
    SB_DFF reg_i92(.Q(z[93]), .C(clk), .D(y[93]));


    (* keep="true"  *)
    (* BEL="X2/Y12/lc5" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i93(.I0(x[93]), .I1(), .I2(), .I3(), .O(x[94]));
    assign y[94] = x[94];

    (* keep="true"  *)
    (* BEL="X3/Y12/lc5" *)
    SB_DFF reg_i93(.Q(z[94]), .C(clk), .D(y[94]));


    (* keep="true"  *)
    (* BEL="X2/Y12/lc6" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i94(.I0(x[94]), .I1(), .I2(), .I3(), .O(x[95]));
    assign y[95] = x[95];

    (* keep="true"  *)
    (* BEL="X3/Y12/lc6" *)
    SB_DFF reg_i94(.Q(z[95]), .C(clk), .D(y[95]));


    (* keep="true"  *)
    (* BEL="X2/Y12/lc7" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i95(.I0(x[95]), .I1(), .I2(), .I3(), .O(x[96]));
    assign y[96] = x[96];

    (* keep="true"  *)
    (* BEL="X3/Y12/lc7" *)
    SB_DFF reg_i95(.Q(z[96]), .C(clk), .D(y[96]));


    (* keep="true"  *)
    (* BEL="X2/Y13/lc0" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i96(.I0(x[96]), .I1(), .I2(), .I3(), .O(x[97]));
    assign y[97] = x[97];

    (* keep="true"  *)
    (* BEL="X3/Y13/lc0" *)
    SB_DFF reg_i96(.Q(z[97]), .C(clk), .D(y[97]));


    (* keep="true"  *)
    (* BEL="X2/Y13/lc1" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i97(.I0(x[97]), .I1(), .I2(), .I3(), .O(x[98]));
    assign y[98] = x[98];

    (* keep="true"  *)
    (* BEL="X3/Y13/lc1" *)
    SB_DFF reg_i97(.Q(z[98]), .C(clk), .D(y[98]));


    (* keep="true"  *)
    (* BEL="X2/Y13/lc2" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i98(.I0(x[98]), .I1(), .I2(), .I3(), .O(x[99]));
    assign y[99] = x[99];

    (* keep="true"  *)
    (* BEL="X3/Y13/lc2" *)
    SB_DFF reg_i98(.Q(z[99]), .C(clk), .D(y[99]));


    (* keep="true"  *)
    (* BEL="X2/Y13/lc3" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i99(.I0(x[99]), .I1(), .I2(), .I3(), .O(x[100]));
    assign y[100] = x[100];

    (* keep="true"  *)
    (* BEL="X3/Y13/lc3" *)
    SB_DFF reg_i99(.Q(z[100]), .C(clk), .D(y[100]));


    (* keep="true"  *)
    (* BEL="X2/Y13/lc4" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i100(.I0(x[100]), .I1(), .I2(), .I3(), .O(x[101]));
    assign y[101] = x[101];

    (* keep="true"  *)
    (* BEL="X3/Y13/lc4" *)
    SB_DFF reg_i100(.Q(z[101]), .C(clk), .D(y[101]));


    (* keep="true"  *)
    (* BEL="X2/Y13/lc5" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i101(.I0(x[101]), .I1(), .I2(), .I3(), .O(x[102]));
    assign y[102] = x[102];

    (* keep="true"  *)
    (* BEL="X3/Y13/lc5" *)
    SB_DFF reg_i101(.Q(z[102]), .C(clk), .D(y[102]));


    (* keep="true"  *)
    (* BEL="X2/Y13/lc6" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i102(.I0(x[102]), .I1(), .I2(), .I3(), .O(x[103]));
    assign y[103] = x[103];

    (* keep="true"  *)
    (* BEL="X3/Y13/lc6" *)
    SB_DFF reg_i102(.Q(z[103]), .C(clk), .D(y[103]));


    (* keep="true"  *)
    (* BEL="X2/Y13/lc7" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i103(.I0(x[103]), .I1(), .I2(), .I3(), .O(x[104]));
    assign y[104] = x[104];

    (* keep="true"  *)
    (* BEL="X3/Y13/lc7" *)
    SB_DFF reg_i103(.Q(z[104]), .C(clk), .D(y[104]));


    (* keep="true"  *)
    (* BEL="X2/Y14/lc0" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i104(.I0(x[104]), .I1(), .I2(), .I3(), .O(x[105]));
    assign y[105] = x[105];

    (* keep="true"  *)
    (* BEL="X3/Y14/lc0" *)
    SB_DFF reg_i104(.Q(z[105]), .C(clk), .D(y[105]));


    (* keep="true"  *)
    (* BEL="X2/Y14/lc1" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i105(.I0(x[105]), .I1(), .I2(), .I3(), .O(x[106]));
    assign y[106] = x[106];

    (* keep="true"  *)
    (* BEL="X3/Y14/lc1" *)
    SB_DFF reg_i105(.Q(z[106]), .C(clk), .D(y[106]));


    (* keep="true"  *)
    (* BEL="X2/Y14/lc2" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i106(.I0(x[106]), .I1(), .I2(), .I3(), .O(x[107]));
    assign y[107] = x[107];

    (* keep="true"  *)
    (* BEL="X3/Y14/lc2" *)
    SB_DFF reg_i106(.Q(z[107]), .C(clk), .D(y[107]));


    (* keep="true"  *)
    (* BEL="X2/Y14/lc3" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i107(.I0(x[107]), .I1(), .I2(), .I3(), .O(x[108]));
    assign y[108] = x[108];

    (* keep="true"  *)
    (* BEL="X3/Y14/lc3" *)
    SB_DFF reg_i107(.Q(z[108]), .C(clk), .D(y[108]));


    (* keep="true"  *)
    (* BEL="X2/Y14/lc4" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i108(.I0(x[108]), .I1(), .I2(), .I3(), .O(x[109]));
    assign y[109] = x[109];

    (* keep="true"  *)
    (* BEL="X3/Y14/lc4" *)
    SB_DFF reg_i108(.Q(z[109]), .C(clk), .D(y[109]));


    (* keep="true"  *)
    (* BEL="X2/Y14/lc5" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i109(.I0(x[109]), .I1(), .I2(), .I3(), .O(x[110]));
    assign y[110] = x[110];

    (* keep="true"  *)
    (* BEL="X3/Y14/lc5" *)
    SB_DFF reg_i109(.Q(z[110]), .C(clk), .D(y[110]));


    (* keep="true"  *)
    (* BEL="X2/Y14/lc6" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i110(.I0(x[110]), .I1(), .I2(), .I3(), .O(x[111]));
    assign y[111] = x[111];

    (* keep="true"  *)
    (* BEL="X3/Y14/lc6" *)
    SB_DFF reg_i110(.Q(z[111]), .C(clk), .D(y[111]));


    (* keep="true"  *)
    (* BEL="X2/Y14/lc7" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i111(.I0(x[111]), .I1(), .I2(), .I3(), .O(x[112]));
    assign y[112] = x[112];

    (* keep="true"  *)
    (* BEL="X3/Y14/lc7" *)
    SB_DFF reg_i111(.Q(z[112]), .C(clk), .D(y[112]));


    (* keep="true"  *)
    (* BEL="X2/Y15/lc0" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i112(.I0(x[112]), .I1(), .I2(), .I3(), .O(x[113]));
    assign y[113] = x[113];

    (* keep="true"  *)
    (* BEL="X3/Y15/lc0" *)
    SB_DFF reg_i112(.Q(z[113]), .C(clk), .D(y[113]));


    (* keep="true"  *)
    (* BEL="X2/Y15/lc1" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i113(.I0(x[113]), .I1(), .I2(), .I3(), .O(x[114]));
    assign y[114] = x[114];

    (* keep="true"  *)
    (* BEL="X3/Y15/lc1" *)
    SB_DFF reg_i113(.Q(z[114]), .C(clk), .D(y[114]));


    (* keep="true"  *)
    (* BEL="X2/Y15/lc2" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i114(.I0(x[114]), .I1(), .I2(), .I3(), .O(x[115]));
    assign y[115] = x[115];

    (* keep="true"  *)
    (* BEL="X3/Y15/lc2" *)
    SB_DFF reg_i114(.Q(z[115]), .C(clk), .D(y[115]));


    (* keep="true"  *)
    (* BEL="X2/Y15/lc3" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i115(.I0(x[115]), .I1(), .I2(), .I3(), .O(x[116]));
    assign y[116] = x[116];

    (* keep="true"  *)
    (* BEL="X3/Y15/lc3" *)
    SB_DFF reg_i115(.Q(z[116]), .C(clk), .D(y[116]));


    (* keep="true"  *)
    (* BEL="X2/Y15/lc4" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i116(.I0(x[116]), .I1(), .I2(), .I3(), .O(x[117]));
    assign y[117] = x[117];

    (* keep="true"  *)
    (* BEL="X3/Y15/lc4" *)
    SB_DFF reg_i116(.Q(z[117]), .C(clk), .D(y[117]));


    (* keep="true"  *)
    (* BEL="X2/Y15/lc5" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i117(.I0(x[117]), .I1(), .I2(), .I3(), .O(x[118]));
    assign y[118] = x[118];

    (* keep="true"  *)
    (* BEL="X3/Y15/lc5" *)
    SB_DFF reg_i117(.Q(z[118]), .C(clk), .D(y[118]));


    (* keep="true"  *)
    (* BEL="X2/Y15/lc6" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i118(.I0(x[118]), .I1(), .I2(), .I3(), .O(x[119]));
    assign y[119] = x[119];

    (* keep="true"  *)
    (* BEL="X3/Y15/lc6" *)
    SB_DFF reg_i118(.Q(z[119]), .C(clk), .D(y[119]));


    (* keep="true"  *)
    (* BEL="X2/Y15/lc7" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i119(.I0(x[119]), .I1(), .I2(), .I3(), .O(x[120]));
    assign y[120] = x[120];

    (* keep="true"  *)
    (* BEL="X3/Y15/lc7" *)
    SB_DFF reg_i119(.Q(z[120]), .C(clk), .D(y[120]));


    (* keep="true"  *)
    (* BEL="X2/Y16/lc0" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i120(.I0(x[120]), .I1(), .I2(), .I3(), .O(x[121]));
    assign y[121] = x[121];

    (* keep="true"  *)
    (* BEL="X3/Y16/lc0" *)
    SB_DFF reg_i120(.Q(z[121]), .C(clk), .D(y[121]));


    (* keep="true"  *)
    (* BEL="X2/Y16/lc1" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i121(.I0(x[121]), .I1(), .I2(), .I3(), .O(x[122]));
    assign y[122] = x[122];

    (* keep="true"  *)
    (* BEL="X3/Y16/lc1" *)
    SB_DFF reg_i121(.Q(z[122]), .C(clk), .D(y[122]));


    (* keep="true"  *)
    (* BEL="X2/Y16/lc2" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i122(.I0(x[122]), .I1(), .I2(), .I3(), .O(x[123]));
    assign y[123] = x[123];

    (* keep="true"  *)
    (* BEL="X3/Y16/lc2" *)
    SB_DFF reg_i122(.Q(z[123]), .C(clk), .D(y[123]));


    (* keep="true"  *)
    (* BEL="X2/Y16/lc3" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i123(.I0(x[123]), .I1(), .I2(), .I3(), .O(x[124]));
    assign y[124] = x[124];

    (* keep="true"  *)
    (* BEL="X3/Y16/lc3" *)
    SB_DFF reg_i123(.Q(z[124]), .C(clk), .D(y[124]));


    (* keep="true"  *)
    (* BEL="X2/Y16/lc4" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i124(.I0(x[124]), .I1(), .I2(), .I3(), .O(x[125]));
    assign y[125] = x[125];

    (* keep="true"  *)
    (* BEL="X3/Y16/lc4" *)
    SB_DFF reg_i124(.Q(z[125]), .C(clk), .D(y[125]));


    (* keep="true"  *)
    (* BEL="X2/Y16/lc5" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i125(.I0(x[125]), .I1(), .I2(), .I3(), .O(x[126]));
    assign y[126] = x[126];

    (* keep="true"  *)
    (* BEL="X3/Y16/lc5" *)
    SB_DFF reg_i125(.Q(z[126]), .C(clk), .D(y[126]));


    (* keep="true"  *)
    (* BEL="X2/Y16/lc6" *)
    SB_LUT4 #(.LUT_INIT(1)) lut_i126(.I0(x[126]), .I1(), .I2(), .I3(), .O(x[127]));
    assign y[127] = x[127];

    (* keep="true"  *)
    (* BEL="X3/Y16/lc6" *)
    SB_DFF reg_i126(.Q(z[127]), .C(clk), .D(y[127]));



    assign y[0] = x[0];
    assign z[0] = y[0];
    assign delay_value = z;

endmodule