/**
 * Classe PionBlanc. Hérite de la classe pion, spécifie le cas d'un pion blanc.
 * 
 * @author (Emeric de Bernis)
 */
public class PionNoir extends Pion {

	public PionNoir(Backgammon world, Colonne col) {		
		super(world, col);
		this.setImage("images/pionnoir.png");
	}

	public String getColor() {
		return "noir";
	}

	public void isReadyToLeave() {
		if (this.getCol().getId()<7) setReadyToLeave(true);	
	}
	
	public boolean onTheWayOut() {
		return this.X > 745 && this.X < 770 && this.Y > 310 && this.Y < 600;
	}
	
	public boolean areColumnsBeforeEmpty() {
		boolean cond = true;
		for (int i = this.getCol().getId()+1; i<7; i++) {
			cond = cond && (this.world.getColumns().get(i).isEmpty() || (this.world.getColumns().get(i).getLast().getColor() != this.getColor()));
		}
		return cond;
	}
}
