package robot.imperium.packet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import robot.util.ByteUtil;

/**
 * @author Mitchell
 * 
 * represents a packet exchanged between a Imperium device and the host
 *
 */
public class ImperiumPacket {
	/**
	 * current version of this packet implementation
	 */
	public static final int VERSION = 1;
	/**
	 * the number of bytes in the header (bytes before the packet content)
	 */
	public static final int HEADER_SIZE = 4;
	/**
	 * maximum number of data bytes that can be sent in a packet
	 */
	public static final int MAX_DATA_SIZE = 1024;
	/**
	 * the maximum size of a packet
	 */
	public static final int MAX_PACKET_SIZE = size(MAX_DATA_SIZE);
	private static int size(int dataSize){
		return 1 + 1 + 2 + dataSize + 1;
	}

	//version: 1 byte
	private int id = 0;//1 byte
	private int dataLength = 0;//2 bytes
	private byte[] data = new byte[MAX_DATA_SIZE];//n bytes
	//checksum: 1 byte
	
	
	/**
	 * read a packet from an input stream and validate it
	 * 
	 * @param is the input stream to be read from
	 * @throws IOException
	 */
	public void read(InputStream is) throws IOException{
		byte[] header = new byte[HEADER_SIZE];
		is.read(header);
		
		int inputVersion = header[0];
		if(inputVersion!=VERSION)
			throw new IOException("Invalid Imperium packet: invalid version number "+inputVersion);
		
		id = header[1];
		dataLength = ByteUtil.getUnsigned(header, 2, 2);
		if(dataLength>MAX_DATA_SIZE)
			throw new IOException("Invalid Imperium packet: data length cannot be larger than "+MAX_DATA_SIZE);
		is.read(data, 0, dataLength);
		
		int inputChecksum = (byte) is.read();
		if(inputChecksum!=calculateChecksum())
			throw new IOException("Invalid Imperium packet: checksum does not match");
	}
	
	/**
	 * write a packet to an output stream
	 * @param os the stream the packet will be written to
	 * @throws IOException 
	 */
	public void write(OutputStream os) throws IOException{
		os.write(VERSION);
		os.write(id);
		os.write((dataLength>>8)&0xFF);
		os.write(dataLength&0xFF);
		os.write(data, 0, dataLength);
		os.write(getChecksum());
	}


	/**
	 * @return the version of a packet
	 */
	public static int getVersion() {
		return VERSION;
	}
	/**
	 * @return the packet id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the number of bytes of data in the packet
	 */
	public int getDataLength() {
		return dataLength;
	}
	
	/**
	 * @return the data stored in the packet, this may be larger than the amount of valid data in the packet
	 * Use getDataLength() for the length of data
	 * @see ImperiumPacket#getDataLength()
	 */
	public byte[] getData() {
		return data;
	}
	/**
	 * @return the checksum for the packet
	 */
	public byte getChecksum() {
		return calculateChecksum();
	}
	
	/**
	 * @return the total number of bytes in the packet
	 */
	public int size(){
		return size(dataLength);
	}


	/**
	 * @param id new id for the packet
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @param data new data for the packet<br>NOTE: this array will be copied
	 * @throws IOException
	 */
	public void setData(byte... data) throws IOException {
		setDataLength(data.length);
		
		System.arraycopy(data, 0, this.data, 0, data.length);
	}
	/**
	 * Put an integer of a given size at a given index in the data byte array
	 * Big Endian
	 * @param index index in the data array
	 * @param value value to be stored
	 * @param size number of bytes to store from the value
	 * @throws IOException
	 */
	public void putInteger(int index, long value, int size) {
		if(index+size>MAX_DATA_SIZE)
			throw new RuntimeException("Invalid Imperium packet: data will exceed the max data size of a packet: "+MAX_DATA_SIZE);
		ByteUtil.put(data, index, value, size);
	}
	
	/**
	 * set the length of the data in the packet
	 * 
	 * @param dataLength
	 * @throws IOException
	 */
	public void setDataLength(int dataLength) {
		if(data.length>MAX_DATA_SIZE)
			throw new RuntimeException("Invalid Imperium packet: data length cannot be larger than "+MAX_DATA_SIZE);
		this.dataLength = dataLength;
	}

	private byte calculateChecksum() {
		byte value = 0;
		value ^= VERSION;
		value ^= id;
		value ^= (dataLength>>8)&0xFF;
		value ^= dataLength&0xFF;
		for(int i = 0; i<dataLength; ++i){
			value ^= data[i];
		}
		return value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ImperiumPacket [version=");
		builder.append(VERSION);
		builder.append(", id=");
		builder.append(id);
		builder.append(", data=[");
		for(int i = 0; i<getDataLength(); ++i){
			builder.append(Integer.toHexString(getData()[i]));
			if(i!=getDataLength()-1)
				builder.append(", ");
		}
		builder.append("]");
		builder.append(", checksum=");
		builder.append(Integer.toHexString(getChecksum()));
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
