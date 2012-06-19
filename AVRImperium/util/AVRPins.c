/*
 * Pins.c
 *
 *  Created on: May 16, 2012
 *      Author: Mitchell
 */

#include "AVRPins.h"
#include <stdlib.h>
static volatile unsigned char tmp = 0;

PinRegister Pin_getDataRegister(int pin){
	switch(pin/8){
	case 0:
		return &PORTA;
	case 1:
		return &PORTB;
	case 2:
		return &PORTC;
	case 3:
		return &PORTD;
	case 4:
		return &PORTE;
	case 5:
		return &PORTF;
	}
	return &tmp;
}

PinRegister Pin_getDirectionRegister(int pin){
	switch(pin/8){
	case 0:
		return &DDRA;
	case 1:
		return &DDRB;
	case 2:
		return &DDRC;
	case 3:
		return &DDRD;
	case 4:
		return &DDRE;
	case 5:
		return &DDRF;
	}
	return &tmp;
}

PinRegister Pin_getInputRegister(int pin){
	switch(pin/8){
	case 0:
		return &PINA;
	case 1:
		return &PINB;
	case 2:
		return &PINC;
	case 3:
		return &PIND;
	case 4:
		return &PINE;
	case 5:
		return &PINF;
	}
	return &tmp;
}

unsigned char Pin_getRegOffset(int pin){
	return pin%8;
}

unsigned char Pin_getMask(int pin){
	return 1<<(Pin_getRegOffset(pin));
}


void initPin(AVRPin_t* pinData, uint8_t rawPin, uint8_t enable){
	pinData->rawPin = rawPin;
	pinData->dataRegister = Pin_getDataRegister(rawPin);
	pinData->directionRegister = Pin_getDirectionRegister(rawPin);
	pinData->inputRegister = Pin_getInputRegister(rawPin);
	pinData->mask = Pin_getMask(rawPin);
	pinData->regOffset = Pin_getRegOffset(rawPin);
	pinData->isValid = 1;
	if(enable)
		pinData->isEnabled = 1;
	else
		pinData->isEnabled = 0;
}

AVRPin_t* newPin(int rawPin, uint8_t enable){
	AVRPin_t* pinData = (AVRPin_t*)malloc(sizeof(AVRPin_t));
	initPin(pinData, rawPin, enable);
	return pinData;
}
