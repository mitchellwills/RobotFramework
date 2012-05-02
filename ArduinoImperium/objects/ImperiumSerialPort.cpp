/*
 * ImperiumPPMReader.cpp
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */


#ifndef LOW_MEMORY


#include "ImperiumSerialPort.h"
#include "Imperium.h"
#include "pins.h"



ImperiumObject* ImperiumSerialPort::newSerialPort(int objectId, int* pins, int pinCount){
	return new ImperiumSerialPort(objectId, pins, pinCount);
}

ImperiumSerialPort::ImperiumSerialPort(int objectId, int* pins, int pinCount) : ImperiumObject(objectId, pins, pinCount){
	unsigned long baud = 0;
	switch(getPin(1)){
	case BAUD_300:
		baud = 300;
		break;
	case BAUD_1200:
		baud = 1200;
		break;
	case BAUD_2400:
		baud = 2400;
		break;
	case BAUD_4800:
		baud = 4800;
		break;
	case BAUD_9600:
		baud = 9600;
		break;
	case BAUD_14400:
		baud = 14400;
		break;
	case BAUD_19200:
		baud = 19200;
		break;
	case BAUD_28800:
		baud = 28800;
		break;
	case BAUD_38400:
		baud = 38400;
		break;
	case BAUD_57600:
		baud = 57600;
		break;
	case BAUD_115200:
		baud = 115200;
		break;
	}
	port = digitalPinToSerial(getPin(0), baud);
	lastRead = 0;
}


void ImperiumSerialPort::update(){
	if(port!=NULL){
		int available = 0;
		long time = millis();
		if( (available = port->available())>0 ){
			if( (time-lastRead>MIN_READ_DELAY) ){
				port->readBytes(readBuffer, available);
				sendImperiumMessagePacket(getObjectId(), readBuffer, available);
			}
		}
		else
			lastRead = time;
	}
}

void ImperiumSerialPort::receiveMessage(ImperiumPacket& packet){
	int length = packet.getDataLength()-2;
	char* data = packet.getData()+2;
	int i;
	for(i = 0; i<length; ++i)
		writeBuffer[i] = data[i];

	port->write(writeBuffer, length);
}

void ImperiumSerialPort::setValue(long value){
}

long ImperiumSerialPort::getValue(){
	return 99999;
}

#endif
