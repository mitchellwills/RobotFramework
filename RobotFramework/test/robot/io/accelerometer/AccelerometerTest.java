package robot.io.accelerometer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import robot.error.RobotException;

@SuppressWarnings("javadoc")
@RunWith(JUnit4.class)
public class AccelerometerTest {
	@Test
	public void testVirtaul(){
		VirtualAccelerometer accel = new VirtualAccelerometer(3);
		accel.setAcceleration(0, 1);
		accel.setAcceleration(1, 33);
		accel.setAcceleration(2, 24);
		assertEquals(1, accel.getLinearAcceleration(0), 0);
		assertEquals(33, accel.getLinearAcceleration(1), 0);
		assertEquals(24, accel.getLinearAcceleration(2), 0);

		try{
			accel.setAcceleration(3, 10);
			fail();
		} catch(RobotException e){
			//success
		}
		try{
			accel.getLinearAcceleration(3);
			fail();
		} catch(RobotException e){
			//success
		}
	}
}
