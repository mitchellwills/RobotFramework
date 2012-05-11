package robot.io.binary;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import robot.io.VirtualRobotObjectListener;

@SuppressWarnings("javadoc")
@RunWith(JUnit4.class)
public class BinaryTest {
	@Test
	public void testVirtaulInput(){
		VirtualBinaryInput input = new VirtualBinaryInput();
		VirtualRobotObjectListener listener = new VirtualRobotObjectListener();
		input.addUpdateListener(listener);

		input.set(false);
		assertEquals(false, input.get());
		assertEquals(0, input.getValue(), 0);
		assertEquals(1, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		input.set(true);
		assertEquals(true, input.get());
		assertEquals(1.0, input.getValue(), 0);
		assertEquals(2, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		input.set(true);
		assertEquals(true, input.get());
		assertEquals(1.0, input.getValue(), 0);
		assertEquals(3, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		input.set(false);
		assertEquals(false, input.get());
		assertEquals(0, input.getValue(), 0);
		assertEquals(4, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		input.removeUpdateListener(listener);
		input.set(false);
		assertEquals(4, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
	}
	@Test
	public void testVirtaulOutput(){
		VirtualBinaryOutput output = new VirtualBinaryOutput();
		VirtualRobotObjectListener listener = new VirtualRobotObjectListener();
		output.addUpdateListener(listener);

		output.set(false);
		assertEquals(false, output.get());
		assertEquals(0, output.getValue(), 0);
		assertEquals(1, listener.getUpdateCount());
		assertEquals(output, listener.getLastObject());
		
		output.set(true);
		assertEquals(true, output.get());
		assertEquals(1.0, output.getValue(), 0);
		assertEquals(2, listener.getUpdateCount());
		assertEquals(output, listener.getLastObject());
		
		output.set(true);
		assertEquals(true, output.get());
		assertEquals(1.0, output.getValue(), 0);
		assertEquals(3, listener.getUpdateCount());
		assertEquals(output, listener.getLastObject());
		
		output.set(false);
		assertEquals(false, output.get());
		assertEquals(0, output.getValue(), 0);
		assertEquals(4, listener.getUpdateCount());
		assertEquals(output, listener.getLastObject());
		
		output.setValue(0);
		assertEquals(false, output.get());
		assertEquals(0, output.getValue(), 0);
		assertEquals(5, listener.getUpdateCount());
		assertEquals(output, listener.getLastObject());
		
		output.setValue(1);
		assertEquals(true, output.get());
		assertEquals(1.0, output.getValue(), 0);
		assertEquals(6, listener.getUpdateCount());
		assertEquals(output, listener.getLastObject());
		
		output.setValue(-22);
		assertEquals(true, output.get());
		assertEquals(1.0, output.getValue(), 0);
		assertEquals(7, listener.getUpdateCount());
		assertEquals(output, listener.getLastObject());
		
		output.removeUpdateListener(listener);
		output.set(false);
		assertEquals(7, listener.getUpdateCount());
		assertEquals(output, listener.getLastObject());
	}
}
