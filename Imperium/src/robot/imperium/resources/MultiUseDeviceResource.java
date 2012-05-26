package robot.imperium.resources;

import java.util.HashSet;
import java.util.Set;

import robot.error.RobotInitializationException;

public class MultiUseDeviceResource extends DeviceResource{
	private Set<DeviceResourceOwner> owners;
	
	public MultiUseDeviceResource(String name, int id, Set<? extends DeviceResourceState> states, Set<ResourceDependancy> dependancies){
		super(name, id, states, dependancies);
		owners = new HashSet<DeviceResourceOwner>();
	}
	
	@Override
	public void acquire(DeviceResourceOwner newOwner, DeviceResourceState state){
		if(!supportsState(state))
			throw new RobotInitializationException(this+" does not contain the required state: "+state);
		if(newOwner==null)
			throw new RobotInitializationException("null cannot acquire "+this);
		if(owners.size()>0 && !getState().equals(state))
			throw new RobotInitializationException(this+" has already been acquired in another state");
		_acquire(newOwner, state);
		owners.add(newOwner);
	}
	
	@Override
	public void surrender(DeviceResourceOwner oldOwner){
		if(oldOwner==null)
			return;
		if(!owners.contains(oldOwner))
			throw new RobotInitializationException(oldOwner+" is not the current owner of "+this);
		_surrender(oldOwner);
		owners.remove(oldOwner);
	}
}
