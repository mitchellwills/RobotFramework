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
public class VirtualAnalogTest {
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	@After
	public void mockeryCheck() {
		context.assertIsSatisfied();
	}

	@Parameters public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
				{ 5, new double[] {1.0, 4.3} },
				{ 3, new double[] {1} },
				{ 12, new double[] {8, 3.2, 1, 6.2, 10} } };
		return Arrays.asList(data);
	}

	private final double[] values;
	private final int maxVoltage;
	public VirtualAnalogTest(int maxVoltage, double[] values) {
		this.maxVoltage = maxVoltage;
		this.values = values;
	}
	
	private VirtualAnalogVoltageInput input;
	private RobotObjectListener listener;
	@Before public void setup(){
		input = new VirtualAnalogVoltageInput(maxVoltage);
		listener = context.mock(RobotObjectListener.class);
		input.addUpdateListener(listener);
	}
	
	@Test public void testMaxVoltage() {
		context.checking(new Expectations() {
			{
				never(listener).objectUpdated(input);
			}
		});
		assertEquals(maxVoltage, input.getMaxVoltage(), 0);
	}

	@Test public void testSet() {
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
	
	@Test public void testRemoveListener() {
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
