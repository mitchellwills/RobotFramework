package robot.io.encoder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import robot.io.VirtualRobotObjectListener;
import robot.io.encoder.VirtualEncoder;

@SuppressWarnings("javadoc")
@RunWith(JUnit4.class)
public class EncoderTest {
	@Test
	public void testVirtaul(){
		VirtualEncoder input = new VirtualEncoder();
		VirtualRobotObjectListener listener = new VirtualRobotObjectListener();
		input.addUpdateListener(listener);
		
		assertEquals(0, input.getPosition());
		
		input.setPosition(-12);
		assertEquals(-12, input.getPosition());
		assertEquals(-12, input.getValue(), 0);
		assertEquals(1, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		input.setPosition(11);
		assertEquals(11, input.getPosition());
		assertEquals(11, input.getValue(), 0);
		assertEquals(2, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());

		
		input.increment();
		assertEquals(12, input.getPosition());
		assertEquals(12, input.getValue(), 0);
		assertEquals(3, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		input.decrement();
		assertEquals(11, input.getPosition());
		assertEquals(11, input.getValue(), 0);
		assertEquals(4, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		
		input.removeUpdateListener(listener);
		input.setPosition(111);
		assertEquals(111, input.getPosition());
		assertEquals(111, input.getValue(), 0);
		assertEquals(4, listener.getUpdateCount());
	}
}
