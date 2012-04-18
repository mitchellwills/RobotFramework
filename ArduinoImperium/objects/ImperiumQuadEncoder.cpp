/*
 * ImperiumQuadEncoder.cpp
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */




#include "ImperiumQuadEncoder.h"
#include "pins.h"


static ImperiumQuadEncoder* counterObjects[NUM_INTERRUPTS];
static inline void intHandler(unsigned int interruptNum, unsigned int pin){
	int current = pin==counterObjects[interruptNum]->pinAInterrupt;
	if(counterObjects[interruptNum]->last){
		if(current)
			counterObjects[interruptNum]->count--;
		else
			counterObjects[interruptNum]->count++;
	}
	else{
		if(current)
			counterObjects[interruptNum]->count++;
		else
			counterObjects[interruptNum]->count--;
	}
	counterObjects[interruptNum]->last = current;
}



#define INTERRUPT_HANDLER intHandler
#include "interrupts.h"
#undef INTERRUPT_HANDLER


ImperiumObject* ImperiumQuadEncoder::newQuadEncoder(int objectId, int* pins, int pinCount){
	return new ImperiumQuadEncoder(objectId, pins, pinCount);
}

ImperiumQuadEncoder::ImperiumQuadEncoder(int objectId, int* pins, int pinCount) : ImperiumObject(objectId, pins, pinCount){
	pinMode(getPin(0), INPUT);
	digitalWrite(getPin(0), HIGH);//pullup
	pinMode(getPin(1), INPUT);
	digitalWrite(getPin(1), HIGH);//pullup

	pinAInterrupt = digitalPinToInterrupt(getPin(0));
	pinBInterrupt = digitalPinToInterrupt(getPin(1));

	counterObjects[pinAInterrupt] = this;
	counterObjects[pinBInterrupt] = this;

	count = 0;
	last = 0;

	attachInterrupt(pinAInterrupt, interruptHandlers[pinAInterrupt], RISING);
	attachInterrupt(pinBInterrupt, interruptHandlers[pinBInterrupt], RISING);
}


void ImperiumQuadEncoder::update(){
}

void ImperiumQuadEncoder::setValue(long value){
}

long ImperiumQuadEncoder::getValue(){
	return count;
}


