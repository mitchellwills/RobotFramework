/*
 * ServoOutput.h
 *
 *  Created on: May 16, 2012
 *      Author: Mitchell
 */

#ifndef SERVOOUTPUT_H_
#define SERVOOUTPUT_H_

#include "ImperiumObject.h"
#include "AVRPins.h"

#define SERVO_TIMEOUT 300
#define SERVO_DISABLED_VALUE -1

ImperiumObject* ServoOutput_new(int objectId, char* data, int dataSize);

#endif /* SERVOOUTPUT_H_ */
