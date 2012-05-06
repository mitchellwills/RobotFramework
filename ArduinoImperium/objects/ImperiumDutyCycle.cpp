/*
 * ImperiumDutyCycle.cpp
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */


#ifndef LOW_MEMORY


#include "ImperiumDutyCycle.h"
#include "pins.h"


static ImperiumDutyCycle* dutyCycleObjects[NUM_INTERRUPTS];
static inline void intHandler(int interruptNum, int pin){
	ImperiumDutyCycle* dutyCycle = dutyCycleObjects[interruptNum];
	if(digitalRead(pin)){//rising edge
		unsigned long time = micros();
		unsigned long highTime = dutyCycle->lastFalling - dutyCycle->lastRising;
		unsigned long cycleTime = time - dutyCycle->lastRising;
		dutyCycle->duty = highTime * 5000 / cycleTime;
		dutyCycle->lastRising = time;
	}
	else{//falling edge
		dutyCycle->lastFalling = micros();
	}
}



#define INTERRUPT_HANDLER intHandler
#include "interrupts.h"
#undef INTERRUPT_HANDLER


ImperiumObject* ImperiumDutyCycle::newDutyCycle(int objectId, int* pins, int pinCount){
	return new ImperiumDutyCycle(objectId, pins, pinCount);
}

ImperiumDutyCycle::ImperiumDutyCycle(int objectId, int* pins, int pinCount) : ImperiumObject(objectId, pins, pinCount){
	pinMode(getPin(0), INPUT);
	digitalWrite(getPin(0), HIGH);//pullup

	interruptNum = digitalPinToInterrupt(getPin(0));
	dutyCycleObjects[interruptNum] = this;
	attachInterrupt(interruptNum, interruptHandlers[interruptNum], CHANGE);

	lastRising = 0;
	lastFalling = 0;
	duty = 0;
}


void ImperiumDutyCycle::update(){
}
void ImperiumDutyCycle::receiveMessage(ImperiumPacket& packet){
}

void ImperiumDutyCycle::setValue(long value){
}

long ImperiumDutyCycle::getValue(){
	return duty;
}

#endif
