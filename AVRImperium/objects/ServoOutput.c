/*
 * DigitalOutput.c
 *
 *  Created on: May 16, 2012
 *      Author: Mitchell
 */

#include "ServoOutput.h"
#include "ImperiumPacket.h"
#include "stdlib.h"
#include "Servo.h"

static void setValue(ImperiumObject* object, ImperiumPacket* packet){
	uint16_t outputValue = Packet_readUInteger(packet, 2);
	writeServoMicroseconds(object->value, outputValue);
}

ImperiumObject* ServoOutput_new(int objectId, char* data, int dataSize){
	ImperiumObject* object = Object_new(objectId, NULL);
	object->value = newServo();
	attachServo(object->value, data[0]);
	initPin(&object->pin, data[0]);
	setPinOutput(&object->pin);

	object->outputSize = 2;
	object->setValue = setValue;

	return object;
}


