package gui;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.StyledDocument;

public class DisplayPanel extends JPanel {

	private JTextPane textPane;
	
	public DisplayPanel() {
		this.init();
	}
	
	public void init() {
		this.textPane = new JTextPane();
		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(610, 345));
		
		textPane.setEditable(false);
		textPane.setMargin(new Insets(5, 5, 5, 5));
		
		this.add(scrollPane);
	}
	
	public void repaintData(String text) {
		this.textPane.setText(text);
	}
}
