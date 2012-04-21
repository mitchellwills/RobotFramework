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
import robot.io.RobotObject;
import robot.io.RobotObjectListener;
import robot.io.joystick.Joystick;
import robot.io.joystick.JoystickDirectional;

/**
 * @author Mitchell
 * 
 *         A widget that displays the state of a joysticks axes, buttons and
 *         directionals
 * 
 */
public class JoystickWidget extends Widget<Joystick> implements RobotObjectListener {
	private Joystick joystick;
	private final AxesView axesView;
	private final ButtonsView buttonsView;
	private final DirectionalsView directionalsView;

	/**
	 * Create a new widget
	 * 
	 * @param j
	 */
	public JoystickWidget() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(axesView = new AxesView());
		add(buttonsView = new ButtonsView());
		add(directionalsView = new DirectionalsView());

		objectUpdated(null);
	}

	@Override
	public void setObject(Joystick newJoystick) {
		if (joystick != null)
			joystick.removeUpdateListener(this);
		if (newJoystick != null)
			newJoystick.addUpdateListener(this);
		joystick = newJoystick;

		axesView.setObject(joystick);
		buttonsView.setObject(joystick);
		directionalsView.setObject(joystick);

		objectUpdated(joystick);
	}

	@Override
	public void objectUpdated(RobotObject object) {
		axesView.update(joystick);
		buttonsView.update(joystick);
		directionalsView.update(joystick);
	}

	private static class AxesView extends JComponent {

		private JLabel[] nameLabels;
		private JProgressBar[] valueBars;

		public AxesView() {
			setBorder(new TitledBorder("Axes"));
			setLayout(new GridBagLayout());
		}

		void setObject(Joystick joystick) {
			removeAll();
			if (joystick != null) {
				nameLabels = new JLabel[joystick.getAxisCount()];
				valueBars = new JProgressBar[joystick.getAxisCount()];
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.BOTH;
				c.insets = new Insets(2, 0, 2, 5);
				for (int i = 0; i < joystick.getAxisCount(); ++i) {
					c.gridy = i;
					c.gridx = 0;
					add(nameLabels[i] = new JLabel("(" + i + ") "
							+ joystick.getAxis(i).getName(),
							SwingConstants.LEADING), c);
					c.gridx = 1;
					add(valueBars[i] = new JProgressBar(-1000, 1000), c);
					valueBars[i].setStringPainted(true);
				}
			}
		}

		public void update(Joystick joystick) {
			if (joystick != null) {
				for (int i = 0; i < valueBars.length; ++i) {
					valueBars[i]
							.setValue((int) (joystick.getAxis(i).getValue() * 1000));
					valueBars[i].setString(String.format("%.2f", joystick
							.getAxis(i).getValue()));
				}
			}
		}
	}

	private static class ButtonsView extends JComponent {
		private static final int ROW_SIZE = 5;

		private JRadioButton[] buttons;

		public ButtonsView() {
			setBorder(new TitledBorder("Buttons"));
			setLayout(new GridBagLayout());
		}

		void setObject(Joystick joystick) {
			removeAll();
			if (joystick != null) {
				buttons = new JRadioButton[joystick.getButtonCount()];
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.BOTH;
				c.insets = new Insets(1, 1, 1, 1);

				for (int i = 0; i < joystick.getButtonCount();) {
					for (int j = 0; j < ROW_SIZE
							&& i < joystick.getButtonCount(); ++j) {
						c.gridy = i / ROW_SIZE;
						c.gridx = j;
						add(buttons[i] = new JRadioButton("(" + i + ") "
								+ joystick.getButton(i).getName()), c);
						buttons[i].setEnabled(false);
						++i;
					}
				}
			}
		}

		public void update(Joystick joystick) {
			if (joystick != null) {
				for (int i = 0; i < buttons.length; ++i)
					buttons[i].setSelected(joystick.getButton(i).get());
			}
		}
	}

	private static class DirectionalsView extends JComponent {
		private static final int ROW_SIZE = 3;

		private DirectionalView[] views;

		public DirectionalsView() {
			setBorder(new TitledBorder("Directionals"));
			setLayout(new GridBagLayout());
		}

		void setObject(Joystick joystick) {
			removeAll();
			if (joystick != null) {
				views = new DirectionalView[joystick.getDirectionalCount()];
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.BOTH;
				c.insets = new Insets(5, 5, 5, 5);

				for (int i = 0; i < joystick.getDirectionalCount();) {
					for (int j = 0; j < ROW_SIZE
							&& i < joystick.getDirectionalCount(); ++j) {
						c.gridy = i / ROW_SIZE;
						c.gridx = j;
						add(views[i] = new DirectionalView(i,
								joystick.getDirectional(i)), c);
						views[i].setEnabled(false);
						++i;
					}
				}
			}
		}

		public void update(Joystick joystick) {
			if (joystick != null) {
				for (int i = 0; i < views.length; ++i)
					views[i].update();
			}
		}
	}

	private static class DirectionalView extends JComponent {
		public static final int PADDING = 20;
		public static final int SIZE = 130;
		private JoystickDirectional directional;

		public DirectionalView(int index, JoystickDirectional directional) {
			this.directional = directional;
			setBorder(new TitledBorder("(" + index + ") "
					+ directional.getName()));
			setPreferredSize(new Dimension(SIZE, SIZE));
			update();
		}

		public void update() {
			repaint();
		}

		@Override
		public void paintComponent(Graphics g) {
			g.setColor(Color.BLACK);
			g.drawOval(PADDING, PADDING, SIZE - PADDING * 2, SIZE - PADDING * 2);
			g.drawLine(
					SIZE / 2,
					SIZE / 2,
					(int) (SIZE / 2 + (SIZE / 2 - PADDING)
							* directional.getMagnatude()
							* Math.cos(Math.toRadians(directional.getAngle() - 90))),
					(int) (SIZE / 2 + (SIZE / 2 - PADDING)
							* directional.getMagnatude()
							* Math.sin(Math.toRadians(directional.getAngle() - 90))));
		}
	}

}
