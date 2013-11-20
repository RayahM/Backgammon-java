/**
 * Classe PionBlanc. Hérite de la classe pion, spécifie le cas d'un pion blanc.
 * 
 * @author (Emeric de Bernis)
 */
public class PionBlanc extends Pion {

	public PionBlanc(Backgammon world, Colonne col) {		
		super(world, col);
		this.setImage("images/pionblanc.png");
	}

	public String getColor() {
		return "blanc";
	}

	public void isReadyToLeave() {
		if (this.getCol().getId()>18) setReadyToLeave(true);
	}
	
	public boolean onTheWayOut() {
		return this.X > 745 && this.X < 770 && this.Y > 10 && this.Y < 295;
	}

	public boolean areColumnsBeforeEmpty() {
		boolean cond = true;
		for (int i = 19; i<this.getCol().getId(); i++) {
			cond = cond && (this.world.getColumns().get(i).isEmpty() || (this.world.getColumns().get(i).getLast().getColor() != this.getColor()));
		}
		return cond;
	}
	
}
