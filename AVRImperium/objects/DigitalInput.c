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

static void getValue(ImperiumObject* object, ImperiumPacket* packet){
	Packet_appendInteger(packet, getPinInput(&object->pin), 1);
}
static void setValue(ImperiumObject* object, ImperiumPacket* packet){
	if(Packet_readByte(packet))
		setPinHigh(&object->pin);
	else
		setPinLow(&object->pin);
}

static void cleanup(ImperiumObject* object){
	setPinLow(&object->pin);
}

ImperiumObject* DigitalInput_new(int objectId, char* data, int dataSize){
	ImperiumObject* object = Object_new(objectId, NULL);

	initPin(&object->pin, data[0], 1);
	setPinInput(&object->pin);

	object->outputSize = 1;
	object->inputSize = 1;

	object->getValue = getValue;
	object->setValue = setValue;
	object->cleanup = cleanup;

	return object;
}


