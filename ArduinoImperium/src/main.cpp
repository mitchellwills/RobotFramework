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
#include "ImperiumQuadEncoder.h"
#include "ImperiumSerialPort.h"


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
	setObjectTypeInitializer(9, ImperiumQuadEncoder::newQuadEncoder);
	setObjectTypeInitializer(10, ImperiumSerialPort::newSerialPort);
}

void loop(){
	periodicImperium();
}
