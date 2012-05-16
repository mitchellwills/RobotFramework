/*
 * Imperium.h
 *
 *  Created on: Mar 25, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUM_H_
#define IMPERIUM_H_

#include "ImperiumObject.h"
#include "ImperiumPacket.h"

#define MAX_OBJECT_TYPES 30
#define MAX_NUM_OBJECTS 30

extern int available(void);
extern int read(void);
extern void write(char b);
extern void writeBytes(char* b, int num);
extern void readBytes(char* b, int num);

void Imperium_init(void);

void sendImperiumPacket(ImperiumPacket* packet);
void sendImperiumInputPacket(int objectId, long value);
void sendImperiumMessagePacket(int objectId, char* data, int dataLength);

typedef ImperiumObject* (*ObjectInitializer)(int objectId, int* pins, int pinCount);

void setObjectTypeInitializer(int typeId, ObjectInitializer initializer);

void Imperium_periodic(void);



#endif /* IMPERIUM_H_ */
