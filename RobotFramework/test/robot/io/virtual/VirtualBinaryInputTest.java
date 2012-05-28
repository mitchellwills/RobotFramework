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
public class VirtualBinaryInputTest {
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	@After
	public void mockeryCheck() {
		context.assertIsSatisfied();
	}

	@Parameters public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
				{ new boolean[] {true}, new double[] {1} },
				{ new boolean[] {true, false}, new double[] {1, 0} },
				{ new boolean[] {false, true, false, true, true}, new double[] {0, 1, 0, 1, 1} } };
		return Arrays.asList(data);
	}

	private final boolean[] values;
	private final double[] doubleValues;
	public VirtualBinaryInputTest(boolean[] values, double[] doubleValues) {
		this.values = values;
		this.doubleValues = doubleValues;
	}
	
	private VirtualBinaryInput input;
	private RobotObjectListener listener;
	@Before public void setup(){
		input = new VirtualBinaryInput();
		listener = context.mock(RobotObjectListener.class);
		input.addUpdateListener(listener);
	}

	@Test public void testGet() {
		context.checking(new Expectations() {
			{
				exactly(values.length).of(listener).objectUpdated(input);
			}
		});

		for(int i = 0; i<values.length; ++i){
			input.set(values[i]);
			assertEquals(values[i], input.get());
			assertEquals(doubleValues[i], input.getValue(), 0);
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
		input.set(!values[0]);
	}
}
