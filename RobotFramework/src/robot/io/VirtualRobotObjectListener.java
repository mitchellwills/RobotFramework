package robot.io;

/**
 * A RobotObjectListener that counts the number of times it was triggered
 * 
 * @author Mitchell
 *
 */
public class VirtualRobotObjectListener implements RobotObjectListener{
	private int updateCount = 0;
	private RobotObject lastObject = null;
	@Override
	public void objectUpdated(RobotObject object) {
		++updateCount;
		lastObject = object;
	}
	/**
	 * @return the number of times this listener was triggered
	 */
	public int getUpdateCount(){
		return updateCount;
	}
	
	/**
	 * @return the last object that the update method was called with
	 */
	public RobotObject getLastObject(){
		return lastObject;
	}

}
