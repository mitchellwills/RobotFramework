/*
 * ImperiumPacket.cpp
 *
 *  Created on: Mar 25, 2012
 *      Author: Mitchell
 */

#include "ImperiumPacket.h"
#include "ByteUtil.h"

ImperiumPacket::ImperiumPacket(){
	id = 0;
	dataLength = 0;
	readPosition = 0;
}


/**
 * read a packet from an input stream and validate it
 *
 * @param is the input stream to be read from
 * @throws IOException
 */
int ImperiumPacket::read(Stream & stream) {
	if(stream.available()<=4)
		return 1;
	char header[IMPERIUM_PACKET_HEADER_SIZE];
	stream.readBytes(header, IMPERIUM_PACKET_HEADER_SIZE);

	int inputVersion = header[0];
	if(inputVersion!=IMPERIUM_PACKET_VERSION)
		return 2;

	id = header[1];
	dataLength = getUnsigned(header, 2, 2);
	if(dataLength>IMPERIUM_PACKET_MAX_DATA_SIZE)
		return 3;

	//TODO ensure that all bytes are read, handle this better
	while(stream.available()<=min(128, (int)dataLength));//buffer will store maximum of 128 bytes


	stream.readBytes(data, dataLength);

	char inputChecksum = stream.read();
	if(inputChecksum!=calculateChecksum())
		return 4;

	return 0;
}

/**
 * write a packet to an output stream
 * @param os the stream the packet will be written to
 * @throws IOException
 */
int ImperiumPacket::write(Stream & stream) {
	stream.write(IMPERIUM_PACKET_VERSION);
	stream.write(id);

	stream.write((dataLength>>8)&0xFF);
	stream.write(dataLength&0xFF);

	stream.write((const uint8_t *)data, dataLength);

	stream.write(getChecksum());
	return 0;
}
/**
 * @return the packet id
 */
int ImperiumPacket::getId() {
	return id;
}
/**
 * @return the number of bytes of data in the packet
 */
int ImperiumPacket::getDataLength() {
	return dataLength;
}

/**
 * @return the data stored in the packet, this may be larger than the amount of valid data in the packet
 * Use getDataLength() for the length of data
 * @see ImperiumPacket#getDataLength()
 */
char* ImperiumPacket::getData() {
	return data;
}
/**
 * @return the checksum for the packet
 */
char ImperiumPacket::getChecksum() {
	return calculateChecksum();
}

/**
 * @return the total number of bytes in the packet
 */
int ImperiumPacket::size() {
	return size(dataLength);
}

int ImperiumPacket::size(int dataSize){
	return 1 + 1 + 2 + dataSize + 1;
}

/**
 * @param id new id for the packet
 */
void ImperiumPacket::setId(unsigned int _id) {
	id = _id;
}
/**
 * @param data new data for the packet<br>NOTE: this array will be copied
 * @throws IOException
 */
int ImperiumPacket::setData(char* d, unsigned int dataLength){
	if(setDataLength(dataLength))
		return 1;
	memcpy(data, d, dataLength);
	return 0;
}
/**
 * Put an integer of a given size at a given index in the data byte array
 * Big Endian
 * @param index index in the data array
 * @param value value to be stored
 * @param size number of bytes to store from the value
 * @throws IOException
 */
int ImperiumPacket::appendInteger(long value, unsigned int size) {
	if(dataLength+size>IMPERIUM_PACKET_MAX_DATA_SIZE)
		return 1;
	put(data, dataLength, value, size);
	dataLength+=size;
	return 0;
}

/**
 * set the length of the data in the packet
 *
 * @param dataLength
 * @throws IOException
 */
int ImperiumPacket::setDataLength(int _dataLength) {
	if(dataLength>IMPERIUM_PACKET_MAX_DATA_SIZE)
		return 1;
	dataLength = _dataLength;
	return 0;
}

void ImperiumPacket::resetReadPosition(){
	readPosition = 0;
}
long ImperiumPacket::readInteger(int size){
	long value = getSigned(getData(), readPosition, size);
	readPosition+=size;
	return value;
}

unsigned long ImperiumPacket::readUInteger(int size){
	long value = getUnsigned(getData(), readPosition, size);
	readPosition+=size;
	return value;
}

char ImperiumPacket::calculateChecksum() {
	char value = 0;
	value ^= IMPERIUM_PACKET_VERSION;
	value ^= id;
	value ^= (dataLength>>8)&0xFF;
	value ^= dataLength&0xFF;
	for(unsigned int i = 0; i<dataLength; ++i) {
		value ^= data[i];
	}
	return value;
}

