/*
 * ImperiumObject.cpp
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */
#include "ImperiumObject.h"


void Object_init(ImperiumObject* object, int _objectId, int* _pins, int _pinCount){
	object->objectId = _objectId;
	object->pins = _pins;
	object->pinCount = _pinCount;
}
