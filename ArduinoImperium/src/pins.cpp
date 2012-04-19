/*
 * pins_arduino.cpp
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */

#include "pins.h"

int digitalPinToInterrupt(int pin) { //see WInterrupts.c
	switch (pin) {
#if NUM_INTERRUPTS>=2
	case INT0_PIN: return 0;
	case INT1_PIN: return 1;
#if NUM_INTERRUPTS>=6
	case INT2_PIN: return 2;
	case INT3_PIN: return 3;
	case INT4_PIN: return 4;
	case INT5_PIN: return 5;
#if NUM_INTERRUPTS>=8
	case INT6_PIN: return 6;
	case INT7_PIN: return 7;
#endif
#endif
#endif
	default:
		return -1;
	}
}
