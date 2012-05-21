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

#define setPinRegister(pin, mask) ( setBit(*pin, mask) )
#define clearPinRegister(pin, mask) ( clearBit(*pin, mask) )
#define getPinRegister(pin, mask) ( getBit(*pin, mask) )

PinRegister Pin_getDataRegister(int pin);
PinRegister Pin_getDirectionRegister(int pin);
PinRegister Pin_getInputRegister(int pin);
PinMask Pin_getMask(int pin);
unsigned char Pin_getPinPosition(int pin);

#endif /* PINS_H_ */
