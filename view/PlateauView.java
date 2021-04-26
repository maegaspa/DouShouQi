package view;

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
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import model.*;
import controller.Controller;

public class PlateauView extends JPanel{

	private static BufferedImage img;
	private static ViewGestion viewGestion;
	private PieceModel[][] pieceModel;
	private PlateauModel plateauModel;
	private static final PieceView[][] piece = new PieceView[7][9];

	public PlateauView(PlateauModel plateauModel) {
		super();
		setLayout(new GridLayout(9, 7));
		this.plateauModel = plateauModel;
		this.pieceModel = plateauModel.getModel();
		try {
			img = ImageIO.read(new File("view/images/DouShouQiPlateau2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		addButtons();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, viewGestion); // draw sur le layout de ViewGestion
		super.repaint();
		for (int j = 0; j < piece[0].length; j++){
			for (int i = 0; i < piece.length; i++) {
				piece[i][j].repaint();
			}
		}
		putPieces();
	}

	public PieceView[][] getButtons() {
		return piece;
	}

	public PlateauModel getPlateauModel() {
		return plateauModel;
	}

	private void putPieces() {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 7; x++) {
				if (pieceModel[x][y] != null) {
					piece[x][y].showPiece(pieceModel[x][y]);
				}
			}
		}
	}

	private void addButtons() {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 7; x++) {
				piece[x][y] = new PieceView();
				piece[x][y].setSelected(false);
				add(piece[x][y]);
			}
		}
	}
}