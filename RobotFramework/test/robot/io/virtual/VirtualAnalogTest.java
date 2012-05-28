package robot.io.virtual;

import static org.junit.Assert.*;

import org.jmock.*;
import org.jmock.auto.*;
import org.junit.*;
import org.junit.runner.*;

import robot.error.*;
import robot.io.*;
import test.*;
import test.RobotTestRunner.*;

@RunWith(RobotTestRunner.class)
public class VirtualAnalogTest {
	public final Mockery context = new Mockery();
	
	@DefaultTestParameter
	@TestParameter
	public static final Object[] test1 = {5, new double[] {1.0, 4.3}};
	@TestParameter
	public static final Object[] test2 = {3, new double[] {1}};
	@TestParameter
	public static final Object[] test3 = {12, new double[] {8, 3.2, 1, 6.2, 10}};


	private final double[] values;
	private final int maxVoltage;
	public VirtualAnalogTest(int maxVoltage, double[] values) {
		this.maxVoltage = maxVoltage;
		this.values = values;
	}
	
	private VirtualAnalogVoltageInput input;
	@Mock private RobotObjectListener listener;
	@Before public void setup(){
		input = new VirtualAnalogVoltageInput(maxVoltage);
		input.addUpdateListener(listener);
	}
	
	@ParamTest public void testMaxVoltage() {
		context.checking(new Expectations() {
			{
				never(listener).objectUpdated(input);
			}
		});
		assertEquals(maxVoltage, input.getMaxVoltage(), 0);
	}

	@ParamTest public void testSet() {
		context.checking(new Expectations() {
			{
				exactly(values.length).of(listener).objectUpdated(input);
			}
		});

		for(int i = 0; i<values.length; ++i){
			input.setVoltage(values[i]);
			assertEquals(values[i], input.getVoltage(), 0);
			assertEquals(values[i], input.getValue(), 0);
		}

	}
	
	@ParamTest public void testRemoveListener() {
		if(values.length==0)
			return;
		
		context.checking(new Expectations() {
			{
				oneOf(listener).objectUpdated(input);
			}
		});
		
		input.setVoltage(values[0]);
		
		input.removeUpdateListener(listener);
		input.setVoltage(0);
	}
	
	@Test public void testInvalidMaxVoltage(){
		try{
			input = new VirtualAnalogVoltageInput(0);
			fail();
		} catch(RobotInitializationException e){
			//success
		}
		try{
			input = new VirtualAnalogVoltageInput(-1);
			fail();
		} catch(RobotInitializationException e){
			//success
		}
	}
}
