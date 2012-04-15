/*
 * ImperiumPPMReader.h
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMPPMREADER_H_
#define IMPERIUMPPMREADER_H_

#include "ImperiumObject.h"
#include "Arduino.h"


class ImperiumPPMReader: public ImperiumObject {
	private:
		unsigned int interruptNum;
	public:
		volatile long lastRise;
		volatile long lastFall;

		volatile int numChannels;
		long volatile* channels;
		volatile int currentChannel;
		volatile long frameId;
		long lastSentFrame;

		static ImperiumObject* newPPMReader(int objectId, int* pins, int pinCount);

		ImperiumPPMReader(int objectId, int* pins, int pinCount);

		virtual void update();
		virtual void setValue(long value);
		virtual long getValue();
};


#endif /* IMPERIUMPPMREADER_H_ */
