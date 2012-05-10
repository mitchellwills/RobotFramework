package robot.io.analog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import robot.error.RobotInitializationException;
import robot.io.VirtualRobotObjectListener;

@SuppressWarnings("javadoc")
@RunWith(JUnit4.class)
public class AnalogTest {
	@Test
	public void testVirtaulVoltage(){
		VirtualAnalogVoltageInput input = new VirtualAnalogVoltageInput(5);
		VirtualRobotObjectListener listener = new VirtualRobotObjectListener();
		input.addUpdateListener(listener);
		
		assertEquals(5, input.getMaxVoltage(), 0);
		
		input.setVoltage(3);
		assertEquals(3, input.getVoltage(), 0);
		assertEquals(3, input.getValue(), 0);
		assertEquals(5, input.getMaxVoltage(), 0);
		assertEquals(1, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		input.setVoltage(4.4);
		assertEquals(4.4, input.getVoltage(), 0);
		assertEquals(4.4, input.getValue(), 0);
		assertEquals(5, input.getMaxVoltage(), 0);
		assertEquals(2, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());

		try{
			input = new VirtualAnalogVoltageInput(0);
			fail();
		} catch(RobotInitializationException e){
			//success
		}
		try{
			input = new VirtualAnalogVoltageInput(-1);
			fail();
		} catch(RobotInitializationException e){
			//success
		}
		assertEquals(2, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		input.removeUpdateListener(listener);
		input.setVoltage(1);
		assertEquals(2, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
	}
}
