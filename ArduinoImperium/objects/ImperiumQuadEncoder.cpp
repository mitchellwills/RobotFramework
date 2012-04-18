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
	int currentPin = (interruptNum==counterObjects[interruptNum]->pinAInterrupt);//true for pin A
	int currentPinState = digitalRead(pin);//just went high
	int state = counterObjects[interruptNum]->state;

	if(state==1){
		if(currentPin && currentPinState){
			state = 4;
			counterObjects[interruptNum]->count--;
		}
		else if(!currentPin && currentPinState){
			state = 2;
			counterObjects[interruptNum]->count++;
		}
	}
	else if(state==2){
		if(!currentPin && !currentPinState){
			state = 1;
			counterObjects[interruptNum]->count--;
		}
		else if(currentPin && currentPinState){
			state = 3;
			counterObjects[interruptNum]->count++;
		}
	}
	else if(state==3){
		if(currentPin && !currentPinState){
			state = 2;
			counterObjects[interruptNum]->count--;
		}
		else if(!currentPin && !currentPinState){
			state = 4;
			counterObjects[interruptNum]->count++;
		}
	}
	else if(state==4){
		if(!currentPin && currentPinState){
			state = 3;
			counterObjects[interruptNum]->count--;
		}
		else if(currentPin && !currentPinState){
			state = 1;
			counterObjects[interruptNum]->count++;
		}
	}
	counterObjects[interruptNum]->state = state;
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
	state = 1;

	attachInterrupt(pinAInterrupt, interruptHandlers[pinAInterrupt], CHANGE);
	attachInterrupt(pinBInterrupt, interruptHandlers[pinBInterrupt], CHANGE);
}


void ImperiumQuadEncoder::update(){
}

void ImperiumQuadEncoder::setValue(long value){
}

long ImperiumQuadEncoder::getValue(){
	return count | (state<<16);
}


