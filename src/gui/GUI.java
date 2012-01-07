package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.DisplayMode;

import javax.swing.JFrame;

import app.DPKP;

public class GUI {

	private JFrame frame;
	private DisplayPanel displayPanel;
	
	int maximumWeight = 5;
	int numberOfObjects = 3;
	int[] objectsGain = {5, 3, 4};
	int[] objectsWeights = {3, 2, 1};
	
	public void init() {
		this.frame = new JFrame("Knapsack Problem");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setPreferredSize(new Dimension(640, 480));
		
		this.addPanels();
		
		this.frame.pack();
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
	}

	private void addPanels() {
		this.frame.setLayout(new BorderLayout());
		this.frame.add(new ControlPanel(this), BorderLayout.NORTH);
		this.displayPanel = new DisplayPanel();
		this.frame.add(this.displayPanel, BorderLayout.CENTER);
	}
	
	public void run() {
		DPKP dpkp = new DPKP(this.maximumWeight, this.objectsGain.length, this.objectsGain, this.objectsWeights);
		this.displayPanel.repaintData(dpkp.fillSack());
		System.out.println("here");
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void setMaximumWeight(int maximumWeight) {
		this.maximumWeight = maximumWeight;
	}

	public void setNumberOfObjects(int numberOfObjects) {
		this.numberOfObjects = numberOfObjects;
	}

	public void setObjectsGain(int[] objectsGain) {
		this.objectsGain = objectsGain;
	}

	public void setObjectsWeights(int[] objectsWeights) {
		this.objectsWeights = objectsWeights;
	}
}
