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
	uint8_t rawPin;
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




AVRPin_t* newPin(int rawPin, uint8_t enable);
void initPin(AVRPin_t* pinData, uint8_t rawPin, uint8_t enable);


#define AVRRaw_PA0 0
#define AVRRaw_PA1 1
#define AVRRaw_PA2 2
#define AVRRaw_PA3 3
#define AVRRaw_PA4 4
#define AVRRaw_PA5 5
#define AVRRaw_PA6 6
#define AVRRaw_PA7 7
#define AVRRaw_PB0 8
#define AVRRaw_PB1 9
#define AVRRaw_PB2 10
#define AVRRaw_PB3 11
#define AVRRaw_PB4 12
#define AVRRaw_PB5 13
#define AVRRaw_PB6 14
#define AVRRaw_PB7 15
#define AVRRaw_PC0 16
#define AVRRaw_PC1 17
#define AVRRaw_PC2 18
#define AVRRaw_PC3 19
#define AVRRaw_PC4 20
#define AVRRaw_PC5 21
#define AVRRaw_PC6 22
#define AVRRaw_PC7 23
#define AVRRaw_PD0 24
#define AVRRaw_PD1 25
#define AVRRaw_PD2 26
#define AVRRaw_PD3 27
#define AVRRaw_PD4 28
#define AVRRaw_PD5 29
#define AVRRaw_PD6 30
#define AVRRaw_PD7 31
#define AVRRaw_PE0 32
#define AVRRaw_PE1 33
#define AVRRaw_PE2 34
#define AVRRaw_PE3 35
#define AVRRaw_PE4 36
#define AVRRaw_PE5 37
#define AVRRaw_PE6 38
#define AVRRaw_PE7 39
#define AVRRaw_PF0 40
#define AVRRaw_PF1 41
#define AVRRaw_PF2 42
#define AVRRaw_PF3 43
#define AVRRaw_PF4 44
#define AVRRaw_PF5 45
#define AVRRaw_PF6 46
#define AVRRaw_PF7 47



#endif /* PINS_H_ */
