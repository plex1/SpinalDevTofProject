`timescale 1ns / 1ps

module toplevel( 
    input 	 CLK, //J3
    input 	 jtag_tck, //H16
    input 	 jtag_tdi, //G15
    output 	 jtag_tdo, //G16			 
    input 	 jtag_tms, //F15

    output 	 uart_txd, //B12
    input 	 uart_rxd, //B10
		 
    output [7:0] io_led
  );
  
  wire [31:0] io_gpioA_read;
  wire [31:0] io_gpioA_write;
  wire [31:0] io_gpioA_writeEnable;
  wire io_mainClk;
  wire jtag_tck_buf;

  SB_GB mainClkBuffer (
    .USER_SIGNAL_TO_GLOBAL_BUFFER (CLK),
    .GLOBAL_BUFFER_OUTPUT (mainClk)
  );

  SB_GB jtagClkBuffer (
    .USER_SIGNAL_TO_GLOBAL_BUFFER (jtag_tck), //H16
    .GLOBAL_BUFFER_OUTPUT (jtag_tck_buf)
  );

  assign io_led = io_gpioA_write[7 : 0]; 

  MuraxCustom murax ( 
    .io_asyncReset(0),
    .io_mainClk (mainClk),
    .io_jtag_tck(jtag_tck_buf),
    .io_jtag_tdi(jtag_tdi), //G15
    .io_jtag_tdo(jtag_tdo), //G16
    .io_jtag_tms(jtag_tms), //F15
    .io_gpioA_read       (io_gpioA_read),
    .io_gpioA_write      (io_gpioA_write),
    .io_gpioA_writeEnable(io_gpioA_writeEnable),
    .io_uart_txd(uart_txd),
    .io_uart_rxd(uart_rxd)
  );
		
endmodule
