import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Classe Colonne. Colonne du plateau de jeu de Backgammon
 * 
 * @author (Emeric de Bernis)
 */
public class Colonne {

	private int id;
	private int Xcoordinate;
	private HashMap<Integer, Integer> Ycoordinates;
	private ArrayList<Pion> pion_list;
	private int currentRank;
	
	// Initialise les attributs sauf Xcoordinate qui est initialisé par l'objet Backgammon lors de sa construction
	public Colonne (int id) {
		this.id = id;
		this.pion_list = new ArrayList<Pion>();
		this.currentRank = 0;
		this.Ycoordinates = new HashMap<Integer, Integer>();
	}
	
	public HashMap<Integer, Integer> getYcoordinates() {
		return Ycoordinates;
	}

	public void setYcoordinates(HashMap<Integer, Integer> ycoordinates) {
		Ycoordinates = ycoordinates;
	}

	// Ajoute un pion à la colonne
	public void add(Pion pion) {
		this.pion_list.add(pion);
		this.currentRank++;
		pion.setCol_rank(this.currentRank);
		pion.setGraphic_col_rank((this.currentRank - 1) % 5 + 1);
	}
	
	// Retire un pion de la colonne
	public void remove(Pion pion) {
		this.pion_list.remove(pion);
		this.currentRank--;
	}

	// Retourne le nombre de pions de la colonne
	public int count() {
		return this.pion_list.size();
	}
	
	// Renvoie true si le rang donné en argument est le dernier de la colonne
	public boolean isLast(int col_rank) {
		return col_rank == this.currentRank;
	}
	
	// Renvoie le dernier pion de la colonne
	public Pion getLast() {
		if (!isEmpty()) {
			return this.pion_list.get(this.currentRank-1);
		}
		return null;
	}
	
	// Renvoie true si la colonne est vide
	public boolean isEmpty() {
		return this.currentRank == 0;
	}
	
	public Iterator<Pion> getIterator() {
		return this.pion_list.iterator();
	}

	public int getcurrentRank() {
		return this.currentRank;
	}

	public int getId() {
		return id;
	}

	public int getXcoordinate() {
		return Xcoordinate;
	}

	public void setXcoordinate(int xcoordinate) {
		this.Xcoordinate = xcoordinate;
	}

	public ArrayList<Pion> getPion_list() {
		return pion_list;
	}

	public void setPion_list(ArrayList<Pion> pion_list) {
		this.pion_list = pion_list;
	}

	public void setCurrentRank(int currentRank) {
		this.currentRank = currentRank;
	}
}
