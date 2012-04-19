/*
 * pins_arduino.h
 *
 *  Created on: Mar 30, 2012
 *      Author: Mitchell
 */

#ifndef PINS_ARDUINO_H_
#define PINS_ARDUINO_H_

#include "Arduino.h"


#if defined(__AVR_ATmega328P__)
#define NUM_INTERRUPTS 2
#define INT0_PIN 2
#define INT1_PIN 3

#elif defined(__AVR_ATmega2560__)
#define NUM_INTERRUPTS 6
#define INT0_PIN 2
#define INT1_PIN 3
#define INT2_PIN 21
#define INT3_PIN 20
#define INT4_PIN 19
#define INT5_PIN 18

#elif defined(__AVR_AT90USB1286__)
#define NUM_INTERRUPTS 8
#include "core_pins.h"
#define INT0_PIN CORE_INT0_PIN
#define INT1_PIN CORE_INT1_PIN
#define INT2_PIN CORE_INT2_PIN
#define INT3_PIN CORE_INT3_PIN
#define INT4_PIN CORE_INT4_PIN
#define INT5_PIN CORE_INT5_PIN
#define INT6_PIN CORE_INT6_PIN
#define INT7_PIN CORE_INT7_PIN

#endif


int digitalPinToInterrupt(int pin);
Stream* digitalPinToSerial(int pin, unsigned long baud);


#endif /* PINS_ARDUINO_H_ */
