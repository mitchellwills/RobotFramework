/*
 * ADCManager.c
 *
 *  Created on: Jun 9, 2012
 *      Author: Mitchell
 */

#include "ADCManager.h"
#include "LUFA/Drivers/Peripheral/ADC.h"

static uint8_t hasInit = 0;
static uint8_t hasInvalidResult = 0;
static uint8_t currentChannel = 0;
static uint16_t channelValue[NUM_ADC_CHANNELS];
static uint8_t channelEnabled[NUM_ADC_CHANNELS];
static uint16_t channelMUXMask[NUM_ADC_CHANNELS];

void initADC(){
	if(!hasInit){
		for(int i = 0; i<NUM_ADC_CHANNELS; ++i)
			channelValue[i] = 0;
		currentChannel = 0;
		ADC_Init( ADC_SINGLE_CONVERSION | ADC_PRESCALE_128 );
		hasInit = 1;
		hasInvalidResult = 1;
	}
}

static uint16_t adcChannelToMask(int chan){
	switch(chan){
#ifdef ADC_CHANNEL0
	case 0: return ADC_CHANNEL0;
#endif
#ifdef ADC_CHANNEL1
	case 1: return ADC_CHANNEL1;
#endif
#ifdef ADC_CHANNEL2
	case 2: return ADC_CHANNEL2;
#endif
#ifdef ADC_CHANNEL3
	case 3: return ADC_CHANNEL3;
#endif
#ifdef ADC_CHANNEL4
	case 4: return ADC_CHANNEL4;
#endif
#ifdef ADC_CHANNEL5
	case 5: return ADC_CHANNEL5;
#endif
#ifdef ADC_CHANNEL6
	case 6: return ADC_CHANNEL6;
#endif
#ifdef ADC_CHANNEL7
	case 7: return ADC_CHANNEL7;
#endif
#ifdef ADC_CHANNEL8
	case 8: return ADC_CHANNEL8;
#endif
#ifdef ADC_CHANNEL9
	case 9: return ADC_CHANNEL9;
#endif
#ifdef ADC_CHANNEL10
	case 10: return ADC_CHANNEL10;
#endif
#ifdef ADC_CHANNEL11
	case 11: return ADC_CHANNEL11;
#endif
#ifdef ADC_CHANNEL12
	case 12: return ADC_CHANNEL12;
#endif
#ifdef ADC_CHANNEL13
	case 13: return ADC_CHANNEL13;
#endif
	}
	return 0;//should not happen
}


void enableADCChannel(int chan){
	channelMUXMask[chan] = ADC_REFERENCE_AVCC | ADC_RIGHT_ADJUSTED | adcChannelToMask(chan);
	ADC_SetupChannel(chan);
	channelEnabled[chan] = 1;
}

void disableADCChannel(int chan){
	channelEnabled[chan] = 0;
	ADC_DisableChannel(chan);

	for(int i = 0; i<NUM_ADC_CHANNELS; ++i){
		if(channelEnabled[i])
			return;
	}
	ADC_Disable();//If all channels are disabled then disable the adc
	hasInit = 0;
}

uint16_t getADCChannelValue(int chan){
	return channelValue[chan];
}

void updateADCRead(){
	if( hasInit && (ADC_IsReadingComplete() || hasInvalidResult) ){
		if(!hasInvalidResult)
			channelValue[currentChannel] = ADC_GetResult();

		currentChannel++;
		if(currentChannel==NUM_ADC_CHANNELS)
			currentChannel = 0;

		if(channelEnabled[currentChannel]){
			ADC_StartReading( channelMUXMask[currentChannel] );
			hasInvalidResult = 0;
		}
		else
			hasInvalidResult = 1;
	}
}

void readADCAll(){
	for(int i = 0; i<NUM_ADC_CHANNELS; ++i)
		channelValue[i] = ADC_GetChannelReading( channelMUXMask[i] );
}
