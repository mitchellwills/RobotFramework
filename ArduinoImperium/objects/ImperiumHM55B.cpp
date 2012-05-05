/*
 * ImperiumShift.cpp
 *
 * See http://arduino.cc/playground/Main/HM55B
 *
 *
 *  Created on: Apr 12, 2012
 *      Author: Mitchell
 */

#ifndef LOW_MEMORY

#include "ImperiumHM55B.h"
extern "C" {
#include "ImperiumShift.h"
}

ImperiumObject* ImperiumHM55B::newHM55B(int objectId, int* pins, int pinCount) {
	return new ImperiumHM55B(objectId, pins, pinCount);
}

ImperiumHM55B::ImperiumHM55B(int objectId, int* pins, int pinCount) :
		ImperiumObject(objectId, pins, pinCount) {
	lastMeasureCommand = 0;
	measurementPending = false;
	value = 0;

	enPin = getPin(0);
	clkPin = getPin(1);
	dioPin = getPin(2);
	pinMode(enPin, OUTPUT);
	pinMode(clkPin, OUTPUT);
	pinMode(dioPin, INPUT);
	reset();
}

void ImperiumHM55B::update() {
	if (millis() - lastMeasureCommand > MEASURE_RATE) {
		lastMeasureCommand = millis();
		sendMeasureCommand();
		measurementPending = true;
	} else if (measurementPending) {
		if (millis() - lastMeasureCommand > MEASURE_WAIT_TIME) {
			//if(sendReadCommand()==B1100){ //TODO check if fails
				sendReadCommand();
				value = readValue();
				measurementPending = false;
			//}
			//else
			//	digitalWrite(enPin, HIGH);
		}
	}
}

void ImperiumHM55B::reset() {
	pinMode(dioPin, OUTPUT);
	digitalWrite(enPin, LOW);
	ShiftOut(B0000, 3, clkPin, dioPin);
	digitalWrite(enPin, HIGH);
}
void ImperiumHM55B::sendMeasureCommand() {
	pinMode(dioPin, OUTPUT);
	digitalWrite(enPin, LOW);
	ShiftOut(B1000, 3, clkPin, dioPin);
	digitalWrite(enPin, HIGH);
}
int ImperiumHM55B::sendReadCommand() {
	pinMode(dioPin, OUTPUT);
	digitalWrite(enPin, LOW);
	ShiftOut(B1100, 3, clkPin, dioPin);
	pinMode(dioPin, INPUT);
	return ShiftIn(4, clkPin, dioPin);
}

long ImperiumHM55B::readValue() {
	pinMode(dioPin, INPUT);
	long value = ShiftIn(24, clkPin, dioPin);
	digitalWrite(enPin, HIGH);
	return value;
}

void ImperiumHM55B::receiveMessage(ImperiumPacket& packet) {
}
void ImperiumHM55B::setValue(long value) {
}
long ImperiumHM55B::getValue() {
	return value;
}

#endif
