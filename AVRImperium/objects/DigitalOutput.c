/*
 * DigitalOutput.c
 *
 *  Created on: May 16, 2012
 *      Author: Mitchell
 */

#include "DigitalOutput.h"
#include "ImperiumPacket.h"
#include "stdlib.h"

void setValue(struct ImperiumObject* object, ImperiumPacket* packet){
	DigitalOutputData* objectData = (DigitalOutputData*)object->data;
	if(Packet_readByte(packet))
		setPinRegister( objectData->dataRegister, objectData->mask );
	else
		clearPinRegister( objectData->dataRegister, objectData->mask );
}

ImperiumObject* DigitalOutput_new(int objectId, int* data, int dataSize){
	DigitalOutputData* objectData = (DigitalOutputData*)malloc(sizeof(DigitalOutputData));
	ImperiumObject* object = Object_new(objectId, objectData);

	objectData->directionRegister = Pin_getDirectionRegister(data[0]);
	objectData->dataRegister = Pin_getDataRegister(data[0]);
	objectData->mask = Pin_getMask(data[0]);

	return object;
}


