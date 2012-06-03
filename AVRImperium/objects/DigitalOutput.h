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

ImperiumObject* DigitalOutput_new(int objectId, char* data, int dataSize);

#endif /* DIGITALOUTPUT_H_ */
