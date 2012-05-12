package robot.dashboard.widget;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import robot.dashboard.Widget;
import robot.io.RobotObject;
import robot.io.RobotObjectListener;
import robot.io.analog.AnalogVoltageInput;
import robot.io.analog.VirtualAnalogVoltageInput;

/**
 * @author Mitchell
 * 
 * A widget that displays an analog voltage input using a progress bar
 *
 */
public class AnalogVoltageInputWidget extends Widget<AnalogVoltageInput> implements RobotObjectListener, ChangeListener {
	private static final double SCALE_FACTOR = 1000;
	
	private AnalogVoltageInput input;
	private final JProgressBar valueBar;
	private final JSlider valueSlider;
	/**
	 * Create a new widget
	 */
	public AnalogVoltageInputWidget(){
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;

		
		add(valueBar = new JProgressBar(0, 1), c);
		valueBar.setStringPainted(true);
		
		add(valueSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 1, 0), c);
		valueSlider.setMajorTickSpacing((int) SCALE_FACTOR);
		valueSlider.addChangeListener(this);
		
		setPreferredSize(new Dimension(200, 75));
		
		objectUpdated(null);
	}
	@Override
	public void setObject(AnalogVoltageInput newInput){
		if(input!=null)
			input.removeUpdateListener(this);
		if(newInput!=null){
			newInput.addUpdateListener(this);
			if(newInput instanceof VirtualAnalogVoltageInput){
				valueSlider.setVisible(true);
				valueBar.setVisible(false);
				valueSlider.setMinimum(0);
				valueSlider.setMaximum((int)(newInput.getMaxVoltage()*SCALE_FACTOR));
				Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
				labelTable.put( 0, new JLabel(String.format("%.2f V", 0.0)));
				labelTable.put( (int)(newInput.getMaxVoltage()*SCALE_FACTOR/2), new JLabel(String.format("%.2f V", newInput.getMaxVoltage()/2)) );
				labelTable.put( (int)(newInput.getMaxVoltage()*SCALE_FACTOR), new JLabel(String.format("%.2f V", newInput.getMaxVoltage())) );
				valueSlider.setLabelTable( labelTable );
				valueSlider.setPaintLabels(true);
			}
			else{
				valueBar.setMaximum((int) (newInput.getMaxVoltage()*SCALE_FACTOR));
				valueSlider.setVisible(false);
				valueBar.setVisible(true);
			}
		}
		else{
			valueSlider.setVisible(false);
			valueBar.setVisible(true);
		}
		input = newInput;
		objectUpdated(input);
	}
	@Override
	public void objectUpdated(RobotObject output) {
		if(input!=null){
			if(input instanceof VirtualAnalogVoltageInput)
				valueSlider.setValue((int) (SCALE_FACTOR*input.getVoltage()));
			else{
				valueBar.setValue((int) (input.getVoltage()*SCALE_FACTOR));
				valueBar.setString(String.format("%.3f V", input.getVoltage()));
			}
		}
		else{
			valueBar.setValue(0);
			valueBar.setString("Not Connected");
		}
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		if(input instanceof VirtualAnalogVoltageInput)
			((VirtualAnalogVoltageInput)input).setVoltage(valueSlider.getValue()/SCALE_FACTOR);
	}
}
