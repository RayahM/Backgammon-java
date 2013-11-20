import greenfoot.Actor;

/**
 * Classe Bouton. Définit un bouton dans le monde Backgammon.
 * 
 * @author (Emeric de Bernis)
 */
public abstract class Bouton extends Actor{

	protected Backgammon world;
	protected int Xcoordinate;
	protected int Ycoordinate;
	
	// Initialise les attributs, charge l'image du bouton, crée le bouton.
	public Bouton(Backgammon world, int X, int Y) {
		this.world = world;
		this.Xcoordinate = X;
		this.Ycoordinate = Y;
	}
	
	public abstract void act();
	
	public int getXcoordinate() {
		return Xcoordinate;
	}

	public int getYcoordinate() {
		return Ycoordinate;
	}
}
