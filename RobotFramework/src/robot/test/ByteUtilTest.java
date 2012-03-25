package robot.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import robot.util.ByteUtil;

@SuppressWarnings("javadoc")
public class ByteUtilTest {
	byte[] b1 = {(byte) 0xFF, (byte)0xE5, (byte)0x10, (byte)0xFB};
	byte[] b2 = {(byte) 0x00, (byte)0x00, (byte)0x00, (byte)0x00};
	byte[] b3 = {(byte) 0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF};

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
	}
	@Test
	public void testGetByte() {
		byte value = -1;
		for(boolean b7 = true; b7; b7=false)
			for(boolean b6 = true; b6; b6=false)
				for(boolean b5 = true; b5; b5=false)
					for(boolean b4 = true; b4; b4=false)
						for(boolean b3 = true; b3; b3=false)
							for(boolean b2 = true; b2; b2=false)
								for(boolean b1 = true; b1; b1=false)
									for(boolean b0 = true; b0; b0=false){
										assertEquals(value, ByteUtil.getByte(b7, b6, b5, b4, b3, b2, b1, b0));
										--value;
									}
	}
	@Test
	public void testGetBit() {
		byte value = -1;
		boolean[] b = new boolean[8];
		for(b[7] = true; b[7]; b[7]=false)
			for(b[6] = true; b[6]; b[6]=false)
				for(b[5] = true; b[5]; b[5]=false)
					for(b[4] = true; b[4]; b[4]=false)
						for(b[3] = true; b[3]; b[3]=false)
							for(b[2] = true; b[2]; b[2]=false)
								for(b[1] = true; b[1]; b[1]=false)
									for(b[0] = true; b[0]; b[0]=false){
										for(int i = 0; i<8; ++i)
											assertEquals(b[i], ByteUtil.getBit(value, i));
										--value;
									}
	}

}
