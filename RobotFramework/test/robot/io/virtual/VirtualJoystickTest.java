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
import test.RobotTestRunner.TestInjectModule;
import test.RobotTestRunner.TestParameter;

import com.google.inject.*;

@RunWith(RobotTestRunner.class)
public class VirtualJoystickTest {
	public final Mockery context = new Mockery();
	
	@TestInjectModule
	public final Module module = new VirtualObjectModule();
	
	@DefaultTestParameter
	@TestParameter
	public static final Object[] test1 = {
		new boolean[]{true, false, true, true},
		new double[]{5.5, 4, 3},
		new double[]{3.3, 2, 3, 4},
		new double[]{100, 200, 180, 200}
	};
	@TestParameter
	public static final Object[] test2 = {
		new boolean[]{false, false, false, false, true},
		new double[]{},
		new double[]{4.4, 3.3},
		new double[]{2, 180}
	};
	@TestParameter
	public static final Object[] test3 = {
		new boolean[]{},
		new double[]{5.5, 4, 3, 7, 9},
		new double[]{1},
		new double[]{0}
	};

	
	private final boolean[] buttons;
	private final double[] axes;
	private final double[] directionalMagnitude;
	private final double[] directionalAngle;
	
	public VirtualJoystickTest(boolean[] buttons, double[] axes, double[] directionalMagnitude, double[] directionalAngle) {
		this.buttons = buttons;
		this.axes = axes;
		this.directionalMagnitude = directionalMagnitude;
		this.directionalAngle = directionalAngle;
		if(directionalAngle.length!=directionalMagnitude.length)
			throw new RuntimeException("Invalid test params");
	}
	
	private VirtualJoystick input;
	@Mock private RobotObjectListener listener;
	@Inject VirtualObjectFactory fact;
	@Before public void setup(){
		input = new VirtualJoystick(fact, buttons.length, axes.length, directionalAngle.length);
		input.addUpdateListener(listener);
	}
	
	@ParamTest public void testNum() {
		context.checking(new Expectations() {
			{
				never(listener).objectUpdated(input);
			}
		});
		assertEquals(axes.length, input.getAxisCount());
		assertEquals(buttons.length, input.getButtonCount());
		assertEquals(directionalAngle.length, input.getDirectionalCount());
	}
	
	
	@ParamTest public void testSet() {
		context.checking(new Expectations() {
			{
				exactly(axes.length+buttons.length+directionalAngle.length*2).of(listener).objectUpdated(input);
			}
		});

		for(int i = 0; i<axes.length; ++i){
			input.setAxisValue(i, axes[i]);
			assertEquals(axes[i], input.getAxis(i).getValue(), 0);
		}
		for(int i = 0; i<buttons.length; ++i){
			input.setButtonState(i, buttons[i]);
			assertEquals(buttons[i], input.getButton(i).get());
		}
		for(int i = 0; i<directionalAngle.length; ++i){
			input.setDirectionalAngle(i, directionalAngle[i]);
			input.setDirectionalMagnitude(i, directionalMagnitude[i]);
			assertEquals(directionalAngle[i], input.getDirectional(i).getAngle(), 0);
			assertEquals(directionalMagnitude[i], input.getDirectional(i).getMagnitude(), 0);
		}
	}
	
	@ParamTest public void testButtonPush() {
		context.checking(new Expectations() {
			{
				exactly(buttons.length*2).of(listener).objectUpdated(input);
			}
		});
		for(int i = 0; i<buttons.length; ++i){
			input.pressButton(i);
			assertEquals(true, input.getButton(i).get());
		}
		for(int i = 0; i<buttons.length; ++i){
			input.releaseButon(i);
			assertEquals(false, input.getButton(i).get());
		}
	}

	@Test public void testRemoveListener() {
		context.checking(new Expectations() {
			{
				oneOf(listener).objectUpdated(input);
			}
		});
		
		input.setButtonState(0, buttons[0]);
		
		input.removeUpdateListener(listener);
		input.setButtonState(0, buttons[0]);
	}
}
