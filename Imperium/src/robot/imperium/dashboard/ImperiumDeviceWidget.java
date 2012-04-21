package robot.imperium.dashboard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import robot.dashboard.Widget;
import robot.imperium.ImperiumDevice;
import robot.io.RobotObject;
import robot.io.RobotObjectListener;

/**
 * @author Mitchell
 * 
 *         A widget that displays the state of an Imperium device
 * 
 */
public class ImperiumDeviceWidget extends Widget<ImperiumDevice> implements RobotObjectListener {
	private ImperiumDevice device;
	private final JLabel stateLabel;

	private final JButton pingButton;
	private final JLabel pingLabel;
	private final JLabel bulkUpdateLabel;
	private final JLabel rxPacketLabel;
	private final JLabel txPacketLabel;

	/**
	 * Create a new widget
	 * 
	 * @param device
	 *            the device the widget is representing
	 */
	public ImperiumDeviceWidget() {
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(200, 150));

		GridBagConstraints c = new GridBagConstraints();

		add(stateLabel = new JLabel("Not Connected"), c);
		stateLabel.setOpaque(true);

		c.gridy = 1;
		c.gridx = 0;
		add(pingLabel = new JLabel("Last Ping: ??"), c);
		c.gridx = 1;
		add(pingButton = new JButton("Ping"), c);
		pingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (device != null)
					pingLabel.setText("Last Ping: "
							+ ImperiumDeviceWidget.this.device.ping());
			}
		});

		c.gridy = 2;
		c.gridx = 0;
		c.gridwidth = 2;
		add(bulkUpdateLabel = new JLabel("Bulk Update Rate: ??????"), c);
		c.gridy = 3;
		add(rxPacketLabel = new JLabel("Rx: ???"), c);
		c.gridy = 4;
		add(txPacketLabel = new JLabel("Tx: ???"), c);
		c.gridwidth = 1;

		objectUpdated(null);
	}

	@Override
	public void setObject(ImperiumDevice newDevice) {
		if (device != null)
			device.removeUpdateListener(this);
		if (newDevice != null)
			newDevice.addUpdateListener(this);
		device = newDevice;
		objectUpdated(device);
	}

	@Override
	public void objectUpdated(RobotObject object) {
		if (device != null) {
			stateLabel.setText(device.getState().toString());
			switch (device.getState()) {
			case DISCONNECTED:
				stateLabel.setBackground(Color.RED);
				break;
			case CONFIGURING:
				stateLabel.setBackground(Color.ORANGE);
				break;
			case CONNECTED:
				stateLabel.setBackground(Color.GREEN);
				break;
			}

			bulkUpdateLabel.setText(String.format("Bulk Update Rate: %.2f",
					device.getBulkUpdateRate()));
			rxPacketLabel.setText(String.format("Rx: %d (%d B)",
					device.getPacketReceivedCount(),
					device.getPacketReceivedSize()));
			txPacketLabel.setText(String.format("Tx: %d (%d B)",
					device.getPacketSentCount(), device.getPacketSentSize()));
		} else {
			stateLabel.setText("Not Connected");
			stateLabel.setBackground(null);
			bulkUpdateLabel.setText("");
			rxPacketLabel.setText("");
			txPacketLabel.setText("");
		}
	}
}
