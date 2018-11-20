/*
 * two_way_peripheral.h
 *
 *  Created on: Nov 19, 2018
 *      Author: spinaldev
 */

#ifndef TWO_LED_PERIPHERAL_H_
#define TWO_LED_PERIPHERAL_H_


	#include <stdint.h>


	typedef struct
	{
	  volatile uint32_t LED1;
	  volatile uint32_t LED2;
	  volatile uint32_t CONS;
	  volatile uint32_t REG;
	} TwoLedPeripheral_Reg;

	static void twoledperipheral_init(TwoLedPeripheral_Reg *reg){
		reg->LED1 = 0;
		reg->LED2 = 0;
	}

	#define TWO_LED_PERIPHERAL ((TwoLedPeripheral_Reg*)0xF0030000)


#endif /* TWO_LED_PERIPHERAL_H_ */
