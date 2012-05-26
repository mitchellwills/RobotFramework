package robot.imperium.resources;


public enum ResourceState implements DeviceResourceState{
	DigitalInput,
	DigitalInputPullup,
	DigitalInputPulldown,
	DigitalOutput,
	Disconnected,
	
	Enabled,
	Disabled,
	
	AnalogInput,
	Interrupt,
	
	SerialPort,
	SPI,
	I2C
}
