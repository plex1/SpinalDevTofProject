module DelayLine #(parameter num_elements = 32) (
    input clk,
    input in_signal,
    output [num_elements:0]delay_value);

    wire [num_elements:0] x;
    assign x[0] = in_signal;

    genvar ii;
    generate

        for (ii = 0; ii < num_elements; ii = ii + 1) begin
            (* delayline *)
            SB_LUT4 #(.LUT_INIT(1)) lut_i(.I0(x[ii]), .I1(), .I2(), .I3(), .O(x[ii+1]));
        end
    endgenerate

    assign delay_value = x;

endmodule