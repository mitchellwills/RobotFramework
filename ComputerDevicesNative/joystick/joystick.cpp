/*
 * joystick.cpp
 *
 *  Created on: Apr 1, 2012
 *      Author: Mitchell
 */

#include "joystick.h"
#include <stdio.h>
#include <string.h>

extern DIDATAFORMAT df;


DIJoystick::DIJoystick(HWND window, LPDIRECTINPUTDEVICE8 _joystick){
	joystick = _joystick;

	joystick->SetDataFormat(&df);

	info.dwSize = sizeof(DIDEVICEINSTANCE);
	joystick->GetDeviceInfo(&info);


	joystick->SetCooperativeLevel(window, DISCL_NONEXCLUSIVE | DISCL_BACKGROUND);


	capabilities.dwSize = sizeof(DIDEVCAPS);
	joystick->GetCapabilities(&capabilities);

	axisCount = 0;
	buttonCount = 0;
	povCount = 0;
	joystick->EnumObjects( DIJoystick::objectCallback, (void*)this, DIDFT_ALL );

	clearState(&state);

	joystick->Acquire();
}
BOOL CALLBACK DIJoystick::objectCallback( const DIDEVICEOBJECTINSTANCE* objectInfo,  VOID* _obj ){
	DIJoystick* obj = (DIJoystick*)_obj;
	int type = objectInfo->dwType&0xFF;
	if((type&DIDFT_ABSAXIS)==DIDFT_ABSAXIS || (type&DIDFT_RELAXIS)==DIDFT_RELAXIS){
		obj->axisNames[obj->axisCount] = strdup(objectInfo->tszName);
		++obj->axisCount;
	}
	else if((type&DIDFT_POV)==DIDFT_POV){
		obj->povNames[obj->povCount] = strdup(objectInfo->tszName);
		++obj->povCount;
	}
	else if((type&DIDFT_PSHBUTTON)==DIDFT_PSHBUTTON || (type&DIDFT_TGLBUTTON)==DIDFT_TGLBUTTON){
		obj->buttonNames[obj->buttonCount] = strdup(objectInfo->tszName);
		++obj->buttonCount;
	}

	return DIENUM_CONTINUE;
}


char* DIJoystick::getName(){
	return info.tszInstanceName;
}


int DIJoystick::getNumButtons(){
	return buttonCount;
}
int DIJoystick::getNumAxes(){
	return axisCount;
}
int DIJoystick::getNumPOVs(){
	return povCount;
}


long DIJoystick::getAxis(int id){
	return state.axes[id];
}
int DIJoystick::getPOV(int id){
	return state.povs[id];
}
boolean DIJoystick::getButton(int id){
	return state.buttons[id];
}

const char* DIJoystick::getAxisName(int id){
	return axisNames[id];
}
const char* DIJoystick::getButtonName(int id){
	return buttonNames[id];
}
const char* DIJoystick::getPOVName(int id){
	return povNames[id];
}


void DIJoystick::poll(){
	joystick->Acquire();
	joystick->Poll();
	joystick->GetDeviceState(sizeof(MYDATA), &state);
}

void DIJoystick::cleanup(){
	joystick->Unacquire();
	//TODO cleanup malloced memory in config
}
