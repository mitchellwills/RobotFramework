/*
 * ImperiumLEDDisplay.cpp
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#include "ImperiumLEDDisplay.h"
#include "Arduino.h"

ImperiumObject* ImperiumLEDDisplay::newLEDDisplay(int objectId, int* pins, int pinCount){
	return new ImperiumLEDDisplay(objectId, pins, pinCount);
}

ImperiumLEDDisplay::ImperiumLEDDisplay(int objectId, int* pins, int pinCount) : ImperiumObject(objectId, pins, pinCount){
	position = 0;
	lastChange = 0;

	outputCount = getPin(0);
	positionCount = getPin(1);


	outputPins = (int*)malloc(sizeof(int)*outputCount);
	for(int i = 0; i<outputCount; ++i){
		outputPins[i] = getPin(i+2);
		pinMode(outputPins[i], OUTPUT);
	}

	positionPins = (int*)malloc(sizeof(int)*positionCount);
	for(int i = 0; i<positionCount; ++i){
		positionPins[i] = getPin(outputCount+i+2);
		pinMode(positionPins[i], OUTPUT);
	}
}


void ImperiumLEDDisplay::update(){
	if(millis()-lastChange>=UPDATE_DELAY){
		lastChange = millis();
		position++;
		if(position>=positionCount)
			position = 0;

		for(int i = 0; i<positionCount; ++i)
			digitalWrite(positionPins[i], HIGH);

		for(int i = 0; i<outputCount; ++i)
			digitalWrite(outputPins[i], (value>>(outputCount*position + i))&1 );


		digitalWrite(positionPins[position], LOW);
	}
}
void ImperiumLEDDisplay::receiveMessage(ImperiumPacket& packet){
	packet.resetReadPosition();
	packet.readInteger(2);//read past header //TODO replace with constant, also see serial port
	value = packet.readUInteger(4);
}
void ImperiumLEDDisplay::setValue(long _value){

}

long ImperiumLEDDisplay::getValue(){
	return value;
}
