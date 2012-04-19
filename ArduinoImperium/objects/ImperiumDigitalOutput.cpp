/*
 * ImperiumDigitalOutput.cpp
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#include "ImperiumDigitalOutput.h"
#include "Arduino.h"

ImperiumObject* ImperiumDigitalOutput::newDigitalOutput(int objectId, int* pins, int pinCount){
	return new ImperiumDigitalOutput(objectId, pins, pinCount);
}

ImperiumDigitalOutput::ImperiumDigitalOutput(int objectId, int* pins, int pinCount) : ImperiumObject(objectId, pins, pinCount){
	pinMode(getPin(0), OUTPUT);
}


void ImperiumDigitalOutput::update(){

}
void ImperiumDigitalOutput::receiveMessage(ImperiumPacket& packet){
}
void ImperiumDigitalOutput::setValue(long value){
	digitalWrite(getPin(0), value!=0);
}

long ImperiumDigitalOutput::getValue(){
	return digitalRead(getPin(0));
}
