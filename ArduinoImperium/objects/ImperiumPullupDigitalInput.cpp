/*
 * ImperiumPullupDigitalInput.cpp
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#include "ImperiumPullupDigitalInput.h"
#include "Arduino.h"
#include "Imperium.h"

ImperiumObject* ImperiumPullupDigitalInput::newPullupDigitalInput(int objectId, int* pins, int pinCount){
	return new ImperiumPullupDigitalInput(objectId, pins, pinCount);
}

ImperiumPullupDigitalInput::ImperiumPullupDigitalInput(int objectId, int* pins, int pinCount) : ImperiumObject(objectId, pins, pinCount){
	pinMode(getPin(0), INPUT);
	digitalWrite(getPin(0), HIGH);//enable pullup
}


void ImperiumPullupDigitalInput::update(){
}
void ImperiumPullupDigitalInput::receiveMessage(ImperiumPacket& packet){
}

void ImperiumPullupDigitalInput::setValue(long value){
}

long ImperiumPullupDigitalInput::getValue(){
	return digitalRead(getPin(0));
}
