/*
 * ADCManager.h
 *
 *  Created on: Jun 9, 2012
 *      Author: Mitchell
 */

#ifndef ADCMANAGER_H_
#define ADCMANAGER_H_
#include "stdint.h"

#define NUM_ADC_CHANNELS 8


void initADC(void);

void enableADCChannel(int chan);

void disableADCChannel(int chan);

uint16_t getADCChannelValue(int chan);

void updateADCRead(void);

void readADCAll(void);


#endif /* ADCMANAGER_H_ */
