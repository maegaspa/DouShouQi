package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.*;
import view.*;

//IMPORT DE TOUTES LES STRUCTURES CONTENANT LES INFOS DE PIECE / PLATEAU / CLICK

public class Controller {
//	UN MODE SELECT
	//VERIF SI LE CLICK EST SUR UNE POS DE PIECE
	//SI OUI BORDER DEVIENS BRILLANT SEULEMENT SI C EST LE TOUR DE LA COULEUR
	//

//	ET UN MODE STEP
	//VERIF SI LA CASE EST VIDE ET SI LE MOVE EST LEGAL
	//REMOVE ANCIEN BOUTON ET PUT LE NEW SI C GOOD AVEC LES BONNES COORD SAVE

	private static PlateauModel plateauModel;
	private static PlateauView plateauView;
	private static PieceView[][] pieces;
	private static PieceModel[][] pieceModel;
	private static Mode mode;
	private static JFrame winViewRed;
	private static JFrame winViewBlue;
	private static JLabel scoreView;
	private static boolean win = false;
	private static String nomMortRouge = " ";
	private static String nomMortBleu = " ";


	enum Mode {
		SELECT, STEP
	}

	private static PieceModel selected;

	public Controller (PlateauView plateauView, JFrame winViewRed, JFrame winViewBlue, JLabel scoreView) {
		Controller.plateauView = plateauView;
		Controller.plateauModel = plateauView.getPlateauModel();
		Controller.pieces = Controller.plateauView.getButtons();
		Controller.pieceModel = plateauModel.getModel();
		Controller.mode = Mode.SELECT;
		Controller.winViewRed = winViewRed;
		Controller.winViewBlue = winViewBlue;
		Controller.scoreView = scoreView;
		addEventListeners();
	}

	private static void addEventListeners () {
		for (int y = 0; y < pieces[0].length; y++){
			for (int x = 0; x < pieces.length; x++) {
				pieces[x][y].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (win == false) {
							processSelection(e);
						}
					}
				});
			}
		}
	}

	private static void processSelection(ActionEvent e) {
		int x = -1;
		int y = -1;
		// find out which square was selected
		Object source = e.getSource();
		for (int j = 0; j < pieces[0].length; j++){
			for (int i = 0; i < pieces.length; i++) {
				if (pieces[i][j] == source) {
					x = i;
					y = j;
				}
			}
		}
		if (x != -1 && y != -1) {
			switch (mode) {
				case SELECT:
					selected = pieceModel[x][y];
					if (selected == null) {
						return;
					}
					// returns if piece does not belong to current player
					if (selected.getCouleur() != plateauModel.getTurn()) {
						return;
					}
					mode = Mode.STEP;
					updateSelection(x, y);
					break;
				case STEP:
					// change selected piece if on same team
					if ((pieceModel[x][y] != null) && (pieceModel[x][y].getCouleur() == plateauModel.getTurn())) {
						// de-select all other buttons and highlight this square
						updateSelection(x, y);
						// update selected
						selected = pieceModel[x][y];
						break;
					}
					int origX = selected.getPosx();
					int origY = selected.getPosy();
					if (plateauModel.movePiece (origX, origY, x, y)) {
						nomMortRouge = plateauModel.getNomMortRouge();
						nomMortBleu = plateauModel.getNomMortBleu();
						pieces[origX][origY].removePiece();
						selected.setPosx(x);
						selected.setPosy(y);
						mode = Mode.SELECT;
						pieces[x][y].setSelected(true);
						selected = null;
						pieces[origX][origY].setSelected(false);
						pieces[origX][origY].setBorderPainted(false);
						if (plateauModel.gameWon()) {
							win = true;
							break;
						}
						plateauModel.setTurn(!plateauModel.getTurn());
					}
					break;
			}
		}
		updateScore();
		plateauView.repaint();
		if (win == true) {
			if (plateauModel.getTurn() == true) {
				winViewRed.setVisible(true);
			}
			else {
				winViewBlue.setVisible(true);
			}
			updateScore();
		}
	}

	private static void updateScore() {
		String score = "Rouge capturé = " + nomMortRouge + "    |   Bleu capturé =  " + nomMortBleu;
		scoreView.setText(score);
	}

	public void reset() {
		// reset mode to select and de-select all selected squares
		mode = Mode.SELECT;
		updateSelection(-1, -1);
		plateauModel.setTurn(true);
		plateauModel.setWin(false);
		win = false;
		plateauModel.putPieceInStart();
		for (int j = 0; j < pieces[0].length; j++){
			for (int i = 0; i < pieces.length; i++) {
				pieces[i][j].removePiece();
			}
		}
		// remove win display if it's visible
		winViewRed.setVisible(false);
		winViewBlue.setVisible(false);
		plateauView.repaint();
	}

	private static void updateSelection(int x, int y) {
		// de-select all squares
		for (int j = 0; j < pieces[0].length; j++){
			for (int i = 0; i < pieces.length; i++) {
				pieces[i][j].setSelected(false);
				pieces[i][j].setBorderPainted(false);
			}
		}
		// highlight this square if needed
		if (x != -1 && y != -1) {
			pieces[x][y].setSelected(true);
		}
	}
}