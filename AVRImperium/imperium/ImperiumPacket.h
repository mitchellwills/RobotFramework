/*
 * ImperiumPacket.h
 *
 *  Created on: Mar 25, 2012
 *      Author: Mitchell
 */

#ifndef IMPERIUMPACKET_H_
#define IMPERIUMPACKET_H_


#define IMPERIUM_PACKET_VERSION 2
#define IMPERIUM_PACKET_HEADER_SIZE 4
#define IMPERIUM_PACKET_MAX_DATA_SIZE 400

struct ImperiumPacket {
	unsigned int id;
	unsigned int dataLength;
	char* data;
	unsigned int readPosition;
};
typedef struct ImperiumPacket ImperiumPacket;

ImperiumPacket* Packet_new(int dataSize);


void Packet_reset(ImperiumPacket* packet, unsigned int id);
void Packet_resetReadPosition(ImperiumPacket* packet);

int Packet_read(ImperiumPacket* packet);
int Packet_write(ImperiumPacket* packet);

int Packet_size(ImperiumPacket* packet);
int Packet_setData(ImperiumPacket* packet, unsigned char* d, unsigned int dataLength);

unsigned char Packet_calculateChecksum(ImperiumPacket* packet);



int Packet_appendInteger(ImperiumPacket* packet, long value, unsigned int size);
long Packet_readInteger(ImperiumPacket* packet, int size);
unsigned long Packet_readUInteger(ImperiumPacket* packet, int size);
char Packet_readByte(ImperiumPacket* packet);

char* Packet_getDataFromReadPosition(ImperiumPacket* packet);


#endif /* IMPERIUMPACKET_H_ */
