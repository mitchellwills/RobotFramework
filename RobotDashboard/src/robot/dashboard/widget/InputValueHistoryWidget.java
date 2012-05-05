package robot.dashboard.widget;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JPanel;

import robot.dashboard.Widget;
import robot.io.RobotObject;
import robot.io.RobotObjectListener;
import robot.io.UpdatableObject;
import robot.io.value.InputValue;
import robot.util.AsyncTimer;
import robot.util.RingBuffer;

/**
 * @author Mitchell
 * 
 * A widget that displays the history of an InputValue
 *
 */
public class InputValueHistoryWidget extends Widget<InputValue> implements RobotObjectListener {
	private static final int TIME_TICK_SIZE = 5;
	private static final int VALUE_TICK_SIZE = 5;
	
	private static final int LEFT_PADDING = 40;
	private static final int RIGHT_PADDING = 5;
	private static final int TOP_PADDING = 5;
	private static final int BOTTOM_PADDING = TIME_TICK_SIZE+5;

	private InputValue input;
	private double min;
	private double max;
	private double valueGridlineSpacing;
	private int totalTime;
	private int timeInterval;
	private int timeTickSpacing;
	private String valueLabelFormat;
	
	private RingBuffer buffer;
	/**
	 * Create a new widget
	 * @param params 
	 * @param output
	 */
	public InputValueHistoryWidget(Map<String, String> params){
		setLayout(new BorderLayout());

		min = Double.parseDouble(params.get("min"));
		max = Double.parseDouble(params.get("max"));
		valueGridlineSpacing = Double.parseDouble(params.get("gridlineSpacing"));
		if(params.containsKey("valueLabel"))
			valueLabelFormat = params.get("valueLabel");
		else
			valueLabelFormat = "%.2f";

		totalTime = Integer.parseInt(params.get("time"));
		timeInterval = Integer.parseInt(params.get("timeInterval"));
		timeTickSpacing = 1000;
		

		buffer = new RingBuffer(totalTime/timeInterval);
		
		new UpdateThread().start();
		add(new HistoryPanel());
		
		setPreferredSize(new Dimension(500, 200));
		
		objectUpdated(null);
	}
	
	@Override
	public void setObject(InputValue newInput){
		if(input instanceof UpdatableObject)
			((UpdatableObject)input).removeUpdateListener(this);
		if(newInput instanceof UpdatableObject)
			((UpdatableObject)newInput).addUpdateListener(this);
		input = newInput;
		buffer.clear();
		objectUpdated(input);
	}
	private class UpdateThread extends Thread{
		private boolean run = true;
		private final AsyncTimer timer = new AsyncTimer();
		@Override
		public void run(){
			while(run){
				if(timer.waitComplete()){
					timer.startWaitFromPrevious(timeInterval);
					if(input!=null){
						buffer.append(input.getValue());
						repaint();
					}
				}
			}
		}
	}
	
	private class HistoryPanel extends JPanel{
		private int timeToX(int time){
			return LEFT_PADDING+time*(getWidth()-LEFT_PADDING-RIGHT_PADDING)/totalTime;
		}
		private int toX(int index){
			return LEFT_PADDING+index*(getWidth()-LEFT_PADDING-RIGHT_PADDING)/(buffer.size()-1);
		}
		private int toY(double value){
			return getHeight()-BOTTOM_PADDING- (int)((value-min)/(max-min)*(getHeight()-BOTTOM_PADDING-TOP_PADDING));
		}
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			int height = getHeight();
			int width = getWidth();
			
			//draw axes
			g.setColor(Color.BLACK);
			g.drawLine(LEFT_PADDING, TOP_PADDING, LEFT_PADDING, height-BOTTOM_PADDING);
			g.drawLine(LEFT_PADDING, height-BOTTOM_PADDING, width-RIGHT_PADDING, height-BOTTOM_PADDING);
			
			//draw time ticks
			for(int i = 0; timeToX(i*timeTickSpacing)<=getWidth()-RIGHT_PADDING; ++i){
				g.drawLine(timeToX(i*timeTickSpacing), height-BOTTOM_PADDING+TIME_TICK_SIZE, timeToX(i*timeTickSpacing), height-BOTTOM_PADDING);
			}

			//draw value ticks
			String maxLabel = String.format(valueLabelFormat, max);
			g.drawLine(LEFT_PADDING-VALUE_TICK_SIZE, TOP_PADDING, LEFT_PADDING, TOP_PADDING);
			g.drawString(maxLabel, LEFT_PADDING-VALUE_TICK_SIZE-g.getFontMetrics().stringWidth(maxLabel), TOP_PADDING+g.getFontMetrics().getAscent());
			String minLabel = String.format(valueLabelFormat, min);
			g.drawLine(LEFT_PADDING-VALUE_TICK_SIZE, height-BOTTOM_PADDING, LEFT_PADDING, height-BOTTOM_PADDING);
			g.drawString(minLabel, LEFT_PADDING-VALUE_TICK_SIZE-g.getFontMetrics().stringWidth(minLabel), height-BOTTOM_PADDING);
			
			//draw gridlines
			g.setColor(Color.LIGHT_GRAY);
			for(int i = 1; toY(min+valueGridlineSpacing*i*Math.signum(max-min))>=TOP_PADDING; ++i){
				g.drawLine(LEFT_PADDING, toY(min+valueGridlineSpacing*i*Math.signum(max-min)), width-RIGHT_PADDING, toY(min+valueGridlineSpacing*i*Math.signum(max-min)));
			}
			
			//draw values
			g.setColor(Color.BLACK);
			int lastY = toY(buffer.get(0));
			int lastX = toX(0);
			for(int i = 1; i<buffer.size(); ++i){
				double value = buffer.get(i);
				int y = toY(value);
				int x = toX(i);
				
				g.drawLine(lastX, lastY, x, y);
				
				lastY = y;
				lastX = x;
			}
		}
	}
	
	@Override
	public void objectUpdated(RobotObject o) {
		//display is updated in update thread
	}

}
