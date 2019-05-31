#include <stdio.h>
#include <string.h>
#include <stdint.h>
#include <stdlib.h>
#include <murax.h>
#include "queue.h"
#include "two_led_peripheral.h"
#include "utils.h"

// header definition
typedef struct {
  uint8_t id;
  uint8_t tag;
  uint8_t command;
  union {
    struct {
      uint8_t incr : 1;
      uint8_t request : 1;
      uint8_t reserved : 6;
    } fields;
    uint8_t value;
  } flags;
  uint32_t addr;
  uint32_t len;
} __attribute__ ((aligned (4), packed)) msg_header_t;

void print(char *str){
  while(*str){
    uart_write(UART,*(str++));
  }
}

int main() {
  Uart_Config uartConfig;
  uartConfig.dataLength = 8;
  uartConfig.parity = NONE;
  uartConfig.stop = ONE;
  uartConfig.clockDivider = 12000000/8/115200-1;
  uart_applyConfig(UART,&uartConfig);
  uart_applyConfig(UART2,&uartConfig);

  GPIO_A->OUTPUT_ENABLE = 0x000000FF;
  GPIO_A->OUTPUT = 0x00000000;

  twoledperipheral_init(TWO_LED_PERIPHERAL);

  int val0, val1, val2;
  val0 = TWO_LED_PERIPHERAL->LED1;
  val1 = TWO_LED_PERIPHERAL->LED2;
  val2 = TWO_LED_PERIPHERAL->CONS;

  msg_header_t msg_header;
  uint8_t *msg_header_byte = (uint8_t *)&msg_header;
  uint8_t msg_header_byte2[12];
  uint32_t elem_left = 0;

  // statistics
  uint32_t n_rx = 0;
  uint32_t n_tx = 0;
  uint32_t uart_drx[4];


   print("Start !\n");

   while (uart_readOccupancy(UART2)) {
         uart_read(UART2);
   }
  // while (uart_readOccupancy(UART)) {
  //       uart_read(UART);
   //}

/*
    while(1){
    for(uint32_t idx = 'a';idx <= 'z';idx++){
    for(uint32_t idx = 0;idx < 200000;idx++) asm volatile("");
    GPIO_A->OUTPUT = (GPIO_A->OUTPUT & ~0x3F) | ((GPIO_A->OUTPUT + 1) & 0x3F);  //Counter on LED[5:0]
    uart_write(UART, idx);
    uart_write(UART, idx);
    //uart_write(UART2, idx);
    //uart_write(UART2, idx);
    //uart_write(UART2, idx);
    if (uart_readOccupancy(UART)) {
      uart_write(UART, uart_read(UART));
    }
    if (uart_readOccupancy(UART2)) {
      uint8_t r;
      r=uart_read(UART2);
      uart_write(UART2, r );
      uart_write(UART2, r);
    }

    }
    }
    */




  GPIO_A->OUTPUT = 0x1;
  while(1){

    //for(uint32_t idx = 0;idx < 10000;idx++) asm volatile("");
    //GPIO_A->OUTPUT = (GPIO_A->OUTPUT & ~0x3F) | ((GPIO_A->OUTPUT + 1) & 0x3F);  //Counter on LED[5:0]

    if (uart_readOccupancy(UART2) && queueLength<12) {
      uint32_t r;
      r=uart_read(UART2);
      queue_push(r);
      print("1");
    }
    if (queueLength>=12){
      print("Header received !\n");
      GPIO_A->OUTPUT = 0x0;

      // get request
      for (int i=0; i<12; i++) {
	msg_header_byte[i] = queue_pop();
	msg_header_byte2[i]=msg_header_byte[i];
	elem_left = uart_readOccupancy(UART2);

      }
      n_rx++;
      msg_header.addr = ntohl(msg_header.addr);
      msg_header.len = ntohl(msg_header.len);


      // read command handling
      if (msg_header.command == 0) {

	// send response

	// header
	msg_header.addr = htonl(msg_header.addr);
	msg_header.len = htonl(msg_header.len);
	msg_header.flags.fields.request = 0;
	for (int i=0; i<12; i++) {
	  while (!uart_writeAvailability(UART2)) {}
	  uart_write(UART2, msg_header_byte[i]);
	}

	msg_header.addr = ntohl(msg_header.addr);
	msg_header.len = ntohl(msg_header.len);

	// data
	if (msg_header.len < 128) {
	  for (int i=0; i<msg_header.len; i++){
	    uint32_t data = *((uint32_t *) (msg_header.addr + 4*i*msg_header.flags.fields.incr));
	    data=htonl(data);
	    for (int j=0; j<4; j++) {
	      while (!uart_writeAvailability(UART2)) {}
	      uart_write(UART2, (data>>8*j) & 0xff);
	    }
	  }
	}
      }

      // write command handling
      if (msg_header.command == 1) {

	// send response

	// header
	msg_header.addr = htonl(msg_header.addr);
	msg_header.len = htonl(msg_header.len);
	msg_header.flags.fields.request = 0;
	for (int i=0; i<12; i++) {
	  while (!uart_writeAvailability(UART2)) {}
	  uart_write(UART2, msg_header_byte[i]);
	}
	msg_header.addr = ntohl(msg_header.addr);
	msg_header.len = ntohl(msg_header.len);

	// data
	if (msg_header.len < 128) {
	  for (int i=0; i<msg_header.len; i++){
	    uint32_t data = 0;
	    for (int j=0; j<4; j++) {
	      while (!uart_readOccupancy(UART2)) {}
	      for(uint32_t idx = 0;idx < 100;idx++) asm volatile("");
	      uart_drx[j] = uart_read(UART2);
	      data = data + (uart_drx[j]<<(j*8));
	    }
	    data = ntohl(data);
	    *((uint32_t *) (msg_header.addr + 4*i*msg_header.flags.fields.incr)) = data;
	  }
	}
      }

      n_tx++;
    }

  }

}



