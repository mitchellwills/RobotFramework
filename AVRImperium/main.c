#include "usbserial/VirtualSerial.h"
#include "util/time.h"
#include "imperium/Imperium.h"

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

	initImperium();

	for (;;) {
		periodicImperium();

		virtualSerialTask();
	}
}

