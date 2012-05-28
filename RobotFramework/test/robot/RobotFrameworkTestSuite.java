package robot;

import org.junit.runner.*;
import org.junit.runners.*;

import robot.io.*;
import robot.io.virtual.*;
import robot.math.*;
import robot.util.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	RobotIOTestSuite.class,
	VirtualIOTestSuite.class,
	MathTestSuite.class,
	UtilTestSuite.class
})
public class RobotFrameworkTestSuite {
//
}
