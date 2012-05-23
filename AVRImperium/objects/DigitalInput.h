/*
 * DigitalInput.h
 *
 *  Created on: May 16, 2012
 *      Author: Mitchell
 */

#ifndef DIGITAL_INPUT_H_
#define DIGITAL_INPUT_H_

#include "ImperiumObject.h"
#include "AVRPins.h"

struct DigitalInputData {
	PinRegister pullupRegister;
	PinRegister directionRegister;
	PinRegister inputRegister;
	PinMask mask;
};

typedef struct DigitalInputData DigitalInputData;

ImperiumObject* DigitalInput_new(int objectId, char* data, int dataSize);

#endif /* DIGITAL_INPUT_H_ */
