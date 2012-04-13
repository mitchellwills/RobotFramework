/*
 * pins_arduino.h
 *
 *  Created on: Mar 30, 2012
 *      Author: Mitchell
 */

#ifndef PINS_ARDUINO_H_
#define PINS_ARDUINO_H_


#if defined(__AVR_ATmega328P__)
#include "pins_arduino_standard.h"
#define NUM_INTERRUPTS 2
#elif defined(__AVR_ATmega2560__)
#include "pins_arduino_mega.h"
#define NUM_INTERRUPTS 6
#endif


int digitalPinToInterrupt(int pin);


#endif /* PINS_ARDUINO_H_ */
