package robot.io.computerdevices;

import robot.io.battery.Battery;

/**
 * @author Mitchell
 * 
 * Represents the status of a computer's battery
 *
 */
public class ComputerBattery implements Battery{
	private final MyKernel32.SYSTEM_POWER_STATUS batteryStatus = new MyKernel32.SYSTEM_POWER_STATUS();
	
	@Override
	public double getBatteryLeftPercent(){
		MyKernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
		return batteryStatus.getBatteryLifePercent();
	}
}
