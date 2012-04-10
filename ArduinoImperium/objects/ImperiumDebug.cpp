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
}



void ImperiumDebug::update(){
}

void ImperiumDebug::setValue(long value){
}


long ImperiumDebug::getValue(){
	return 0;
}
