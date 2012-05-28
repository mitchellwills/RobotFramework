package robot.boostrap;

import java.util.*;

public class ClassUtil {
	private static final Map<Class<?>,Class<?>> primitiveMap = Collections.unmodifiableMap(new HashMap<Class<?>,Class<?>>(){{
       put(Integer.TYPE, Integer.class);
       put(Long.TYPE, Long.class);
       put(Double.TYPE, Double.class);
       put(Float.TYPE, Float.class);
       put(Boolean.TYPE, Boolean.class);
       put(Character.TYPE, Character.class);
       put(Byte.TYPE, Byte.class);
       put(Short.TYPE, Short.class);
       put(Void.TYPE, Void.class);
	}});
	
	public static Class<?> primitiveToWraper(Class<?> clazz){
		Class<?> primitiveWrapper = primitiveMap.get(clazz);
		if(primitiveWrapper==null)
			return clazz;
		return primitiveWrapper;
	}
}
