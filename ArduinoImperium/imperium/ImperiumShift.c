/*
 * ImperiumShift.c
 *
 * See http://arduino.cc/playground/Main/HM55B
 *
 *
 *  Created on: May 4, 2012
 *      Author: Mitchell
 */

#include "ImperiumShift.h"

void ShiftOut(long value, int bitCount, unsigned int CLK_pin, unsigned int DIO_pin) {
	for (int i = bitCount; i >= 0; i--) {
		digitalWrite(CLK_pin, LOW);
		if ((value & 1 << i) == (1 << i)) {
			digitalWrite(DIO_pin, HIGH);
		} else {
			digitalWrite(DIO_pin, LOW);
		}
		digitalWrite(CLK_pin, HIGH);
		delayMicroseconds(1);
	}
}

long ShiftIn(int bitCount, unsigned int clkPin, unsigned int dioPin) {
	long ShiftIn_result;
	ShiftIn_result = 0;
	for (int i = bitCount; i > 0; i--) {
		digitalWrite(clkPin, HIGH);
		delayMicroseconds(1);
		if (digitalRead(dioPin) == HIGH) {
			ShiftIn_result = (ShiftIn_result << 1) + 1;
		} else {
			ShiftIn_result = (ShiftIn_result << 1) + 0;
		}
		digitalWrite(clkPin, LOW);
		delayMicroseconds(1);
	}
	return ShiftIn_result;
}

