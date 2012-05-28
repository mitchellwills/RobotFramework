package robot.util;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.*;

import test.*;

@RunWith(RobotTestRunner.class)
public class RingBufferTest {
	//TODO make param test
	@Test
	public void testSumAverage(){
		RingBuffer buffer = new RingBuffer(5);
		assertEquals(Double.NaN, buffer.average(), 0);
		assertEquals(Double.NaN, buffer.sum(), 0);
		
		buffer.append(10);
		assertEquals(10, buffer.average(), 0);
		assertEquals(10, buffer.sum(), 0);
		
		buffer.append(20);
		assertEquals(15, buffer.average(), 0);
		assertEquals(30, buffer.sum(), 0);
		
		buffer.append(15);
		assertEquals(15, buffer.average(), 0);
		assertEquals(45, buffer.sum(), 0);

		buffer.append(35);
		assertEquals(20, buffer.average(), 0);
		assertEquals(80, buffer.sum(), 0);

		buffer.append(0);
		assertEquals(16, buffer.average(), 0);
		assertEquals(80, buffer.sum(), 0);

		buffer.append(90);
		assertEquals(32, buffer.average(), 0);
		assertEquals(160, buffer.sum(), 0);
	}

	@Test
	public void testAppend(){
		RingBuffer buffer = new RingBuffer(5);
		buffer.append(10);
		assertEquals(10, buffer.get(4), 0);
		buffer.append(12);
		assertEquals(10, buffer.get(3), 0);
		assertEquals(12, buffer.get(4), 0);
		buffer.append(14);
		assertEquals(10, buffer.get(2), 0);
		assertEquals(12, buffer.get(3), 0);
		assertEquals(14, buffer.get(4), 0);
		buffer.append(15);
		assertEquals(10, buffer.get(1), 0);
		assertEquals(12, buffer.get(2), 0);
		assertEquals(14, buffer.get(3), 0);
		assertEquals(15, buffer.get(4), 0);
		buffer.append(17);
		assertEquals(10, buffer.get(0), 0);
		assertEquals(12, buffer.get(1), 0);
		assertEquals(14, buffer.get(2), 0);
		assertEquals(15, buffer.get(3), 0);
		assertEquals(17, buffer.get(4), 0);
		buffer.append(22);
		assertEquals(12, buffer.get(0), 0);
		assertEquals(14, buffer.get(1), 0);
		assertEquals(15, buffer.get(2), 0);
		assertEquals(17, buffer.get(3), 0);
		assertEquals(22, buffer.get(4), 0);
	}
}
