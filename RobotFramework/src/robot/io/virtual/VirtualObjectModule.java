package robot.io.virtual;

import com.google.inject.*;
import com.google.inject.assistedinject.*;

public class VirtualObjectModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().build(VirtualObjectFactory.class));
	}

}
