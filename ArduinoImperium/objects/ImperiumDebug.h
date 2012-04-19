/*
 * ImperiumDigitalOutput.h
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMDEBUG_H_
#define IMPERIUMDEBUG_H_

#include "ImperiumObject.h"

class ImperiumDebug: public ImperiumObject {
	private:
	public:
		static ImperiumObject* newDebug(int objectId, int* pins, int pinCount);

		ImperiumDebug(int objectId, int* pins, int pinCount);

		virtual void update();
		virtual void receiveMessage(ImperiumPacket& packet);
		virtual void setValue(long value);
		virtual long getValue();
};

#endif /* IMPERIUMDEBUG_H_ */
