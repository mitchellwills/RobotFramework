#include "pins.h"


#if NUM_INTERRUPTS>=2
static void int0(){//pin 2
	INTERRUPT_HANDLER(0, 2);
}
static void int1(){//pin 3
	INTERRUPT_HANDLER(1, 3);
}
#if NUM_INTERRUPTS>=6
static void int2(){//pin 21
	INTERRUPT_HANDLER(2, 21);
}
static void int3(){//pin 20
	INTERRUPT_HANDLER(3, 20);
}
static void int4(){//pin 19
	INTERRUPT_HANDLER(4, 19);
}
static void int5(){//pin 18
	INTERRUPT_HANDLER(5, 18);
}
/*#if NUM_INTERRUPTS>=8
static void int6(){
	INTERRUPT_HANDLER(6, 1);
}
static void int7(){
	INTERRUPT_HANDLER(7, 2);
}
#endif*/
#endif
#endif

#if NUM_INTERRUPTS==2
static void (*interruptHandlers[])(void) = {int0, int1};
#elif NUM_INTERRUPTS==6
static void (*interruptHandlers[])(void) = {int0, int1, int2, int3, int4, int5};
#elif NUM_INTERRUPTS==8
static void (*interruptHandlers[])(void) = {int0, int1, int2, int3, int4, int5, int6, int7};
#endif
