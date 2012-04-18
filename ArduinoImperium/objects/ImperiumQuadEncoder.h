/*
 * ImperiumQuadEncoder.h
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMQUADENCODER_H_
#define IMPERIUMQUADENCODER_H_

#include "ImperiumObject.h"
#include "Arduino.h"


class ImperiumQuadEncoder: public ImperiumObject {
	private:
	public:
		unsigned int pinAInterrupt;
		unsigned int pinBInterrupt;
		int last;
		volatile long count;
		static ImperiumObject* newQuadEncoder(int objectId, int* pins, int pinCount);

		ImperiumQuadEncoder(int objectId, int* pins, int pinCount);

		virtual void update();
		virtual void setValue(long value);
		virtual long getValue();
};


#endif /* IMPERIUMQUADENCODER_H_ */
