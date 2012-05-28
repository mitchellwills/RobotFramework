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
public class VirtualJoystickDirectionalTest {
	public final Mockery context = new Mockery();

	@DefaultTestParameter
	@TestParameter
	public static final Object[] test1 = { 5, new double[] { 1.0, 4.3 } };
	@TestParameter
	public static final Object[] test2 = { 3, new double[] { 1 } };
	@TestParameter
	public static final Object[] test3 = { 12, new double[] { 8, 3.2, 1, 6.2, 10 } };

	private final double[] values;
	private final int id;

	public VirtualJoystickDirectionalTest(int id, double[] values) {
		this.id = id;
		this.values = values;
	}

	private VirtualJoystickDirectional input;
	private RobotObjectListener listener;

	@Before
	public void setup() {
		input = new VirtualJoystickDirectional(id);
		listener = context.mock(RobotObjectListener.class);
		input.addUpdateListener(listener);
	}

	@Test
	public void test0Arg() {
		context.checking(new Expectations() {
			{
				never(listener).objectUpdated(input);
			}
		});
		VirtualJoystickDirectional input0 = new VirtualJoystickDirectional();
		assertEquals(0, input0.getId());
	}

	@ParamTest
	public void testId() {
		context.checking(new Expectations() {
			{
				never(listener).objectUpdated(input);
			}
		});
		assertEquals(id, input.getId());
	}

	@Test
	public void testCentered() {
		context.checking(new Expectations() {
			{
				allowing(listener).objectUpdated(input);
			}
		});
		input.setMagnitude(0);
		assertEquals(true, input.isCentered());
		input.setMagnitude(1);
		assertEquals(false, input.isCentered());
	}

	@ParamTest
	public void testSet() {
		context.checking(new Expectations() {
			{
				exactly((values.length - 1) * 2).of(listener).objectUpdated(
						input);
			}
		});

		for (int i = 0; i < values.length - 1; ++i) {
			input.setAngle(values[i]);
			input.setMagnitude(values[i + 1]);
			assertEquals(values[i], input.getAngle(), 0);
			assertEquals(values[i + 1], input.getMagnitude(), 0);
		}
	}

	@ParamTest
	public void testSetMagnitude() {
		context.checking(new Expectations() {
			{
				exactly(values.length).of(listener).objectUpdated(input);
			}
		});

		for (int i = 0; i < values.length; ++i) {
			input.setMagnitude(values[i]);
			assertEquals(values[i], input.getMagnitude(), 0);
		}
	}

	@ParamTest
	public void testSetAngle() {
		context.checking(new Expectations() {
			{
				exactly(values.length).of(listener).objectUpdated(input);
			}
		});

		for (int i = 0; i < values.length; ++i) {
			input.setAngle(values[i]);
			assertEquals(values[i], input.getAngle(), 0);
		}
	}

	@Test
	public void testRemoveListener() {
		if (values.length == 0)
			return;

		context.checking(new Expectations() {
			{
				oneOf(listener).objectUpdated(input);
			}
		});

		input.setMagnitude(values[0]);

		input.removeUpdateListener(listener);
		input.setMagnitude(0);
	}
}
