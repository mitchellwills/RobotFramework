/*
 * ImperiumPulseCounter.cpp
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */




#include "ImperiumPulseCounter.h"


static ImperiumPulseCounter* counterObjects[NUM_INTERRUPTS];
static inline void intHandler(int interruptNum){
	counterObjects[interruptNum]->count++;
}
#if defined(__AVR_ATmega328P__) || defined(__AVR_ATmega2560__)
static void int0(){
	intHandler(0);
}
static void int1(){
	intHandler(1);
}
#if defined(__AVR_ATmega2560__)
static void int2(){
	intHandler(2);
}
static void int3(){
	intHandler(3);
}
static void int4(){
	intHandler(4);
}
static void int5(){
	intHandler(5);
}
#endif
#endif

#if defined(__AVR_ATmega328P__)
static void (*interruptHandlers[])(void) = {int0, int1};
#elif defined(__AVR_ATmega2560__)
static void (*interruptHandlers[])(void) = {int0, int1, int2, int3, int4, int5};
#endif


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

void ImperiumPulseCounter::setValue(long value){
}

long ImperiumPulseCounter::getValue(){
	return count;
}


