package robot.imperium.resources;

import java.util.Set;

public abstract class DeviceResource implements DeviceResourceOwner{
	private final String name;
	private final int id;
	
	private final Set<? extends DeviceResourceState> states;
	private final Set<? extends DeviceResource> dependancies;
	private DeviceResourceState state = null;
	
	public DeviceResource(String name, int id, Set<? extends DeviceResourceState> states, Set<? extends DeviceResource> dependancies){
		this.name = name;
		this.id = id;
		this.states = states;
		this.dependancies = dependancies;
	}
	
	@Override
	public final String getName(){
		return name;
	}
	
	public final int getId(){
		return id;
	}
	
	public final DeviceResourceState getState(){
		return state;
	}
	
	
	public boolean supportsState(DeviceResourceState state){
		return states.contains(state);
	}
	
	protected final void _acquire(DeviceResourceOwner newOwner, DeviceResourceState state){
		for(DeviceResource dependancy:dependancies)
			dependancy.acquire(this, ResourceState.Dependancy);
		this.state = state;
	}
	protected final void _surrender(DeviceResourceOwner oldOwner){
		for(DeviceResource dependancy:dependancies)
			dependancy.surrender(this);
		state = null;
	}
	
	public abstract void acquire(DeviceResourceOwner newOwner, DeviceResourceState state);
	public abstract void surrender(DeviceResourceOwner oldOwner);
}
