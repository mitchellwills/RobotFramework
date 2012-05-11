package robot.io.accelerometer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import robot.error.RobotException;
import robot.error.RobotInitializationException;
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

	@Test
	public void testSingleAxis(){
		VirtualAccelerometer input = new VirtualAccelerometer(3);
		SingleAxisAccelerometer single = new SingleAxisAccelerometer(input, 1);
		VirtualRobotObjectListener listener = new VirtualRobotObjectListener();
		single.addUpdateListener(listener);
		assertEquals(1, single.getNumAccelerometerAxes());
		
		input.setAcceleration(0, 1);
		input.setAcceleration(1, 33);
		input.setAcceleration(2, 24);
		assertEquals(33, single.getLinearAcceleration(0), 0);
		assertEquals(33, single.getValue(), 0);
		assertTrue(listener.getUpdateCount()!=0);
		listener.resetUpdateCount();
		assertEquals(single, listener.getLastObject());
		
		input.setAcceleration(0, 111);
		input.setAcceleration(1, 23);
		input.setAcceleration(2, 24);
		assertEquals(23, single.getLinearAcceleration(0), 0);
		assertEquals(23, single.getValue(), 0);
		assertTrue(listener.getUpdateCount()!=0);
		listener.resetUpdateCount();
		assertEquals(single, listener.getLastObject());

		try{
			single.getLinearAcceleration(1);
			fail();
		} catch(RobotException e){
			//success
		}

		assertTrue(listener.getUpdateCount()==0);
		assertEquals(single, listener.getLastObject());
		
		single.removeUpdateListener(listener);
		input.setAcceleration(1, 24);
		assertEquals(24, single.getLinearAcceleration(0), 0);
		assertEquals(24, single.getValue(), 0);
		assertTrue(listener.getUpdateCount()==0);
		assertEquals(single, listener.getLastObject());
		


		try{
			single = new SingleAxisAccelerometer(input, -1);
			fail();
		} catch(RobotInitializationException e){
			//success
		}
		try{
			single = new SingleAxisAccelerometer(input, 3);
			fail();
		} catch(RobotInitializationException e){
			//success
		}
	}
}
