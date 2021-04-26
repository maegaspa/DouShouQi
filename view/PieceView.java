package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import model.PieceModel;
import controller.Controller;



public class PieceView extends JButton {

	private int thickness = 3;
	private Color highlight_color = Color.blue;
	private boolean visible = false;
	private BufferedImage img;
	private int width;
	private int height;
	private static ViewGestion viewGestion;


	public PieceView() {
		super();
	}

	@Override
	public void paintComponent(Graphics g){
		if (visible) {
			width = getWidth();
			height = getHeight();
			g.drawImage(img, 0, 0, width, height, viewGestion);
		}
		if (isSelected()) {
			setBorderPainted(true);
			setBorder(BorderFactory.createLineBorder(highlight_color,
					thickness));
			paintBorder(g);
		}
	}

	public void showPiece(PieceModel p) {
		if (p.isAlive()) {
			img = p.getImage();
			visible = true;
		}
	}

	public void removePiece() {
		img = null;
		visible = false;
	}

}