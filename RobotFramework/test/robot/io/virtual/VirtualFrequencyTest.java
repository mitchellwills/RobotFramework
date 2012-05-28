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
public class VirtualFrequencyTest {
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	@After
	public void mockeryCheck() {
		context.assertIsSatisfied();
	}

	@Parameters public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
				{ new long[] {1, 4, 3} },
				{ new long[] {1} },
				{ new long[] {8, 3, 1, 6, 10} } };
		return Arrays.asList(data);
	}

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

	@Test public void testSet() {
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
