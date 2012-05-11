package robot.io.ppm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import robot.error.RobotException;
import robot.io.VirtualRobotObjectListener;

@SuppressWarnings("javadoc")
@RunWith(JUnit4.class)
public class PPMTest {
	@Test
	public void testVirtaul(){
		VirtualPPMReader input = new VirtualPPMReader(3);
		VirtualRobotObjectListener listener = new VirtualRobotObjectListener();
		input.addUpdateListener(listener);

		assertEquals(3, input.getChannelCount());
		assertEquals(PPMReader.INVALID_VALUE, input.getChannel(0));
		assertEquals(PPMReader.INVALID_VALUE, input.getChannel(1));
		assertEquals(PPMReader.INVALID_VALUE, input.getChannel(2));
		
		input.setChannelValue(0, 1000);
		input.setChannelValue(1, 1500);
		input.setChannelValue(2, 1700);
		assertEquals(1000, input.getChannel(0));
		assertEquals(1500, input.getChannel(1));
		assertEquals(1700, input.getChannel(2));
		
		assertEquals(3, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());

		input.setChannelValues(new long[]{600, 1300, 1000});
		assertEquals(600, input.getChannel(0));
		assertEquals(1300, input.getChannel(1));
		assertEquals(1000, input.getChannel(2));
		
		assertEquals(4, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());

		try{
			input.setChannelValue(3, 10);
			fail();
		} catch(RobotException e){
			//success
		}

		try{
			input.setChannelValue(-1, 10);
			fail();
		} catch(RobotException e){
			//success
		}
		try{
			input.getChannel(-1);
			fail();
		} catch(RobotException e){
			//success
		}
		try{
			input.getChannel(3);
			fail();
		} catch(RobotException e){
			//success
		}
		try{
			input.setChannelValues(new long[]{600, 1300});
			fail();
		} catch(RobotException e){
			//success
		}

		assertEquals(4, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		input.removeUpdateListener(listener);
		input.setChannelValue(2, 24);
		assertEquals(4, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
	}

	@Test
	public void testAxis(){
		VirtualPPMReader input = new VirtualPPMReader(3);
		PPMChannelJoystickAxis axis = new PPMChannelJoystickAxis(input, 1, 1000, 2000);
		VirtualRobotObjectListener listener = new VirtualRobotObjectListener();
		axis.addUpdateListener(listener);

		assertEquals("PPM channel 1", axis.getName());
		
		assertEquals(0, axis.getValue(), 0);
		
		input.setChannelValue(0, 1000);
		input.setChannelValue(1, 1500);
		input.setChannelValue(2, 1700);
		assertEquals(0, axis.getValue(), 0);
		
		assertEquals(3, listener.getUpdateCount());
		assertEquals(axis, listener.getLastObject());

		input.setChannelValues(new long[]{600, 1250, 1000});
		assertEquals(-.5, axis.getValue(), 0);
		
		assertEquals(4, listener.getUpdateCount());
		assertEquals(axis, listener.getLastObject());
		
		axis.removeUpdateListener(listener);
		input.setChannelValue(1, 24);
		assertEquals(4, listener.getUpdateCount());
		assertEquals(axis, listener.getLastObject());
	}

}
