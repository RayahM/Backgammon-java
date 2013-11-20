import greenfoot.Actor;

/**
 * Classe Texte. Crée une zone de texte dans le jeu.
 * 
 * @author (Emeric de Bernis)
 */
public class Texte extends Actor{

	private String value;
	private int Xcoordinate;
	private int Ycoordinate;
	
	// Initialise les attributs
	public Texte(String text, int X, int Y) {
		this.Xcoordinate = X;
		this.Ycoordinate = Y;
		ecrire(text);
	}
	
	// Charge l'image correspondante au texte choisi
	public void ecrire(String text) {
		this.value = text;
		this.setImage("images/"+this.value+".png");
	}

	public int getXcoordinate() {
		return Xcoordinate;
	}

	public int getYcoordinate() {
		return Ycoordinate;
	}
}
