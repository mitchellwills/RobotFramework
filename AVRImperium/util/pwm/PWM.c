/*
 * PWM.c
 *
 *  Created on: Jun 18, 2012
 *      Author: Mitchell
 */


#include "PWM.h"

#define PWMMacro(macroName) \
	macroName(AVRRaw_PB5, 1, A);\
	macroName(AVRRaw_PB6, 1, B);\
	macroName(AVRRaw_PB7, 1, C);\
\
	macroName(AVRRaw_PB4, 2, A);\
	macroName(AVRRaw_PD1, 2, B);\
\
	macroName(AVRRaw_PC6, 3, A);\
	macroName(AVRRaw_PC5, 3, B);\
	macroName(AVRRaw_PC4, 3, C);



#define PWMInitCase(rawPin, t, oc) case rawPin:\
	setPinOutput(pin);\
	setPinLow(pin);\
	(TCCR ## t ## A) = (1<<WGM##t##0);/*PWM, Phase Correct, 8-bit*/\
	(TCCR ## t ## B) = (1<<CS##t##1);/*Set prescaling to 8*/\
	(TIMSK ## t) = 0;/*disable timer interrupts*/\
	break

void initPWM(AVRPin_t* pin){//TODO enable/disable interrupts
	switch(pin->rawPin){
	PWMMacro(PWMInitCase);
	}
}


#define PWMWriteCase(rawPin, t, oc) case rawPin:\
		(OCR ## t ## oc) = val;/*Set output compare value*/\
		sbi( (TCCR ## t ## A), (COM ## t ## oc ## 1) );/*Set compare match behavior*/\
	break
void pwmWrite(AVRPin_t* pin, uint8_t val){
	switch (pin->rawPin) {
	PWMMacro(PWMWriteCase);
	}
}

#define PWMDisableCase(rawPin, t, oc) case rawPin:\
		cbi( (TCCR ## t ## A), (COM ## t ## oc ## 1) );/*Disconnect output compare*/\
	break
void pwmDisable(AVRPin_t* pin){
	switch (pin->rawPin) {
	PWMMacro(PWMDisableCase);
	}
}
