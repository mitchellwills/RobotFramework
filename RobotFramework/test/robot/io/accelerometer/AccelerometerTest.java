package robot.io.accelerometer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import robot.error.RobotException;
import robot.io.VirtualRobotObjectListener;

@SuppressWarnings("javadoc")
@RunWith(JUnit4.class)
public class AccelerometerTest {
	@Test
	public void testVirtaul(){
		VirtualAccelerometer input = new VirtualAccelerometer(3);
		VirtualRobotObjectListener listener = new VirtualRobotObjectListener();
		input.addUpdateListener(listener);
		
		input.setAcceleration(0, 1);
		input.setAcceleration(1, 33);
		input.setAcceleration(2, 24);
		assertEquals(1, input.getLinearAcceleration(0), 0);
		assertEquals(33, input.getLinearAcceleration(1), 0);
		assertEquals(24, input.getLinearAcceleration(2), 0);
		
		assertEquals(3, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());

		try{
			input.setAcceleration(3, 10);
			fail();
		} catch(RobotException e){
			//success
		}
		try{
			input.getLinearAcceleration(3);
			fail();
		} catch(RobotException e){
			//success
		}

		assertEquals(3, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		input.removeUpdateListener(listener);
		input.setAcceleration(2, 24);
		assertEquals(3, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
	}
}
