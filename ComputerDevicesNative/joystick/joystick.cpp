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

	info.dwSize = sizeof(DIDEVICEINSTANCE);
	joystick->GetDeviceInfo(&info);


	joystick->SetDataFormat(&df);

	joystick->SetCooperativeLevel(window, DISCL_NONEXCLUSIVE | DISCL_BACKGROUND);


	capabilities.dwSize = sizeof(DIDEVCAPS);
	joystick->GetCapabilities(&capabilities);

	joystick->EnumObjects( DIJoystick::objectCallback, (void*)this, DIDFT_ALL );

	clearState(&state);

	joystick->Acquire();
}
BOOL CALLBACK DIJoystick::objectCallback( const DIDEVICEOBJECTINSTANCE* objectInfo,  VOID* _obj ){
	DIJoystick* obj = (DIJoystick*)_obj;
	int type = objectInfo->dwType&0xFF;
	if((type&DIDFT_ABSAXIS)==DIDFT_ABSAXIS || (type&DIDFT_RELAXIS)==DIDFT_RELAXIS){
		int id = offsetToAxis(objectInfo->dwOfs);
		obj->axisNames[id] = strdup(objectInfo->tszName);
	}
	else if((type&DIDFT_POV)==DIDFT_POV){
		int id = offsetToPov(objectInfo->dwOfs);
		obj->povNames[id] = strdup(objectInfo->tszName);
	}
	else if((type&DIDFT_PSHBUTTON)==DIDFT_PSHBUTTON || (type&DIDFT_TGLBUTTON)==DIDFT_TGLBUTTON){
		int id = offsetToButton(objectInfo->dwOfs);
		obj->buttonNames[id] = strdup(objectInfo->tszName);
	}

	return DIENUM_CONTINUE;
}


char* DIJoystick::getName(){
	return info.tszInstanceName;
}


int DIJoystick::getNumButtons(){
	return capabilities.dwButtons;
}
int DIJoystick::getNumAxes(){
	return capabilities.dwAxes;
}
int DIJoystick::getNumPOVs(){
	return capabilities.dwPOVs;
}


long DIJoystick::getAxis(int id){
	return state.axes[id];
}
boolean DIJoystick::getButton(int id){
	return state.buttons[id]!=0;
}
int DIJoystick::getPOV(int id){
	return state.povs[id];
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
