/*
 * ByteUtil.h
 *
 *  Created on: Mar 25, 2012
 *      Author: Mitchell
 */

#ifndef BYTEUTIL_H_
#define BYTEUTIL_H_

#include "Arduino.h"

#ifdef __cplusplus
extern "C" {
#endif

unsigned long getUnsigned(char* b, int offset, int size);
unsigned long getSigned(char* b, int offset, int size);

void put(char* b, int index, long value, unsigned int size);

char getByte(long value, int index);

char getByteFromBits(boolean b7, boolean b6, boolean b5, boolean b4, boolean b3,
		boolean b2, boolean b1, boolean b0);

boolean getBit(char b, int index);

#ifdef __cplusplus
}
#endif

#endif /* BYTEUTIL_H_ */
