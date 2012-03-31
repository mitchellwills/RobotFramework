/*
 * ImperiumAnalogInput.cpp
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#include "ImperiumAnalogInput.h"
#include "Arduino.h"
#include "Imperium.h"

ImperiumObject* ImperiumAnalogInput::newAnalogInput(int objectId, int* pins, int pinCount){
	return new ImperiumAnalogInput(objectId, pins, pinCount);
}

ImperiumAnalogInput::ImperiumAnalogInput(int objectId, int* pins, int pinCount) : ImperiumObject(objectId, pins, pinCount){
	pinMode(getPin(0), INPUT);
	lastSent = -1;
}


void ImperiumAnalogInput::update(){
	int value = analogRead(getPin(0));
	if(lastSent!=value){
		lastSent = value;
		sendImperiumInputPacket(getObjectId(), value);
	}
}
void ImperiumAnalogInput::setValue(long value){
}
