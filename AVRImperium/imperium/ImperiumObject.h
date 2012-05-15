/*
 * ImperiumObject.h
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMOBJECT_H_
#define IMPERIUMOBJECT_H_

#include "ImperiumPacket.h"

typedef void (*TYPE_setValue)(long value);
typedef long (*TYPE_getValue)(void);
typedef void (*TYPE_receiveMessage)(ImperiumPacket* packet);
typedef void (*TYPE_update)(void);

struct ImperiumObject {
	int objectId;
	int* pins;
	int pinCount;

	//methods
	TYPE_setValue setValue;
	TYPE_getValue getValue;
	TYPE_receiveMessage receiveMessage;
	TYPE_update update;

};
typedef struct ImperiumObject ImperiumObject;

void Object_init(ImperiumObject* object, int _objectId, int* _pins, int _pinCount);

#endif /* IMPERIUMOBJECT_H_ */
