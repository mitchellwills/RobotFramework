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
#if MSPWM_TIMEOUT!=0
	if(millis()-lastSet>MSPWM_TIMEOUT)
		setValue(MSPWM_DISABLED_VALUE);
#endif
}
void ImperiumMSPWMOutput::setValue(long value){
	lastSet = millis();
	if(value==MSPWM_DISABLED_VALUE){
		msOutput.detach();
	}
	else{
		if(!msOutput.attached())
			msOutput.attach(getPin(0));
		msOutput.writeMicroseconds(value);
	}
}

long ImperiumMSPWMOutput::getValue(){
	if(!msOutput.attached())
		return MSPWM_DISABLED_VALUE;
	return msOutput.readMicroseconds();
}
