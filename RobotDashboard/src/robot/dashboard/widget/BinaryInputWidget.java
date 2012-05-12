package robot.dashboard.widget;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;

import robot.dashboard.Widget;
import robot.io.RobotObject;
import robot.io.RobotObjectListener;
import robot.io.binary.BinaryInput;

/**
 * @author Mitchell
 * 
 * 
 *
 */
public class BinaryInputWidget extends Widget<BinaryInput> implements RobotObjectListener {
	private BinaryInput input;
	private final JButton button;
	/**
	 * Create a new widget
	 */
	public BinaryInputWidget(){
		setLayout(new BorderLayout());
		
		add(button = new JButton());
		setPreferredSize(new Dimension(150, 50));
		
		objectUpdated(null);
	}
	@Override
	public void setObject(BinaryInput newInput){
		if(input!=null)
			input.removeUpdateListener(this);
		if(newInput!=null)
			newInput.addUpdateListener(this);
		input = newInput;
		objectUpdated(input);
	}
	@Override
	public void objectUpdated(RobotObject o) {
		if(input==null){
			button.setEnabled(false);
			button.setText("Not Connected");
			return;
		}
		if(input.get()){
			button.setEnabled(true);
			button.setText("HIGH");
		}
		else{
			button.setEnabled(false);
			button.setText("LOW ");
		}
	}
}
