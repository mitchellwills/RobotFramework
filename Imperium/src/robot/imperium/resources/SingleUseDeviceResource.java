package robot.imperium.resources;

import java.util.Set;

import robot.error.RobotInitializationException;

public class SingleUseDeviceResource extends DeviceResource{
	private DeviceResourceOwner owner;
	
	public SingleUseDeviceResource(String name, int id, Set<? extends DeviceResourceState> states, Set<? extends DeviceResource> dependancies){
		super(name, id, states, dependancies);
		owner = null;
	}
	
	@Override
	public void acquire(DeviceResourceOwner newOwner, DeviceResourceState state){
		if(!supportsState(state))
			throw new RobotInitializationException(this+" does not contain the required state: "+state);
		if(newOwner==null)
			throw new RobotInitializationException("null cannot acquire "+this);
		if(owner!=null && owner!=newOwner)
			throw new RobotInitializationException(this+" is already acquired by an owner");
		_acquire(newOwner, state);
		this.owner = newOwner;
	}
	
	@Override
	public void surrender(DeviceResourceOwner oldOwner){
		if(oldOwner==null)
			return;
		if(oldOwner!=owner)
			throw new RobotInitializationException(oldOwner+" is not the current owner of "+this);
		_surrender(oldOwner);
		owner = null;
	}
}
