package robot.util;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.*;

import test.*;

@RunWith(RobotTestRunner.class)
public class ByteUtilTest {
	@SuppressWarnings("unused")
	@Test
	public void testConstructor() {
		new ByteUtil();
	}

	byte[] b1 = { (byte) 0xFF, (byte) 0xE5, (byte) 0x10, (byte) 0xFB };
	byte[] b2 = { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
	byte[] b3 = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
	byte[] b4 = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
			(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x88 };

	@Test
	public void testUnsignedNegative() {
		try {
			ByteUtil.getUnsignedL(b1, -1, 1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// success
		}
	}

	@Test
	public void testOutOfBounds() {
		try {
			ByteUtil.getUnsignedL(b1, 3, 2);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// success
		}
	}

	@Test
	public void test0Size() {
		try {
			ByteUtil.getUnsignedL(b1, 3, 0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// success
		}
	}

	@Test
	public void testLargeSize() {
		ByteUtil.getUnsignedL(b4, 0, 8);
		try {
			ByteUtil.getUnsignedL(b4, 0, 9);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// success
		}
	}

	@Test
	public void testUnsigned() {
		assertEquals(255, ByteUtil.getUnsignedL(b1, 0, 1));
		assertEquals(229, ByteUtil.getUnsignedL(b1, 1, 1));
		assertEquals(16, ByteUtil.getUnsignedL(b1, 2, 1));
		assertEquals(251, ByteUtil.getUnsignedL(b1, 3, 1));

		assertEquals(65509, ByteUtil.getUnsignedL(b1, 0, 2));
		assertEquals(58640, ByteUtil.getUnsignedL(b1, 1, 2));
		assertEquals(4347, ByteUtil.getUnsignedL(b1, 2, 2));

		assertEquals(16770320, ByteUtil.getUnsignedL(b1, 0, 3));
		assertEquals(15012091, ByteUtil.getUnsignedL(b1, 1, 3));

		assertEquals(4293202171L, ByteUtil.getUnsignedL(b1, 0, 4));

		assertEquals(0, ByteUtil.getUnsignedL(b2, 0, 1));
		assertEquals(0, ByteUtil.getUnsignedL(b2, 1, 1));
		assertEquals(0, ByteUtil.getUnsignedL(b2, 2, 1));
		assertEquals(0, ByteUtil.getUnsignedL(b2, 3, 1));

		assertEquals(0, ByteUtil.getUnsignedL(b2, 0, 2));
		assertEquals(0, ByteUtil.getUnsignedL(b2, 1, 2));
		assertEquals(0, ByteUtil.getUnsignedL(b2, 2, 2));

		assertEquals(0, ByteUtil.getUnsignedL(b2, 0, 3));
		assertEquals(0, ByteUtil.getUnsignedL(b2, 1, 3));

		assertEquals(0, ByteUtil.getUnsignedL(b2, 0, 4));

		assertEquals(255, ByteUtil.getUnsignedL(b3, 0, 1));
		assertEquals(255, ByteUtil.getUnsignedL(b3, 1, 1));
		assertEquals(255, ByteUtil.getUnsignedL(b3, 2, 1));
		assertEquals(255, ByteUtil.getUnsignedL(b3, 3, 1));

		assertEquals(65535, ByteUtil.getUnsignedL(b3, 0, 2));
		assertEquals(65535, ByteUtil.getUnsignedL(b3, 1, 2));
		assertEquals(65535, ByteUtil.getUnsignedL(b3, 2, 2));

		assertEquals(16777215, ByteUtil.getUnsignedL(b3, 0, 3));
		assertEquals(16777215, ByteUtil.getUnsignedL(b3, 1, 3));

		assertEquals(4294967295L, ByteUtil.getUnsignedL(b3, 0, 4));

		assertEquals(16777215, ByteUtil.getUnsigned(b3, 1, 3));
		assertEquals(0, ByteUtil.getUnsigned(b2, 0, 4));
		assertEquals(251, ByteUtil.getUnsigned(b1, 3, 1));
	}

	@Test
	public void testSigned() {
		assertEquals(-1, ByteUtil.getSignedL(b1, 0, 1));
		assertEquals(-27, ByteUtil.getSignedL(b1, 1, 1));
		assertEquals(16, ByteUtil.getSignedL(b1, 2, 1));
		assertEquals(-5, ByteUtil.getSignedL(b1, 3, 1));

		assertEquals(-27, ByteUtil.getSignedL(b1, 0, 2));
		assertEquals(-6896, ByteUtil.getSignedL(b1, 1, 2));
		assertEquals(4347, ByteUtil.getSignedL(b1, 2, 2));

		assertEquals(-6896, ByteUtil.getSignedL(b1, 0, 3));
		assertEquals(-1765125, ByteUtil.getSignedL(b1, 1, 3));

		assertEquals(-1765125, ByteUtil.getSignedL(b1, 0, 4));

		assertEquals(0, ByteUtil.getSignedL(b2, 0, 1));
		assertEquals(0, ByteUtil.getSignedL(b2, 1, 1));
		assertEquals(0, ByteUtil.getSignedL(b2, 2, 1));
		assertEquals(0, ByteUtil.getSignedL(b2, 3, 1));

		assertEquals(0, ByteUtil.getSignedL(b2, 0, 2));
		assertEquals(0, ByteUtil.getSignedL(b2, 1, 2));
		assertEquals(0, ByteUtil.getSignedL(b2, 2, 2));

		assertEquals(0, ByteUtil.getSignedL(b2, 0, 3));
		assertEquals(0, ByteUtil.getSignedL(b2, 1, 3));

		assertEquals(0, ByteUtil.getSignedL(b2, 0, 4));

		assertEquals(-1, ByteUtil.getSignedL(b3, 0, 1));
		assertEquals(-1, ByteUtil.getSignedL(b3, 1, 1));
		assertEquals(-1, ByteUtil.getSignedL(b3, 2, 1));
		assertEquals(-1, ByteUtil.getSignedL(b3, 3, 1));

		assertEquals(-1, ByteUtil.getSignedL(b3, 0, 2));
		assertEquals(-1, ByteUtil.getSignedL(b3, 1, 2));
		assertEquals(-1, ByteUtil.getSignedL(b3, 2, 2));

		assertEquals(-1, ByteUtil.getSignedL(b3, 0, 3));
		assertEquals(-1, ByteUtil.getSignedL(b3, 1, 3));

		assertEquals(-1, ByteUtil.getSignedL(b3, 0, 4));

		assertEquals(-1, ByteUtil.getSigned(b3, 0, 4));
		assertEquals(0, ByteUtil.getSigned(b2, 1, 3));
		assertEquals(-1765125, ByteUtil.getSigned(b1, 0, 4));
	}

	@Test
	public void testByteFromBits() {
		byte value = -1;
		for (int b7 = 1; b7 >= 0; b7--)
			for (int b6 = 1; b6 >= 0; b6--)
				for (int b5 = 1; b5 >= 0; b5--)
					for (int b4 = 1; b4 >= 0; b4--)
						for (int b3 = 1; b3 >= 0; b3--)
							for (int b2 = 1; b2 >= 0; b2--)
								for (int b1 = 1; b1 >= 0; b1--)
									for (int b0 = 1; b0 >= 0; b0--) {
										assertEquals(value,
												ByteUtil.getByteFromBits(
														toBool(b7), toBool(b6),
														toBool(b5), toBool(b4),
														toBool(b3), toBool(b2),
														toBool(b1), toBool(b0)));
										--value;
									}
	}

	private boolean toBool(int value) {
		return value != 0;
	}

	@Test
	public void testGetBit() {
		byte value = -1;
		int[] b = new int[8];
		for (b[7] = 1; b[7] >= 0; b[7]--)
			for (b[6] = 1; b[6] >= 0; b[6]--)
				for (b[5] = 1; b[5] >= 0; b[5]--)
					for (b[4] = 1; b[4] >= 0; b[4]--)
						for (b[3] = 1; b[3] >= 0; b[3]--)
							for (b[2] = 1; b[2] >= 0; b[2]--)
								for (b[1] = 1; b[1] >= 0; b[1]--)
									for (b[0] = 1; b[0] >= 0; b[0]--) {
										for (int i = 0; i < 8; ++i)
											assertEquals(toBool(b[i]),
													ByteUtil.getBit(value, i));
										--value;
									}
	}

	@Test
	public void testGetByte() {
		assertEquals((byte) 0xFF, ByteUtil.getByte(-4, 3));
		assertEquals((byte) 0xFC, ByteUtil.getByte(-4, 0));
		assertEquals((byte) 0x00, ByteUtil.getByte(10, 3));
		assertEquals((byte) 10, ByteUtil.getByte(10, 0));
	}

	@Test
	public void testToUnsigned() {
		assertEquals(255, ByteUtil.toUnsigned(255));
		assertEquals(0, ByteUtil.toUnsigned(0));
		assertEquals(4294967295L, ByteUtil.toUnsigned(-1));
		assertEquals(4294967294L, ByteUtil.toUnsigned(-2));
		assertEquals(4294967292L, ByteUtil.toUnsigned(-4));
	}

	private static final byte[] result1 = { 3, 0, 0, 0, 0, 0 };
	private static final byte[] result2 = { 4, 3, 0, 0, 0, 0 };
	private static final byte[] result3 = { 2, 13, 65, -53, 0, 0 };
	private static final byte[] result4 = { 0, 2, 13, 65, -53, 0 };
	private static final byte[] result5 = { 0, 0, 2, 13, 65, -53 };
	private static final byte[] result6 = { 0, 0, 4, 3 };

	@Test
	public void testPutByte() {
		byte[] dst = new byte[6];
		ByteUtil.put(dst, 0, 1027, 1);
		assertArrayEquals(result1, dst);

		dst = new byte[6];
		ByteUtil.put(dst, 0, 1027, 2);
		assertArrayEquals(result2, dst);

		dst = new byte[6];
		ByteUtil.put(dst, 0, 34423243, 4);
		assertArrayEquals(result3, dst);

		dst = new byte[6];
		ByteUtil.put(dst, 1, 34423243, 4);
		assertArrayEquals(result4, dst);

		dst = new byte[6];
		ByteUtil.put(dst, 2, 34423243, 4);
		assertArrayEquals(result5, dst);

		dst = new byte[4];
		ByteUtil.put(dst, 2, 1027, 2);
		assertArrayEquals(result6, dst);

		dst = new byte[4];
		try {
			ByteUtil.put(dst, 3, 1027, 2);
			fail();
		} catch (ArrayIndexOutOfBoundsException e) {
			// success
		}
	}

}
