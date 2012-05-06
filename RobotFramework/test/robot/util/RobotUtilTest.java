package robot.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.assertEquals;

@SuppressWarnings("javadoc")
@RunWith(JUnit4.class)
public class RobotUtilTest {

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
}