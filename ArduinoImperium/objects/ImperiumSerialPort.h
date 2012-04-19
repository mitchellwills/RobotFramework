/*
 * ImperiumPPMReader.h
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMSERIALPORT_H_
#define IMPERIUMSERIALPORT_H_

#include "ImperiumObject.h"
#include "Arduino.h"

#define BAUD_300 0
#define BAUD_1200 1
#define BAUD_2400 2
#define BAUD_4800 3
#define BAUD_9600 4
#define BAUD_14400 5
#define BAUD_19200 6
#define BAUD_28800 7
#define BAUD_38400 8
#define BAUD_57600 9
#define BAUD_115200 10

#define BUFFER_SIZE 200

#define MIN_READ_DELAY 100


class ImperiumSerialPort: public ImperiumObject {
	private:
		Stream* port;
		char readBuffer[BUFFER_SIZE];
		uint8_t writeBuffer[BUFFER_SIZE];
		long lastRead;
	public:
		static ImperiumObject* newSerialPort(int objectId, int* pins, int pinCount);

		ImperiumSerialPort(int objectId, int* pins, int pinCount);

		virtual void update();
		virtual void receiveMessage(ImperiumPacket& packet);
		virtual void setValue(long value);
		virtual long getValue();
};


#endif /* IMPERIUMSERIALPORT_H_ */
