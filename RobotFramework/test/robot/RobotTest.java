package robot;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@SuppressWarnings("javadoc")
@RunWith(JUnit4.class)
public class RobotTest {

	@Test
	public void testInstance(){
		try{
			Robot.getInstance();
			fail();
		} catch(IllegalStateException e){
			//success
		}
		FakeRobot robot = new FakeRobot();
		try{
			Robot.getInstance();
			fail();
		} catch(IllegalStateException e){
			//success
		}
		Robot.go(robot);
		assertTrue(robot==Robot.getInstance());
	}
}
