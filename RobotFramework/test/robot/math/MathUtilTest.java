package robot.math;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.*;

import test.*;
import test.RobotTestRunner.*;

@RunWith(RobotTestRunner.class)
public class MathUtilTest {

	@SuppressWarnings("unused")
	@Test
	public void testConstructor() {
		new MathUtil();
	}
	
	@DefaultTestParameter
	@TestParameter
	public static final Object[] test1 = {0, new int[] {}};
	@TestParameter
	public static final Object[] test2 = {75, new int[] {10, 20, 45}};
	@TestParameter
	public static final Object[] test3 = {28, new int[] {28}};
	@TestParameter
	public static final Object[] test4 = {-2, new int[] {2, -1, 7, -10}};
	
	
	private final int sum;
	private final int[] array;
	public MathUtilTest(int sum, int[] array){
		this.sum = sum;
		this.array = array;
	}

	@ParamTest
	public void testSum() {
		assertEquals(sum, MathUtil.sum(array));
	}
}
