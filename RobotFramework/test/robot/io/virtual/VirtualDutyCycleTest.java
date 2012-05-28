package robot.io.virtual;

import static org.junit.Assert.*;

import java.util.*;

import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.runners.Parameterized.Parameters;

import robot.io.*;

@RunWith(Parameterized.class)
public class VirtualDutyCycleTest {
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	@After
	public void mockeryCheck() {
		context.assertIsSatisfied();
	}

	@Parameters public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
				{ new double[] {1.0, 4.3} },
				{ new double[] {1} },
				{ new double[] {8, 3.2, 1, 6.2, 10} } };
		return Arrays.asList(data);
	}

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

	@Test public void testSet() {
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
	
	@Test public void testRemoveListener() {
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
