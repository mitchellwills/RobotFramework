/*
 * format.cpp
 *
 *  Created on: Apr 1, 2012
 *      Author: Mitchell
 */

#include "format.h"




// Then, it can use the following data format.

DIOBJECTDATAFORMAT* rgodf;
DIDATAFORMAT df;


void initFormat(){
	int numObjects = MAX_NUM_AXES + MAX_NUM_POVS + MAX_NUM_BUTTONS;
	rgodf = (DIOBJECTDATAFORMAT*)malloc(numObjects * sizeof(DIOBJECTDATAFORMAT));

	DWORD offset = 0x0;
	DIOBJECTDATAFORMAT* ptr = rgodf;
	int i;
	for(i = 0; i<MAX_NUM_AXES; ++i){
		ptr->pguid = NULL;
		ptr->dwOfs = offset;
		ptr->dwType = DIDFT_AXIS | DIDFT_ANYINSTANCE | DIDFT_OPTIONAL | DIDFT_MAKEINSTANCE(i);
		ptr->dwFlags = DIDOI_ASPECTPOSITION;
		++ptr;
		offset += sizeof(LONG);
	}
	for(i = 0; i<MAX_NUM_POVS; ++i){
		ptr->pguid = NULL;
		ptr->dwOfs = offset;
		ptr->dwType = DIDFT_POV | DIDFT_ANYINSTANCE | DIDFT_OPTIONAL | DIDFT_MAKEINSTANCE(i);
		ptr->dwFlags = 0x0;
		++ptr;
		offset += sizeof(DWORD);
	}
	for(i = 0; i<MAX_NUM_BUTTONS; ++i){
		ptr->pguid = NULL;
		ptr->dwOfs = offset;
		ptr->dwType = DIDFT_BUTTON | DIDFT_ANYINSTANCE | DIDFT_OPTIONAL | DIDFT_MAKEINSTANCE(i);
		ptr->dwFlags = 0x0;
		++ptr;
		offset += sizeof(BYTE);
	}
	df = {
	    sizeof(DIDATAFORMAT),       // Size of this structure
	    sizeof(DIOBJECTDATAFORMAT), // Size of object data format
	    DIDF_ABSAXIS,               // Absolute axis coordinates
	    sizeof(MYDATA),             // Size of device data
	    numObjects,                 // Number of objects
	    rgodf,                      // And here they are
	};
}

DWORD offsetToAxis(int offset){
	return offset / sizeof(LONG);
}
DWORD offsetToPov(int offset){
	return (offset - MAX_NUM_AXES * sizeof(LONG)) / sizeof(DWORD);
}
DWORD offsetToButton(int offset){
	return (offset - MAX_NUM_AXES * sizeof(LONG) - MAX_NUM_POVS * sizeof(DWORD)) / sizeof(BYTE);
}



void clearState(MYDATA* state){
	int i;
	for(i = 0; i<MAX_NUM_AXES; ++i){
		state->axes[i] = 0;
	}
	for(i = 0; i<MAX_NUM_POVS; ++i){
		state->povs[i] = 0;
	}
	for(i = 0; i<MAX_NUM_BUTTONS; ++i){
		state->buttons[i] = 0;
	}
}
