package robot.boostrap.partial;

import java.util.*;

import robot.*;

public interface PartialBinding<T> {
	boolean matches(Map<String, Object> params);
	T get(RobotObjectStore objectStore, Map<String, Object> params);
}
