#include <stdio.h>
#include <string.h>
#include <stdint.h>
#include <stdlib.h>
#include <murax.h>

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

	GPIO_A->OUTPUT_ENABLE = 0x000000FF;
        GPIO_A->OUTPUT = 0x00000000;


	print("Hello !\n");

	while(1){
		for(uint32_t idx = 'a';idx <= 'z';idx++){
		  for(uint32_t idx = 0;idx < 50000;idx++) asm volatile("");
		  GPIO_A->OUTPUT = (GPIO_A->OUTPUT & ~0x3F) | ((GPIO_A->OUTPUT + 1) & 0x3F);  //Counter on LED[5:0]
		  uart_write(UART, idx);
		}
	}
}



