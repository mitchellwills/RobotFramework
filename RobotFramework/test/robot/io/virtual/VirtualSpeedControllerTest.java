package robot.io.virtual;

import static org.junit.Assert.*;

import org.jmock.*;
import org.junit.*;
import org.junit.runner.*;

import robot.io.*;
import test.*;
import test.RobotTestRunner.DefaultTestParameter;
import test.RobotTestRunner.ParamTest;
import test.RobotTestRunner.TestParameter;

@RunWith(RobotTestRunner.class)
public class VirtualSpeedControllerTest {
	public final Mockery context = new Mockery();

	@DefaultTestParameter
	@TestParameter
	public static final Object[] test1 = { 0, 90, new double[] {80, 30} };
	@TestParameter
	public static final Object[] test2 = { 60, 80, new double[] {70} };
	@TestParameter
	public static final Object[] test3 = { 80, 200, new double[] {90, 100, 140, 120.4} };

	private final double[] values;
	private final double min;
	private final double max;
	public VirtualSpeedControllerTest(double min, double max, double[] values) {
		this.min = min;
		this.max = max;
		this.values = values;
	}
	
	private VirtualServo input;
	private RobotObjectListener listener;
	@Before public void setup(){
		input = new VirtualServo(min, max);
		listener = context.mock(RobotObjectListener.class);
		input.addUpdateListener(listener);
	}

	@ParamTest public void testSet() {
		context.checking(new Expectations() {
			{
				exactly(values.length).of(listener).objectUpdated(input);
			}
		});

		for(int i = 0; i<values.length; ++i){
			input.setAngle(values[i]);
			assertEquals(values[i], input.getAngle(), 0);
			assertEquals(values[i], input.getValue(), 0);
		}
	}

	@ParamTest public void testBounds() {
		context.checking(new Expectations() {
			{
				never(listener).objectUpdated(input);
			}
		});

		assertEquals(min, input.getMinAngle(), 0);
		assertEquals(max, input.getMaxAngle(), 0);
	}
	
	@ParamTest public void testSetValue() {
		context.checking(new Expectations() {
			{
				exactly(values.length).of(listener).objectUpdated(input);
			}
		});

		for(int i = 0; i<values.length; ++i){
			input.setValue(values[i]);
			assertEquals(values[i], input.getAngle(), 0);
			assertEquals(values[i], input.getValue(), 0);
		}

	}
	
	@Test public void testRemoveListener() {
		if(values.length==0)
			return;
		
		context.checking(new Expectations() {
			{
				oneOf(listener).objectUpdated(input);
			}
		});
		
		input.setAngle(values[0]);
		
		input.removeUpdateListener(listener);
		input.setAngle(0);
	}
}
