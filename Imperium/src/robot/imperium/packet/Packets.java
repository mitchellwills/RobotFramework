package robot.imperium.packet;


/**
 * @author Mitchell
 * 
 * Utility methods for making packets
 *
 */
public final class Packets {
	private Packets(){}
	
	/**
	 * @param packet the packet that will be modified
	 * @param objectId the id of the object whose value will be set
	 * @param objectValue the new value
	 */
	public static void makeSetValue(ImperiumPacket packet, int objectId, int objectValue){
		packet.setId(PacketIds.SET_VALUE);
		packet.putInteger(0, objectValue, 2);
		packet.setDataLength(2);
	}
}
