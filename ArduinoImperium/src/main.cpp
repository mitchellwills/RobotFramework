#include "Arduino.h"

#include "Imperium.h"
#include "ImperiumDebug.h"
#include "ImperiumDigitalOutput.h"
#include "ImperiumPWMOutput.h"
#include "ImperiumMSPWMOutput.h"
#include "ImperiumDigitalInput.h"
#include "ImperiumAnalogInput.h"

#include "MemoryFree.h"

int main(){
	init();

	Serial.begin(115200);

	initImperium(Serial);

	setObjectTypeInitializer(1, ImperiumDebug::newDebug);
	setObjectTypeInitializer(2, ImperiumDigitalOutput::newDigitalOutput);
	setObjectTypeInitializer(3, ImperiumDigitalInput::newDigitalInput);
	setObjectTypeInitializer(4, ImperiumPWMOutput::newPWMOutput);
	setObjectTypeInitializer(5, ImperiumMSPWMOutput::newMSPWMOutput);
	setObjectTypeInitializer(6, ImperiumAnalogInput::newAnalogInput);


	while(true){
		periodicImperium();
	}
}
