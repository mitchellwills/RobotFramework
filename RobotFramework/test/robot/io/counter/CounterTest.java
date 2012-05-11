package robot.io.counter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import robot.io.VirtualRobotObjectListener;

@SuppressWarnings("javadoc")
@RunWith(JUnit4.class)
public class CounterTest {
	@Test
	public void testVirtaul(){
		VirtualCounter input = new VirtualCounter();
		VirtualRobotObjectListener listener = new VirtualRobotObjectListener();
		input.addUpdateListener(listener);
		
		assertEquals(0, input.getCount());
		
		input.setCount(1);
		assertEquals(1, input.getCount());
		assertEquals(1, input.getValue(), 0);
		assertEquals(1, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		input.setCount(11);
		assertEquals(11, input.getCount());
		assertEquals(11, input.getValue(), 0);
		assertEquals(2, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		input.increment();
		assertEquals(12, input.getCount());
		assertEquals(12, input.getValue(), 0);
		assertEquals(3, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		input.decrement();
		assertEquals(11, input.getCount());
		assertEquals(11, input.getValue(), 0);
		assertEquals(4, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		
		input.removeUpdateListener(listener);
		input.setCount(111);
		assertEquals(111, input.getCount());
		assertEquals(111, input.getValue(), 0);
		assertEquals(4, listener.getUpdateCount());
	}
}
