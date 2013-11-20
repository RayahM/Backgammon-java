import greenfoot.Actor;
import greenfoot.Greenfoot;

/**
 * Classe Dé. Décrit le comportement des 2 dès du jeu.
 * 
 * @author (Emeric de Bernis)
 */
public class De extends Actor {
	
	private boolean used;
	private int value;
	private int Xcoordinate;
	private int Ycoordinate;
	
	public De(int X, int Y) {
		// Initialisation des attributs et chargement de l'image
		this.value = 6;
		this.setImage("images/d"+this.value+".png");
		this.Xcoordinate = X;
		this.Ycoordinate = Y;
		this.use();
	}
	
	public void act() {
	}
	
	// Lance le dé
	public void roll() {
		this.value = Greenfoot.getRandomNumber(6) + 1;
		this.used = false;
		this.setImage("images/d"+this.value+".png");
	}
	
	// Utilisation du dé : changement de l'image
	public void use() {
		this.used = true;
		this.setImage("images/d"+this.value+"use.png");
	}
	
	public int getValue() {
		return value;
	}

	public int getXcoordinate() {
		return Xcoordinate;
	}

	public int getYcoordinate() {
		return Ycoordinate;
	}

	public boolean isUsed() {
		return used;
	}
}
