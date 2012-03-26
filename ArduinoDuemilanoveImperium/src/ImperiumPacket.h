/*
 * ImperiumPacket.h
 *
 *  Created on: Mar 25, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMPACKET_H_
#define IMPERIUMPACKET_H_

#include "Arduino.h"

#define IMPERIUM_PACKET_VERSION 1
#define IMPERIUM_PACKET_HEADER_SIZE 4
#define IMPERIUM_PACKET_MAX_DATA_SIZE 1024

class ImperiumPacket {
	private:
		unsigned int id;
		unsigned int dataLength;
		char data[IMPERIUM_PACKET_MAX_DATA_SIZE];
		int readPosition;
		int size(int dataSize);
		char calculateChecksum();

	public:
		ImperiumPacket();
		int read(Stream & stream);
		int write(Stream & stream);
		int getId();
		int getDataLength();
		char* getData();
		char getChecksum();
		int size();
		void setId(unsigned int id);
		int setData(char* data, unsigned int dataLength);

		int appendInteger(long value, unsigned int size);
		int setDataLength(int dataLength);

		void resetReadPosition();
		long readInteger(int size);
};






#endif /* IMPERIUMPACKET_H_ */
