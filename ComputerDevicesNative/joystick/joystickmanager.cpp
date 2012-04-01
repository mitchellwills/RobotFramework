/*
 * joystickmanager.cpp
 *
 *  Created on: Apr 1, 2012
 *      Author: Mitchell
 */


#define DIRECTINPUT_VERSION 0x0800
#include <dinput.h>
#include <stdio.h>
#include "joystick.h"
#include "format.h"
#include "joystickmanager.h"

extern "C" {



HWND window;
LPDIRECTINPUT8 di;
int initDI() {
	HRESULT hr;

	if (FAILED(hr = DirectInput8Create(GetModuleHandle(NULL), DIRECTINPUT_VERSION,
					IID_IDirectInput8, (VOID**)&di, NULL))) {
		return 1;
	}

	initFormat();

	window =  CreateWindowEx(
		  0/*WS_EX_APPWINDOW*/,			// DWORD dwExStyle,      // extended window style
		  "STATIC",						// LPCTSTR lpClassName,  // pointer to registered class name
		  NULL,							// LPCTSTR lpWindowName, // pointer to window name
		  0/*WS_CAPTION*/,				// DWORD dwStyle,        // window style
		  0,							// int x,                // horizontal position of window
		  0,							// int y,                // vertical position of window
		  0,							// int nWidth,           // window width
		  0,							// int nHeight,          // window height
		  NULL,							// HWND hWndParent,      // handle to parent or owner window
		  NULL,							// HMENU hMenu,          // handle to menu, or child-window identifier
		  GetModuleHandle(NULL),						// HINSTANCE hInstance,  // handle to application instance
		  NULL							// LPVOID lpParam        // pointer to window-creation data
		);

	return 0;
}




static DIJoystick* joysticks[MAX_NUM_JOYSTICKS];
static int joystickCount = 0;

BOOL CALLBACK deviceEnumCallback(const DIDEVICEINSTANCE* instance, VOID* context) {
	LPDIRECTINPUTDEVICE8 nativeJoystick;

	di->CreateDevice(instance->guidInstance, &nativeJoystick, NULL);
	joysticks[joystickCount] = new DIJoystick(window, nativeJoystick);

	++joystickCount;

	if(joystickCount<MAX_NUM_JOYSTICKS)
		return DIENUM_CONTINUE;
	return DIENUM_STOP;
}

int enumerateDevices() {
	joystickCount = 0;

	HRESULT hr;
	if (FAILED(hr = di->EnumDevices(DI8DEVCLASS_GAMECTRL, deviceEnumCallback,
					NULL, DIEDFL_ATTACHEDONLY))) {
		return hr;
	}

	return 0;
}

DIJoystick* getJoystickReference(int id){
	return joysticks[id];
}
int getJoystickCount(){
	return joystickCount;
}

}
