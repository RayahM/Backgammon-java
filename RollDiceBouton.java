import greenfoot.Greenfoot;

/**
 * Classe RollDiceBouton. Bouton "Lancer les dés".
 * 
 * @author (Emeric de Bernis)
 */
public class RollDiceBouton extends Bouton {

	private boolean isClickable;
	
	// Initialise les attributs, charge l'image du bouton, crée le bouton.
	public RollDiceBouton(Backgammon world, int X, int Y) {
		super(world, X, Y);
		this.setImage("images/rolldice.png");
		this.isClickable = true;
	}
	
	public void act() {
		
		// Change l'image lors du clic sur le bouton
		if (Greenfoot.mousePressed(this) && this.isClickable) {
			this.setImage("images/rolldicemouseover.png");
		}
		
		// Lance les dés
		if (Greenfoot.mouseClicked(this) && this.isClickable) {
			this.setImage("images/rolldice.png");	
			this.world.getManager().rollTheDice();
		}
	}

	public boolean isClickable() {
		return isClickable;
	}

	public void setClickable(boolean isClickable) {
		this.isClickable = isClickable;
	}
}
