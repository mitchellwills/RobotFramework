/*
 * ImperiumDigitalOutput.h
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMDIGITALOUTPUT_H_
#define IMPERIUMDIGITALOUTPUT_H_

#include "ImperiumObject.h"

class ImperiumDigitalOutput: public ImperiumObject {
	public:
		static ImperiumObject* newDigitalOutput(int objectId, int* pins, int pinCount);

		ImperiumDigitalOutput(int objectId, int* pins, int pinCount);

		virtual void update();
		virtual void setValue(long value);
		virtual long getValue();
};

#endif /* IMPERIUMDIGITALOUTPUT_H_ */
