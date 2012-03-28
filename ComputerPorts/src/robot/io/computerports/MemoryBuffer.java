package robot.io.computerports;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;


/**
 * @author Mitchell
 * 
 * a buffer backed by memory
 *
 */
public class MemoryBuffer {
	private final Memory buffer;
	private int writePosition;
	private int readPosition;
	
	
	/**
	 * Create a native memory buffer
	 * @param size the size of the malloced memory
	 */
	public MemoryBuffer(int size){
		buffer = new Memory(size);
		writePosition = 0;
		readPosition = 0;
	}
	
	/**
	 * @return the size of the buffer
	 */
	public int size(){
		return (int) buffer.size();
	}

	/**
	 * @param b byte to write to memory
	 * @return the number of bytes that were written
	 */
	public int append(byte b){
		return append(new byte[]{b});
	}

	/**
	 * @param bytes array containing bytes that will be copied into memory
	 * @return the number of bytes that were written
	 */
	public int append(byte[] bytes){
		return append(bytes, bytes.length);
	}
	
	/**
	 * @param bytes array containing bytes that will be copied into memory
	 * @param bufferSize the length of the byte array that contains valid data
	 * @return the number of bytes that were written
	 */
	public int append(byte[] bytes, int bufferSize){
		return append(bytes, 0, bufferSize);
	}

	/**
	 * @param bytes array containing bytes that will be copied into memory
	 * @param startIndex the start index in the input array
	 * @param bufferSize the length of the byte array that contains valid data
	 * @return the number of bytes that were written
	 */
	public int append(byte[] bytes, int startIndex, int bufferSize){
		int remainingSpace = size()-writePosition;
		int bytesAdded = Math.min(remainingSpace, bufferSize-startIndex);
		buffer.write(writePosition, bytes, startIndex, bytesAdded);
		incrementWritePointer(bytesAdded);
		return bytesAdded;
	}
	
	/**
	 * @return a native pointer to the current write position
	 */
	public Pointer getWritePointer(){
		return buffer.getPointer(writePosition);
	}
	/**
	 * @param size amount to increment pointer
	 */
	public void incrementWritePointer(int size){
		writePosition+=size;
		if(writePosition==size())
			writePosition = 0;
	}
	/**
	 * @return the number of bytes that can be written to continuously
	 */
	public int getContinuousWriteable(){
		return size()-writePosition;
	}
	
	/**
	 * @return the number of bytes that have been written to the buffer
	 */
	public int getWrittenData(){
		return writePosition;
	}

	/**
	 * reset the read and write position back to the beginning of the buffer
	 */
	public void clearWrittenData() {
		writePosition = 0;
		readPosition = 0;
	}
	
	/**
	 * @return the number of available bytes to read
	 */
	public int available(){
		int available = writePosition-readPosition;
		if(available<0)
			available = size()-available;
		return available;
	}
	
	/**
	 * @return read a single byte from the byte buffer
	 * -1 if there are no bytes left in the buffer
	 */
	public int read(){
		if(available()>0){
			byte value = buffer.getByte(readPosition);
			readPosition++;
			if(readPosition==size())
				readPosition = 0;
			return value;
		}
		return -1;
	}
	
	//TODO implement read into an array
	
	
	/**
	 * @return the native memory in which data is stored
	 */
	public Memory getMemory() {
		return buffer;
	}
}
