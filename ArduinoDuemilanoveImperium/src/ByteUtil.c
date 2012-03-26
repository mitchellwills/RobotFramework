/*
 * ByteUtil.c
 *
 *  Created on: Mar 25, 2012
 *      Author: Mitchell
 */

#include "ByteUtil.h"

unsigned long getUnsigned(char* b, int offset, int size) {
	unsigned long value = 0;
	for (int i = 0; i < size; ++i) {
		value <<= 8;
		value |= b[i + offset] & 0xFFL;
	}
	return value;
}
unsigned long getSigned(char* b, int offset, int size) {
	long value = getUnsigned(b, offset, size);
	value <<= 8 * (8 - size);
	value >>= 8 * (8 - size); //force signed
	return value;
}

void put(char* b, int index, long value, unsigned int size) {
	for (unsigned int i = 0; i < size; ++i) {
		b[index + size - 1 - i] = getByte(value, i);
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

/**
 * @param b7 most significant bit
 * @param b6
 * @param b5
 * @param b4
 * @param b3
 * @param b2
 * @param b1
 * @param b0 least significant bit
 * @return a byte composed of the given bits
 */
char getByteFromBits(boolean b7, boolean b6, boolean b5, boolean b4,
		boolean b3, boolean b2, boolean b1, boolean b0) {
	char _0 = 0;
	char _1 = 1;
	char b = b0 ? _1 : _0;
	b <<= 1;
	b |= b1 ? _1 : _0;
	b <<= 1;
	b |= b2 ? _1 : _0;
	b <<= 1;
	b |= b3 ? _1 : _0;
	b <<= 1;
	b |= b4 ? _1 : _0;
	b <<= 1;
	b |= b5 ? _1 : _0;
	b <<= 1;
	b |= b6 ? _1 : _0;
	b <<= 1;
	b |= b7 ? _1 : _0;
	return b;
}

/**
 * @param b byte to get the bit from
 * @param index index of the bit (0 is least significant bit)
 * @return true if the bit is set
 */
boolean getBit(char b, int index) {
	int mask = 1 << index;
	return (mask & b) != 0;
}
