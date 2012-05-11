package robot.io.dutycycle;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import robot.io.VirtualRobotObjectListener;
import robot.io.dutycycle.VirtualDutyCycleInput;

@SuppressWarnings("javadoc")
@RunWith(JUnit4.class)
public class DutyCycleTest {
	@Test
	public void testVirtaul(){
		VirtualDutyCycleInput input = new VirtualDutyCycleInput();
		VirtualRobotObjectListener listener = new VirtualRobotObjectListener();
		input.addUpdateListener(listener);
		
		input.setDutyCycle(0.5);
		assertEquals(0.5, input.getDutyCycle(), 0);
		assertEquals(0.5, input.getValue(), 0);
		assertEquals(1, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		input.setDutyCycle(0.2);
		assertEquals(0.2, input.getDutyCycle(), 0);
		assertEquals(0.2, input.getValue(), 0);
		assertEquals(2, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		
		input.removeUpdateListener(listener);
		input.setDutyCycle(0.0);
		assertEquals(0.0, input.getDutyCycle(), 0);
		assertEquals(0.0, input.getValue(), 0);
		assertEquals(2, listener.getUpdateCount());
	}
}
