/*
 * ImperiumObject.h
 *
 *  Created on: Mar 26, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMOBJECT_H_
#define IMPERIUMOBJECT_H_


class ImperiumObject {
	private:
		int objectId;
		int* pins;
		int pinCount;

	public:
		ImperiumObject(int objectId, int* pins, int pinCount);
		int getObjectId();
		int getPin(int index);
		int getPinCount();
		virtual void update() = 0;
		virtual void setValue(long value) = 0;
		virtual long getValue() = 0;

};


#endif /* IMPERIUMOBJECT_H_ */
