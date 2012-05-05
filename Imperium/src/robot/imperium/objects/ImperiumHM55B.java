package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.HM55B_TYPE_ID;

import java.util.EnumSet;
import java.util.Set;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.hardware.PinCapability;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.compass.Compass;

/**
 * A Hitachi HM55B Compass Module
 * 
 * @author Mitchell
 *
 */
public class ImperiumHM55B extends ImperiumDeviceObject implements Compass {
	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}
	
	
	private double angle;

	/**
	 * Create a new object that represents a HM55B compass
	 * @param device the Imperium device the compass exists on
	 * @param enPin
	 * @param clkPin
	 * @param dioPin
	 */
	public ImperiumHM55B(ImperiumDevice device, String enPin, String clkPin, String dioPin) {
		super(HM55B_TYPE_ID, device, enPin, clkPin, dioPin);
		angle = 0;
	}

	@Override
	public Set<PinCapability> getRequiredCapabilities(int pinId) {
		if(pinId==2)
			return EnumSet.of(PinCapability.DigitalOutput, PinCapability.DigitalInput);//DIO pin
		return EnumSet.of(PinCapability.DigitalOutput);//clk and en pin
	}
	
	

	@Override
	public void setValue(int value) {
		short xData = (short) ((value>>12)&0x7FF);
		if(((value>>12)&0x800)!=0)
			xData = (short) (xData|0xF800);
		short yData = (short) (value&0x7FF);
		if((value&0x800)!=0)
			yData = (short) (yData|0xF800);
		angle = (Math.toDegrees(Math.atan2(-yData, xData))+360)%360;
		model.fireUpdateEvent();
	}

	@Override
	public double getHeading() {
		return angle;
	}

	@Override
	public double getValue() {
		return getHeading();
	}
	


	@Override
	public void message(long[] values) {
		//
	}

}
