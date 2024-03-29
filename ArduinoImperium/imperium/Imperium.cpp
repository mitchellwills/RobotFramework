/*
 * Imperium.cpp
 *
 *  Created on: Mar 25, 2012
 *      Author: Mitchell
 */


#include "Imperium.h"
#include "PacketIds.h"
#include "ByteUtil.h"

static Stream* stream = NULL;
static ImperiumPacket sendPacket;
static ImperiumPacket readPacket;

static int numObjects = 0;
static ImperiumObject* objects[MAX_NUM_OBJECTS];
static ObjectInitializer objectInitializers[MAX_OBJECT_TYPES];

static int minUpdateDelay = 0;
static unsigned long lastUpdate = 0;

void initImperium(Stream & _stream){
	stream = &_stream;
	for(int i = 0; i<MAX_OBJECT_TYPES; ++i)
		objectInitializers[i] = NULL;
}

void setObjectTypeInitializer(int typeId, ObjectInitializer initializer){
	objectInitializers[typeId] = initializer;
}



void sendImperiumPacket(ImperiumPacket& packet){
	packet.write(*stream);
#if defined(__AVR_AT90USB1286__)
	Serial.send_now();
#endif
}
void sendImperiumInputPacket(int objectId, long value){
	sendPacket.setId(PACKETID_INPUT_VALUE);
	sendPacket.setDataLength(0);
	sendPacket.appendInteger(objectId, 1);
	sendPacket.appendInteger(value, 4);
	sendImperiumPacket(sendPacket);
}
void sendImperiumMessagePacket(int objectId, long* data, int dataSize, int dataLength){
	sendPacket.setId(PACKETID_MESSAGE);
	sendPacket.setDataLength(0);
	sendPacket.appendInteger(objectId, 1);
	sendPacket.appendInteger(dataSize, 1);
	for(int i = 0; i<dataLength; ++i)
		sendPacket.appendInteger(data[i], dataSize);
	sendImperiumPacket(sendPacket);
}

void sendImperiumMessagePacket(int objectId, char* data, int dataLength){
	sendPacket.setId(PACKETID_MESSAGE);
	sendPacket.setDataLength(0);
	sendPacket.appendInteger(objectId, 1);
	sendPacket.appendInteger(1, 1);
	for(int i = 0; i<dataLength; ++i)
		sendPacket.appendInteger(data[i], 1);
	sendImperiumPacket(sendPacket);
}





static void processGlobalConfigure(ImperiumPacket& packet){
	sendPacket.setId(PACKETID_CONFIGURE_CONFIRM);
	sendPacket.setDataLength(0);

	//TODO: Reset interrupts
	//TODO free ImperiumObjects

	packet.resetReadPosition();

	unsigned int updateRate = packet.readUInteger(2);
	if(updateRate==0)
		minUpdateDelay = -1;
	else
		minUpdateDelay = 1000/updateRate;

	numObjects = packet.readInteger(1);

	for(int i = 0; i<numObjects; ++i){
		int objectId = packet.readInteger(1);
		int typeId = packet.readInteger(1);
		int pinCount = packet.readInteger(1);
		int* pins = (int*)malloc(pinCount*sizeof(int*));
		for(int p = 0; p<pinCount; ++p){
			pins[p] = packet.readInteger(1);
		}
		if(objectInitializers[typeId]!=NULL){
			objects[objectId] =(*objectInitializers[typeId])(objectId, pins, pinCount);
			sendPacket.appendInteger(typeId, 1);
			sendPacket.appendInteger(0, 1);
		}
		else{
			sendPacket.appendInteger(typeId, 1);
			sendPacket.appendInteger(1, 1);
		}
	}

	sendImperiumPacket(sendPacket);
}
static void processConfigure(ImperiumPacket& packet){
	sendPacket.setId(PACKETID_CONFIGURE_CONFIRM);
	sendPacket.setDataLength(0);

	packet.resetReadPosition();

	numObjects++;

	int objectId = packet.readInteger(1);
	int typeId = packet.readInteger(1);
	int pinCount = packet.readInteger(1);
	int* pins = (int*)malloc(pinCount*sizeof(int*));
	for(int p = 0; p<pinCount; ++p){
		pins[p] = packet.readInteger(1);
	}
	if(objectInitializers[typeId]!=NULL){
		objects[objectId] =(*objectInitializers[typeId])(objectId, pins, pinCount);
		sendPacket.appendInteger(typeId, 1);
		sendPacket.appendInteger(0, 1);
	}
	else{
		sendPacket.appendInteger(typeId, 1);
		sendPacket.appendInteger(1, 1);
	}

	sendImperiumPacket(sendPacket);
}

static void processSet(ImperiumPacket& packet){
	packet.resetReadPosition();
	int objectId = packet.readUInteger(1);
	long value = packet.readInteger(2);
	objects[objectId]->setValue(value);
}
static void processPing(ImperiumPacket& packet){
	sendPacket.setDataLength(0);
	sendPacket.setId(PACKETID_PING_RESPONSE);
	sendImperiumPacket(sendPacket);
}
static void sendBulkInput(){
	sendPacket.setId(PACKETID_BULK_INPUT);
	sendPacket.setDataLength(0);
	sendPacket.appendInteger(numObjects, 1);
	for(int i = 0; i<numObjects; ++i){
		sendPacket.appendInteger(objects[i]->getValue(), 4);
	}
	sendImperiumPacket(sendPacket);
}
static void processMessage(ImperiumPacket& packet){
	packet.resetReadPosition();
	int objectId = packet.readUInteger(1);
	objects[objectId]->receiveMessage(packet);
}


static void readOnePacket(){
	int errorCode = 0;
	if(!(errorCode = readPacket.read(*stream))){
		switch(readPacket.getId()){
		case PACKETID_GLOBAL_CONFIGURE:
			processGlobalConfigure(readPacket);
			break;
		case PACKETID_CONFIGURE_OBJECT:
			processConfigure(readPacket);
			break;
		case PACKETID_SET_VALUE:
			processSet(readPacket);
			break;
		case PACKETID_PING_REQUEST:
			processPing(readPacket);
			break;
		case PACKETID_MESSAGE:
			processMessage(readPacket);
			break;
		default:
			errorCode = 5;
			break;
		}
	}
	if(errorCode!=0 && errorCode!=1){
		sendPacket.setId(PACKETID_ERROR_MESSAGE);
		sendPacket.setDataLength(0);
		sendPacket.appendInteger(errorCode, 1);
		sendImperiumPacket(sendPacket);
	}
}


void periodicImperium(){
	if(numObjects==0 || minUpdateDelay==-1)
		readOnePacket();

	unsigned long time = millis();
	if( minUpdateDelay>=0 && (time-lastUpdate)>=(unsigned)minUpdateDelay){
		lastUpdate = time;

		if(numObjects>0)
			sendBulkInput();
	}

	for(int i = 0; i<numObjects; ++i){
		objects[i]->update();
		readOnePacket();
	}
}
