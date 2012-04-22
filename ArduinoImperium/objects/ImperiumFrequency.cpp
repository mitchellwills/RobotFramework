/*
 * ImperiumFrequency.cpp
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */




#include "ImperiumFrequency.h"
#include "pins.h"


static ImperiumFrequency* frequencyObjects[NUM_INTERRUPTS];
static inline void intHandler(int interruptNum, int pin){
	ImperiumFrequency* object = frequencyObjects[interruptNum];
	object->count++;
	long time = millis();
	if(time-object->last>=100){
		object->frequency = 10*object->count;
		object->count = 0;
		object->last = time;
	}
}



#define INTERRUPT_HANDLER intHandler
#include "interrupts.h"
#undef INTERRUPT_HANDLER


ImperiumObject* ImperiumFrequency::newFrequency(int objectId, int* pins, int pinCount){
	return new ImperiumFrequency(objectId, pins, pinCount);
}

ImperiumFrequency::ImperiumFrequency(int objectId, int* pins, int pinCount) : ImperiumObject(objectId, pins, pinCount){
	pinMode(getPin(0), INPUT);
	digitalWrite(getPin(0), HIGH);//pullup

	interruptNum = digitalPinToInterrupt(getPin(0));
	frequencyObjects[interruptNum] = this;
	count = 0;
	last = millis();
	attachInterrupt(interruptNum, interruptHandlers[interruptNum], RISING);
}


void ImperiumFrequency::update(){
}
void ImperiumFrequency::receiveMessage(ImperiumPacket& packet){
}

void ImperiumFrequency::setValue(long value){
}

long ImperiumFrequency::getValue(){
	return frequency;
}


