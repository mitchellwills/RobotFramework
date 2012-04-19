/*
 * ImperiumMSPWMOutput.h
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMMSPWMOUTPUT_H_
#define IMPERIUMMSPWMOUTPUT_H_

#include "ImperiumObject.h"
#include "Servo.h"

#define MSPWM_TIMEOUT 300

#define MSPWM_DISABLED_VALUE (-1)

class ImperiumMSPWMOutput: public ImperiumObject {
	private:
		Servo msOutput;
		long lastSet;
	public:
		static ImperiumObject* newMSPWMOutput(int objectId, int* pins, int pinCount);

		ImperiumMSPWMOutput(int objectId, int* pins, int pinCount);

		virtual void update();
		virtual void receiveMessage(ImperiumPacket& packet);
		virtual void setValue(long value);
		virtual long getValue();
};

#endif /* IMPERIUMMSPWMOUTPUT_H_ */
