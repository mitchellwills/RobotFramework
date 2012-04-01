/*
 * format.h
 *
 *  Created on: Apr 1, 2012
 *      Author: Mitchell
 */

#ifndef FORMAT_H_
#define FORMAT_H_

#define DIRECTINPUT_VERSION 0x0800
#include <dinput.h>


#define MAX_NUM_AXES 6
#define MAX_NUM_POVS 4
#define MAX_NUM_BUTTONS 10

typedef struct MYDATA {
    LONG  axes[MAX_NUM_AXES];

    DWORD povs[MAX_NUM_POVS];

    BYTE  buttons[MAX_NUM_BUTTONS];


    BYTE  bPadding[2];          // Must be DWORD multiple in size.
} MYDATA;


void initFormat();
void clearState(MYDATA* state);


DWORD offsetToAxis(int offset);
DWORD offsetToPov(int offset);
DWORD offsetToButton(int offset);

#endif /* FORMAT_H_ */
