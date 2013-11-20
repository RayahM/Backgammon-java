import javax.swing.JOptionPane;

import greenfoot.Greenfoot;

/**
 * Classe SaveBouton. Bouton permettant de sauvegarder une partie.
 * 
 * @author (Emeric de Bernis)
 */
public class SaveBouton extends Bouton {
	
	// Initialise les attributs, charge l'image du bouton, crée le bouton.
	public SaveBouton(Backgammon world, int X, int Y) {
		super(world, X, Y);
		this.setImage("images/save.png");
	}
	
	public void act() {
		// Sauve la partie en cours
		if (Greenfoot.mouseClicked(this)) {
			if (this.world.getManager().getMoves_remaining().isEmpty()) new Saver(this.world).saveGame();
			else JOptionPane.showMessageDialog(null, "Finissez votre tour pour pouvoir sauvegarder la partie.", "Sauvegarde", JOptionPane.ERROR_MESSAGE);
		}
	}
}