package robot.imperium.hardware;

/**
 * @author Mitchell
 * 
 * Contains definitions ports on Atmega chips
 *
 */
@SuppressWarnings("javadoc")
public interface AtmegaPins {
	public static final int PORTA = 0<<3;
	public static final int PA0 = PORTA | 0;
	public static final int PA1 = PORTA | 1;
	public static final int PA2 = PORTA | 2;
	public static final int PA3 = PORTA | 3;
	public static final int PA4 = PORTA | 4;
	public static final int PA5 = PORTA | 5;
	public static final int PA6 = PORTA | 6;
	public static final int PA7 = PORTA | 7;

	public static final int PORTB = 1<<3;
	public static final int PB0 = PORTB | 0;
	public static final int PB1 = PORTB | 1;
	public static final int PB2 = PORTB | 2;
	public static final int PB3 = PORTB | 3;
	public static final int PB4 = PORTB | 4;
	public static final int PB5 = PORTB | 5;
	public static final int PB6 = PORTB | 6;
	public static final int PB7 = PORTB | 7;

	public static final int PORTC = 2<<3;
	public static final int PC0 = PORTC | 0;
	public static final int PC1 = PORTC | 1;
	public static final int PC2 = PORTC | 2;
	public static final int PC3 = PORTC | 3;
	public static final int PC4 = PORTC | 4;
	public static final int PC5 = PORTC | 5;
	public static final int PC6 = PORTC | 6;
	public static final int PC7 = PORTC | 7;

	public static final int PORTD = 3<<3;
	public static final int PD0 = PORTD | 0;
	public static final int PD1 = PORTD | 1;
	public static final int PD2 = PORTD | 2;
	public static final int PD3 = PORTD | 3;
	public static final int PD4 = PORTD | 4;
	public static final int PD5 = PORTD | 5;
	public static final int PD6 = PORTD | 6;
	public static final int PD7 = PORTD | 7;

	public static final int PORTE = 4<<3;
	public static final int PE0 = PORTE | 0;
	public static final int PE1 = PORTE | 1;
	public static final int PE2 = PORTE | 2;
	public static final int PE3 = PORTE | 3;
	public static final int PE4 = PORTE | 4;
	public static final int PE5 = PORTE | 5;
	public static final int PE6 = PORTE | 6;
	public static final int PE7 = PORTE | 7;

	public static final int PORTF = 5<<3;
	public static final int PF0 = PORTF | 0;
	public static final int PF1 = PORTF | 1;
	public static final int PF2 = PORTF | 2;
	public static final int PF3 = PORTF | 3;
	public static final int PF4 = PORTF | 4;
	public static final int PF5 = PORTF | 5;
	public static final int PF6 = PORTF | 6;
	public static final int PF7 = PORTF | 7;

	public static final int PORTG = 6<<3;
	public static final int PG0 = PORTG | 0;
	public static final int PG1 = PORTG | 1;
	public static final int PG2 = PORTG | 2;
	public static final int PG3 = PORTG | 3;
	public static final int PG4 = PORTG | 4;
	public static final int PG5 = PORTG | 5;
	public static final int PG6 = PORTG | 6;
	public static final int PG7 = PORTG | 7;

	public static final int PORTH = 7<<3;
	public static final int PH0 = PORTH | 0;
	public static final int PH1 = PORTH | 1;
	public static final int PH2 = PORTH | 2;
	public static final int PH3 = PORTH | 3;
	public static final int PH4 = PORTH | 4;
	public static final int PH5 = PORTH | 5;
	public static final int PH6 = PORTH | 6;
	public static final int PH7 = PORTH | 7;

	public static final int PORTI = 8<<3;
	public static final int PI0 = PORTI | 0;
	public static final int PI1 = PORTI | 1;
	public static final int PI2 = PORTI | 2;
	public static final int PI3 = PORTI | 3;
	public static final int PI4 = PORTI | 4;
	public static final int PI5 = PORTI | 5;
	public static final int PI6 = PORTI | 6;
	public static final int PI7 = PORTI | 7;

	public static final int PORTJ = 9<<3;
	public static final int PJ0 = PORTJ | 0;
	public static final int PJ1 = PORTJ | 1;
	public static final int PJ2 = PORTJ | 2;
	public static final int PJ3 = PORTJ | 3;
	public static final int PJ4 = PORTJ | 4;
	public static final int PJ5 = PORTJ | 5;
	public static final int PJ6 = PORTJ | 6;
	public static final int PJ7 = PORTJ | 7;

	public static final int PORTK = 10<<3;
	public static final int PK0 = PORTK | 0;
	public static final int PK1 = PORTK | 1;
	public static final int PK2 = PORTK | 2;
	public static final int PK3 = PORTK | 3;
	public static final int PK4 = PORTK | 4;
	public static final int PK5 = PORTK | 5;
	public static final int PK6 = PORTK | 6;
	public static final int PK7 = PORTK | 7;
}
