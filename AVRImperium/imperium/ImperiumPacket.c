/*
 * ImperiumPacket.cpp
 *
 *  Created on: Mar 25, 2012
 *      Author: Mitchell
 */

#include "Imperium.h"
#include "ImperiumPacket.h"
#include "ByteUtil.h"
#include <string.h>

inline void Packet_reset(ImperiumPacket* packet, unsigned int id){
	packet->id = id;
	packet->dataLength = 0;
	Packet_resetRWPosition(packet);
}

inline void Packet_resetRWPosition(ImperiumPacket* packet){
	packet->rwPosition = 0;
}


/**
 * read a packet from an input stream and validate it
 *
 * @param is the input stream to be read from
 * @throws IOException
 */
int Packet_read(ImperiumPacket* packet) {
	if(available()<=4)
		return 1;
	char header[IMPERIUM_PACKET_HEADER_SIZE];
	readBytes(header, IMPERIUM_PACKET_HEADER_SIZE);

	int inputVersion = header[0];
	if(inputVersion!=IMPERIUM_PACKET_VERSION)
		return 2;

	packet->id = header[1];
	packet->dataLength = getUnsignedFromBytes(header, 2, 2);
	if(packet->dataLength>IMPERIUM_PACKET_MAX_DATA_SIZE)
		return 3;

	readBytes(packet->data, packet->dataLength);

	char inputChecksum;
	while( (inputChecksum = read())<0 );
	if(inputChecksum!=Packet_calculateChecksum(packet))
		return 4;

	return 0;
}

/**
 * write a packet to an output stream
 * @param os the stream the packet will be written to
 * @throws IOException
 */
int Packet_write(ImperiumPacket* packet) {
	write(IMPERIUM_PACKET_VERSION);
	write(packet->id);

	write((packet->dataLength>>8)&0xFF);
	write(packet->dataLength&0xFF);

	writeBytes(packet->data, packet->dataLength);

	write(Packet_calculateChecksum(packet));
	return 0;
}

/**
 * @return the total number of bytes in the packet
 */
int Packet_size(ImperiumPacket* packet) {
	return 1 + 1 + 2 + packet->dataLength + 1;
}

/**
 * @param data new data for the packet<br>NOTE: this array will be copied
 * @throws IOException
 */
int Packet_setData(ImperiumPacket* packet, unsigned char* d, unsigned int dataLength){
	packet->dataLength = dataLength;
	memcpy(packet->data, d, dataLength);
	return 0;
}

unsigned char Packet_calculateChecksum(ImperiumPacket* packet) {
	char value = 0;
	value ^= IMPERIUM_PACKET_VERSION;
	value ^= packet->id;
	value ^= (packet->dataLength>>8)&0xFF;
	value ^= packet->dataLength&0xFF;
	for(unsigned int i = 0; i<packet->dataLength; ++i) {
		value ^= packet->data[i];
	}
	return value;
}





int Packet_appendInteger(ImperiumPacket* packet, long value, unsigned int size) {
	if(packet->dataLength+size>IMPERIUM_PACKET_MAX_DATA_SIZE)
		return 1;
	putBytes(packet->data, packet->dataLength, value, size);
	packet->dataLength+=size;
	return 0;
}

long Packet_readInteger(ImperiumPacket* packet, int size){
	long value = getSignedFromBytes(packet->data, packet->rwPosition, size);
	packet->rwPosition+=size;
	return value;
}

unsigned long Packet_readUInteger(ImperiumPacket* packet, int size){
	long value = getUnsignedFromBytes(packet->data, packet->rwPosition, size);
	packet->rwPosition+=size;
	return value;
}
