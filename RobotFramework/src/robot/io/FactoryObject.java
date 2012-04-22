package robot.io;

/**
 * @author Mitchell
 * 
 * A robot object that has a factory that can be used to generate other objects
 *
 */
public interface FactoryObject extends RobotObject {
	/**
	 * @return the factory associated with the object
	 */
	public RobotObjectFactory getFactory();
}
