package gui;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DisplayPanel extends JPanel {

	private JTextArea textArea;
	
	public DisplayPanel() {
		this.init();
	}
	
	public void init() {
		this.textArea = new JTextArea();
		this.add(this.textArea);
	}
	
	public void repaintData(String text) {
		this.textArea.setText(text);
	}
}
