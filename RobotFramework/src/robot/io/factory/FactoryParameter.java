package robot.io.factory;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a parameter should actually be given a value by the name
 * specified in the annotation and not the name of the parameter
 * 
 * @author Mitchell
 * 
 * @see FactoryConstructable
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface FactoryParameter {
	/**
	 * Indicates the name that a constructor parameter should be referred to as in the parameter map
	 * 
	 */
	String value();
}
