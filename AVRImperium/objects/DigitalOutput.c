/*
 * DigitalOutput.c
 *
 *  Created on: May 16, 2012
 *      Author: Mitchell
 */

#include "DigitalOutput.h"
#include "ImperiumPacket.h"
#include "stdlib.h"

static void setValue(ImperiumObject* object, ImperiumPacket* packet){
	if(Packet_readByte(packet))
		setPinHigh(&object->pin);
	else
		setPinLow(&object->pin);
}

ImperiumObject* DigitalOutput_new(int objectId, char* data, int dataSize){
	ImperiumObject* object = Object_new(objectId, NULL);

	initPin(&object->pin, data[0]);
	setPinOutput(&object->pin);

	object->outputSize = 1;
	object->setValue = setValue;

	return object;
}


