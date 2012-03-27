/*
 * ImperiumDigitalInput.cpp
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#include "ImperiumDigitalInput.h"
#include "Arduino.h"
#include "Imperium.h"

ImperiumObject* ImperiumDigitalInput::newDigitalInput(int objectId, int* pins, int pinCount){
	return new ImperiumDigitalInput(objectId, pins, pinCount);
}

ImperiumDigitalInput::ImperiumDigitalInput(int objectId, int* pins, int pinCount) : ImperiumObject(objectId, pins, pinCount){
	pinMode(getPin(0), INPUT);
}


void ImperiumDigitalInput::update(){
	sendImperiumInputPacket(getObjectId(), digitalRead(getPin(0)));

}
void ImperiumDigitalInput::setValue(long value){
}
