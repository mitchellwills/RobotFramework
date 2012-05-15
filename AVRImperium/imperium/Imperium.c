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

#undef NULL
#define NULL 0

static ImperiumPacket sendPacket;
static ImperiumPacket readPacket;

static int numObjects = 0;
static ImperiumObject* objects[MAX_NUM_OBJECTS];
static ObjectInitializer objectInitializers[MAX_OBJECT_TYPES];

static int minUpdateDelay = 0;
static unsigned long lastUpdate = 0;

void initImperium(void){
	for(int i = 0; i<MAX_OBJECT_TYPES; ++i)
		objectInitializers[i] = NULL;
}

void setObjectTypeInitializer(int typeId, ObjectInitializer initializer){
	objectInitializers[typeId] = initializer;
}

void sendImperiumInputPacket(int objectId, long value){
	Packet_reset(&sendPacket, PACKETID_INPUT_VALUE);
	Packet_appendInteger(&sendPacket, objectId, 1);
	Packet_appendInteger(&sendPacket, value, 4);
	Packet_write(&sendPacket);
}

void sendImperiumMessagePacket(int objectId, char* data, int dataLength){
	Packet_reset(&sendPacket, PACKETID_MESSAGE);
	Packet_appendInteger(&sendPacket, objectId, 1);
	for(int i = 0; i<dataLength; ++i)
		Packet_appendInteger(&sendPacket, data[i], 1);
	Packet_write(&sendPacket);
}





static void processGlobalConfigure(ImperiumPacket* packet){
	Packet_reset(&sendPacket, PACKETID_CONFIGURE_CONFIRM);

	//TODO: Reset interrupts
	//TODO free ImperiumObjects

	Packet_resetRWPosition(packet);

	unsigned int updateRate = Packet_readUInteger(packet, 2);
	if(updateRate==0)
		minUpdateDelay = -1;
	else
		minUpdateDelay = 1000/updateRate;

	Packet_write(&sendPacket);
}
static void processConfigure(ImperiumPacket* packet){
	Packet_reset(&sendPacket, PACKETID_CONFIGURE_CONFIRM);

	Packet_resetRWPosition(packet);

	numObjects++;

	int objectId = Packet_readInteger(packet, 1);
	int typeId = Packet_readInteger(packet, 1);
	int pinCount = Packet_readInteger(packet, 1);

	int* pins = (int*)malloc(pinCount*sizeof(int));
	for(int p = 0; p<pinCount; ++p){
		pins[p] = Packet_readInteger(packet, 1);
	}

	if(objectInitializers[typeId]!=NULL){
		objects[objectId] = (*objectInitializers[typeId])(objectId, pins, pinCount);
		Packet_appendInteger(&sendPacket, typeId, 1);
		Packet_appendInteger(&sendPacket, 0, 1);
	}
	else{
		Packet_appendInteger(&sendPacket, typeId, 1);
		Packet_appendInteger(&sendPacket, 1, 1);
	}

	Packet_write(&sendPacket);
}

static void processSet(ImperiumPacket* packet){
	Packet_resetRWPosition(packet);
	int objectId = Packet_readUInteger(packet, 1);
	long value = Packet_readInteger(packet, 2);
	objects[objectId]->setValue(value);
}
static void processPing(ImperiumPacket* packet){
	Packet_reset(&sendPacket, PACKETID_PING_RESPONSE);
	Packet_write(&sendPacket);
}
static void sendBulkInput(void){
	Packet_reset(&sendPacket, PACKETID_BULK_INPUT);
	Packet_appendInteger(&sendPacket, numObjects, 1);
	for(int i = 0; i<numObjects; ++i){
		Packet_appendInteger(&sendPacket, objects[i]->getValue(), 4);
	}
	Packet_write(&sendPacket);
}
static void processMessage(ImperiumPacket* packet){
	Packet_resetRWPosition(packet);
	int objectId = Packet_readUInteger(packet, 1);
	objects[objectId]->receiveMessage(packet);
}


static void readOnePacket(void){
	int errorCode = 0;
	if(!(errorCode = Packet_read(&readPacket))){
		switch(readPacket.id){
		case PACKETID_GLOBAL_CONFIGURE:
			processGlobalConfigure(&readPacket);
			break;
		case PACKETID_CONFIGURE_OBJECT:
			processConfigure(&readPacket);
			break;
		case PACKETID_SET_VALUE:
			processSet(&readPacket);
			break;
		case PACKETID_PING_REQUEST:
			processPing(&readPacket);
			break;
		case PACKETID_MESSAGE:
			processMessage(&readPacket);
			break;
		default:
			errorCode = 5;
			break;
		}
	}
	if(errorCode!=0 && errorCode!=1){
		Packet_reset(&sendPacket, PACKETID_ERROR_MESSAGE);
		Packet_appendInteger(&sendPacket, errorCode, 1);
		Packet_write(&sendPacket);
	}
}


void periodicImperium(void){
	if(numObjects==0 || minUpdateDelay==-1)
		readOnePacket();

	unsigned long time = millis();
	if( minUpdateDelay>=0 && (time-lastUpdate)>=(unsigned)minUpdateDelay){
		lastUpdate = time;

		//if(numObjects>0)
		//	sendBulkInput();
	}

	for(int i = 0; i<numObjects; ++i){
		//objects[i]->update();
		readOnePacket();
	}
}
