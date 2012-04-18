/*
 * pins_arduino.cpp
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */



#if defined(__AVR_ATmega328P__)
int digitalPinToInterrupt(int pin){
	if(pin==2)
		return 0;
	if(pin==3)
		return 1;
	return -1;
}
#elif defined(__AVR_ATmega2560__)
int digitalPinToInterrupt(int pin){
	if(pin==2)
		return 0;
	if(pin==3)
		return 1;
	if(pin==21)
		return 2;
	if(pin==20)
		return 3;
	if(pin==19)
		return 4;
	if(pin==18)
		return 5;
	return -1;
}
#elif defined(__AVR_AT90USB1286__)
#include "core_pins.h"
int digitalPinToInterrupt(int pin){//see WInterrupts.c
	switch (pin) {
		case CORE_INT0_PIN: return 0;
		case CORE_INT1_PIN: return 1;
		case CORE_INT2_PIN: return 2;
		case CORE_INT3_PIN: return 3;
		case CORE_INT4_PIN: return 4;
		case CORE_INT5_PIN: return 5;
		case CORE_INT6_PIN: return 6;
		case CORE_INT7_PIN: return 7;
		default: return -1;
	}
}
#endif

