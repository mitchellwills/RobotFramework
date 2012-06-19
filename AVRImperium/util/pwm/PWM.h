/*
 * PWM.h
 *
 *  Created on: Jun 18, 2012
 *      Author: Mitchell
 */

#ifndef PWM_H_
#define PWM_H_

#include "AVRPins.h"


#ifndef cbi
#define cbi(sfr, bit) (_SFR_BYTE(sfr) &= ~_BV(bit))
#endif
#ifndef sbi
#define sbi(sfr, bit) (_SFR_BYTE(sfr) |= _BV(bit))
#endif

void initPWM(AVRPin_t* pin);

void pwmWrite(AVRPin_t* pin, uint8_t val);

void pwmDisable(AVRPin_t* pin);


#endif /* PWM_H_ */
