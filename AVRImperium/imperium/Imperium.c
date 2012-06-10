/*
 * Imperium.cpp
 *
 *  Created on: Mar 25, 2012
 *      Author: Mitchell
 */


#include <stdlib.h>
#include "Imperium.h"
#include "ByteUtil.h"
#include "time.h"
#include "PacketIds.h"
#include "TypeIds.h"
#include "ErrorIds.h"
#include "ImperiumPacket.h"
#include "ADCManager.h"

#undef NULL
#define NULL 0

static ImperiumPacket* sendPacket;
static ImperiumPacket* readPacket;
static ImperiumPacket* errorPacket;

static ObjectInitializer objectInitializers[MAX_OBJECT_TYPES];
static int numObjects = 0;
static ImperiumObject* objects[MAX_NUM_OBJECTS];

static unsigned long minUpdateDelay;
static unsigned long lastUpdate;


void Imperium_init(void){
	sendPacket = Packet_new(IMPERIUM_PACKET_MAX_DATA_SIZE);
	readPacket = Packet_new(IMPERIUM_PACKET_MAX_DATA_SIZE);
	errorPacket = Packet_new(100);

	for(int i = 0; i<MAX_OBJECT_TYPES; ++i)
		objectInitializers[i] = NULL;

	for(int i = 0; i<MAX_NUM_OBJECTS; ++i)
		objects[i] = NULL;

	minUpdateDelay = -1;
	lastUpdate = 0;

	Packet_reset(sendPacket, PACKETID_DEVICE_BOOT);
	Packet_write(sendPacket);
}

void Imperium_setObjectInitializer(int typeId, ObjectInitializer initializer){
	objectInitializers[typeId] = initializer;
}

void Imperium_sendError(int id, int simpleData){
	Packet_reset(errorPacket, PACKETID_ERROR_MESSAGE);
	Packet_appendInteger(errorPacket, id, 1);
	Packet_appendInteger(errorPacket, simpleData, 1);
	Packet_write(errorPacket);
	virtualSerialTask();
}


static void Imperium_readPacket(void){
	int numOldObjects;
	if( !Packet_read(readPacket) ){
		Packet_resetReadPosition(readPacket);
		switch(readPacket->id){
		case PACKETID_PING_REQUEST:
			Packet_reset(sendPacket, PACKETID_PING_RESPONSE);
			Packet_write(sendPacket);
			break;

		case PACKETID_GLOBAL_CONFIGURE_REQUEST:
			numOldObjects = numObjects;
			numObjects = 0;
			for(int i = 0; i<numOldObjects; ++i){
				ImperiumObject* object = objects[i];
				Object_cleanup(object);
			}
			minUpdateDelay = 1000/Packet_readUInteger(readPacket, 2);
			Packet_reset(sendPacket, PACKETID_GLOBAL_CONFIGURE_RESPONSE);
			Packet_write(sendPacket);
			break;

		case PACKETID_OBJECT_CONFIGURE_REQUEST:{
			int objectId = Packet_readUInteger(readPacket, 1);
			int typeId = Packet_readUInteger(readPacket, 1);
			ObjectInitializer objectInitializer = objectInitializers[typeId];
			if(objectInitializer==NULL){
				Imperium_sendError(ERRORID_UNKNOWN_TYPE_ID, typeId);
				return;
			}
			ImperiumObject* object = objectInitializer(objectId, Packet_getDataFromReadPosition(readPacket), readPacket->dataLength - 2);
			objects[objectId] = object;
			++numObjects;

			 Packet_reset(sendPacket, PACKETID_OBJECT_CONFIGURE_RESPONSE);
			Packet_appendInteger(sendPacket, objectId, 1);
			Packet_appendInteger(sendPacket, typeId, 1);
			Packet_appendInteger(sendPacket, object->inputSize, 1);
			Packet_appendInteger(sendPacket, object->outputSize, 1);
			Packet_write(sendPacket);
			break;
		}
		case PACKETID_BULK_OUTPUT_VALUE:
			for(int i = 0; i<numObjects; ++i){
				ImperiumObject* object = objects[i];
				void (*setValueMethod)(struct ImperiumObject* object, ImperiumPacket* packet) = object->setValue;
				if(setValueMethod!=NULL)
					setValueMethod(object, readPacket);
			}
			break;
		default:
			Imperium_sendError(ERRORID_UNKNOWN_PACKET_ID, readPacket->id);
			break;
		}
	}
}

static int x;
static void sendBulkInput(void){
	Packet_reset(sendPacket, PACKETID_BULK_INPUT_VALUE);
	for(int i = 0; i<numObjects; ++i){
		ImperiumObject* object = objects[i];
		void (*inputMethod)(ImperiumObject* object, ImperiumPacket* packet) = object->getValue;
		if(inputMethod!=NULL)
			inputMethod(object, sendPacket);
	}
	Packet_write(sendPacket);
}

void Imperium_periodic(void){
	Imperium_readPacket();

	unsigned long time = millis();
	if( minUpdateDelay>=0 && (time-lastUpdate)>=minUpdateDelay){
		lastUpdate = time;

		if(numObjects>0)
			sendBulkInput();
	}

	for(int i = 0; i<numObjects; ++i){
		ImperiumObject* object = objects[i];
		void (*updateMethod)(ImperiumObject* object) = object->update;
		if(updateMethod!=NULL)
			updateMethod(object);
	}
	updateADCRead();
}
