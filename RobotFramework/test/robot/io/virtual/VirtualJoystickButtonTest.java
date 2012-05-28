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
public class VirtualJoystickButtonTest {
	public final Mockery context = new Mockery();

	@DefaultTestParameter
	@TestParameter
	public static final Object[] test1 = { 0, new boolean[] {true}, new double[] {1} };
	@TestParameter
	public static final Object[] test2 = { 10, new boolean[] {true, false}, new double[] {1, 0} };
	@TestParameter
	public static final Object[] test3 = { 1, new boolean[] {false, true, false, true, true}, new double[] {0, 1, 0, 1, 1} };

	private final int id;
	private final boolean[] values;
	private final double[] doubleValues;
	public VirtualJoystickButtonTest(int id, boolean[] values, double[] doubleValues) {
		this.id = id;
		this.values = values;
		this.doubleValues = doubleValues;
	}
	
	private VirtualJoystickButton input;
	@Mock private RobotObjectListener listener;
	@Before public void setup(){
		input = new VirtualJoystickButton(id);
		input.addUpdateListener(listener);
	}
	
	@Test public void test0Arg() {
		context.checking(new Expectations() {
			{
				never(listener).objectUpdated(input);
			}
		});
		VirtualJoystickButton input0 = new VirtualJoystickButton();
		assertEquals(0, input0.getId());
	}

	@ParamTest public void testId() {
		context.checking(new Expectations() {
			{
				never(listener).objectUpdated(input);
			}
		});

		assertEquals(id, input.getId());
	}
	
	@ParamTest public void testGet() {
		context.checking(new Expectations() {
			{
				exactly(values.length).of(listener).objectUpdated(input);
			}
		});

		for(int i = 0; i<values.length; ++i){
			input.set(values[i]);
			assertEquals(values[i], input.get());
			assertEquals(doubleValues[i], input.getValue(), 0);
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
		input.set(!values[0]);
	}
}
