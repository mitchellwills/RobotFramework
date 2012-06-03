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

typedef volatile uint8_t* PinRegister;
typedef uint8_t PinMask;

typedef struct  {
	PinRegister dataRegister;
	PinRegister directionRegister;
	PinRegister inputRegister;
	PinMask mask;
	uint8_t regOffset           :3;
	uint8_t isValid             :1;
	uint8_t isEnabled           :1;
} AVRPin_t;



inline void setPinRegister(PinRegister reg, PinMask mask){
	(*reg) |= mask;
}
inline void clearPinRegister(PinRegister reg, PinMask mask){
	(*reg) &= ~mask;
}
inline uint8_t getPinRegister(PinRegister reg, PinMask mask){
	return (*reg) & mask;
}

inline uint8_t getPinRegisterBit(PinRegister reg, uint8_t bitPosition){
	return ((*reg) >> bitPosition)&1;
}

inline void setPinOutput(AVRPin_t* pin){
	setPinRegister(pin->directionRegister, pin->mask);
}
inline void setPinInput(AVRPin_t* pin){
	clearPinRegister(pin->directionRegister, pin->mask);
}

inline void setPinHigh(AVRPin_t* pin){
	setPinRegister(pin->dataRegister, pin->mask);
}
inline void setPinLow(AVRPin_t* pin){
	clearPinRegister(pin->dataRegister, pin->mask);
}
inline uint8_t getPinInput(AVRPin_t* pin){
	return getPinRegister(pin->inputRegister, pin->mask);
}





//PinRegister Pin_getDataRegister(int pin);
//PinRegister Pin_getDirectionRegister(int pin);
//PinRegister Pin_getInputRegister(int pin);
//PinMask Pin_getMask(int pin);
//unsigned char Pin_getRegOffset(int pin);

AVRPin_t* newPin(int rawPin);
void initPin(AVRPin_t* pinData, uint8_t rawPin, uint8_t enable);

#endif /* PINS_H_ */
