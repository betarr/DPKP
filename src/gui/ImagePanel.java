package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private String imagePath;
	private Dimension size;
	BufferedImage image;

	public ImagePanel(String imagePath, Dimension size) {
		this.imagePath = imagePath;
		this.size = size;
		this.init();
	}
	
	public void init() {
		this.setPreferredSize(this.size);
		try {
			image = ImageIO.read(new File(this.imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(this.image, 0, 0, null);
	}
	
	
	
}
