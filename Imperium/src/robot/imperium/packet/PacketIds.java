package robot.imperium.packet;

/**
 * 
 * Contains Imperium packet ids
 * 
 * @author Mitchell
 *
 */
public interface PacketIds {
	int DEVICE_BOOT = 0;

	int PING_REQUEST = 1;

	int PING_RESPONSE = 2;

	int ERROR_MESSAGE = 3;

	int GLOBAL_CONFIGURE_REQUEST = 4;

	int GLOBAL_CONFIGURE_RESPONSE = 5;

	int OBJECT_CONFIGURE_REQUEST = 6;

	int OBJECT_CONFIGURE_RESPONSE = 7;

	int MESSAGE = 8;

	int BULK_INPUT_VALUE = 9;

	int BULK_OUTPUT_VALUE = 10;
}
