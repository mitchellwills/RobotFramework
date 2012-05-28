package robot.io;

import java.util.*;

import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ForwardingRobotObjectModelTest {
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	@After
	public void mockeryCheck() {
		context.assertIsSatisfied();
	}

	@Parameters public static Collection<Object[]> data() {
		Object[][] data = new Object[][] { { 1 }, { 3 }, { 12 } };
		return Arrays.asList(data);
	}

	private final int numListeners;
	public ForwardingRobotObjectModelTest(int numListeners) {
		this.numListeners = numListeners;
	}
	
	private ForwardingRobotObjectModel model;
	private RobotObject mockRobotObject;
	private RobotObject innerMockRobotObject;
	private Set<RobotObjectListener> listeners = new HashSet<RobotObjectListener>();
	@Before public void setup(){
		innerMockRobotObject = context.mock(RobotObject.class, "InnerRobotObject");
		model = new ForwardingRobotObjectModel(mockRobotObject = context.mock(RobotObject.class));
		for(int i = 0; i<numListeners; ++i){
			RobotObjectListener listener = context.mock(RobotObjectListener.class, "RobotObjectListener"+i);
			listeners.add(listener);
			model.addUpdateListener(listener);
		}
	}
	
	@Test public void testUpdateEvent() {
		for(final RobotObjectListener listener:listeners){
			context.checking(new Expectations() {
				{
					oneOf(listener).objectUpdated(mockRobotObject);
				}
			});
		}
		model.objectUpdated(innerMockRobotObject);
	}

}
