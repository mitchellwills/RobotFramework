package robot.imperium;

import java.util.Arrays;
import java.util.Collection;

import robot.error.RobotInitializationException;

public class DeviceFeature {
	private ImperiumDeviceObject owner;
	private final String name;
	private final int id;
	private final Collection<DeviceFeatureCapability> capabilities;
	
	public DeviceFeature(String name, int id, DeviceFeatureCapability...capabilities){
		this.name = name;
		this.id = id;
		this.capabilities = Arrays.asList(capabilities);
		owner = null;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId(){
		return id;
	}

	public int acquire(ImperiumDeviceObject newOwner, DeviceFeatureCapability... requiredCapabilities){
		if(!capabilities.containsAll(Arrays.asList(requiredCapabilities)))
			throw new RobotInitializationException(this+" does not contain the required capabilities: "+requiredCapabilities);
		if(owner==newOwner)
			return id;
		if(owner!=null)
			throw new RobotInitializationException("Cannot acquire "+this+" it already has an owner");
		this.owner = newOwner;
		return id;
	}
	public void surrender(ImperiumDeviceObject oldOwner){
		if(oldOwner!=owner)
			throw new RobotInitializationException(oldOwner+" is not the current owner of "+this);
		owner = null;
	}
}
