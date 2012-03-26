/*
 * Imperium.cpp
 *
 *  Created on: Mar 25, 2012
 *      Author: Mitchell
 */


#include "Imperium.h"
#include "ImperiumPacket.h"
#include "PacketIds.h"
#include "ByteUtil.h"
#include "ImperiumObject.h"

static Stream* stream = NULL;

static int numObjects = 0;
static ImperiumObject** objects = NULL;


static int maxUpdateRate = 0;

void initImperium(Stream & _stream){
	stream = &_stream;
}

void sendPacket(ImperiumPacket& packet){
	packet.write(*stream);
}

static void processGlobalConfigure(ImperiumPacket& packet){
	if(objects!=NULL){
		//free
		free(objects);
	}

	packet.resetReadPosition();
	maxUpdateRate = packet.readInteger(1);

	numObjects = packet.readInteger(1);
	objects = malloc(numObjects * sizeof(ImperiumObject*));


	ImperiumPacket responsePacket;
	responsePacket.setId(PACKETID_CONFIGURE_CONFIRM);
	sendPacket(responsePacket);
}


static ImperiumPacket readPacket;
void periodicImperium(){
	if(!readPacket.read(*stream)){
		switch(readPacket.getId()){
		case PACKETID_GLOBAL_CONFIGURE:
			processGlobalConfigure(readPacket);
			break;
		}
	}
}
