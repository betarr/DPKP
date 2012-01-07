package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class KnapsackConfigurationDialog extends JDialog {
	ControlPanel controlPanel;
	
	private JButton confirmButton = new JButton("Confirm");
	private JButton cancelButton = new JButton("Cancel");
	
	JTextField maxWeightTextField;
	
	public KnapsackConfigurationDialog(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
		this.init();
	}

	public void init() {
		this.setTitle("Knapsack Configuration Dialog");
		this.setSize(new Dimension(300, 250));
		
		this.setLayout(new BorderLayout());
		
		this.add(this.buildContentPanel(), BorderLayout.CENTER);
		this.add(this.buildControlPanel(), BorderLayout.SOUTH);
		
		this.setModalityType(ModalityType.APPLICATION_MODAL);
	}

	private Component buildContentPanel() {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(1, 2));
		
		contentPanel.add(new ImagePanel("knapsack.png", new Dimension(100, 100)));
		
		JPanel right = new JPanel();
		right.setLayout(new GridLayout(2, 1));
		right.add(new JLabel("Maximum Weight"));
		
		maxWeightTextField = new JTextField(String.valueOf(this.controlPanel.getGui().getMaximumWeight()));
		right.add(maxWeightTextField);
		
		contentPanel.add(right);
		return contentPanel;
		
	}

	private Component buildControlPanel() {
		JPanel controlPanel = new JPanel();
		this.confirmButton.addActionListener(new ConfirmKnapsackConfListener(this));
		this.cancelButton.addActionListener(new CancelKnapsackConfListener(this));
		controlPanel.add(this.confirmButton);
		controlPanel.add(this.cancelButton);
		return controlPanel;
	}
	
	public boolean isMaxWeightValid() {
		String maxWeightString = this.maxWeightTextField.getText();
		try {
			Integer.parseInt(maxWeightString);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void setMaxWeight() {
		this.controlPanel.setMaxWeight(Integer.parseInt(this.maxWeightTextField.getText()));
	}
	
	public class ConfirmKnapsackConfListener implements ActionListener {
		KnapsackConfigurationDialog dialog;
		
		public ConfirmKnapsackConfListener(KnapsackConfigurationDialog dialog) {
			this.dialog = dialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (this.dialog.isMaxWeightValid()) {
				this.dialog.setMaxWeight();
				this.dialog.dispose();
			}
		}
		
	}
	
	public class CancelKnapsackConfListener implements ActionListener {
		private KnapsackConfigurationDialog dialog;

		public CancelKnapsackConfListener(KnapsackConfigurationDialog dialog) {
			super();
			this.dialog = dialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			this.dialog.dispose();
		}
		
		
	}
}
