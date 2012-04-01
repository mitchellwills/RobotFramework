/*
 * joystickc.cpp
 *
 *  Created on: Apr 1, 2012
 *      Author: Mitchell
 */
#include "joystick.h"

extern "C" {

char* getName(DIJoystick* joystick){
	return joystick->getName();
}

int getNumButtons(DIJoystick* joystick){
	return joystick->getNumButtons();
}
int getNumAxes(DIJoystick* joystick){
	return joystick->getNumAxes();
}
int getNumPOVs(DIJoystick* joystick){
	return joystick->getNumPOVs();
}


long getAxis(DIJoystick* joystick, int id){
	return joystick->getAxis(id);
}
boolean getButton(DIJoystick* joystick, int id){
	return joystick->getButton(id);
}
int getPOV(DIJoystick* joystick, int id){
	return joystick->getPOV(id);
}


const char* getAxisName(DIJoystick* joystick, int id){
	return joystick->getAxisName(id);
}
const char* getButtonName(DIJoystick* joystick, int id){
	return joystick->getButtonName(id);
}
const char* getPOVName(DIJoystick* joystick, int id){
	return joystick->getPOVName(id);
}



void poll(DIJoystick* joystick){
	return joystick->poll();
}



void cleanup(DIJoystick* joystick){
	return joystick->cleanup();
}


}
