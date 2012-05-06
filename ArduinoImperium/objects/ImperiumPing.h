/*
 * ImperiumPing.h
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMPING_H_
#define IMPERIUMPING_H_

#include "ImperiumObject.h"
#include "Arduino.h"

#define PRE_COMMAND_TIME 200
#define TOTAL_COMMAND_TIME PRE_COMMAND_TIME+1

#define PRE_COMMAND_STATE 0
#define COMMAND_STATE 1
#define WAITING_MESSAGE 2

class ImperiumPing: public ImperiumObject {
	private:
		unsigned int interruptNum;
	public:
		volatile unsigned long measureStart;
		volatile unsigned long measureEnd;
		volatile int state;

		volatile unsigned long lastPing;
		static ImperiumObject* newPing(int objectId, int* pins, int pinCount);

		ImperiumPing(int objectId, int* pins, int pinCount);

		virtual void update();
		virtual void receiveMessage(ImperiumPacket& packet);
		virtual void setValue(long value);
		virtual long getValue();
};


#endif /* IMPERIUMPING_H_ */
