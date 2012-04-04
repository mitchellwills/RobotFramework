/*
 * joystick.h
 *
 *  Created on: Apr 1, 2012
 *      Author: Mitchell
 */

#ifndef JOYSTICK_H_
#define JOYSTICK_H_

#define DIRECTINPUT_VERSION 0x0800
#include <dinput.h>
#include "format.h"



class DIJoystick {
	private:
		DIDEVICEINSTANCE info;
		LPDIRECTINPUTDEVICE8 joystick;
		DIDEVCAPS capabilities;
		MYDATA state;

		int axisCount;
		const char* axisNames[MAX_NUM_AXES];
		int povCount;
		const char* povNames[MAX_NUM_POVS];
		int buttonCount;
		const char* buttonNames[MAX_NUM_BUTTONS];

	public:
		DIJoystick(HWND window, LPDIRECTINPUTDEVICE8 joystick);
		static BOOL CALLBACK objectCallback( const DIDEVICEOBJECTINSTANCE* objectInfo,  VOID* pContext );

		char* getName();

		int getNumButtons();
		int getNumAxes();
		int getNumPOVs();

		long getAxis(int id);
		boolean getButton(int id);
		int getPOV(int id);

		const char* getAxisName(int id);
		const char* getButtonName(int id);
		const char* getPOVName(int id);

		void poll();

		void cleanup();
};




#endif /* JOYSTICK_H_ */
