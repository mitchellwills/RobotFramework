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
	object->pin.isValid = 0;
	object->value = 0;

	object->getValue = NULL;
	object->receiveMessage = NULL;
	object->setValue = NULL;
	object->update = NULL;
	object->cleanup = NULL;

	object->inputSize = 0;
	object->outputSize = 0;
	return object;
}

void Object_cleanup(ImperiumObject* object){
	void (*cleanupMethod)(ImperiumObject* object) = object->cleanup;
	if(cleanupMethod!=NULL)
		cleanupMethod(object);
	free(object);
}
