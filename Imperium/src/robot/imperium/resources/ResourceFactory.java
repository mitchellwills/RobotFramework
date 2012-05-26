package robot.imperium.resources;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ResourceFactory {
	private enum ResourceType{
		Single, Multi;
	}
	
	private ResourceType type;
	private String name;
	private int id;
	private Set<ResourceDependancy> dependancies = new HashSet<ResourceDependancy>();
	private Set<DeviceResourceState> states = new HashSet<DeviceResourceState>();
	private ResourceFactory(ResourceType type, String name, int id){
		this.type = type;
		this.name = name;
		this.id = id;
	}
	public static ResourceFactory single(String name, int id){
		return new ResourceFactory(ResourceType.Single, name, id);
	}
	public static ResourceFactory multi(String name, int id){
		return new ResourceFactory(ResourceType.Multi, name, id);
	}
	public ResourceFactory states(DeviceResourceState... states){
		this.states.addAll(Arrays.asList(states));
		return this;
	}
	public ResourceFactory dependancy(DeviceResource resource, DeviceResourceState state){
		dependancies.add(new ResourceDependancy(resource, state));
		return this;
	}
	public DeviceResource build(){
		if(states.size()==0)
			throw new IllegalArgumentException("A resource must have at least one state");
		switch(type){
		case Single:
			return new SingleUseDeviceResource(name, id, states, dependancies);
		case Multi:
			return new MultiUseDeviceResource(name, id, states, dependancies);
		}
		return null;
	}
}
