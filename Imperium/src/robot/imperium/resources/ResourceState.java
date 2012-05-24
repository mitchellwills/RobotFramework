package robot.imperium.resources;


public enum ResourceState implements DeviceResourceState{
	DigitalInput,
	DigitalOutput,
	
	AnalogInput,
	Interrupt,
	
	SerialPort,
	SPI,
	I2C,
	
	Dependancy
}
