/*
 * DigitalInput.c
 *
 *  Created on: May 16, 2012
 *      Author: Mitchell
 */

#include "DigitalInput.h"
#include "ImperiumPacket.h"
#include "stdlib.h"
#include "util/time.h"
#include "LUFA/Drivers/Peripheral/ADC.h"

static void getValue(ImperiumObject* object, ImperiumPacket* packet){
	DigitalInputData* objectData = (DigitalInputData*)object->data;
	Packet_appendInteger(packet, getPinRegister(objectData->inputRegister, objectData->mask), 1);
}
static void setValue(ImperiumObject* object, ImperiumPacket* packet){
	DigitalInputData* objectData = (DigitalInputData*)object->data;
	if(Packet_readByte(packet))
		setPinRegister( objectData->pullupRegister, objectData->mask );
	else
		clearPinRegister( objectData->pullupRegister, objectData->mask );
}

ImperiumObject* DigitalInput_new(int objectId, char* data, int dataSize){
	DigitalInputData* objectData = (DigitalInputData*)malloc(sizeof(DigitalInputData));
	ImperiumObject* object = Object_new(objectId, objectData);

	objectData->directionRegister = Pin_getDirectionRegister(data[0]);
	objectData->pullupRegister = Pin_getDataRegister(data[0]);
	objectData->inputRegister = Pin_getInputRegister(data[0]);
	objectData->mask = Pin_getMask(data[0]);
	clearPinRegister( objectData->directionRegister, objectData->mask );

	object->outputSize = 1;
	object->inputSize = 1;
	object->getValue = getValue;
	object->setValue = setValue;

	return object;
}


