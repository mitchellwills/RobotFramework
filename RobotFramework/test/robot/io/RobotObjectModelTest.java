package robot.io;

import java.util.*;

import org.jmock.*;
import org.jmock.auto.*;
import org.junit.*;
import org.junit.runner.*;

import test.*;
import test.RobotTestRunner.ParamTest;
import test.RobotTestRunner.TestParameter;

@RunWith(RobotTestRunner.class)
public class RobotObjectModelTest {
	public final Mockery context = new Mockery();

	@TestParameter
	public static final Object[] test1 = {1};
	@TestParameter
	public static final Object[] test2 = {3};
	@TestParameter
	public static final Object[] test3 = {12};
	

	private final int numListeners;
	public RobotObjectModelTest(int numListeners) {
		this.numListeners = numListeners;
	}
	
	private RobotObjectModel model;
	@Mock private RobotObject mockRobotObject;
	private Set<RobotObjectListener> listeners = new HashSet<RobotObjectListener>();
	@Before public void setup(){
		model = new RobotObjectModel(mockRobotObject);
		for(int i = 0; i<numListeners; ++i){
			RobotObjectListener listener = context.mock(RobotObjectListener.class, "RobotObjectListener"+i);
			listeners.add(listener);
			model.addUpdateListener(listener);
		}
	}
	
	@ParamTest public void testUpdateEvent() {
		for(final RobotObjectListener listener:listeners){
			context.checking(new Expectations() {
				{
					oneOf(listener).objectUpdated(mockRobotObject);
				}
			});
		}
		model.fireUpdateEvent();
	}

}
