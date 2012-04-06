package robot.imperium.dashboard;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import robot.dashboard.Widget;
import robot.imperium.ImperiumDevice;
import robot.io.RobotObjectListener;

/**
 * @author Mitchell
 * 
 * A widget that displays the state of an Imperium device
 *
 */
public class ImperiumDeviceWidget extends Widget implements RobotObjectListener<ImperiumDevice> {
	private final ImperiumDevice device;
	private final JLabel stateLabel;
	
	private final JButton pingButton;
	private final JLabel pingLabel;
	/**
	 * Create a new widget
	 * @param device the device the widget is representing
	 */
	public ImperiumDeviceWidget(ImperiumDevice device){
		 this.device = device;
		 setBorder(new TitledBorder("ImperiumDevice ["+device.getHardwareConfiguration().getName()+"]"));
		 setLayout(new GridBagLayout());
		 
		 GridBagConstraints c = new GridBagConstraints();
		 
		 add(stateLabel = new JLabel("Uninitialized"), c);
		 stateLabel.setOpaque(true);
		 
		 c.gridy = 1;
		 c.gridx = 0;
		 add(pingLabel = new JLabel("Last Ping: Unknown"), c);
		 c.gridx = 1;
		 add(pingButton = new JButton("Ping"), c);
		 pingButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				pingLabel.setText("Last Ping: "+ImperiumDeviceWidget.this.device.ping());
			}
		 });
		 
		 device.addUpdateListener(this);

		 objectUpdated(device);
	}
	@Override
	public void objectUpdated(ImperiumDevice device) {
		stateLabel.setText(device.getState().toString());
		switch(device.getState()){
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
	}
}
