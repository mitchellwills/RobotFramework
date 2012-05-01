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
	/**
	 * Create a new ring buffer with a given size
	 * @param size the number of elements that are stored
	 */
	public RingBuffer(int size){
		buffer = new double[size];
		clear();
		head = 0;
	}
	private int calculateIndex(int index){
		return (index+head)%size();
	}
	/**
	 * @param index
	 * @return the value at a given index relative to the head of the buffer
	 */
	public double get(int index){
		return buffer[calculateIndex(index)];
	}
	/**
	 * Append a new value to the buffer overwriting the least recent value to be appended
	 * @param value
	 */
	public void append(double value){
		buffer[head] = value;
		++head;
		if(head>=size())
			head = 0;
	}
	/**
	 * @return the number of elements stored in the buffer
	 */
	public int size(){
		return buffer.length;
	}
	/**
	 * reset all values in the buffer to {@link Double#NaN}
	 */
	public void clear() {
		for(int i = 0; i<buffer.length; ++i)
			buffer[i] = Double.NaN;
	}
}
