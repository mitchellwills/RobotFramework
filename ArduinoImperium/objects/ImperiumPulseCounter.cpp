/*
 * ImperiumPulseCounter.cpp
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */




#include "ImperiumPulseCounter.h"


static ImperiumPulseCounter* counterObjects[NUM_INTERRUPTS];

#if defined(__AVR_ATmega328P__) || defined(__AVR_ATmega2560__)
static void int0(){
	counterObjects[0]->count++;
}
static void int1(){
	counterObjects[1]->count++;
}
#if defined(__AVR_ATmega2560__)
static void int2(){
	counterObjects[2]->count++;
}
static void int3(){
	counterObjects[3]->count++;
}
static void int4(){
	counterObjects[4]->count++;
}
static void int5(){
	counterObjects[5]->count++;
}
#endif
#endif

#if defined(__AVR_ATmega328P__)
static Interrupt interruptHandlers[] = {int0, int1};
#elif defined(__AVR_ATmega2560__)
static Interrupt interruptHandlers[] = {int0, int1, int2, int3, int4, int5};
#endif


ImperiumObject* ImperiumPulseCounter::newPulseCounter(int objectId, int* pins, int pinCount){
	return new ImperiumPulseCounter(objectId, pins, pinCount);
}

ImperiumPulseCounter::ImperiumPulseCounter(int objectId, int* pins, int pinCount) : ImperiumObject(objectId, pins, pinCount){
	pinMode(getPin(0), INPUT);
	digitalWrite(getPin(0), HIGH);
	interruptNum = digitalPinToInterrupt(getPin(0));
	counterObjects[interruptNum] = this;
	count = interruptNum;
	attachInterrupt(interruptNum, interruptHandlers[interruptNum], RISING);
}


void ImperiumPulseCounter::update(){
}

void ImperiumPulseCounter::setValue(long value){
}

long ImperiumPulseCounter::getValue(){
	return count;
}


