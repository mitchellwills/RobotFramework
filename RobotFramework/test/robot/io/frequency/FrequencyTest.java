package robot.io.frequency;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import robot.io.VirtualRobotObjectListener;
import robot.io.frequency.VirtualFrequencyInput;

@SuppressWarnings("javadoc")
@RunWith(JUnit4.class)
public class FrequencyTest {
	@Test
	public void testVirtaul(){
		VirtualFrequencyInput input = new VirtualFrequencyInput();
		VirtualRobotObjectListener listener = new VirtualRobotObjectListener();
		input.addUpdateListener(listener);
		
		input.setFrequency(100);
		assertEquals(100, input.getFrequency());
		assertEquals(100, input.getValue(), 0);
		assertEquals(1, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		input.setFrequency(122);
		assertEquals(122, input.getFrequency());
		assertEquals(122, input.getValue(), 0);
		assertEquals(2, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		
		input.removeUpdateListener(listener);
		input.setFrequency(60);
		assertEquals(60, input.getFrequency());
		assertEquals(60, input.getValue(), 0);
		assertEquals(2, listener.getUpdateCount());
	}
}
