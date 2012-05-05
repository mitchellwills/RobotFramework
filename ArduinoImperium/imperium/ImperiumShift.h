/*
 * ImperiumShift.h
 *
 *  Created on: May 4, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMSHIFT_H_
#define IMPERIUMSHIFT_H_

#include "Arduino.h"

long ShiftIn(int bitCount, unsigned int clkPin, unsigned int dioPin);
void ShiftOut(long value, int bitCount, unsigned int CLK_pin, unsigned int DIO_pin);

#endif /* IMPERIUMSHIFT_H_ */
