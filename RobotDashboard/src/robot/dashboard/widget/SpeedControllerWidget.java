package robot.dashboard.widget;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JComponent;

import robot.dashboard.Widget;
import robot.io.RobotObjectListener;
import robot.io.speedcontroller.SpeedController;

/**
 * @author Mitchell
 * 
 *         A widget that displays the output of a speed controller
 * 
 */
public class SpeedControllerWidget extends Widget<SpeedController> implements
		RobotObjectListener<SpeedController> {

	private SpeedController output;

	/**
	 * Create a new widget
	 * 
	 * @param output
	 */
	public SpeedControllerWidget() {
		setLayout(new BorderLayout());

		add(new SpeedControllerWidgetCanvas());
		setPreferredSize(new Dimension(200, 160));

		objectUpdated(null);
	}
	
	@Override
	public void setObject(SpeedController newOutput){
		if(output!=null)
			output.removeUpdateListener(this);
		if(newOutput!=null)
			newOutput.addUpdateListener(this);
		output = newOutput;
		objectUpdated(output);
	}

	@Override
	public void objectUpdated(SpeedController output) {
		repaint();
	}

	private class SpeedControllerWidgetCanvas extends JComponent {
		public static final int PADDING = 10;
		public static final double MOTOR_SIZE = 0.7;
		public static final double ARROW_WIDTH = 0.3;

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			int size = (int) Math.min(getWidth()-PADDING*2, (getHeight()-PADDING*2)/MOTOR_SIZE);
			int motorSize = (int) (size*MOTOR_SIZE);
			int arrowWidth = (int) (size*ARROW_WIDTH);
			
			g.setColor(Color.BLACK);
			g.drawOval(PADDING, PADDING, motorSize, motorSize);

			String text;
			if(output!=null){
				text = String.format("%.3f", output.get());
				int arrowX = PADDING*2+motorSize+arrowWidth/2;
				if(output.get()>0){
					g.setColor(Color.BLUE);
					g.drawLine(arrowX, PADDING, arrowX, PADDING+motorSize);
					g.drawLine(arrowX, PADDING, arrowX-arrowWidth/2, PADDING+arrowWidth/2);
					g.drawLine(arrowX, PADDING, arrowX+arrowWidth/2, PADDING+arrowWidth/2);
				}
				else if(output.get()<0){
					g.setColor(Color.RED);
					g.drawLine(arrowX, PADDING, arrowX, PADDING+motorSize);
					g.drawLine(arrowX, PADDING+motorSize, arrowX-arrowWidth/2, PADDING+motorSize-arrowWidth/2);
					g.drawLine(arrowX, PADDING+motorSize, arrowX+arrowWidth/2, PADDING+motorSize-arrowWidth/2);
				}
			}
			else
				text = "Not Connected";
			
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
			FontMetrics fm = g.getFontMetrics();
			g.drawString(text, PADDING+motorSize/2-fm.stringWidth(text)/2, PADDING+motorSize/2+fm.getAscent()/2);
		}
	}
}
