package robot.imperium;

import robot.boostrap.partial.*;
import robot.imperium.objects.*;
import robot.io.binary.*;

public class ImperiumPartialModule extends AbstractPartialModule {
	
	@Override
	protected void configure() {
		bind(BinaryInput.class, ImperiumDigitalInput.class);
		bind(BinaryOutput.class, ImperiumDigitalOutput.class);
	}

}
