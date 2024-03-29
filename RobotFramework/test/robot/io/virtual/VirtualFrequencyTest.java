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
public class VirtualFrequencyTest {
	public final Mockery context = new Mockery();

	@DefaultTestParameter
	@TestParameter
	public static final Object[] test1 = { new long[] {1, 4, 3} };
	@TestParameter
	public static final Object[] test2 = { new long[] {1} };
	@TestParameter
	public static final Object[] test3 = { new long[] {8, 3, 1, 6, 10} };

	private final long[] values;
	public VirtualFrequencyTest(long[] values) {
		this.values = values;
	}
	
	private VirtualFrequencyInput input;
	private RobotObjectListener listener;
	@Before public void setup(){
		input = new VirtualFrequencyInput();
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
			input.setFrequency(values[i]);
			assertEquals(values[i], input.getFrequency(), 0);
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
		
		input.setFrequency(values[0]);
		
		input.removeUpdateListener(listener);
		input.setFrequency(0);
	}
}
