package robot.io.virtual;

import static org.junit.Assert.*;

import java.util.*;

import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.runners.Parameterized.Parameters;

import robot.error.*;
import robot.io.*;

@RunWith(Parameterized.class)
public class VirtualAccelerometerTest {
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	@After
	public void mockeryCheck() {
		context.assertIsSatisfied();
	}

	@Parameters public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
				{ new double[] {1.0} },
				{ new double[] {1, -4.3} },
				{ new double[] {8, -3.2, -1, 6.2} } };
		return Arrays.asList(data);
	}

	private final double[] values;
	public VirtualAccelerometerTest(double[] values) {
		this.values = values;
	}
	
	private VirtualAccelerometer input;
	private RobotObjectListener listener;
	@Before public void setup(){
		input = new VirtualAccelerometer(values.length);
		listener = context.mock(RobotObjectListener.class);
		input.addUpdateListener(listener);
	}
	
	@Test public void testNumAxes() {
		context.checking(new Expectations() {
			{
				never(listener).objectUpdated(input);
			}
		});
		assertEquals(values.length, input.getNumAccelerometerAxes());
	}

	@Test public void testSet() {
		context.checking(new Expectations() {
			{
				exactly(values.length).of(listener).objectUpdated(input);
			}
		});

		for(int i = 0; i<values.length; ++i)
			input.setAcceleration(i, values[i]);
		for(int i = 0; i<values.length; ++i)
			assertEquals(values[i], input.getLinearAcceleration(i), 0);

	}
	
	@Test public void testRemoveListener() {
		if(values.length==0)
			return;
		
		context.checking(new Expectations() {
			{
				oneOf(listener).objectUpdated(input);
			}
		});
		
		input.setAcceleration(0, values[0]);
		assertEquals(values[0], input.getLinearAcceleration(0), 0);
		
		input.removeUpdateListener(listener);
		input.setAcceleration(0, -values[0]);
		assertEquals(-values[0], input.getLinearAcceleration(0), 0);
	}
	
	
	@Test public void testOutOfBounds(){
		context.checking(new Expectations() {
			{
				never(listener).objectUpdated(input);
			}
		});
		try {
			input.setAcceleration(values.length, 10);
			fail();
		} catch (RobotException e) {
			// success
		}
		try {
			input.getLinearAcceleration(values.length);
			fail();
		} catch (RobotException e) {
			// success
		}
		
		try {
			input.setAcceleration(-1, 10);
			fail();
		} catch (RobotException e) {
			// success
		}
		try {
			input.getLinearAcceleration(-1);
			fail();
		} catch (RobotException e) {
			// success
		}
	}
}
