/*
 * ServoOutput.c
 *
 *  Created on: May 16, 2012
 *      Author: Mitchell
 */

#include "ServoOutput.h"
#include "ImperiumPacket.h"
#include "stdlib.h"
#include "Servo.h"
#include "time.h"

static void setValue(ImperiumObject* object, ImperiumPacket* packet){
	object->time = millis();
	uint16_t outputValue = Packet_readUInteger(packet, 2);
	if(outputValue<0)
		disableServo(object->value);
	else{
		enableServo(object->value);
		writeServoMicroseconds(object->value, outputValue);
	}
}

static void update(ImperiumObject* object){
#if SERVO_TIMEOUT>0
	if(millis()-object->time>SERVO_TIMEOUT)
		disableServo(object->value);
#endif
}

static void cleanup(ImperiumObject* object){
	detachServo(object->value);
}

ImperiumObject* ServoOutput_new(int objectId, char* data, int dataSize){
	ImperiumObject* object = Object_new(objectId, NULL);
	object->value = newServo();
	attachServo(object->value, data[0]);
	initPin(&object->pin, data[0], 0);
	setPinOutput(&object->pin);

	object->outputSize = 2;
	object->setValue = setValue;
	object->update = update;
	object->cleanup = cleanup;

	return object;
}


