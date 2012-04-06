package robot.dashboard.widget;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;

import robot.dashboard.Widget;
import robot.io.RobotObjectListener;
import robot.io.analog.AnalogVoltageInput;

public class AnalogVoltageInputWidget extends Widget implements RobotObjectListener<AnalogVoltageInput> {
	private final AnalogVoltageInput input;
	private JProgressBar valueBar;
	public AnalogVoltageInputWidget(AnalogVoltageInput input){
		this.input = input;
		setBorder(new TitledBorder("Analog Voltage Input: "+input));
		setLayout(new BorderLayout());
		
		
		add(valueBar = new JProgressBar(0, (int) (input.getMaxVoltage()*1000)));
		valueBar.setStringPainted(true);
		
		objectUpdated(input);
		input.addUpdateListener(this);
	}
	@Override
	public void objectUpdated(AnalogVoltageInput output) {
		valueBar.setValue((int) (input.getVoltage()*1000));
		valueBar.setString(String.format("%.3f V", input.getVoltage()));
	}
}
