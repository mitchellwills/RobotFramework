/*
 * DigitalOutput.h
 *
 *  Created on: May 16, 2012
 *      Author: Mitchell
 */

#ifndef DEBUG_H_
#define DEBUG_H_

#include "ImperiumObject.h"

struct DebugData {
	long lastUpdate;
	long count;
	long value;
};
typedef struct DebugData DebugData;

ImperiumObject* Debug_new(int objectId, char* data, int dataSize);

#endif /* DEBUG_H_ */
