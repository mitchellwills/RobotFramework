package robot.io.virtual;

import static org.junit.Assert.*;

import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.junit.*;
import org.junit.runner.*;

import robot.io.*;
import test.*;
import test.RobotTestRunner.ParamTest;
import test.RobotTestRunner.TestParameter;

@RunWith(RobotTestRunner.class)
public class VirtualDutyCycleTest {
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	@TestParameter
	public static final Object[] test1 = { new double[] {1.0, 4.3} };
	@TestParameter
	public static final Object[] test2 = { new double[] {1} };
	@TestParameter
	public static final Object[] test3 = { new double[] {8, 3.2, 1, 6.2, 10} };

	private final double[] values;
	public VirtualDutyCycleTest(double[] values) {
		this.values = values;
	}
	
	private VirtualDutyCycleInput input;
	private RobotObjectListener listener;
	@Before public void setup(){
		input = new VirtualDutyCycleInput();
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
			input.setDutyCycle(values[i]);
			assertEquals(values[i], input.getDutyCycle(), 0);
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
		
		input.setDutyCycle(values[0]);
		
		input.removeUpdateListener(listener);
		input.setDutyCycle(0);
	}
}
