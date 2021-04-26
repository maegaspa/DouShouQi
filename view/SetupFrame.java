package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.Component;
import javax.swing.JFrame;
import view.ViewMenu;


public class SetupFrame extends JFrame {

	public SetupFrame() {
		this.setTitle("DouShouQi");
		this.setSize(800, 800);
		this.setLocationRelativeTo((Component) null);
		this.setDefaultCloseOperation(3);
		this.setResizable(false);
	}
}