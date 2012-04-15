/*
 * ImperiumPPMReader.cpp
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */




#include "ImperiumPPMReader.h"
#include "Imperium.h"

#define SYNC_PULSE_LENGTH 3000
static ImperiumPPMReader* readerObjects[NUM_INTERRUPTS];
static inline void intHandler(int interruptNum, int pin){
	ImperiumPPMReader* reader = readerObjects[interruptNum];
	if(digitalRead(pin)){//rising edge
		reader->lastRise = micros();

		if( (reader->lastRise-reader->lastFall)>SYNC_PULSE_LENGTH ){
			reader->currentChannel = 0;
			reader->frameId++;
		}
		else{
			if(reader->currentChannel<0 || reader->currentChannel>=reader->numChannels)
				return;

			reader->channels[reader->currentChannel] = reader->lastRise-reader->lastFall;
			reader->currentChannel++;
		}
	}
	else{//falling edge
		reader->lastFall = micros();
	}
}
#if defined(__AVR_ATmega328P__) || defined(__AVR_ATmega2560__)
static void int0(){//pin 2
	intHandler(0, 2);
}
static void int1(){//pin 3
	intHandler(1, 3);
}
#if defined(__AVR_ATmega2560__)
static void int2(){//pin 21
	intHandler(2, 21);
}
static void int3(){//pin 20
	intHandler(3, 20);
}
static void int4(){//pin 19
	intHandler(4, 19);
}
static void int5(){//pin 18
	intHandler(5, 18);
}
#endif
#endif

#if defined(__AVR_ATmega328P__)
static void (*interruptHandlers[])(void) = {int0, int1};
#elif defined(__AVR_ATmega2560__)
static void (*interruptHandlers[])(void) = {int0, int1, int2, int3, int4, int5};
#endif





ImperiumObject* ImperiumPPMReader::newPPMReader(int objectId, int* pins, int pinCount){
	return new ImperiumPPMReader(objectId, pins, pinCount);
}

ImperiumPPMReader::ImperiumPPMReader(int objectId, int* pins, int pinCount) : ImperiumObject(objectId, pins, pinCount){
	lastRise = 0;
	lastFall = 0;
	frameId = 0;
	lastSentFrame = 0;

	pinMode(49, INPUT);
	digitalWrite(49, HIGH);//pullup

	pinMode(getPin(0), INPUT);
	digitalWrite(getPin(0), HIGH);//pullup

	numChannels = getPin(1);//use pin for number of channels
	channels = (long volatile*) malloc(sizeof(long)*numChannels);
	currentChannel = -1;

	interruptNum = digitalPinToInterrupt(getPin(0));
	readerObjects[interruptNum] = this;
	attachInterrupt(interruptNum, interruptHandlers[interruptNum], CHANGE);
}


void ImperiumPPMReader::update(){
	if(frameId>lastSentFrame){
		sendImperiumMessagePacket(getObjectId(), (long*)channels, 2, numChannels);
		lastSentFrame = frameId;
	}
}

void ImperiumPPMReader::setValue(long value){
}

long ImperiumPPMReader::getValue(){
	return numChannels;
}

