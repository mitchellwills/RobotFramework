/*
 * ImperiumDigitalInput.h
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMDIGITALINPUT_H_
#define IMPERIUMDIGITALINPUT_H_

#include "ImperiumObject.h"
#include "Arduino.h"

class ImperiumDigitalInput: public ImperiumObject {
	private:
		boolean lastSent;
		boolean sentInitial;
	public:
		static ImperiumObject* newDigitalInput(int objectId, int* pins, int pinCount);

		ImperiumDigitalInput(int objectId, int* pins, int pinCount);

		virtual void update();
		virtual void setValue(long value);
};

#endif /* IMPERIUMDIGITALINPUT_H_ */
