/*
 * ImperiumDebug.cpp
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#include "ImperiumDebug.h"
#include "Imperium.h"
#include "MemoryFree.h"

ImperiumObject* ImperiumDebug::newDebug(int objectId, int* pins, int pinCount){
	return new ImperiumDebug(objectId, pins, pinCount);
}

ImperiumDebug::ImperiumDebug(int objectId, int* pins, int pinCount) : ImperiumObject(objectId, pins, pinCount){

}


void ImperiumDebug::update(){
	sendImperiumInputPacket(getObjectId(), freeMemory());
}

void ImperiumDebug::setValue(long value){
}
