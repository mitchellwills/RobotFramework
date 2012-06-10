/*
 * AnalogInput.c
 *
 *  Created on: May 16, 2012
 *      Author: Mitchell
 */

#include "AnalogInput.h"
#include "ImperiumPacket.h"
#include "stdlib.h"
#include "util/time.h"
#include "ADCManager.h"

static void getValue(ImperiumObject* object, ImperiumPacket* packet){
	Packet_appendInteger(packet, getADCChannelValue(object->value), 2);
}

static void cleanup(ImperiumObject* object){
	disableADCChannel(object->value);
}

ImperiumObject* AnalogInput_new(int objectId, char* data, int dataSize){
	ImperiumObject* object = Object_new(objectId, NULL);

	object->value = data[0];
	initADC();
	enableADCChannel(data[0]);

	object->inputSize = 2;

	object->getValue = getValue;
	object->cleanup = cleanup;

	initADC();

	return object;
}


