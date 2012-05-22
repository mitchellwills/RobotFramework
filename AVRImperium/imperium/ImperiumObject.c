/*
 * ImperiumObject.cpp
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */
#include "ImperiumObject.h"
#include "stdlib.h"


ImperiumObject* Object_new(int _objectId, void* data){
	ImperiumObject* object = (ImperiumObject*)malloc(sizeof(ImperiumObject));
	object->objectId = _objectId;
	object->data = data;
	object->getValue = NULL;
	object->receiveMessage = NULL;
	object->setValue = NULL;
	object->update = NULL;
	object->inputSize = 0;
	object->outputSize = 0;
	return object;
}
