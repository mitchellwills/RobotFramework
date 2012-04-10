/*
 * ImperiumPWMOutput.h
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMPWMOUTPUT_H_
#define IMPERIUMPWMOUTPUT_H_

#include "ImperiumObject.h"

class ImperiumPWMOutput: public ImperiumObject {
	private:
		long _value;
	public:
		static ImperiumObject* newPWMOutput(int objectId, int* pins, int pinCount);

		ImperiumPWMOutput(int objectId, int* pins, int pinCount);

		virtual void update();
		virtual void setValue(long value);
		virtual long getValue();
};

#endif /* IMPERIUMPWMOUTPUT_H_ */
