package robot.io.compass;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import robot.RobotTestUtils;
import robot.io.VirtualRobotObjectListener;
import robot.util.RobotUtil;

@SuppressWarnings("javadoc")
@RunWith(JUnit4.class)
public class CompassTest {
	@Test
	public void testVirtaul(){
		VirtualCompass input = new VirtualCompass();
		VirtualRobotObjectListener listener = new VirtualRobotObjectListener();
		input.addUpdateListener(listener);
		
		input.setHeading(100);
		assertEquals(100, input.getHeading(), 0);
		assertEquals(100, input.getValue(), 0);
		assertEquals(1, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		input.setHeading(234);
		assertEquals(234, input.getHeading(), 0);
		assertEquals(234, input.getValue(), 0);
		assertEquals(2, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
		
		input.removeUpdateListener(listener);
		input.setHeading(234);
		assertEquals(2, listener.getUpdateCount());
		assertEquals(input, listener.getLastObject());
	}
	
	@Test
	public void testAveragedCompass(){
		RobotTestUtils.initFakeRobot();
		VirtualCompass input = new VirtualCompass();
		input.setHeading(100);
		AveragedCompass average = new AveragedCompass(input, 3, 20);
		VirtualRobotObjectListener listener = new VirtualRobotObjectListener();
		average.addUpdateListener(listener);
		
		RobotUtil.sleep(5);
		assertEquals(100, average.getHeading(), 0);
		assertEquals(100, average.getValue(), 0);
		
		input.setHeading(99);
		RobotUtil.sleep(20);
		assertEquals(99.5, average.getHeading(), 0);
		assertEquals(99.5, average.getValue(), 0);
		
		input.setHeading(101);
		RobotUtil.sleep(20);
		assertEquals(100, average.getHeading(), 0);
		assertEquals(100, average.getValue(), 0);
		
		input.setHeading(103);
		RobotUtil.sleep(20);
		assertEquals(101, average.getHeading(), 0);
		assertEquals(101, average.getValue(), 0);
		
		input.removeUpdateListener(listener);
		input.setHeading(102);
		RobotUtil.sleep(20);
		assertEquals(102, average.getHeading(), 0);
		assertEquals(102, average.getValue(), 0);

		RobotTestUtils.cleanupFakeRobot();
	}
}
