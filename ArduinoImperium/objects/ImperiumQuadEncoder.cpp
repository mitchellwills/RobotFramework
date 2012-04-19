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
	int A = (interruptNum==counterObjects[interruptNum]->pinAInterrupt);//true for pin A
	int s0 = counterObjects[interruptNum]->s0;
	int s1 = counterObjects[interruptNum]->s1;

	if( (!A && !s0) || (A&&s0) )
		counterObjects[interruptNum]->count++;
	else
		counterObjects[interruptNum]->count--;

	counterObjects[interruptNum]->s0 = !s0;
	counterObjects[interruptNum]->s1 = (A&&!s1) || (s1&&!A);
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
	s0 = 0;
	s1 = 0;

	attachInterrupt(pinAInterrupt, interruptHandlers[pinAInterrupt], CHANGE);
	attachInterrupt(pinBInterrupt, interruptHandlers[pinBInterrupt], CHANGE);
}


void ImperiumQuadEncoder::update(){
}
void ImperiumQuadEncoder::receiveMessage(ImperiumPacket& packet){
}

void ImperiumQuadEncoder::setValue(long value){
}

long ImperiumQuadEncoder::getValue(){
	return count;
}


