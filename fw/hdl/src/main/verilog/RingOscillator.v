
module RingOscillator #(parameter divider = 15)(output clk_out);
    localparam N = 31;
    wire [N:0] x;
    assign x[0] = x[N];

    genvar ii;
    generate

        for (ii = 0; ii < N; ii = ii + 1) begin
            (* ringosc *)
            SB_LUT4 #(.LUT_INIT(1)) lut_i(.I0(x[ii]), .I1(), .I2(), .I3(), .O(x[ii+1]));
        end
    endgenerate

    wire clk;
    assign clk = x[N];


    reg [divider:0] ctr;
    always @(posedge clk)
        ctr <= ctr + 1'b1;
    assign clk_out = ctr[divider];
endmodule