package robot.imperium.resources;

public class ResourceDependancy {
	private final DeviceResource resource;
	private final DeviceResourceState state;
	
	public ResourceDependancy(DeviceResource resource, DeviceResourceState state) {
		this.resource = resource;
		this.state = state;
	}

	public DeviceResource getResource() {
		return resource;
	}

	public DeviceResourceState getState() {
		return state;
	}
	
	
}
