/*
 * ImperiumObject.cpp
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */
#include "ImperiumObject.h"



ImperiumObject::ImperiumObject(int _objectId, int* _pins, int _pinCount){
	objectId = _objectId;
	pins = _pins;
	pinCount = _pinCount;
}

int ImperiumObject::getObjectId(){
	return objectId;
}
int ImperiumObject::getPin(int index){
	return pins[index];
}
int ImperiumObject::getPinCount(){
	return pinCount;
}
