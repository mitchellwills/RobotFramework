package robot.io.virtual;

import static org.junit.Assert.*;

import org.jmock.*;
import org.jmock.auto.*;
import org.junit.*;
import org.junit.runner.*;

import robot.error.*;
import robot.io.*;
import robot.io.ppm.*;
import test.*;
import test.RobotTestRunner.DefaultTestParameter;
import test.RobotTestRunner.ParamTest;
import test.RobotTestRunner.TestParameter;

@RunWith(RobotTestRunner.class)
public class VirtualPPMReaderTest {
	public final Mockery context = new Mockery();

	@DefaultTestParameter
	@TestParameter
	public static final Object[] test1 = { new long[] {500} };
	@TestParameter
	public static final Object[] test2 = { new long[] {1000, 1500} };
	@TestParameter
	public static final Object[] test3 = { new long[] {1000, 3000, 2500, 1500, 1850} };

	private final long[] values;
	public VirtualPPMReaderTest(long[] values) {
		this.values = values;
	}
	
	private VirtualPPMReader input;
	@Mock private RobotObjectListener listener;
	@Before public void setup(){
		input = new VirtualPPMReader(values.length);
		input.addUpdateListener(listener);
	}
	
	@ParamTest public void testNumAxes() {
		context.checking(new Expectations() {
			{
				never(listener).objectUpdated(input);
			}
		});
		assertEquals(values.length, input.getChannelCount());
	}

	@ParamTest public void testSet() {
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
	@ParamTest public void testInitialState() {
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
	
	
	@ParamTest public void testOutOfBounds(){
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
