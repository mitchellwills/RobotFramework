/*
 * ImperiumHM55B.h
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMHM55B_H_
#define IMPERIUMHM55B_H_

#include "ImperiumObject.h"
#include "Arduino.h"

#define MEASURE_RATE 100
#define MEASURE_WAIT_TIME 60

class ImperiumHM55B: public ImperiumObject {
	private:
		long lastMeasureCommand;
		bool measurementPending;

		long value;
		unsigned int enPin;
		unsigned int clkPin;
		unsigned int dioPin;

		void reset();
		void sendMeasureCommand();
		int sendReadCommand();
		long readValue();
	public:
		static ImperiumObject* newHM55B(int objectId, int* pins, int pinCount);

		ImperiumHM55B(int objectId, int* pins, int pinCount);

		virtual void update();
		virtual void receiveMessage(ImperiumPacket& packet);
		virtual void setValue(long value);
		virtual long getValue();
};


#endif /* IMPERIUMHM55B_H_ */
