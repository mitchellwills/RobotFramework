package robot.imperium.packet;

/**
 * @author Mitchell
 * 
 * Contains packet ids
 *
 */
public interface PacketIds {
	/**
	 * Configuration of the entire device
	 */
	int GLOBAL_CONFIGURE = 1;
	/**
	 * response packet to global configure
	 */
	int CONFIGURE_CONFIRM = 2;
	/**
	 * set the configuration data for a single object
	 */
	int CONFIGURE_OBJECT = 3;
	
	/**
	 * pass an error message back to the host
	 */
	int ERROR_MESSAGE = 4;
	
	/**
	 * host sends request for device response
	 */
	int PING_REQUEST = 5;
	
	/**
	 * response to a ping
	 */
	int PING_RESPONSE = 6;
	
	
	/**
	 * set a single value
	 */
	int SET_VALUE = 10;
	
	
	/**
	 * an update of a single value
	 */
	int INPUT_VALUE = 20;
	
	
}
