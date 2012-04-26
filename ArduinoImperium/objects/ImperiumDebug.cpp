/*
 * ImperiumDebug.cpp
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#include "ImperiumDebug.h"
#include "Imperium.h"

ImperiumObject* ImperiumDebug::newDebug(int objectId, int* pins, int pinCount){
	return new ImperiumDebug(objectId, pins, pinCount);
}

ImperiumDebug::ImperiumDebug(int objectId, int* pins, int pinCount) : ImperiumObject(objectId, pins, pinCount){
	count = 0;
}



void ImperiumDebug::update(){
	if(millis()-lastUpdate>=1000){
		lastUpdate = millis();
		value = count;
		count = 0;
	}
	++count;
}
void ImperiumDebug::receiveMessage(ImperiumPacket& packet){
}

void ImperiumDebug::setValue(long value){
}


long ImperiumDebug::getValue(){
	return value;
}
