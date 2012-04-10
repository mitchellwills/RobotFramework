/*
 * ImperiumAnalogInput.h
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMANALOGINPUT_H_
#define IMPERIUMANALOGINPUT_H_

#include "ImperiumObject.h"
#include "Arduino.h"

class ImperiumAnalogInput: public ImperiumObject {
	private:
	public:
		static ImperiumObject* newAnalogInput(int objectId, int* pins, int pinCount);

		ImperiumAnalogInput(int objectId, int* pins, int pinCount);

		virtual void update();
		virtual void setValue(long value);
		virtual long getValue();
};

#endif /* IMPERIUMANALOGINPUT_H_ */
