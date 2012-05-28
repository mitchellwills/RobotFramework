package robot.boostrap.partial;

import java.util.*;

public interface PartialBinding<T> {
	boolean matches(Map<String, Object> params);
	T get(Map<String, Object> params);
}
