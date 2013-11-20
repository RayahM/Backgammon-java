import greenfoot.Greenfoot;

/**
 * Classe PassBouton. Bouton permettant de passer son tour.
 * 
 * @author (Emeric de Bernis)
 */
public class PassBouton extends Bouton {
	
	// Initialise les attributs, charge l'image du bouton, crée le bouton.
	public PassBouton(Backgammon world, int X, int Y) {
		super(world, X, Y);
		this.setImage("images/pass.png");
	}
	
	public void act() {
		
		// Change l'image lors du clic sur le bouton
		if (Greenfoot.mousePressed(this)) {
			this.setImage("images/passmouseover.png");
		}
		
		// Lance les dés
		if (Greenfoot.mouseClicked(this)) {
			this.setImage("images/pass.png");	
			this.world.getManager().pass();
		}
	}
}
