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
public class VirtualBinaryOutputTest {
	public final Mockery context = new Mockery();

	@DefaultTestParameter
	@TestParameter
	public static final Object[] test1 = { new boolean[] {true}, new double[] {1}, new double[] {3.7} };
	@TestParameter
	public static final Object[] test2 = { new boolean[] {true, false}, new double[] {1, 0}, new double[] {4.2, 0} };
	@TestParameter
	public static final Object[] test3 = { new boolean[] {false, true, false, true, true},
					new double[] {0, 1, 0, 1, 1},
					new double[] {0, 3.2, 0, 4.2, -4} };

	private final boolean[] values;
	private final double[] doubleValues;
	private final double[] otherDoubleValues;
	public VirtualBinaryOutputTest(boolean[] values, double[] doubleValues, double[] otherDoubleValues) {
		this.values = values;
		this.doubleValues = doubleValues;
		this.otherDoubleValues = otherDoubleValues;
	}
	
	private VirtualBinaryOutput input;
	@Mock private RobotObjectListener listener;
	@Before public void setup(){
		input = new VirtualBinaryOutput();
		input.addUpdateListener(listener);
	}

	@ParamTest public void testSet() {
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
	@ParamTest public void testSetValue() {
		context.checking(new Expectations() {
			{
				exactly(values.length).of(listener).objectUpdated(input);
			}
		});

		for(int i = 0; i<values.length; ++i){
			input.setValue(otherDoubleValues[i]);
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
