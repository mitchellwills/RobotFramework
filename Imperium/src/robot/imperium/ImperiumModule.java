package robot.imperium;

import com.google.inject.*;
import com.google.inject.assistedinject.*;

public class ImperiumModule extends AbstractModule {

	private final ImperiumDevice device;

	public ImperiumModule(ImperiumDevice device){
		this.device = device;
	}
	
	@Override
	protected void configure() {
		bind(ImperiumDevice.class).toInstance(device);
		bind(ImperiumDevice.class).annotatedWith(Assisted.class).toInstance(device);
	}

}
