package robot.io.factory;

import java.util.Map;

import robot.Robot;
import robot.error.RobotInitializationException;
import robot.io.RobotObject;

/**
 * @author Mitchell
 * 
 * A Robot Object Factory that gets factories for generating Robot Objects from their 
 *
 */
public class NameRobotObjectFactory extends RobotObjectFactory{
	/**
	 * the character used to separate the name of an object from the factory name
	 */
	public static final char SEPARATION_CHAR = '/';
	
	private RobotObjectFactory getFactory(String name){
		if(name==null)
			throw new RobotInitializationException("Object Location must have a seperator");
		RobotObject object = Robot.getInstance().getObject(name);
		if(object instanceof FactoryObject)
			return ((FactoryObject)object).getFactory();
		throw new RobotInitializationException(name+" is not a robot factory");
	}
	private String getFactoryName(String location){
		int index = location.indexOf(SEPARATION_CHAR);
		if(index==-1)
			return null;
		return location.substring(0, index);
	}
	private String getSubLocation(String location){
		int index = location.indexOf(SEPARATION_CHAR);
		if(index==-1)
			throw new RobotInitializationException("Object Location must have a seperator");
		return location.substring(index+1);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends RobotObject> T getObject(Class<T> type, Map<String, String> params){
		String location = getParam(params, RobotObject.PARAM_LOCATION);
		if(location==null)
			return null;
		String factoryName = getFactoryName(location);
		if(factoryName==null){
			RobotObject object = Robot.getInstance().getObject(location);
			if(!type.isInstance(object))
				throw new RobotInitializationException(location+" is not an instance of "+type.getName());
			return (T)object;
		}
		params.put(RobotObject.PARAM_LOCATION, getSubLocation(location));
		return getFactory(factoryName).getObject(type, params);
	}
}
