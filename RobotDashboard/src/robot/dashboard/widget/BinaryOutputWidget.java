package robot.dashboard.widget;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import robot.dashboard.Widget;
import robot.io.RobotObjectListener;
import robot.io.binary.BinaryOutput;

public class BinaryOutputWidget extends Widget implements RobotObjectListener<BinaryOutput> {
	private final BinaryOutput output;
	private JButton button;
	public BinaryOutputWidget(BinaryOutput output){
		this.output = output;
		setBorder(new TitledBorder("Binary Output: "+output));
		setLayout(new BorderLayout());
		
		
		add(button = new JButton("    "));
		
		objectUpdated(output);
		output.addUpdateListener(this);
	}
	@Override
	public void objectUpdated(BinaryOutput output) {
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
