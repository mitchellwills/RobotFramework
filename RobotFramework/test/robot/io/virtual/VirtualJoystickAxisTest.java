package robot.io.virtual;

import static org.junit.Assert.*;

import org.jmock.*;
import org.jmock.auto.*;
import org.junit.*;
import org.junit.runner.*;

import robot.io.*;
import test.*;
import test.RobotTestRunner.DefaultTestParameter;
import test.RobotTestRunner.ParamTest;
import test.RobotTestRunner.TestParameter;

@RunWith(RobotTestRunner.class)
public class VirtualJoystickAxisTest {
	public final Mockery context = new Mockery();

	@DefaultTestParameter
	@TestParameter
	public static final Object[] test1 = { 5, new double[] {1.0, 4.3} };
	@TestParameter
	public static final Object[] test2 = { 3, new double[] {1} };
	@TestParameter
	public static final Object[] test3 = { 12, new double[] {8, 3.2, 1, 6.2, 10} };

	private final double[] values;
	private final int id;
	public VirtualJoystickAxisTest(int id, double[] values) {
		this.id = id;
		this.values = values;
	}
	
	private VirtualJoystickAxis input;
	@Mock private RobotObjectListener listener;
	@Before public void setup(){
		input = new VirtualJoystickAxis(id);
		input.addUpdateListener(listener);
	}
	
	@ParamTest public void testId() {
		context.checking(new Expectations() {
			{
				never(listener).objectUpdated(input);
			}
		});
		assertEquals(id, input.getId());
	}
	
	@Test public void test0Arg() {
		context.checking(new Expectations() {
			{
				never(listener).objectUpdated(input);
			}
		});
		VirtualJoystickAxis input0 = new VirtualJoystickAxis();
		assertEquals(0, input0.getId());
	}

	@ParamTest public void testSet() {
		context.checking(new Expectations() {
			{
				exactly(values.length).of(listener).objectUpdated(input);
			}
		});

		for(int i = 0; i<values.length; ++i){
			input.set(values[i]);
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
		
		input.set(values[0]);
		
		input.removeUpdateListener(listener);
		input.set(0);
	}
}
