package gui;

import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {
	private GUI gui;
	
	private JButton knapsackConfigurationButton = new JButton("Knapsack Configuration");
	private JButton itemsConfigurationButton = new JButton("Items Configuration");
	private JButton runButton = new JButton("Run");
	
	public ControlPanel(GUI gui) {
		this.gui = gui;
		this.init();
	}

	private void init() {
		this.knapsackConfigurationButton.addActionListener(new KnapsackConfigurationListener(this));
		this.add(this.knapsackConfigurationButton);
		
		this.itemsConfigurationButton.addActionListener(new ItemsConfigurationListener(this));
		this.add(this.itemsConfigurationButton);
		
		this.runButton.addActionListener(new RunListener(this));
		this.add(this.runButton);
		
	}
	
	public void showKnapsackConfigurationDialog() {
		KnapsackConfigurationDialog dialog = new KnapsackConfigurationDialog(this);
		dialog.setLocationRelativeTo(this.gui.getFrame());
		dialog.setVisible(true);
	}
	
	public void showItemsConfigurationDialog() {
		ItemsConfigurationDialog dialog = new ItemsConfigurationDialog(this);
		dialog.setLocationRelativeTo(this.gui.getFrame());
		dialog.setVisible(true);
	}
	
	public void setMaxWeight(int maxWeight) {
		this.gui.setMaximumWeight(maxWeight);
	}
	
	public void setValues(int[] gains, int[] weights) {
		this.gui.setObjectsGain(gains);
		this.gui.setObjectsWeights(weights);
	}
	
	public void run() {
		this.gui.run();
	}
	
	public class KnapsackConfigurationListener implements ActionListener {
		private ControlPanel controlPanel;
		
		public KnapsackConfigurationListener(ControlPanel controlPanel) {
			this.controlPanel = controlPanel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			this.controlPanel.showKnapsackConfigurationDialog();
		}
	}
	
	public class ItemsConfigurationListener implements ActionListener {
		private ControlPanel controlPanel;
		
		public ItemsConfigurationListener(ControlPanel controlPanel) {
			this.controlPanel = controlPanel;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			this.controlPanel.showItemsConfigurationDialog();
		}
	}
	
	public class RunListener implements ActionListener {
		private ControlPanel controlPanel;
		
		public RunListener(ControlPanel controlPanel) {
			this.controlPanel = controlPanel;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			this.controlPanel.run();
		}
	}

}
