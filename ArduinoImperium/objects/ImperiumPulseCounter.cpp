/*
 * ImperiumPulseCounter.cpp
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */




#include "ImperiumPulseCounter.h"
#include "pins.h"


static ImperiumPulseCounter* counterObjects[NUM_INTERRUPTS];
static inline void intHandler(int interruptNum, int pin){
	counterObjects[interruptNum]->count++;
}



#define INTERRUPT_HANDLER intHandler
#include "interrupts.h"
#undef INTERRUPT_HANDLER


ImperiumObject* ImperiumPulseCounter::newPulseCounter(int objectId, int* pins, int pinCount){
	return new ImperiumPulseCounter(objectId, pins, pinCount);
}

ImperiumPulseCounter::ImperiumPulseCounter(int objectId, int* pins, int pinCount) : ImperiumObject(objectId, pins, pinCount){
	pinMode(getPin(0), INPUT);
	digitalWrite(getPin(0), HIGH);//pullup

	interruptNum = digitalPinToInterrupt(getPin(0));
	counterObjects[interruptNum] = this;
	count = 0;
	attachInterrupt(interruptNum, interruptHandlers[interruptNum], RISING);
}


void ImperiumPulseCounter::update(){
}
void ImperiumPulseCounter::receiveMessage(ImperiumPacket& packet){
}

void ImperiumPulseCounter::setValue(long value){
}

long ImperiumPulseCounter::getValue(){
	return count;
}


