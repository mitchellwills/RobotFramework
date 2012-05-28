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
import robot.io.ppm.*;

@RunWith(Parameterized.class)
public class VirtualPPMReaderTest {
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	@After
	public void mockeryCheck() {
		context.assertIsSatisfied();
	}

	@Parameters public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
				{ new long[] {500} },
				{ new long[] {1000, 1500} },
				{ new long[] {1000, 3000, 2500, 1500, 1850} } };
		return Arrays.asList(data);
	}

	private final long[] values;
	public VirtualPPMReaderTest(long[] values) {
		this.values = values;
	}
	
	private VirtualPPMReader input;
	private RobotObjectListener listener;
	@Before public void setup(){
		input = new VirtualPPMReader(values.length);
		listener = context.mock(RobotObjectListener.class);
		input.addUpdateListener(listener);
	}
	
	@Test public void testNumAxes() {
		context.checking(new Expectations() {
			{
				never(listener).objectUpdated(input);
			}
		});
		assertEquals(values.length, input.getChannelCount());
	}

	@Test public void testSet() {
		context.checking(new Expectations() {
			{
				exactly(values.length).of(listener).objectUpdated(input);
			}
		});

		for(int i = 0; i<values.length; ++i)
			input.setChannelValue(i, values[i]);
		for(int i = 0; i<values.length; ++i)
			assertEquals(values[i], input.getChannel(i));

	}
	@Test public void testInitialState() {
		for(int i = 0; i<values.length; ++i)
			assertEquals(PPMReader.INVALID_VALUE, input.getChannel(i));
	}
	
	@Test public void testRemoveListener() {
		if(values.length==0)
			return;
		
		context.checking(new Expectations() {
			{
				oneOf(listener).objectUpdated(input);
			}
		});
		
		input.setChannelValue(0, values[0]);
		assertEquals(values[0], input.getChannel(0), 0);
		
		input.removeUpdateListener(listener);
		input.setChannelValue(0, values[0]);
	}
	
	
	@Test public void testOutOfBounds(){
		context.checking(new Expectations() {
			{
				never(listener).objectUpdated(input);
			}
		});
		try {
			input.setChannelValue(values.length, 10);
			fail();
		} catch (RobotException e) {
			// success
		}
		try {
			input.getChannel(values.length);
			fail();
		} catch (RobotException e) {
			// success
		}
		
		try {
			input.setChannelValue(-1, 10);
			fail();
		} catch (RobotException e) {
			// success
		}
		try {
			input.getChannel(-1);
			fail();
		} catch (RobotException e) {
			// success
		}
	}
}
