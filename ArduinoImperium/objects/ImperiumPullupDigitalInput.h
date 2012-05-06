/*
 * ImperiumPullupDigitalInput.h
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMPULLUPDIGITALINPUT_H_
#define IMPERIUMPULLUPDIGITALINPUT_H_

#include "ImperiumObject.h"
#include "Arduino.h"

class ImperiumPullupDigitalInput: public ImperiumObject {
	private:
	public:
		static ImperiumObject* newPullupDigitalInput(int objectId, int* pins, int pinCount);

		ImperiumPullupDigitalInput(int objectId, int* pins, int pinCount);

		virtual void update();
		virtual void receiveMessage(ImperiumPacket& packet);
		virtual void setValue(long value);
		virtual long getValue();
};

#endif /* IMPERIUMPULLUPDIGITALINPUT_H_ */
