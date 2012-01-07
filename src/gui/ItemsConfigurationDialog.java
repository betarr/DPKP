package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ItemsConfigurationDialog extends JDialog {
	private ControlPanel controlPanel;

	private JButton confirmButton = new JButton("Confirm");
	private JButton cancelButton = new JButton("Cancel");
	
	private JTextField gainsTextField = new JTextField();
	private JTextField weightsTextField = new JTextField();
	
	public ItemsConfigurationDialog(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
		this.init();
	}

	public void init() {
		this.setTitle("Items Configuration Dialog");
		this.setSize(new Dimension(300, 250));
		
		this.setLayout(new BorderLayout());
		
		this.add(this.buildContentPanel(), BorderLayout.CENTER);
		this.add(this.buildControlPanel(), BorderLayout.SOUTH);
		
		this.setModalityType(ModalityType.APPLICATION_MODAL);
	}
	
	private Component buildContentPanel() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		panel.add(new ImagePanel("item.png", new Dimension(100, 100)), BorderLayout.NORTH);
		
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(4, 1));
		
		content.add(new JLabel("Gains"));
		content.add(this.gainsTextField);
		content.add(new JLabel("Weights"));
		content.add(this.weightsTextField);
		panel.add(content, BorderLayout.CENTER);
		return panel;
	}

	private Component buildControlPanel() {
		JPanel controlPanel = new JPanel();
		this.confirmButton.addActionListener(new ConfirmItemsConfListener(this));
		this.cancelButton.addActionListener(new CancelItemsConfListener(this));
		controlPanel.add(this.confirmButton);
		controlPanel.add(this.cancelButton);
		return controlPanel;
	}
	
	public boolean areValuesValid() {
		String delimiter = ",";
		String gainsString = this.gainsTextField.getText();
		String weightsString = this.weightsTextField.getText();
		
		StringTokenizer st = new StringTokenizer(gainsString, delimiter);
		StringTokenizer st2 = new StringTokenizer(weightsString, delimiter);
		List<Integer> gainsList = new ArrayList<Integer>();
		List<Integer> weightsList = new ArrayList<Integer>();
		try {
			while (st.hasMoreTokens()) {
				gainsList.add(Integer.parseInt(st.nextToken().trim()));
			}
			while (st2.hasMoreTokens()) {
				weightsList.add(Integer.parseInt(st2.nextToken().trim()));
			}
			return gainsList.size() == weightsList.size();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void setValues() {
		String delimiter = ",";
		String gainsString = this.gainsTextField.getText();
		String weightsString = this.weightsTextField.getText();
		
		StringTokenizer st = new StringTokenizer(gainsString, delimiter);
		StringTokenizer st2 = new StringTokenizer(weightsString, delimiter);
		List<Integer> gainsList = new ArrayList<Integer>();
		List<Integer> weightsList = new ArrayList<Integer>();
		try {
			while (st.hasMoreTokens()) {
				gainsList.add(Integer.parseInt(st.nextToken().trim()));
			}
			while (st2.hasMoreTokens()) {
				weightsList.add(Integer.parseInt(st2.nextToken().trim()));
			}
			int[] gainsArray = new int[gainsList.size()];
			int[] weightsArray = new int[weightsList.size()];
			for (int i = 0; i < gainsList.size(); i++) {
				gainsArray[i] = gainsList.get(i);
			}
			for (int i = 0; i < weightsList.size(); i++) {
				weightsArray[i] = weightsList.get(i);
			}
			this.controlPanel.setValues(gainsArray, weightsArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class ConfirmItemsConfListener implements ActionListener {
		private ItemsConfigurationDialog dialog;
		
		public ConfirmItemsConfListener(ItemsConfigurationDialog dialog) {
			this.dialog = dialog;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (this.dialog.areValuesValid()) {
				this.dialog.setValues();
				this.dialog.dispose();
			}
		}
		
	}
	
	public class CancelItemsConfListener implements ActionListener {
		private ItemsConfigurationDialog dialog;

		public CancelItemsConfListener(ItemsConfigurationDialog dialog) {
			this.dialog = dialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			this.dialog.dispose();
		}
		
		
	}

	
}
