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
public class VirtualCompassTest {
	public final Mockery context = new Mockery();

	@DefaultTestParameter
	@TestParameter
	public static final Object[] test1 = {new double[] {1.0}};
	@TestParameter
	public static final Object[] test2 = {new double[] {1, -4.3}};
	@TestParameter
	public static final Object[] test3 = {new double[] {8, -3.2, -1, 6.2}};

	private final double[] values;
	public VirtualCompassTest(double[] values) {
		this.values = values;
	}
	
	private VirtualCompass input;
	@Mock private RobotObjectListener listener;
	@Before public void setup(){
		input = new VirtualCompass();
		input.addUpdateListener(listener);
	}

	@ParamTest public void testSet() {
		context.checking(new Expectations() {
			{
				exactly(values.length).of(listener).objectUpdated(input);
			}
		});

		for(int i = 0; i<values.length; ++i){
			input.setHeading(values[i]);
			assertEquals(values[i], input.getHeading(), 0);
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
		
		input.setHeading(values[0]);
		
		input.removeUpdateListener(listener);
		input.setHeading(0);
	}
}
