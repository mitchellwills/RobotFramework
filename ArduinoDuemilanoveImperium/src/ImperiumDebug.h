/*
 * ImperiumDigitalOutput.h
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMDEBUG_H_
#define IMPERIUMDEBUG_H_

#include "ImperiumObject.h"

class ImperiumDebug: public ImperiumObject {
	public:
		static ImperiumObject* newDebug(int objectId, int* pins, int pinCount);

		ImperiumDebug(int objectId, int* pins, int pinCount);

		virtual void update();
		virtual void setValue(long value);
};

#endif /* IMPERIUMDEBUG_H_ */
