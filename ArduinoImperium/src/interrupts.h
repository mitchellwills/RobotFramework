#include "pins.h"


#if NUM_INTERRUPTS>=2
static void int0(){//pin 2
	INTERRUPT_HANDLER(0, INT0_PIN);
}
static void int1(){//pin 3
	INTERRUPT_HANDLER(1, INT1_PIN);
}
#if NUM_INTERRUPTS>=6
static void int2(){//pin 21
	INTERRUPT_HANDLER(2, INT2_PIN);
}
static void int3(){//pin 20
	INTERRUPT_HANDLER(3, INT3_PIN);
}
static void int4(){//pin 19
	INTERRUPT_HANDLER(4, INT4_PIN);
}
static void int5(){//pin 18
	INTERRUPT_HANDLER(5, INT5_PIN);
}
#if NUM_INTERRUPTS>=8
static void int6(){
	INTERRUPT_HANDLER(6, INT6_PIN);
}
static void int7(){
	INTERRUPT_HANDLER(7, INT7_PIN);
}
#endif
#endif
#endif

#if NUM_INTERRUPTS==2
static void (*interruptHandlers[])(void) = {int0, int1};
#elif NUM_INTERRUPTS==6
static void (*interruptHandlers[])(void) = {int0, int1, int2, int3, int4, int5};
#elif NUM_INTERRUPTS==8
static void (*interruptHandlers[])(void) = {int0, int1, int2, int3, int4, int5, int6, int7};
#endif
