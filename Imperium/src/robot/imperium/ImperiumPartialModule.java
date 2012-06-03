package robot.imperium;

import robot.boostrap.partial.*;
import robot.imperium.objects.*;
import robot.io.binary.*;
import robot.io.pwmms.*;

public class ImperiumPartialModule extends AbstractPartialModule {
	
	@Override
	protected void configure() {
		bind(BinaryInput.class, ImperiumDigitalInput.class);
		bind(BinaryOutput.class, ImperiumDigitalOutput.class);
		bind(MSPWMOutput.class, ImperiumServoOutput.class);
	}

}
