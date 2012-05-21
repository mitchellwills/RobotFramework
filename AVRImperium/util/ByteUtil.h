/*
 * ByteUtil.h
 *
 *  Created on: Mar 25, 2012
 *      Author: Mitchell
 */

#ifndef BYTEUTIL_H_
#define BYTEUTIL_H_

#define setBit(value, mask) ( value |= mask )
#define clearBit(value, mask) ( value &= ~mask )
#define getBit(value, mask) ( value & mask )

unsigned long getUnsignedFromBytes(char* b, int offset, int size);
long getSignedFromBytes(char* b, int offset, int size);

void putBytes(char* dst, int index, long value, unsigned int size);

char getByte(long value, int index);

#endif /* BYTEUTIL_H_ */
