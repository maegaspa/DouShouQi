package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import view.*;
import controller.Controller;

public class PlateauModel {

	public PieceModel[][] plateau;
	private boolean couleur = true;
	private static BufferedImage img;
	public boolean player;
	private boolean win;
	private static PieceModel[] pieces = new PieceModel[16];
	public String nomMortRouge = "";
	public String nomMortBleu = "";

	private static final String[] PIECES_NOM = {"Rat_rouge", "Chat_rouge",
			"Loup_rouge", "Chien_rouge", "Leopard_rouge", "Lion_rouge", "Tigre_rouge",
			"Elephant_rouge", "Rat_bleu", "Chat_bleu", "Loup_bleu", "Chien_bleu",
			"Leopard_bleu", "Lion_bleu", "Tigre_bleu", "Elephant_bleu"};

	private static final int RAT_ROUGE = 0;
	private static final int CHAT_ROUGE = 1;
	private static final int LOUP_ROUGE = 2;
	private static final int CHIEN_ROUGE = 3;
	private static final int LEOPARD_ROUGE = 4;
	private static final int LION_ROUGE = 5;
	private static final int TIGRE_ROUGE = 6;
	private static final int ELEPHANT_ROUGE = 7;
	private static final int RAT_BLEU = 8;
	private static final int CHAT_BLEU = 9;
	private static final int LOUP_BLEU = 10;
	private static final int CHIEN_BLEU = 11;
	private static final int LEOPARD_BLEU = 12;
	private static final int LION_BLEU = 13;
	private static final int TIGRE_BLEU = 14;
	private static final int ELEPHANT_BLEU = 15;


	public PlateauModel () {
		plateau = new PieceModel[7][9];
		initializePieces();
		this.player = true;
		putPieceInStart();
	}

	private void initializePieces() {
		for (int i = 0; i < 16; i++) {
			try {
				img = ImageIO.read(new File("view/images/"
						+ PIECES_NOM[i] + ".png"));
			} catch (IOException e) {
				System.out.println("Internal Error:" + e.getMessage());
			}
			if (i > 7) {
				couleur = false;
			}
			pieces[i] = new PieceModel(img, couleur, PIECES_NOM[i], i);
		}
	}

	public PieceModel[][] getModel () {
		return plateau;
	}

	public boolean getTurn() {
		return player;
	}

	public boolean gameWon() {
		return this.win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public String getNomMortRouge() {
		return this.nomMortRouge;
	}

	public String getNomMortBleu() {
		return this.nomMortBleu;
	}

	public void setTurn(boolean turn) {
		this.player = turn;
	}

	public boolean movePiece (int curX, int curY, int nextX, int nextY) {
		PieceModel cur = plateau[curX][curY];
		// non clickable si l'emplacement est sans pièce
		if (cur == null) {
			return false;
		}

		// on ne peut pas click au même endroit de la pièce
		if (curX == nextX && curY == nextY) {
			return false;
		}

		String nomPiece = cur.getNomPiece();
		if (isLegal(nomPiece, curX, curY, nextX, nextY) == false) {
			return false;
		}

		// gestion capture si il y a bien une pièce a côté
		if (plateau[nextX][nextY] != null && !isCapture(cur, curX, curY, nextX, nextY)) {
			return false;
		}
		//gestion tanière
		if (isTaniereOpp(cur, nextX, nextY) == true){
			this.win = true;
		}

		// si plus de pièce en vie -> stop / win.
		for (int i = 0; i < pieces.length; i++) {
			if (cur.getCouleur() != pieces[i].getCouleur() && pieces[i].isAlive()) {
				break;
			}
			if (i == pieces.length - 1) this.win = true;
		}

		plateau[nextX][nextY] = plateau[curX][curY];
		plateau[curX][curY] = null;

		return true;
	}

	private boolean isTaniereOpp(PieceModel cur, int x, int y) {
		if (cur.getCouleur() == true) {
			if (x == 3 && y == 0) {
				return true;
			}
		}
		else {
			if (x == 3 && y == 8) {
				return true;
			}
		}
		return false;
	}

	private boolean isLegal(String nomPiece, int curX, int curY, int nextX, int nextY) {
		if (isInRiviere(nextX, nextY) == false) {
			if (nomPiece == "Lion_rouge" || nomPiece == "Lion_bleu" ||
					nomPiece == "Tigre_rouge" || nomPiece == "Tigre_bleu") {
				if (curY == 2) {
					if ((curX == 1 && nextX == 1 && nextY == 6 && plateau[1][3] == null && plateau[1][4] == null && plateau[1][5] == null) || (curX == 2 && nextX == 2 && nextY == 6 && plateau[2][3] == null && plateau[2][4] == null && plateau[2][5] == null) ||
							(curX == 4 && nextX == 4 && nextY == 6 && plateau[4][3] == null && plateau[4][4] == null && plateau[4][5] == null) || (curX == 5 && nextX == 5 && nextY == 6 && plateau[5][3] == null && plateau[5][4] == null && plateau[5][5] == null)) {
						return true;
					}
				}
				else if (curY == 6) {
					if ((curX == 1 && nextX == 1 && nextY == 2 && plateau[1][3] == null && plateau[1][4] == null && plateau[1][5] == null) || (curX == 2 && nextX == 2 && nextY == 2  && plateau[2][3] == null && plateau[2][4] == null && plateau[2][5] == null) ||
							(curX == 4 && nextX == 4 && nextY == 2 && plateau[4][3] == null && plateau[4][4] == null && plateau[4][5] == null) || (curX == 5 && nextX == 5 && nextY == 2 && plateau[5][3] == null && plateau[5][4] == null && plateau[5][5] == null)) {
						return true;
					}
				}
				else if (curX == 0) {
					if ((curY == 3 && nextY == 3 && nextX == 3 && plateau[1][3] == null && plateau[2][3] == null) || (curY == 4 && nextY == 4 && nextX == 3 && plateau[1][4] == null && plateau[2][4] == null) ||
							(curY == 5 && nextY == 5 && nextX == 3 && plateau[1][5] == null && plateau[2][5] == null)) {
						return true;
					}
				}
				else if (curX == 3) {
					if ((curY == 3 && nextY == 3 && nextX == 6 && plateau[4][3] == null && plateau[5][3] == null) || (curY == 4 && nextY == 4 && nextX == 6 && plateau[4][4] == null && plateau[5][4] == null) ||
							(curY == 5 && nextY == 5 && nextX == 6 && plateau[4][5] == null && plateau[5][5] == null) || (curY == 3 && nextY == 3 && nextX == 0 && plateau[1][3] == null && plateau[2][3] == null) ||
							(curY == 4 && nextY == 4 && nextX == 0 && plateau[1][4] == null && plateau[2][4] == null) || (curY == 5 && nextY == 5 && nextX == 0 && plateau[1][5] == null && plateau[2][5] == null)) {
						return true;
					}
				}
				else if (curX == 6) {
					if ((curY == 3 && nextY == 3 && nextX == 3 && plateau[4][3] == null && plateau[5][3] == null) || (curY == 4 && nextY == 4 && nextX == 3 && plateau[4][4] == null && plateau[5][4] == null) ||
							(curY == 5 && nextY == 5 && nextX == 3 && plateau[4][5] == null && plateau[5][5] == null)) {
						return true;
					}
				}
			}
			if ((curX - nextX == 1 && curY == nextY) || (curX == nextX && curY - nextY == 1) ||
					(curX - nextX == -1 && curY == nextY) || (curX == nextX && curY - nextY == -1)) {
				return true;
			}
		}
		else if (isInRiviere(nextX, nextY) == true) {
			if (nomPiece == "Rat_rouge" || nomPiece == "Rat_bleu") {
				if ((curX - nextX == 1 && curY == nextY) || (curX == nextX && curY - nextY == 1) ||
						(curX - nextX == -1 && curY == nextY) || (curX == nextX && curY - nextY == -1)) {
					return true;
				}
			}
		}
		return false;
	}

	private String getNomWithoutUnderscore(String oldName) {
		String newName = "";
		int posUnderscore = oldName.indexOf("_");
		newName = oldName.substring(0, posUnderscore);
		return newName;
	}

	private boolean isCapture(PieceModel cur, int curX, int curY, int nextX, int nextY) {
		PieceModel clickedPiece = plateau[nextX][nextY];

		int rank1 = cur.getRank();
		int rank2 = clickedPiece.getRank();
		if (isInPiege(cur, nextX, nextY) == false) {
			if (rankCheck(rank1, rank2) == true) {
				if (cur.getCouleur() == true) {
					this.nomMortBleu = this.nomMortBleu + " " + getNomWithoutUnderscore(clickedPiece.getNomPiece());
					clickedPiece.capture();
				}
				else {
					this.nomMortRouge = this.nomMortRouge + " " + getNomWithoutUnderscore(clickedPiece.getNomPiece());
					clickedPiece.capture();
				}
				return true;
			}
		}
		else if (isInPiege(cur, nextX, nextY) == true) {
			if (cur.getCouleur() == true) {
				this.nomMortBleu = this.nomMortBleu + " " + getNomWithoutUnderscore(clickedPiece.getNomPiece());
				clickedPiece.capture();
			}
			else {
				this.nomMortRouge = this.nomMortRouge + " " + getNomWithoutUnderscore(clickedPiece.getNomPiece());
				clickedPiece.capture();
			}
			return true;
		}
		return false;
	}

	private boolean rankCheck(int rank, int rank2) {
		if (rank > 7) {
			rank -= 8;
		}
		else if (rank2 > 7) { //rang de 0 a 15, equipe rouge = [0-7] / bleu = [8-15]
			rank2 -= 8;
		}
		if (rank >= rank2) {
			return true;
		}
		if (rank == 0 && rank2 == 7) { // rat vs elephant
			return true;
		}
		return false;
	}

	private boolean isInRiviere(int x, int y) {
		if (y > 2 && y < 6) {
			if ((x > 0 && x < 3) || (x > 3 && x < 6)) {
				return true;
			}
		}
		return false;
	}

	private boolean isInPiege(PieceModel cur, int x, int y) {
		if (cur.getCouleur() == false) { // couleur = bleu -> peut capturer n'importe rouge si une piece dans piege
			if ((y == 0 && x == 4) || (y == 1 && x == 3) || (y == 0 && x == 2)) {
				return true;
			}
		}
		else if (cur.getCouleur() == true) { // couleur = rouge -> peut capturer n'importe bleu si une piece dans piege
			if ((y == 8 && x == 4) || (y == 7 && x == 3) || (y == 8 && x == 2)) {
				return true;
			}
		}
		return false;
	}

	public void putPieceInStart() {
		for (int k = 0; k < plateau.length; k++) {
			for (int l = 0; l < plateau[0].length; l++) {
				plateau[k][l] = null;
			}
		}
		plateau[6][6] = pieces[RAT_ROUGE];
		pieces[RAT_ROUGE].setPosx(6);
		pieces[RAT_ROUGE].setPosy(6);

		plateau[1][7] = pieces[CHAT_ROUGE];
		pieces[CHAT_ROUGE].setPosx(1);
		pieces[CHAT_ROUGE].setPosy(7);

		plateau[2][6] = pieces[LOUP_ROUGE];
		pieces[LOUP_ROUGE].setPosx(2);
		pieces[LOUP_ROUGE].setPosy(6);

		plateau[5][7] = pieces[CHIEN_ROUGE];
		pieces[CHIEN_ROUGE].setPosx(5);
		pieces[CHIEN_ROUGE].setPosy(7);

		plateau[4][6] = pieces[LEOPARD_ROUGE];
		pieces[LEOPARD_ROUGE].setPosx(4);
		pieces[LEOPARD_ROUGE].setPosy(6);

		plateau[6][8] = pieces[LION_ROUGE];
		pieces[LION_ROUGE].setPosx(6);
		pieces[LION_ROUGE].setPosy(8);

		plateau[0][8] = pieces[TIGRE_ROUGE];
		pieces[TIGRE_ROUGE].setPosx(0);
		pieces[TIGRE_ROUGE].setPosy(8);

		plateau[0][6] = pieces[ELEPHANT_ROUGE];
		pieces[ELEPHANT_ROUGE].setPosx(0);
		pieces[ELEPHANT_ROUGE].setPosy(6);

		plateau[0][2] = pieces[RAT_BLEU];
		pieces[RAT_BLEU].setPosx(0);
		pieces[RAT_BLEU].setPosy(2);

		plateau[5][1] = pieces[CHAT_BLEU];
		pieces[CHAT_BLEU].setPosx(5);
		pieces[CHAT_BLEU].setPosy(1);

		plateau[4][2] = pieces[LOUP_BLEU];
		pieces[LOUP_BLEU].setPosx(4);
		pieces[LOUP_BLEU].setPosy(2);

		plateau[1][1] = pieces[CHIEN_BLEU];
		pieces[CHIEN_BLEU].setPosx(1);
		pieces[CHIEN_BLEU].setPosy(1);

		plateau[2][2] = pieces[LEOPARD_BLEU];
		pieces[LEOPARD_BLEU].setPosx(2);
		pieces[LEOPARD_BLEU].setPosy(2);

		plateau[0][0] = pieces[LION_BLEU];
		pieces[LION_BLEU].setPosx(0);
		pieces[LION_BLEU].setPosy(0);

		plateau[6][0] = pieces[TIGRE_BLEU];
		pieces[TIGRE_BLEU].setPosx(6);
		pieces[TIGRE_BLEU].setPosy(0);

		plateau[6][2] = pieces[ELEPHANT_BLEU];
		pieces[ELEPHANT_BLEU].setPosx(6);
		pieces[ELEPHANT_BLEU].setPosy(2);

		for (int i = 0; i < pieces.length; i++) {
			pieces[i].revive();
		}
	}
}