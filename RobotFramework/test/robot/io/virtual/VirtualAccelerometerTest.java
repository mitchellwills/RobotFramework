package robot.io.virtual;

import static org.junit.Assert.*;

import org.jmock.*;
import org.jmock.auto.*;
import org.junit.*;
import org.junit.runner.*;

import robot.error.*;
import robot.io.*;
import test.*;
import test.RobotTestRunner.DefaultTestParameter;
import test.RobotTestRunner.ParamTest;
import test.RobotTestRunner.TestParameter;

@RunWith(RobotTestRunner.class)
public class VirtualAccelerometerTest {
	public final Mockery context = new Mockery();

	@DefaultTestParameter
	@TestParameter
	public static final Object[] test1 = {new double[] {1.0}};
	@TestParameter
	public static final Object[] test2 = {new double[] {1, -4.3}};
	@TestParameter
	public static final Object[] test3 = {new double[] {8, -3.2, -1, 6.2}};
	

	private final double[] values;
	public VirtualAccelerometerTest(double[] values) {
		this.values = values;
	}
	
	private VirtualAccelerometer input;
	@Mock private RobotObjectListener listener;
	@Before public void setup(){
		input = new VirtualAccelerometer(values.length);
		input.addUpdateListener(listener);
	}
	
	@ParamTest public void testNumAxes() {
		context.checking(new Expectations() {
			{
				never(listener).objectUpdated(input);
			}
		});
		assertEquals(values.length, input.getNumAccelerometerAxes());
	}

	@ParamTest public void testSet() {
		context.checking(new Expectations() {
			{
				exactly(values.length).of(listener).objectUpdated(input);
			}
		});

		for(int i = 0; i<values.length; ++i)
			input.setAcceleration(i, values[i]);
		for(int i = 0; i<values.length; ++i)
			assertEquals(values[i], input.getLinearAcceleration(i), 0);
	}
	
	@Test public void testRemoveListener() {
		context.checking(new Expectations() {
			{
				oneOf(listener).objectUpdated(input);
			}
		});
		
		input.setAcceleration(0, values[0]);
		assertEquals(values[0], input.getLinearAcceleration(0), 0);
		
		input.removeUpdateListener(listener);
		input.setAcceleration(0, -values[0]);
		assertEquals(-values[0], input.getLinearAcceleration(0), 0);
	}
	
	
	@ParamTest public void testOutOfBounds(){
		context.checking(new Expectations() {
			{
				never(listener).objectUpdated(input);
			}
		});
		try {
			input.setAcceleration(values.length, 10);
			fail();
		} catch (RobotException e) {
			// success
		}
		try {
			input.getLinearAcceleration(values.length);
			fail();
		} catch (RobotException e) {
			// success
		}
		
		try {
			input.setAcceleration(-1, 10);
			fail();
		} catch (RobotException e) {
			// success
		}
		try {
			input.getLinearAcceleration(-1);
			fail();
		} catch (RobotException e) {
			// success
		}
	}
}
