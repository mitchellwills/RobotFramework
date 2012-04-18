/*
 * ImperiumPPMReader.cpp
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */




#include "ImperiumPPMReader.h"
#include "Imperium.h"
#include "pins.h"

#define INVALID_VALUE -1
#define SYNC_PULSE_LENGTH 5000


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

#define INTERRUPT_HANDLER intHandler
#include "interrupts.h"
#undef INTERRUPT_HANDLER




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
	int i;
	for(i = 0; i<numChannels; ++i)
		channels[i] = INVALID_VALUE;

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

