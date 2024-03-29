package robot.util;

/**
 * @author Mitchell
 * 
 * A circular buffer.
 * This buffer will overwrite old values without throwing an exception
 * it is designed to keep a history of values
 * All values are initialized to {@link Double#NaN}
 *
 */
public class RingBuffer {
	private double[] buffer;
	private int head;
	private int size;
	/**
	 * Create a new ring buffer with a given size
	 * @param maxSize the number of elements that are stored
	 */
	public RingBuffer(int maxSize){
		buffer = new double[maxSize];
		clear();
		head = 0;
		size = 0;
	}
	
	private int calculateIndex(int index){
		return (index+head)%buffer.length;
	}
	/**
	 * @param index
	 * @return the value at a given index relative to the head of the buffer
	 */
	public double get(int index){
		if(size<buffer.length)
			return buffer[index];
		return buffer[calculateIndex(index)];
	}
	/**
	 * Append a new value to the buffer overwriting the least recent value to be appended
	 * @param value
	 */
	public void append(double value){
		buffer[head] = value;
		++head;
		if(head>=buffer.length)
			head = 0;
		if(size<buffer.length)
			size = head;
	}
	/**
	 * @return the number of elements stored in the buffer
	 */
	public int size(){
		return size;
	}
	
	
	
	
	
	
	
	/**
	 * reset all values in the buffer to {@link Double#NaN}
	 */
	public void clear() {
		for(int i = 0; i<buffer.length; ++i)
			buffer[i] = Double.NaN;
	}
	
	/**
	 * @return the sum of all values in the buffer ignoring values which are {@link Double#NaN}
	 */
	public double sum(){
		double sum = 0;
		int count = 0;
		for(int i = 0; i<buffer.length; ++i){
			if(!Double.isNaN(buffer[i])){
				sum += buffer[i];
				++count;
			}
		}
		if(count>0)
			return sum;
		return Double.NaN;
	}
	
	/**
	 * @return the average value stored in the buffer ignoring values which are {@link Double#NaN}
	 * Will return {@link Double#NaN} if no values in the buffer are valid
	 */
	public double average(){
		double sum = 0;
		int count = 0;
		for(int i = 0; i<buffer.length; ++i){
			if(!Double.isNaN(buffer[i])){
				sum += buffer[i];
				++count;
			}
		}
		if(count>0)
			return sum/count;
		return Double.NaN;
	}
}
