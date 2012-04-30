package robot.dashboard.widget;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import robot.dashboard.Widget;
import robot.error.RobotInitializationException;
import robot.io.InputValue;
import robot.io.RobotObject;
import robot.io.RobotObjectListener;
import robot.io.UpdatableObject;

/**
 * @author Mitchell
 * 
 * 
 *
 */
public class InputValueBarWidget extends Widget<InputValue> implements RobotObjectListener {
	private static final double SCALE_FACTOR = 1000;
	
	private InputValue input;
	private final JProgressBar valueBar;
	/**
	 * Create a new widget
	 * @param params 
	 * @param output
	 */
	public InputValueBarWidget(Map<String, String> params){
		setLayout(new BorderLayout());

		double min = Double.parseDouble(params.get("min"));
		double max = Double.parseDouble(params.get("max"));

		String orientationString = params.get("orientation");
		int orientation;
		if(orientationString!=null){
			if(orientationString.equals("horizontal"))
				orientation = SwingConstants.HORIZONTAL;
			else if(orientationString.equals("vertical"))
				orientation = SwingConstants.VERTICAL;
			else
				throw new RobotInitializationException("Unsupported InputValueBar orientation: "+orientationString);
		}
		else
			orientation = SwingConstants.HORIZONTAL;
		
		add(valueBar = new JProgressBar(orientation, (int)(min*SCALE_FACTOR), (int)(max*SCALE_FACTOR)));
		valueBar.setStringPainted(true);
		
		setPreferredSize(new Dimension(200, 75));
		
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
		if(input!=null){
			valueBar.setValue((int) (input.getValue()*SCALE_FACTOR));
			valueBar.setString(String.format("%.3f", input.getValue()));
		}
		else{
			valueBar.setValue(0);
			valueBar.setString("Not Connected");
		}
	}
}
