/*
 * ImperiumObject.h
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMOBJECT_H_
#define IMPERIUMOBJECT_H_

#include "ImperiumPacket.h"



struct ImperiumObject {
	int objectId;
	void* data;

	int inputSize;
	int outputSize;

	//methods
	void (*setValue)(struct ImperiumObject* object, ImperiumPacket* packet);
	void (*getValue)(struct ImperiumObject* object, ImperiumPacket* packet);
	void (*receiveMessage)(struct ImperiumObject* object, ImperiumPacket* packet);
	void (*update)(struct ImperiumObject* object);

};
typedef struct ImperiumObject ImperiumObject;


ImperiumObject* Object_new(int objectId, void* data);

#endif /* IMPERIUMOBJECT_H_ */
