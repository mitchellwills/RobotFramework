/*
 * ImperiumPulseCounter.h
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMPULSECOUNTER_H_
#define IMPERIUMPULSECOUNTER_H_

#include "ImperiumObject.h"
#include "Arduino.h"


class ImperiumPulseCounter: public ImperiumObject {
	private:
		unsigned int interruptNum;
	public:
		volatile unsigned long count;
		static ImperiumObject* newPulseCounter(int objectId, int* pins, int pinCount);

		ImperiumPulseCounter(int objectId, int* pins, int pinCount);

		virtual void update();
		virtual void setValue(long value);
		virtual long getValue();
};


#endif /* IMPERIUMPULSECOUNTER_H_ */
