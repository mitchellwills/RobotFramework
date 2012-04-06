package robot.dashboard.widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import robot.dashboard.Widget;
import robot.io.RobotObjectListener;
import robot.io.joystick.Joystick;
import robot.io.joystick.JoystickDirectional;

/**
 * @author Mitchell
 * 
 * A widget that displays the state of a joysticks axes, buttons and directionals
 *
 */
public class JoystickWidget extends Widget implements RobotObjectListener<Joystick>{
	private final Joystick joystick;
	private final AxesView axesView;
	private final ButtonsView buttonsView;
	private final DirectionalsView directionalsView;
	/**
	 * Create a new widget
	 * @param j
	 */
	public JoystickWidget(Joystick j){
		this.joystick = j;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new TitledBorder(joystick.getName()));

		add(axesView = new AxesView(joystick));
		add(buttonsView = new ButtonsView(joystick));
		add(directionalsView = new DirectionalsView(joystick));

		objectUpdated(joystick);
		joystick.addUpdateListener(this);
	}
	@Override
	public void objectUpdated(Joystick joystick) {
		axesView.update();
		buttonsView.update();
		directionalsView.update();
	}

	private static class AxesView extends JComponent{
		private Joystick joystick;
		
		private JLabel[] nameLabels;
		private JProgressBar[] valueBars;
		public AxesView(Joystick joystick){
			this.joystick = joystick;
			setBorder(new TitledBorder("Axes"));
			setLayout(new GridBagLayout());
			nameLabels = new JLabel[joystick.getAxisCount()];
			valueBars = new JProgressBar[joystick.getAxisCount()];
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(2, 0, 2, 5);
			for(int i = 0; i<joystick.getAxisCount(); ++i){
				c.gridy = i;
				c.gridx = 0;
				add(nameLabels[i] = new JLabel("("+i+") "+joystick.getAxis(i).getName(), SwingConstants.LEADING), c);
				c.gridx = 1;
				add(valueBars[i] = new JProgressBar(-1000, 1000), c);
			}
			
			update();
		}
		public void update(){
			for(int i = 0; i<valueBars.length; ++i)
				valueBars[i].setValue((int) (joystick.getAxis(i).getValue()*1000));
		}
	}

	private static class ButtonsView extends JComponent{
		private static final int ROW_SIZE = 5;
		
		private Joystick joystick;
		private JRadioButton[] buttons;
		public ButtonsView(Joystick joystick){
			this.joystick = joystick;
			setBorder(new TitledBorder("Buttons"));
			setLayout(new GridBagLayout());
			
			buttons = new JRadioButton[joystick.getButtonCount()];
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(1, 1, 1, 1);
			
			for(int i = 0; i<joystick.getButtonCount();){
				for(int j = 0; j<ROW_SIZE && i<joystick.getButtonCount(); ++j){
					c.gridy = i/ROW_SIZE;
					c.gridx = j;
					add(buttons[i] = new JRadioButton("("+i+") "+joystick.getButton(i).getName()), c);
					buttons[i].setEnabled(false);
					++i;
				}
			}
			
			update();
		}
		public void update(){
			for(int i = 0; i<buttons.length; ++i)
				buttons[i].setSelected(joystick.getButton(i).get());
		}
	}

	private static class DirectionalsView extends JComponent{
		private static final int ROW_SIZE = 3;
		
		private Joystick joystick;
		private DirectionalView[] views;
		public DirectionalsView(Joystick joy){
			this.joystick = joy;
			setBorder(new TitledBorder("Directionals"));
			setLayout(new GridBagLayout());
			
			views = new DirectionalView[joystick.getDirectionalCount()];
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(5, 5, 5, 5);
			
			for(int i = 0; i<joystick.getDirectionalCount();){
				for(int j = 0; j<ROW_SIZE && i<joystick.getDirectionalCount(); ++j){
					c.gridy = i/ROW_SIZE;
					c.gridx = j;
					add(views[i] = new DirectionalView(i, joystick.getDirectional(i)), c);
					views[i].setEnabled(false);
					++i;
				}
			}
			
			update();
		}
		public void update(){
			for(int i = 0; i<views.length; ++i)
				views[i].update();
		}
	}
	

	private static class DirectionalView extends JComponent{
		public static final int PADDING = 20;
		public static final int SIZE = 130;
		private JoystickDirectional directional;

		public DirectionalView(int index, JoystickDirectional directional){
			this.directional = directional;
			setBorder(new TitledBorder("("+index+") "+directional.getName()));
			setPreferredSize(new Dimension(SIZE, SIZE));
			update();
		}
		public void update(){
			repaint();
		}
		@Override
		public void paintComponent(Graphics g){
			g.setColor(Color.BLACK);
			g.drawOval(PADDING, PADDING, SIZE-PADDING*2, SIZE-PADDING*2);
			g.drawLine(SIZE/2, SIZE/2, (int)(SIZE/2+(SIZE/2-PADDING)*directional.getMagnatude()*Math.cos(Math.toRadians(directional.getAngle()-90))),
					(int)(SIZE/2+(SIZE/2-PADDING)*directional.getMagnatude()*Math.sin(Math.toRadians(directional.getAngle()-90))));
		}
	}
	
	
}
