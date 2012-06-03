/*
 * Debug.c
 *
 *  Created on: May 16, 2012
 *      Author: Mitchell
 */

#include "Debug.h"
#include "ImperiumPacket.h"
#include "stdlib.h"
#include "util/time.h"

static void getValue(ImperiumObject* object, ImperiumPacket* packet){
	DebugData* objectData = (DebugData*)object->data;
	Packet_appendInteger(packet, objectData->value, 2);
}

static void update(ImperiumObject* object){
	DebugData* objectData = (DebugData*)object->data;

	if(millis()-objectData->lastUpdate>=1000){
		objectData->lastUpdate = millis();
		objectData->value = objectData->count;
		objectData->count = 0;
	}
	++objectData->count;
}

static void cleanup(ImperiumObject* object){
	free(object->data);
}

ImperiumObject* Debug_new(int objectId, char* data, int dataSize){
	DebugData* objectData = (DebugData*)malloc(sizeof(DebugData));
	ImperiumObject* object = Object_new(objectId, objectData);

	object->getValue = getValue;
	object->update = update;
	object->cleanup = cleanup;

	object->inputSize = 2;

	objectData->lastUpdate = millis();
	objectData->value = objectData->count;
	objectData->count = 0;

	return object;
}


