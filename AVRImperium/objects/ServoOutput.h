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

ImperiumObject* ServoOutput_new(int objectId, char* data, int dataSize);

#endif /* SERVOOUTPUT_H_ */
