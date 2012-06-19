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
	(TCCR ## t ## A) = (1<<WGM##t##0);\
	(TCCR ## t ## B) = (1<<CS##t##1);\
	break
void initPWM(AVRPin_t* pin){
	switch(pin->rawPin){
	PWMMacro(PWMInitCase);
	}
}


#define PWMWriteCase(rawPin, t, oc) case rawPin:\
		(OCR ## t ## oc) = val;\
		sbi( (TCCR ## t ## A), (COM ## t ## oc ## 1) );\
	break
void pwmWrite(AVRPin_t* pin, uint8_t val){
	switch (pin->rawPin) {
	PWMMacro(PWMWriteCase);
	}
}

#define PWMDisableCase(rawPin, t, oc) case rawPin:\
		cbi( (TCCR ## t ## A), (COM ## t ## oc ## 1) );\
	break
void pwmDisable(AVRPin_t* pin){
	switch (pin->rawPin) {
	PWMMacro(PWMDisableCase);
	}
}
