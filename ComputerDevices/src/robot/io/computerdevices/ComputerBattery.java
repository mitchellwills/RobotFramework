package robot.io.computerdevices;

import robot.io.host.HostBattery;

/**
 * @author Mitchell
 * 
 * Represents the status of a computer's battery
 *
 */
public class ComputerBattery implements HostBattery{
	private final MyKernel32.SYSTEM_POWER_STATUS batteryStatus = new MyKernel32.SYSTEM_POWER_STATUS();
	
	@Override
	public double getBatteryLeftPercent(){
		MyKernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
		return batteryStatus.getBatteryLifePercent();
	}
	@Override
	public int getTimeRemaining(){
		MyKernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
		return batteryStatus.getBatteryLifeTime();
	}
	@Override
	public int getTimeTillCharged(){
		MyKernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
		return batteryStatus.getBatteryFullLifeTime();
	}
	@Override
	public boolean isPluggedIn() {
		// TODO Auto-generated method stub
		return false;
	}
}
