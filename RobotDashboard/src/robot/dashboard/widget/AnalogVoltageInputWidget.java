package robot.dashboard.widget;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JProgressBar;

import robot.dashboard.Widget;
import robot.io.RobotObject;
import robot.io.RobotObjectListener;
import robot.io.analog.AnalogVoltageInput;

/**
 * @author Mitchell
 * 
 * A widget that displays an analog voltage input using a progress bar
 *
 */
public class AnalogVoltageInputWidget extends Widget<AnalogVoltageInput> implements RobotObjectListener {
	private AnalogVoltageInput input;
	private final JProgressBar valueBar;
	/**
	 * Create a new widget
	 * @param input
	 */
	public AnalogVoltageInputWidget(){
		setLayout(new BorderLayout());

		
		add(valueBar = new JProgressBar(0, 1));
		valueBar.setStringPainted(true);
		
		setPreferredSize(new Dimension(200, 50));
		
		objectUpdated(null);
	}
	@Override
	public void setObject(AnalogVoltageInput newInput){
		if(input!=null)
			input.removeUpdateListener(this);
		if(newInput!=null){
			newInput.addUpdateListener(this);
			valueBar.setMaximum((int) (newInput.getMaxVoltage()*1000));
		}
		input = newInput;
		objectUpdated(input);
	}
	@Override
	public void objectUpdated(RobotObject output) {
		if(input!=null){
			valueBar.setValue((int) (input.getVoltage()*1000));
			valueBar.setString(String.format("%.3f V", input.getVoltage()));
		}
		else{
			valueBar.setValue(0);
			valueBar.setString("Not Connected");
		}
	}
}
