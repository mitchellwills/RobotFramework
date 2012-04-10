/*
 * ImperiumPWMOutput.cpp
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#include "ImperiumPWMOutput.h"
#include "Arduino.h"

ImperiumObject* ImperiumPWMOutput::newPWMOutput(int objectId, int* pins, int pinCount){
	return new ImperiumPWMOutput(objectId, pins, pinCount);
}

ImperiumPWMOutput::ImperiumPWMOutput(int objectId, int* pins, int pinCount) : ImperiumObject(objectId, pins, pinCount){
	pinMode(getPin(0), OUTPUT);
	digitalWrite(getPin(0), LOW);
}


void ImperiumPWMOutput::update(){

}
void ImperiumPWMOutput::setValue(long value){
	analogWrite(getPin(0), value);
	_value = value;
}

long ImperiumPWMOutput::getValue(){
	return _value;
}
