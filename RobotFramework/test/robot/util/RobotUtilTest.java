package robot.util;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(JUnit4.class)
public class RobotUtilTest {
	@SuppressWarnings("unused")
	@Test
	public void testConstructor() {
		new RobotUtil();
	}

	@Test
	public void testLimit() {
		assertEquals(10, RobotUtil.limit(10, 0, 20), 0);
		
		assertEquals(15, RobotUtil.limit(15, 15, 20), 0);
		assertEquals(20, RobotUtil.limit(20, 15, 20), 0);

		assertEquals(15, RobotUtil.limit(10, 15, 20), 0);
		assertEquals(20, RobotUtil.limit(30, 15, 20), 0);
	}

	@Test
	public void testWithin() {
		assertEquals(true, RobotUtil.within(10, 0, 20));
		
		assertEquals(false, RobotUtil.within(15, 15, 20));
		assertEquals(false, RobotUtil.within(20, 15, 20));

		assertEquals(false, RobotUtil.within(10, 15, 20));
		assertEquals(false, RobotUtil.within(30, 15, 20));
	}

	@Test
	public void testThreshold() {
		assertEquals(15, RobotUtil.threshold(10, 0, 20, 15), 0);
		
		assertEquals(15, RobotUtil.threshold(15, 15, 20, 0), 0);
		assertEquals(20, RobotUtil.threshold(20, 15, 20, 0), 0);

		assertEquals(10, RobotUtil.threshold(10, 15, 20, 0), 0);
		assertEquals(30, RobotUtil.threshold(30, 15, 20, 0), 0);
	}

	@Test
	public void testThreshold0() {
		assertEquals(0, RobotUtil.threshold(1, 5), 0);
		
		assertEquals(5, RobotUtil.threshold(5, 5), 0);
		assertEquals(-5, RobotUtil.threshold(-5, 5), 0);

		assertEquals(10, RobotUtil.threshold(10, 5), 0);
		assertEquals(-20, RobotUtil.threshold(-20, 5), 0);
	}
	
	@Test
	public void testSleep(){
		long start = System.currentTimeMillis();
		RobotUtil.sleep(100);
		long diff = System.currentTimeMillis()-start;
		assertEquals(100, diff, 5);
	}
	
	@Test
	public void testArrayConcat(){
		Integer[] array1Expected = new Integer[]{0, 4, 5};
		Integer[] array1 = RobotUtil.concat(new Integer[]{0, 4, 5});
		assertArrayEquals(array1Expected, array1);
		
		Integer[] array2Expected = new Integer[]{3, 4, 5, 4, 5};
		Integer[] array2 = RobotUtil.concat(new Integer[]{3, 4, 5}, 4, 5);
		assertArrayEquals(array2Expected, array2);
		
		Integer[] array3Expected = new Integer[]{1, 2, 4};
		Integer[] array3 = RobotUtil.concat(new Integer[]{}, 1, 2, 4);
		assertArrayEquals(array3Expected, array3);
		
		Integer[] array4Expected = new Integer[]{};
		Integer[] array4 = RobotUtil.concat(new Integer[]{});
		assertArrayEquals(array4Expected, array4);
	}
}
