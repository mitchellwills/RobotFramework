/*
 * pins_arduino.h
 *
 *  Created on: Mar 30, 2012
 *      Author: Mitchell
 */

#ifndef PINS_ARDUINO_H_
#define PINS_ARDUINO_H_


#if defined(__AVR_ATmega328P__)
#define NUM_INTERRUPTS 2
#elif defined(__AVR_ATmega2560__)
#define NUM_INTERRUPTS 6
#elif defined(__AVR_AT90USB1286__)
#define NUM_INTERRUPTS 8
#endif


int digitalPinToInterrupt(int pin);


#endif /* PINS_ARDUINO_H_ */
