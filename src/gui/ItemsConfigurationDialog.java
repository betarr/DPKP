package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
	
	private JTextField numberOfItemsTextField = new JTextField();
	private JTextField maxWeightTextField = new JTextField();
	private JTextField maxGaintTextField = new JTextField();
	
	public ItemsConfigurationDialog(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
		this.init();
	}

	public void init() {
		this.setTitle("Items Configuration Dialog");
		this.setSize(new Dimension(300, 400));
		
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
		content.setLayout(new GridLayout(9, 1));
		
		content.add(new JLabel("Gains"));
		this.gainsTextField.setText(this.controlPanel.getGui().getObjectsGainAsString());
		content.add(this.gainsTextField);
		content.add(new JLabel("Weights"));
		this.weightsTextField.setText(this.controlPanel.getGui().getObjectsWeightsAsString());
		content.add(this.weightsTextField);
		
		// generating
		content.add(new JLabel("Generate Items"));
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(1, 2));
		panel1.add(new JLabel("Number Of Items: "));
		panel1.add(this.numberOfItemsTextField);
		content.add(panel1);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1, 2));
		panel2.add(new JLabel("Maximum Gain: "));
		panel2.add(this.maxGaintTextField);
		content.add(panel2);
		
		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(1, 2));
		panel3.add(new JLabel("Maximum Weight: "));
		panel3.add(this.maxWeightTextField);
		content.add(panel3);
		
		JButton generateButton = new JButton("Generate");
		generateButton.addActionListener(new GenerateItemsListener(this));
		content.add(generateButton);
		
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
	
	public boolean areGenerateValuesValid() {
		String numberOfItemsString = this.numberOfItemsTextField.getText();
		String maxGainString = this.maxGaintTextField.getText();
		String maxWeightString = this.maxGaintTextField.getText();
		try {
			Integer.parseInt(numberOfItemsString);
			Integer.parseInt(maxGainString);
			Integer.parseInt(maxWeightString);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void generateInputs() {
		int numberOfItems = Integer.parseInt(this.numberOfItemsTextField.getText());
		int maxGain = Integer.parseInt(this.maxGaintTextField.getText());
		int maxWeight = Integer.parseInt(this.maxGaintTextField.getText());
		
		String gainsString = "";
		String weightsString = "";
		
		Random r = new Random();
		for (int i = 0; i < numberOfItems; i++) {
			if (i != 0) {
				gainsString += ", ";
				weightsString += ", ";
			}
			gainsString += r.nextInt(maxGain)+1;
			weightsString += r.nextInt(maxWeight)+1;
		}
		this.gainsTextField.setText(gainsString);
		this.weightsTextField.setText(weightsString);
	}
	
	public class GenerateItemsListener implements ActionListener {
		private ItemsConfigurationDialog dialog;

		public GenerateItemsListener(ItemsConfigurationDialog dialog) {
			this.dialog = dialog;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (this.dialog.areGenerateValuesValid()) {
				this.dialog.generateInputs();
			}
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
