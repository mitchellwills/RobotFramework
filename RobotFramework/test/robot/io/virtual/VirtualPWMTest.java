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
public class VirtualPWMTest {
	public final Mockery context = new Mockery();

	@DefaultTestParameter
	@TestParameter
	public static final Object[] test1 = { 90, new double[] {80, 30} };
	@TestParameter
	public static final Object[] test2 = { 80, new double[] {70} };
	@TestParameter
	public static final Object[] test3 = { 200, new double[] {90, 100, 140, 120.4} };


	private final double[] values;
	private final double frequency;
	public VirtualPWMTest(double frequency, double[] values) {
		this.frequency = frequency;
		this.values = values;
	}
	
	private VirtualPWMOutput input;
	@Mock private RobotObjectListener listener;
	@Before public void setup(){
		input = new VirtualPWMOutput(frequency);
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
			assertEquals(values[i], input.get(), 0);
			assertEquals(values[i], input.getValue(), 0);
		}
	}

	@ParamTest public void testFrequency() {
		context.checking(new Expectations() {
			{
				never(listener).objectUpdated(input);
			}
		});

		assertEquals(frequency, input.getFrequency(), 0);
	}
	
	@ParamTest public void testSetValue() {
		context.checking(new Expectations() {
			{
				exactly(values.length).of(listener).objectUpdated(input);
			}
		});

		for(int i = 0; i<values.length; ++i){
			input.setValue(values[i]);
			assertEquals(values[i], input.get(), 0);
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
