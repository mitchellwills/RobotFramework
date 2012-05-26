package robot.imperium.resources;

import java.util.Set;

public abstract class DeviceResource implements DeviceResourceOwner{
	private final String name;
	private final int id;
	
	private final Set<? extends DeviceResourceState> states;
	private final Set<ResourceDependancy> dependancies;
	private DeviceResourceState state = null;
	
	public DeviceResource(String name, int id, Set<? extends DeviceResourceState> states, Set<ResourceDependancy> dependancies){
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
		for(ResourceDependancy dependancy:dependancies)
			dependancy.getResource().acquire(this, dependancy.getState());
		this.state = state;
	}
	protected final void _surrender(DeviceResourceOwner oldOwner){
		for(ResourceDependancy dependancy:dependancies)
			dependancy.getResource().surrender(this);
		state = null;
	}
	
	public abstract void acquire(DeviceResourceOwner newOwner, DeviceResourceState state);
	public abstract void surrender(DeviceResourceOwner oldOwner);
}
