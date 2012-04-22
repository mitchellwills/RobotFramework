/*
 * Imperium.h
 *
 *  Created on: Mar 25, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUM_H_
#define IMPERIUM_H_

#include "Arduino.h"
#include "ImperiumObject.h"
#include "ImperiumPacket.h"

#define MAX_OBJECT_TYPES 30
#define MAX_NUM_OBJECTS 30

void initImperium(Stream & _stream);

void sendImperiumPacket(ImperiumPacket& packet);
void sendImperiumInputPacket(int objectId, long value);
void sendImperiumMessagePacket(int objectId, long* data, int dataSize, int dataLength);
void sendImperiumMessagePacket(int objectId, char* data, int dataLength);

typedef ImperiumObject* (*ObjectInitializer)(int objectId, int* pins, int pinCount);

void setObjectTypeInitializer(int typeId, ObjectInitializer initializer);

void periodicImperium();



#endif /* IMPERIUM_H_ */
