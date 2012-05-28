package robot.math;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MathUtilTest {

	@SuppressWarnings("unused")
	@Test
	public void testConstructor() {
		new MathUtil();
	}
	
	@Parameters public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
				{ 0, new int[] {} },
				{ 75, new int[] {10, 20, 45} },
				{ 28, new int[] {28} },
				{ -2, new int[] {2, -1, 7, -10} } };
		return Arrays.asList(data);
	}
	
	private final int sum;
	private final int[] array;
	public MathUtilTest(int sum, int[] array){
		this.sum = sum;
		this.array = array;
	}

	@Test
	public void testSum() {
		assertEquals(sum, MathUtil.sum(array));
	}
}
