/*
 * ImperiumMSPWMOutput.cpp
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#include "ImperiumMSPWMOutput.h"
#include "Arduino.h"

ImperiumObject* ImperiumMSPWMOutput::newMSPWMOutput(int objectId, int* pins, int pinCount){
	return new ImperiumMSPWMOutput(objectId, pins, pinCount);
}

ImperiumMSPWMOutput::ImperiumMSPWMOutput(int objectId, int* pins, int pinCount) : ImperiumObject(objectId, pins, pinCount){
	pinMode(getPin(0), OUTPUT);
	digitalWrite(getPin(0), LOW);
}


void ImperiumMSPWMOutput::update(){

}
void ImperiumMSPWMOutput::setValue(long value){
	if(value==MSPWM_DISABLED_VALUE){
		msOutput.detach();
	}
	else{
		if(!msOutput.attached())
			msOutput.attach(getPin(0));
		msOutput.writeMicroseconds(value);
	}
}
