package robot.io.factory;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a constructor can be called from a robot factory when using the class name and dynamically mapping parameters<br>
 * The mapping of values is done by the name of the parameter or by annotating a constructor parameter with {@link FactoryParameter}<br>
 * Constructors that take a single parameter of type Map are automatically used by a factory
 * 
 * @author Mitchell
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
public @interface FactoryConstructable {
//
}
