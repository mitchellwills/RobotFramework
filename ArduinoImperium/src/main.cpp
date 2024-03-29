#include "Arduino.h"

#include "Imperium.h"
#include "ImperiumDebug.h"
#include "ImperiumDigitalOutput.h"
#include "ImperiumPWMOutput.h"
#include "ImperiumMSPWMOutput.h"
#include "ImperiumDigitalInput.h"
#include "ImperiumAnalogInput.h"
#include "ImperiumPulseCounter.h"
#include "ImperiumPPMReader.h"
#include "ImperiumPullupDigitalInput.h"
#ifndef LOW_MEMORY
#include "ImperiumQuadEncoder.h"
#include "ImperiumSerialPort.h"
#include "ImperiumDutyCycle.h"
#include "ImperiumFrequency.h"
#include "ImperiumLEDDisplay.h"
#include "ImperiumHM55B.h"
#include "ImperiumPing.h"
#endif


void setup(){

	Serial.begin(115200);
	Serial.flush();

	initImperium(Serial);

	setObjectTypeInitializer(1, ImperiumDebug::newDebug);
	setObjectTypeInitializer(2, ImperiumDigitalOutput::newDigitalOutput);
	setObjectTypeInitializer(3, ImperiumDigitalInput::newDigitalInput);
	setObjectTypeInitializer(4, ImperiumPWMOutput::newPWMOutput);
	setObjectTypeInitializer(5, ImperiumMSPWMOutput::newMSPWMOutput);
	setObjectTypeInitializer(6, ImperiumAnalogInput::newAnalogInput);
	setObjectTypeInitializer(7, ImperiumPulseCounter::newPulseCounter);
	setObjectTypeInitializer(8, ImperiumPPMReader::newPPMReader);
	setObjectTypeInitializer(15, ImperiumPullupDigitalInput::newPullupDigitalInput);

#ifndef LOW_MEMORY
	setObjectTypeInitializer(9, ImperiumQuadEncoder::newQuadEncoder);
	setObjectTypeInitializer(10, ImperiumSerialPort::newSerialPort);
	setObjectTypeInitializer(11, ImperiumDutyCycle::newDutyCycle);
	setObjectTypeInitializer(12, ImperiumFrequency::newFrequency);
	setObjectTypeInitializer(13, ImperiumLEDDisplay::newLEDDisplay);
	setObjectTypeInitializer(14, ImperiumHM55B::newHM55B);
	setObjectTypeInitializer(16, ImperiumPing::newPing);
#endif
}

void loop(){
	periodicImperium();
}
