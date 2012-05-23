/*
 * Pins.h
 *
 *  Created on: May 16, 2012
 *      Author: Mitchell
 */

#ifndef PINS_H_
#define PINS_H_

#include <avr/io.h>
#include "ByteUtil.h"

typedef volatile unsigned char* PinRegister;
typedef unsigned char PinMask;


inline void setPinRegister(PinRegister reg, PinMask mask){
	(*reg) |= mask;
}
inline void clearPinRegister(PinRegister reg, PinMask mask){
	(*reg) &= ~mask;
}
inline unsigned char getPinRegister(PinRegister reg, PinMask mask){
	return (*reg) & mask;
}
inline unsigned char getPinRegisterBit(PinRegister reg, unsigned char bitPosition){
	return ((*reg) >> bitPosition)&1;
}

PinRegister Pin_getDataRegister(int pin);
PinRegister Pin_getDirectionRegister(int pin);
PinRegister Pin_getInputRegister(int pin);
PinMask Pin_getMask(int pin);
unsigned char Pin_getPinPosition(int pin);

#endif /* PINS_H_ */
