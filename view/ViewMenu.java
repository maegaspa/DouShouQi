package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewMenu extends JPanel {

	private static BufferedImage img;


	private static PlateauView plateau;
	private static ViewGestion viewGestion;

	public ViewMenu() {
		super();
		try {
			img = ImageIO.read(new File("view/images/DouShouQiMenu.png"));
			//Image du menu
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this);
	}

}