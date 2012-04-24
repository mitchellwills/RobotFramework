package robot.dashboard;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.border.TitledBorder;

/**
 * @author Mitchell
 * 
 * A component that puts a label border around a widget
 *
 */
public class WidgetContainer extends JComponent {
	private final Widget<?> widget;
	/**
	 * Create a new Widget Containter
	 * @param label
	 * @param w
	 */
	public WidgetContainer(String label, Widget<?> w){
		setLayout(new BorderLayout());
		setBorder(new TitledBorder(label));
		add(widget = w);
	}
	
	/**
	 * @return the widget this component wraps
	 */
	public Widget<?> getWidget(){
		return widget;
	}
}
