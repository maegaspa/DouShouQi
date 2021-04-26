package model;

import java.awt.image.BufferedImage;
import view.ViewGestion;
import controller.Controller;

public class PieceModel {

	private boolean couleur;
	private int rank;
	private int posx;
	private int posy;
	private boolean alive;
	final private BufferedImage img;
	private String PieceNom;

	public PieceModel (BufferedImage img, boolean couleur, String PieceNom, int rank) {
		this.couleur = couleur;
		this.img = img;
		this.PieceNom = PieceNom;
		this.alive = true;
		this.rank = rank;
	}

	public String getNomPiece() {
		return PieceNom;
	}

	public int getRank() {
		return rank;
	}

	public void revive() {
		this.alive = true;
	}

	public BufferedImage getImage () {
		return img;
	}

	public boolean getCouleur() {
		return couleur;
	}

	public boolean isAlive() {
		return alive;
	}

	public void capture() {
		this.alive = false;
	}

	public int getPosx() {
		return posx;
	}

	public int getPosy() {
		return posy;
	}

	public void setPosx(int x){
		this.posx = x;
	}

	public void setPosy(int y){
		this.posy = y;
	}
}