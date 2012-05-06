/*
 * ImperiumPing.cpp
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */


#ifndef LOW_MEMORY


#include "ImperiumPing.h"
#include "pins.h"


static ImperiumPing* pingObjects[NUM_INTERRUPTS];
static inline void intHandler(int interruptNum, int pin){
	ImperiumPing* pingObject = pingObjects[interruptNum];
	if(pingObject->state==WAITING_MESSAGE){
		if(digitalRead(pin)){//rising edge
			pingObject->measureStart = micros();
		}
		else{//falling edge
			pingObject->measureEnd = micros();
			pingObject->lastPing = pingObject->measureEnd - pingObject->measureStart;
			pingObject->state = PRE_COMMAND_STATE;
		}
	}
}



#define INTERRUPT_HANDLER intHandler
#include "interrupts.h"
#undef INTERRUPT_HANDLER


ImperiumObject* ImperiumPing::newPing(int objectId, int* pins, int pinCount){
	return new ImperiumPing(objectId, pins, pinCount);
}

ImperiumPing::ImperiumPing(int objectId, int* pins, int pinCount) : ImperiumObject(objectId, pins, pinCount){
	pinMode(getPin(0), OUTPUT);
	digitalWrite(getPin(0), LOW);

	lastPing = 0;
	state = PRE_COMMAND_STATE;
	measureStart = 0;
	measureEnd = millis();

	interruptNum = digitalPinToInterrupt(getPin(0));
	pingObjects[interruptNum] = this;
	attachInterrupt(interruptNum, interruptHandlers[interruptNum], CHANGE);
}


void ImperiumPing::update(){
	if(state==PRE_COMMAND_STATE){
		pinMode(getPin(0), OUTPUT);
		digitalWrite(getPin(0), LOW);
		if(millis()-measureEnd>PRE_COMMAND_TIME){
			state = COMMAND_STATE;
			digitalWrite(getPin(0), HIGH);
		}
	}
	else if(state==COMMAND_STATE){
		if(millis()-measureEnd>TOTAL_COMMAND_TIME){
			pinMode(getPin(0), INPUT);
			digitalWrite(getPin(0), LOW);
			state = WAITING_MESSAGE;
		}
	}
}
void ImperiumPing::receiveMessage(ImperiumPacket& packet){
}

void ImperiumPing::setValue(long value){
}

long ImperiumPing::getValue(){
	return lastPing;
}

#endif
