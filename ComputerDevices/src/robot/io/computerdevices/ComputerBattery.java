package robot.io.computerdevices;

import robot.io.host.HostBattery;
import robot.io.value.InputValue;

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
	public boolean isPluggedIn() {
		// TODO figure out if is plugged in
		return false;
	}
	
	private final InputValue batteryInputValue = new InputValue(){
		@Override
		public double getValue() {
			return getBatteryLeftPercent();
		}
	};
	/**
	 * @return an input value that represents the percent battery remaining (0.0-1.0)
	 */
	public InputValue getPercentInputValue(){
		return batteryInputValue;
	}
}
