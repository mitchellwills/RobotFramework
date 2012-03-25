package robot.util;

/**
 * @author Mitchell
 * 
 * utility classes for manipulating bytes
 *
 */
public class ByteUtil {

	/**
	 * Big endian
	 * 
	 * @param b byte array
	 * @param offset byte offset
	 * @param size size (in bytes of the value) (1-8)
	 * @return the unsigned integer
	 * NOTE: this will return a signed number if size==8
	 */
	public static long getUnsignedL(byte[] b, int offset, int size){
		if(offset<0)
			throw new IndexOutOfBoundsException("the offset cannot be negative");
		if(b.length+size<offset)
			throw new IndexOutOfBoundsException("the cannot exceed the size of the array minus the size of the value");
		if(size<1)
			throw new IndexOutOfBoundsException("the size cannot be <=0");
		if(size>8)
			throw new IndexOutOfBoundsException("the size cannot be >8");
		
		long value = 0;
		for(int i = 0; i<size; ++i){
			value<<=8;
			value |= b[i+offset]&0xFFL;
		}
		return value;
	}
	/**
	 * Big endian
	 * 
	 * @param b byte array
	 * @param offset byte offset
	 * @param size size (in bytes of the value) (1-8)
	 * @return the signed integer
	 */
	public static long getSignedL(byte[] b, int offset, int size){
		long value = getUnsignedL(b, offset, size);
		value <<= 8*(8-size);
		value >>= 8*(8-size);//force signed
		return value;
	}

	/**
	 * Big endian
	 * 
	 * @param b byte array
	 * @param offset byte offset
	 * @param size size (in bytes of the value) (1-8)
	 * @return the unsigned integer
	 * NOTE: this will return a signed number if size==8
	 */
	public static int getUnsigned(byte[] b, int offset, int size){
		return (int)getUnsignedL(b, offset, size);
	}
	/**
	 * Big endian
	 * 
	 * @param b byte array
	 * @param offset byte offset
	 * @param size size (in bytes of the value) (1-8)
	 * @return the signed integer
	 */
	public static int getSigned(byte[] b, int offset, int size){
		return (int)getSignedL(b, offset, size);
	}

	/**
	 * Big endian
	 * 
	 * @param b the array the value will be put into
	 * @param index the starting byte the Most Significant byte will be put into
	 * @param value the value that will be put into hte byte array
	 * @param size the number of low bytes to put into the array
	 */
	public static void put(byte[] b, int index, long value, int size){
		for(int i = 0; i<size; ++i){
			b[index+size-1-i] = getByte(value, i);
		}
	}
	
	/**
	 * @param value the value to get the byte from
	 * @param index index of the byte to get (0 is the least significant bit)
	 * @return the byte at the given index
	 */
	public static byte getByte(long value, int index){
		return (byte) (value>>(8*index));
	}
	
	
	/**
	 * @param b7 most significant bit
	 * @param b6
	 * @param b5
	 * @param b4
	 * @param b3
	 * @param b2
	 * @param b1
	 * @param b0 least significant bit
	 * @return a byte composed of the given bits
	 */
	public static byte getByte(boolean b7, boolean b6, boolean b5, boolean b4, boolean b3, boolean b2, boolean b1, boolean b0){
		byte _0 = 0;
		byte _1 = 1;
		byte b = b0?_1:_0;
		b<<=1;
		b |= b1?_1:_0;
		b<<=1;
		b |= b2?_1:_0;
		b<<=1;
		b |= b3?_1:_0;
		b<<=1;
		b |= b4?_1:_0;
		b<<=1;
		b |= b5?_1:_0;
		b<<=1;
		b |= b6?_1:_0;
		b<<=1;
		b |= b7?_1:_0;
		return b;
	}
	
	/**
	 * @param b byte to get the bit from
	 * @param index index of the bit (0 is least significant bit)
	 * @return true if the bit is set
	 */
	public static boolean getBit(byte b, int index){
		int mask = 1<<index;
		return (mask&b)!=0;
	}
}
