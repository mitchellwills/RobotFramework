package robot.dashboard.widget;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;

import robot.dashboard.Widget;
import robot.io.RobotObject;
import robot.io.RobotObjectListener;
import robot.io.UpdatableObject;
import robot.io.value.InputValue;

/**
 * @author Mitchell
 * 
 * 
 *
 */
public class InputValueWidget extends Widget<InputValue> implements RobotObjectListener {
	private InputValue input;
	private final JLabel label;
	/**
	 * Create a new widget
	 */
	public InputValueWidget(){
		setLayout(new BorderLayout());
		
		add(label = new JLabel());
		setPreferredSize(new Dimension(100, 50));
		
		objectUpdated(null);
	}
	@Override
	public void setObject(InputValue newInput){
		if(input instanceof UpdatableObject)
			((UpdatableObject)input).removeUpdateListener(this);
		if(newInput instanceof UpdatableObject)
			((UpdatableObject)newInput).addUpdateListener(this);
		input = newInput;
		objectUpdated(input);
	}
	@Override
	public void objectUpdated(RobotObject o) {
		if(input==null){
			label.setText("Disconnected");
		}
		else{
			label.setText(String.format("%.2f", input.getValue()));
		}
	}
}
