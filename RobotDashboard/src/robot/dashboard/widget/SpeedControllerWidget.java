package robot.dashboard.widget;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.border.TitledBorder;

import robot.dashboard.Widget;
import robot.io.RobotObjectListener;
import robot.io.speedcontroller.SpeedController;

/**
 * @author Mitchell
 * 
 *         A widget that displays the output of a speed controller
 * 
 */
public class SpeedControllerWidget extends Widget implements
		RobotObjectListener<SpeedController> {

	private final SpeedController output;

	/**
	 * Create a new widget
	 * 
	 * @param output
	 */
	public SpeedControllerWidget(SpeedController output) {
		this.output = output;
		setBorder(new TitledBorder("Speed Controller: " + output));
		setLayout(new BorderLayout());

		add(new SpeedControllerWidgetCanvas());

		objectUpdated(output);
		output.addUpdateListener(this);
	}

	@Override
	public void objectUpdated(SpeedController output) {
		repaint();
	}

	private class SpeedControllerWidgetCanvas extends JComponent {
		public static final int PADDING = 10;
		public static final int MOTOR_SIZE = 70;
		public static final int ARROW_WIDTH = 30;
		{
			setPreferredSize(new Dimension(PADDING*3+MOTOR_SIZE+ARROW_WIDTH, PADDING*2+MOTOR_SIZE));
		}

		@Override
		public void paintComponent(Graphics g) {
			g.setColor(Color.BLACK);
			g.drawOval(PADDING, PADDING, MOTOR_SIZE, MOTOR_SIZE);
			String text = String.format("%.3f", output.get());
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
			FontMetrics fm = g.getFontMetrics();
			g.drawString(text, PADDING+MOTOR_SIZE/2-fm.stringWidth(text)/2, PADDING+MOTOR_SIZE/2+fm.getAscent()/2);
			
			int arrowX = PADDING*2+MOTOR_SIZE+ARROW_WIDTH/2;
			if(output.get()>0){
				g.setColor(Color.BLUE);
				g.drawLine(arrowX, PADDING, arrowX-ARROW_WIDTH/2, PADDING+ARROW_WIDTH/2);
				g.drawLine(arrowX, PADDING, arrowX+ARROW_WIDTH/2, PADDING+ARROW_WIDTH/2);
				g.drawLine(arrowX, PADDING, arrowX, getHeight()-1-PADDING);
			}
			else if(output.get()<0){
				g.setColor(Color.RED);
				g.drawLine(arrowX, PADDING, arrowX, getHeight()-1-PADDING);
				g.drawLine(arrowX, getHeight()-1-PADDING, arrowX-ARROW_WIDTH/2, getHeight()-1-PADDING-ARROW_WIDTH/2);
				g.drawLine(arrowX, getHeight()-1-PADDING, arrowX+ARROW_WIDTH/2, getHeight()-1-PADDING-ARROW_WIDTH/2);
			}
		}
	}
}
