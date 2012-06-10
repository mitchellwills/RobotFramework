/*
 * AnalogInput.h
 *
 *  Created on: May 16, 2012
 *      Author: Mitchell
 */

#ifndef ANALOG_INPUT_H_
#define ANALOG_INPUT_H_

#include "ImperiumObject.h"
#include "AVRPins.h"

ImperiumObject* AnalogInput_new(int objectId, char* data, int dataSize);

#endif /* ANALOG_INPUT_H_ */
