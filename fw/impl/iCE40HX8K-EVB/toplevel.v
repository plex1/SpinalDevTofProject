`timescale 1ns / 1ps

module toplevel(
    input  CLK,
    input  BUT1,
    input  BUT2,
    output LED1,
    output LED2,
    input  jtag_tck,
    input  jtag_tms, 
    input  jtag_tdi,
    output jtag_tdo,
    output uart_txd,
    input  uart_rxd,
    output uart2_txd,
    input  uart2_rxd, 
    output trigsOut0,
    input  trigsIn0,
    input  trigsIn1,
    output delay0, delay1, delay2, delay3, delay4, delay5, delay6, delay7,
    output io_ro_clk,
    output lockin1Out,
    output lockin2Out
  );

  assign LED1 = io_gpioA_write[0];
  assign LED2 = io_gpioA_write[5];
  assign trigsOut0 = io_trigsOut[0];
  assign io_trigsIn[0] = trigsIn0;
  assign io_trigsIn[1] = trigsIn1;
  assign {delay7, delay6, delay5, delay4, delay3, delay2, delay1, delay0}  = io_delay;
   
 
  wire [31:0] io_gpioA_read;
  wire [31:0] io_gpioA_write;
  wire [31:0] io_gpioA_writeEnable;
  wire [0:0] io_trigsOut;
  wire [1:0] io_trigsIn;
  wire [7:0] io_delay;
  wire mainClk, jtag_tck_buf;

  // Use PLL to downclock external clock.
  toplevel_pll toplevel_pll_inst(.REFERENCECLK(CLK),
                                 .PLLOUTCORE(mainClk),
                                 .PLLOUTGLOBAL(),
                                 .RESET(1'b1));

  SB_GB jtagClkBuffer (
    .USER_SIGNAL_TO_GLOBAL_BUFFER (jtag_tck),
    .GLOBAL_BUFFER_OUTPUT (jtag_tck_buf)
  ); 

  MuraxCustom murax ( 
    .io_asyncReset(1'b0),
    .io_mainClk (mainClk),
    .io_jtag_tck(jtag_tck_buf),
    .io_jtag_tdi(jtag_tdi),
    .io_jtag_tdo(jtag_tdo),
    .io_jtag_tms(jtag_tms),
    .io_gpioA_read       (io_gpioA_read),
    .io_gpioA_write      (io_gpioA_write),
    .io_gpioA_writeEnable(io_gpioA_writeEnable),
    .io_uart_txd(uart_txd),
    .io_uart_rxd(uart_rxd),
    .io_uart2_txd(uart2_txd),
    .io_uart2_rxd(uart2_rxd),		      
    .io_trigsOut(io_trigsOut),
    .io_trigsIn(io_trigsIn),
    .io_delay(io_delay),
    .io_ro_clk(io_ro_clk),
    .io_lockin1Out(lockin1Out),
    .io_lockin2Out(lockin2Out)
  );

endmodule
