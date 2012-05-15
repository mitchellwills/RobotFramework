/*
 * ByteUtil.c
 *
 *  Created on: Mar 25, 2012
 *      Author: Mitchell
 */

#include "ByteUtil.h"

unsigned long getUnsignedFromBytes(char* b, int offset, int size) {
	unsigned long value = 0;
	for (int i = 0; i < size; ++i) {
		value <<= 8;
		value |= b[i + offset] & 0xFF;
	}
	return value;
}
long getSignedFromBytes(char* b, int offset, int size) {
	long value = getUnsignedFromBytes(b, offset, size);
	value <<= 8 * (4 - size);
	value >>= 8 * (4 - size); //force signed
	return value;
}

void putBytes(char* dst, int index, long value, unsigned int size) {
	for (unsigned int i = 0; i < size; ++i) {
		dst[index + size - 1 - i] = getByte(value, i);
	}
}

/**
 * @param value the value to get the byte from
 * @param index index of the byte to get (0 is the least significant bit)
 * @return the byte at the given index
 */
char getByte(long value, int index) {
	return (char)(value >> (8 * index));
}
