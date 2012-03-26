#include "Arduino.h"

#include "Imperium.h"

int main(){
	init();

	pinMode(13, OUTPUT);
	digitalWrite(13, HIGH);

	Serial.begin(115200);
	initImperium(Serial);

	while(true){
		digitalWrite(13, LOW);
		periodicImperium();
	}
}
