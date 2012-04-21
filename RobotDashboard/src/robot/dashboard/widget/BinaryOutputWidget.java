package robot.dashboard.widget;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;

import robot.dashboard.Widget;
import robot.io.RobotObject;
import robot.io.RobotObjectListener;
import robot.io.binary.BinaryOutput;

/**
 * @author Mitchell
 * 
 * 
 *
 */
public class BinaryOutputWidget extends Widget<BinaryOutput> implements RobotObjectListener {
	private BinaryOutput output;
	private final JButton button;
	/**
	 * Create a new widget
	 * @param output
	 */
	public BinaryOutputWidget(){
		setLayout(new BorderLayout());
		
		add(button = new JButton());
		setPreferredSize(new Dimension(150, 50));
		
		objectUpdated(null);
	}
	@Override
	public void setObject(BinaryOutput newOutput){
		if(output!=null)
			output.removeUpdateListener(this);
		if(newOutput!=null)
			newOutput.addUpdateListener(this);
		output = newOutput;
		objectUpdated(output);
	}
	@Override
	public void objectUpdated(RobotObject o) {
		if(output==null){
			button.setEnabled(false);
			button.setText("Not Connected");
			return;
		}
		if(output.get()){
			button.setEnabled(true);
			button.setText("HIGH");
		}
		else{
			button.setEnabled(false);
			button.setText("LOW ");
		}
	}
}
