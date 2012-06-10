#include "usbserial/VirtualSerial.h"
#include "util/time.h"
#include "imperium/Imperium.h"
#include "util/AVRPins.h"
#include "imperium/TypeIds.h"
#include "objects/Debug.h"
#include "objects/DigitalOutput.h"
#include "objects/DigitalInput.h"
#include "objects/AnalogInput.h"
#include "objects/ServoOutput.h"

inline int available(void) {
	return virtualSerialAvailable();
}

inline int read(void) {
	return virtualSerialRead();
}

inline void write(char b) {
	virtualSerialWrite(b);
}

inline void writeBytes(char* b, int num) {
	virtualSerialWriteBytes(b, num);
}
inline void readBytes(char* b, int num){
	int i;
	for(i = 0; i<num;){
		int c = read();
		if(c>=0){
			b[i] = c;
			++i;
		}
	}
}

int main(void) {
	sei();

	SetupHardware();
	initTime();

	Imperium_init();

	Imperium_setObjectInitializer(TYPEID_DEBUG, Debug_new);
	Imperium_setObjectInitializer(TYPEID_DIGITAL_OUTPUT, DigitalOutput_new);
	Imperium_setObjectInitializer(TYPEID_DIGITAL_INPUT, DigitalInput_new);
	Imperium_setObjectInitializer(TYPEID_ANALOG_VOLTAGE_INPUT, AnalogInput_new);
	Imperium_setObjectInitializer(TYPEID_SERVO_OUTPUT, ServoOutput_new);


	for (;;) {
		Imperium_periodic();

		virtualSerialTask();
	}
}

