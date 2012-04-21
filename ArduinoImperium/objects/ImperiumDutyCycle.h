/*
 * ImperiumDutyCycle.h
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMDUTYCYCLE_H_
#define IMPERIUMDUTYCYCLE_H_

#include "ImperiumObject.h"
#include "Arduino.h"


class ImperiumDutyCycle: public ImperiumObject {
	private:
		unsigned int interruptNum;
	public:
		volatile unsigned long lastRising;
		volatile unsigned long lastFalling;
		volatile unsigned long duty;
		static ImperiumObject* newDutyCycle(int objectId, int* pins, int pinCount);

		ImperiumDutyCycle(int objectId, int* pins, int pinCount);

		virtual void update();
		virtual void receiveMessage(ImperiumPacket& packet);
		virtual void setValue(long value);
		virtual long getValue();
};


#endif /* IMPERIUMDUTYCYCLE_H_ */
