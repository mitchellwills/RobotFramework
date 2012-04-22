/*
 * ImperiumFrequency.h
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMFREQUENCY_H_
#define IMPERIUMFREQUENCY_H_

#include "ImperiumObject.h"
#include "Arduino.h"


class ImperiumFrequency: public ImperiumObject {
	private:
		unsigned int interruptNum;
	public:
		volatile unsigned long last;
		volatile unsigned long count;
		volatile unsigned long frequency;
		static ImperiumObject* newFrequency(int objectId, int* pins, int pinCount);

		ImperiumFrequency(int objectId, int* pins, int pinCount);

		virtual void update();
		virtual void receiveMessage(ImperiumPacket& packet);
		virtual void setValue(long value);
		virtual long getValue();
};


#endif /* IMPERIUMFREQUENCY_H_ */
