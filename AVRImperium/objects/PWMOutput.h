/*
 * PWMOutput.h
 *
 *  Created on: May 16, 2012
 *      Author: Mitchell
 */

#ifndef PWMOUTPUT_H_
#define PWMOUTPUT_H_

#include "ImperiumObject.h"
#include "AVRPins.h"

ImperiumObject* PWMOutput_new(int objectId, char* data, int dataSize);

#endif /* PWMOUTPUT_H_ */
