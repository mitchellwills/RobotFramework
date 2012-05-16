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
}


static void Imperium_readPacket(void){
	if( !Packet_read(readPacket) ){
		switch(readPacket->id){
		case PACKETID_PING_REQUEST:
			Packet_reset(sendPacket, PACKETID_PING_RESPONSE);
			Packet_write(sendPacket);
			break;
		default:
			Imperium_sendError(ERRORID_UNKNOWN_PACKET_ID, readPacket->id);
			break;
		}
	}
}


void Imperium_periodic(void){
	Imperium_readPacket();

	unsigned long time = millis();
	if( minUpdateDelay>=0 && (time-lastUpdate)>=minUpdateDelay){
		lastUpdate = time;

//		if(numObjects>0)
//			sendBulkInput();
	}

	for(int i = 0; i<numObjects; ++i){
		TYPE_update updateMethod = objects[i]->update;
		if(updateMethod!=NULL)
			updateMethod();
	}
}
