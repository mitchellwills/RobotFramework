package robot.dashboard;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.border.TitledBorder;

public class WidgetContainer extends JComponent {
	private final Widget widget;
	public WidgetContainer(String label, Widget w){
		setLayout(new BorderLayout());
		setBorder(new TitledBorder(label));
		add(widget = w);
	}
	
	public Widget getWidget(){
		return widget;
	}
}
