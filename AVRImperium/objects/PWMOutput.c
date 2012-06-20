/*
 * PWMOutput.c
 *
 *  Created on: May 16, 2012
 *      Author: Mitchell
 */

#include "PWMOutput.h"
#include "ImperiumPacket.h"
#include "stdlib.h"
#include "PWM.h"
#include "time.h"

static void setValue(ImperiumObject* object, ImperiumPacket* packet){
	uint8_t outputValue = Packet_readUInteger(packet, 1);
	if(outputValue<0)
		pwmDisable(&object->pin);
	else{
		pwmWrite(&object->pin, outputValue);
	}
}

static void cleanup(ImperiumObject* object){
	pwmDisable(&object->pin);
}

ImperiumObject* PWMOutput_new(int objectId, char* data, int dataSize){
	ImperiumObject* object = Object_new(objectId, NULL);
	initPin(&object->pin, data[0], 0);
	initPWM(&object->pin);

	object->outputSize = 1;
	object->setValue = setValue;
	object->cleanup = cleanup;

	return object;
}


