import greenfoot.Greenfoot;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;


/**
 * Classe GameManager. Gère le déroulement de la partie.
 * 
 * @author (Emeric de Bernis)
 */
public class GameManager {

	private Backgammon world;
	private String turn;
	private ArrayList<Integer> moves_remaining;
	private boolean whiteCanLeave;
	private boolean blackCanLeave;
	private int numberOfWhiteOut;
	private int numberOfBlackOut;
	
	// Initialise les attributs
	public GameManager(Backgammon world) {
		this.world = world;
		this.turn = "blanc";
		this.moves_remaining = new ArrayList<Integer>();
		this.whiteCanLeave = false;
		this.blackCanLeave = false;
		this.numberOfBlackOut = 0;
		this.numberOfWhiteOut = 0;
	}
	
	// Teste si le coup est autorisé suivant la valeur des dés et les pions dans la colonne visée
	public boolean isAllowedToMove(Pion pion, Colonne aimed_col) {
		// Le coup doit respecter le sens du jeu
		int diff;
		if (pion.getColor() == "noir") diff = pion.getCol().getId() - aimed_col.getId();
		else diff = aimed_col.getId() - pion.getCol().getId();
		
		if (this.moves_remaining.contains(diff) && (aimed_col.getcurrentRank() < 2 || aimed_col.getLast().getColor() == pion.getColor())) return true;
		else return false;
	}
	
	public boolean isAllowedToMoveOut(Pion pion, Colonne aimed_col) {
		// Le coup doit respecter le sens du jeu
		int diff;
		if (pion.getColor() == "noir") diff = pion.getCol().getId() - aimed_col.getId();
		else diff = aimed_col.getId() - pion.getCol().getId();
			
		if (this.moves_remaining.contains(diff) || (pion.areColumnsBeforeEmpty() && getMaxOfMovesRemaining()>=diff)) return true;
		else return false;
	}
	
	// Execute le coup
	public void performMove(int move) {
		// Retire la valeur jouée des possiblités
		this.moves_remaining.remove(this.moves_remaining.indexOf(move));
		if (!this.moves_remaining.contains(move) || (this.moves_remaining.size() % 2 == 0)) {
			if (this.world.getDice().get(0).getValue() == move && !this.world.getDice().get(0).isUsed()) this.world.getDice().get(0).use();
			else this.world.getDice().get(1).use();
		}
	}
	
	// Change le tour de jeu et vérifie si le joueur peut commencer à sortir les pions du jeu
	public void changeTurn() {
		// Si tous les dés ont été joué, on change de tour
		if (this.moves_remaining.isEmpty()) {
			if (!getCanLeaveBoolean(this.turn)) canLeave(this.turn);
			if (this.turn == "blanc") this.turn = "noir";
			else this.turn = "blanc";
			this.world.getText().ecrire(this.turn+"_turn_text");
			this.world.getDice().get(0).use();
			this.world.getDice().get(1).use();
			this.world.getBouton().setClickable(true);
		}
	}
	
	// Règle l'attribut colorCanLeave : à savoir tous les pions de la couleur color sont-ils dans leur propre jan intérieur ?
	public void canLeave(String color) {
		Iterator<Pion> it;
		if (color == "blanc") it = this.world.getPionsBlancs().iterator();
		else it = this.world.getPionsNoirs().iterator();
		boolean cond = true;
		while (cond && it.hasNext()) {
			cond = cond && it.next().getReadyToLeave();
		}
		if (color == "blanc") this.whiteCanLeave = cond;
		else this.blackCanLeave = cond;
	}
	
	public boolean getCanLeaveBoolean(String color) {
		if (color == "blanc") return this.whiteCanLeave;
		else return this.blackCanLeave;
	}

	// Renvoie true si c'est à la couleur du pion de jouer
	public boolean isYourTurn(Pion pion) {
		return this.turn == pion.getColor();
	}
	
	public void rollTheDice() {
		De d1 = this.world.getDice().get(0);
		De d2 = this.world.getDice().get(1);
		d1.roll(); d2.roll();
		if (d1.getValue() == d2.getValue()) {
			this.moves_remaining.add(d1.getValue());
			this.moves_remaining.add(d1.getValue());
			this.moves_remaining.add(d2.getValue());
			this.moves_remaining.add(d2.getValue());
		}
		else {
			this.moves_remaining.add(d1.getValue());
			this.moves_remaining.add(d2.getValue());
		}
		this.world.getBouton().setClickable(false);
	}
	
	public void pass() {
		this.world.getManager().getMoves_remaining().clear();
		this.world.getManager().changeTurn();
	}

	public String getTurn() {
		return turn;
	}

	public ArrayList<Integer> getMoves_remaining() {
		return moves_remaining;
	}
	
	public int getMaxOfMovesRemaining() {
		int max = 1;
		for (int i=0; i<this.moves_remaining.size(); i++) max = Math.max(max, this.moves_remaining.get(i));
		return max;
	}

	// Retourne le nombre de pions sortis de la couleur passée en argument
	public int getNumberOut(String color) {
		if (color == "blanc") return this.numberOfWhiteOut;
		else return this.numberOfBlackOut;
	}
	
	// Incrémente le compteur de nombre de pions sortis de la couleur passée en argument
	public void incrementsNumberOut(String color) {
		if (color == "blanc") this.numberOfWhiteOut++;
		else this.numberOfBlackOut++;
		
		if (this.numberOfWhiteOut == 15) win("blanc");
		else if(this.numberOfBlackOut == 15) win("noir");
	}
	
	// Termine le jeu et affiche le gagnant !
	public void win(String color) {
		this.world.getBouton().setClickable(false);
		JOptionPane.showMessageDialog(null, "Les "+color+"s ont gagné !", "Victoire !", JOptionPane.INFORMATION_MESSAGE);
		Greenfoot.stop();
	}
	
	public int[] getInts() {
		int tab[] = {this.numberOfBlackOut, this.numberOfWhiteOut};
		return tab;
	}
	
	public boolean[] getBools() {
		boolean tab[] = {this.blackCanLeave, this.whiteCanLeave};
		return tab;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

	public void setWhiteCanLeave(boolean whiteCanLeave) {
		this.whiteCanLeave = whiteCanLeave;
	}

	public void setBlackCanLeave(boolean blackCanLeave) {
		this.blackCanLeave = blackCanLeave;
	}

	public void setNumberOfWhiteOut(int numberOfWhiteOut) {
		this.numberOfWhiteOut = numberOfWhiteOut;
	}

	public void setNumberOfBlackOut(int numberOfBlackOut) {
		this.numberOfBlackOut = numberOfBlackOut;
	}

	public void setMoves_remaining(ArrayList<Integer> moves_remaining) {
		this.moves_remaining = moves_remaining;
	}
}
