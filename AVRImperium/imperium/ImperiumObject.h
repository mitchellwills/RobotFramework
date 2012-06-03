/*
 * ImperiumObject.h
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMOBJECT_H_
#define IMPERIUMOBJECT_H_

#include "ImperiumPacket.h"
#include "AVRPins.h"



struct ImperiumObject {
	int objectId;
	AVRPin_t pin;
	unsigned long time;
	unsigned int value;
	void* data;

	int inputSize;
	int outputSize;

	//methods
	void (*setValue)(struct ImperiumObject* object, ImperiumPacket* packet);
	void (*getValue)(struct ImperiumObject* object, ImperiumPacket* packet);
	void (*receiveMessage)(struct ImperiumObject* object, ImperiumPacket* packet);
	void (*update)(struct ImperiumObject* object);
	void (*cleanup)(struct ImperiumObject* object);

};
typedef struct ImperiumObject ImperiumObject;


ImperiumObject* Object_new(int objectId, void* data);
void Object_cleanup(ImperiumObject* object);

#endif /* IMPERIUMOBJECT_H_ */
