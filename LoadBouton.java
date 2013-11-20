import java.util.ArrayList;

import greenfoot.Greenfoot;

/**
 * Classe LoadBouton. Bouton de chargement d'une partie.
 * 
 * @author (Emeric de Bernis)
 */
public class LoadBouton extends Bouton {
	
	// Initialise les attributs, charge l'image du bouton, crée le bouton.
	public LoadBouton(Backgammon world, int X, int Y) {
		super(world, X, Y);
		this.setImage("images/load.png");
	}
	
	public void act() {
		
		// Charge une partie sauvegardée
		if (Greenfoot.mouseClicked(this)) {
			new Loader(this.world).loadGame();
			this.world.getManager().setMoves_remaining(new ArrayList<Integer>());
			this.world.getRolldiceBouton().setClickable(true);
			for (De de : this.world.getDice()) de.use();
		}
	}
}