#include "usbserial/VirtualSerial.h"
#include "util/time.h"


inline int read(void){
	return virtualSerialRead();
}

inline void write(int b){
	virtualSerialWrite(b);
}


int main(void){
	sei();

	SetupHardware();

	initTime();


	for (;;)
	{
		int r = -1;
		while( (r=read())>=0 ){
			if(r=='x'){
				write('0');
				write('1');
				write('2');
				write('x');
			}
		}

		virtualSerialTask();
	}
}

