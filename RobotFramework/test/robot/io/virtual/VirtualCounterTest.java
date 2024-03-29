package robot.io.virtual;

import static org.junit.Assert.*;

import org.jmock.*;
import org.junit.*;
import org.junit.runner.*;

import robot.io.*;
import robot.math.*;
import test.*;
import test.RobotTestRunner.DefaultTestParameter;
import test.RobotTestRunner.ParamTest;
import test.RobotTestRunner.TestParameter;

@RunWith(RobotTestRunner.class)
public class VirtualCounterTest {
	public final Mockery context = new Mockery();

	@DefaultTestParameter
	@TestParameter
	public static final Object[] test1 = { new int[] {1, 4, 3} };
	@TestParameter
	public static final Object[] test2 = { new int[] {2} };
	@TestParameter
	public static final Object[] test3 = { new int[] {8, 3, 1, 6, 10} };

	private final int[] values;
	public VirtualCounterTest(int[] values) {
		this.values = values;
	}
	
	private VirtualCounter input;
	private RobotObjectListener listener;
	@Before public void setup(){
		input = new VirtualCounter();
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
			input.setCount(values[i]);
			assertEquals(values[i], input.getCount());
			assertEquals(values[i], input.getValue(), 0);
		}
	}
	
	@ParamTest public void testIncrementDecrement() {
		context.checking(new Expectations() {
			{
				exactly(MathUtil.sum(values)*2-values[0]+1).of(listener).objectUpdated(input);
			}
		});

		input.setCount(values[0]);
		int value = values[0];
		for(int i = 1; i<values.length; ++i){
			for(int j = 0; j<values[i]; ++j){
				input.increment();
				++value;
				assertEquals(value, input.getCount());
				assertEquals(value, input.getValue(), 0);
			}
		}
		for(int i = 0; i<values.length; ++i){
			for(int j = 0; j<values[i]; ++j){
				input.decrement();
				--value;
				assertEquals(value, input.getCount());
				assertEquals(value, input.getValue(), 0);
			}
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
		
		input.setCount(values[0]);
		
		input.removeUpdateListener(listener);
		input.setCount(0);
	}
}
