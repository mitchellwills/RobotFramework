/*
 * DigitalOutput.h
 *
 *  Created on: May 16, 2012
 *      Author: Mitchell
 */

#ifndef DIGITALOUTPUT_H_
#define DIGITALOUTPUT_H_

#include "ImperiumObject.h"
#include "AVRPins.h"

struct DigitalOutputData {
	PinRegister directionRegister;
	PinRegister dataRegister;
	PinMask mask;
};
typedef struct DigitalOutputData DigitalOutputData;

ImperiumObject* DigitalOutput_new(int objectId, int* data, int dataSize);

#endif /* DIGITALOUTPUT_H_ */
