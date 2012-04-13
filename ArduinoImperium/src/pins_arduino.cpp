/*
 * pins_arduino.cpp
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */



#if defined(__AVR_ATmega328P__)
#include "pins_arduino_standard.h"
int digitalPinToInterrupt(int pin){
	if(pin==2)
		return 0;
	if(pin==3)
		return 1;
	return -1;
}
#elif defined(__AVR_ATmega2560__)
#include "pins_arduino_mega.h"
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
#endif
