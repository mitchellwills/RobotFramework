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
	updateCount = 0;
	lastUpdate = 0;
	countTotal = 0;
	lastSend = 0;
}



void ImperiumDebug::update(){
	/*countTotal += (millis()-lastUpdate);
	++updateCount;

	if(millis()-lastSend>1000){
		sendImperiumInputPacket(getObjectId(), countTotal/updateCount);
		lastSend = millis();
	}

	lastUpdate = millis();*/
}

void ImperiumDebug::setValue(long value){
}
