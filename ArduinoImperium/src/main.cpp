#include "Arduino.h"

#include "Imperium.h"
#include "ImperiumDebug.h"
#include "ImperiumDigitalOutput.h"
#include "ImperiumDigitalInput.h"

#include "MemoryFree.h"

int main(){
	init();

	Serial.begin(115200);

	initImperium(Serial);

	setObjectTypeInitializer(1, ImperiumDebug::newDebug);
	setObjectTypeInitializer(2, ImperiumDigitalOutput::newDigitalOutput);
	setObjectTypeInitializer(3, ImperiumDigitalInput::newDigitalInput);

	while(true){
		periodicImperium();
	}
}
