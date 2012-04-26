/*
 * ImperiumLEDDisplay.h
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMLEDDISPLAY_H_
#define IMPERIUMLEDDISPLAY_H_

#include "ImperiumObject.h"

#define UPDATE_DELAY 3

class ImperiumLEDDisplay: public ImperiumObject {
	private:
		unsigned long value;

		long lastChange;

		int position;
		int positionCount;
		int* positionPins;

		int outputCount;
		int* outputPins;

	public:
		static ImperiumObject* newLEDDisplay(int objectId, int* pins, int pinCount);

		ImperiumLEDDisplay(int objectId, int* pins, int pinCount);

		virtual void update();
		virtual void receiveMessage(ImperiumPacket& packet);
		virtual void setValue(long value);
		virtual long getValue();
};

#endif /* IMPERIUMLEDDISPLAY_H_ */
