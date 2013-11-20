import java.util.ArrayList;

import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Classe Pion. Décrit le comportement d'un pion du plateau
 * 
 * @author (Emeric de Bernis)
 * 
 */
public abstract class Pion extends Actor {
	
	protected Backgammon world;
	private Colonne col;
	private Colonne old_col;
	protected int X;
	protected int Y;
	private int Xstart;
	private int Ystart;
	private int col_rank;
	private int graphic_col_rank;
	private boolean movable;
	private boolean isInPrison;
	protected boolean isReadyToLeave;
	private int X_TOLERANCE = 10;
	
	// Constructeur, on ajoute le pion au monde et à une colonne
	public Pion(Backgammon world, Colonne col) {
		// Initialisation des attributs
		this.world = world;
		this.col = col;
		this.old_col = col;
		this.X = 0;
		this.Y = 0;
		this.Xstart = this.X;
		this.Ystart = this.Y;
		this.col.add(this);
		this.movable = false;
		this.isInPrison = false;
		this.isReadyToLeave = false;
	}
	
	public void act() {
		// Permet le glissé d'un pion
		if (Greenfoot.mouseDragged(this) && canBeDragged()) mouseDragged();
		
		// Comportement du pion lors du déposer
		if (Greenfoot.mouseDragEnded(this)) mouseDragEnded();
		
		// Mise en mémoire de la position initiale
		if (Greenfoot.mousePressed(this)) mousePressed();
	}
	
	// Renvoie true si le pion se trouve sur le plateau verticalement parlant
	public boolean isInTheGameVertically() {
		return (this.Y>24 && this.Y<266) || (this.Y>339 && this.Y<581);
	}
	
	// Renvoie true si le pion se situe dans la partie haute du plateau
	public boolean isUpside() {
		return this.Y<300;
	}
	
	public boolean canBeDragged() {
		return this.movable && this.world.getManager().isYourTurn(this) && (this.world.getPrisons().get(this.world.getManager().getTurn()).isEmpty() || this.isInPrison);
	}
	
	// Renvoie l'id de la colonne sur laquelle se trouve le pion ou 0 sinon
	public int calculateColumn() {
		int i=0;
		boolean cond = false;
		while (!cond && i<13) {
			i++;
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int j=-this.X_TOLERANCE; j<(this.X_TOLERANCE+1); j++) {
				list.add(this.world.getColumns().get(i).getXcoordinate()+j);
			}
			cond = cond || list.contains(this.X);
		}
		if (cond && isInTheGameVertically()) {
			if (isUpside()) return 25-i;
			else return i;
		}
		else return 0;
	}
	
	// Le pion a-t-il été glissé au dessus de sa sortie ?
	public abstract boolean onTheWayOut(); 
	
	// Place un pion en prison
	public void putOnePionInPrison() {
		Pion pion_en_prison = this.col.getLast();
		Colonne prison = this.world.getPrisons().get(pion_en_prison.getColor());
		this.col.remove(pion_en_prison);
		if (!prison.isEmpty()) prison.getLast().setMovable(false);
		prison.add(pion_en_prison);
		this.world.putActorToTheTop(pion_en_prison);
		pion_en_prison.setLocation(prison.getXcoordinate(), prison.getYcoordinates().get(pion_en_prison.getGraphic_col_rank()));
		pion_en_prison.setInPrison(true);
		pion_en_prison.setMovable(true);
		pion_en_prison.setCol(prison);
		pion_en_prison.setOld_col(this.col);
	}
	
	// Sors un pion du jeu et le place dans la colonne sortie correspondante
	public void putOnePionOut() {
		this.col.remove(this);
		if (!this.col.isEmpty()) this.col.getLast().setMovable(true);
		Colonne sortie = this.world.getSorties().get(this.getColor());
		sortie.add(this);
		this.world.getManager().incrementsNumberOut(this.getColor());
		this.setLocation(sortie.getXcoordinate(), sortie.getYcoordinates().get(this.world.getManager().getNumberOut(this.getColor())));
		int diff = Math.abs(this.col.getId() - sortie.getId());
		
		// On peut être éventuellement dans le cas où il n'y a plus de pions sur les colonnes précédentes et une valeur de dé supérieure à diff
		if (this.world.getManager().getMoves_remaining().contains(diff)) this.world.getManager().performMove(diff);
		else this.world.getManager().performMove(this.world.getManager().getMaxOfMovesRemaining());
		
		// On change éventuellement de tour de jeu
		this.world.getManager().changeTurn();
	}
	
	public void mousePressed() {
		this.Xstart = this.getX();
		this.Ystart = this.getY();
		// Place le pion au premier plan
		this.world.putActorToTheTop(this);
	}
	
	public void mouseDragged() {
		this.X = Greenfoot.getMouseInfo().getX();
		this.Y = Greenfoot.getMouseInfo().getY();
		this.setLocation(this.X, this.Y);
	}
	
	public void mouseDragEnded() {
		this.X = this.getX();
		this.Y = this.getY();
		
		// Teste si le joueur tente de sortir un pion du jeu
		if (this.world.getManager().getCanLeaveBoolean(this.getColor()) && this.onTheWayOut() && this.world.getManager().isAllowedToMoveOut(this, this.world.getSorties().get(this.getColor()))) putOnePionOut();
		else {
			int col_number = calculateColumn();
			// Si le pion ne se trouve pas au-dessus d'une colonne valide, on le remet à sa position initiale
			if (col_number == 0 || !this.world.getManager().isAllowedToMove(this, this.world.getColumns().get(col_number))) this.setLocation(this.Xstart, this.Ystart);
			else {	
				this.old_col = this.col;	
				this.col = this.world.getColumns().get(col_number);
					
				this.world.getManager().performMove(Math.abs(this.col.getId()-this.old_col.getId()));
						
				// Cas où le pion était en prison
				if (this.isInPrison) {
					Colonne prison = this.world.getPrisons().get(this.getColor());
					prison.remove(this);
					if (!prison.isEmpty()) prison.getLast().setMovable(true);
					this.isInPrison = false;
				}
				else {
					this.old_col.remove(this);
					if (!this.old_col.isEmpty()) this.old_col.getLast().setMovable(true);
				}
					
				// Cas de la mise en prison d'un pion
				if (this.col.getcurrentRank() == 1 && this.col.getLast().getColor() != this.getColor()) putOnePionInPrison();
				
				// On ajoute le pion à sa nouvelle colonne et on modifie les movable
				if (!this.col.isEmpty()) this.col.getLast().setMovable(false);
				this.col.add(this);
				
				// On modifie le booleen isReadyToLeave si nécessaire
				isReadyToLeave();
				
				// On place le pion sur la colonne
				this.setLocation(this.col.getXcoordinate(), this.col.getYcoordinates().get(this.graphic_col_rank));
				this.movable = true;
				
				// On change éventuellement de tour de jeu
				this.world.getManager().changeTurn();
			}
		}
	}
	
	public void putInPrison() {
		this.isInPrison = true;
		this.world.getPrisons().get(this.getColor()).add(this);
	}
	
	public abstract String getColor();
	
	public abstract void isReadyToLeave();
	
	// Dans le cas où tous les pions de la couleur sont dans leur propre jan intérieur
	public abstract boolean areColumnsBeforeEmpty();

	public int getCol_rank() {
		return col_rank;
	}

	public void setCol_rank(int col_rank) {
		this.col_rank = col_rank;
	}

	public boolean isMovable() {
		return movable;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}

	public int getGraphic_col_rank() {
		return graphic_col_rank;
	}

	public void setGraphic_col_rank(int graphic_col_rank) {
		this.graphic_col_rank = graphic_col_rank;
	}

	public Colonne getCol() {
		return col;
	}

	public void setReadyToLeave(boolean isReadyToLeave) {
		this.isReadyToLeave = isReadyToLeave;
	}
	
	public boolean getReadyToLeave() {
		return this.isReadyToLeave;
	}

	public void setInPrison(boolean isInPrison) {
		this.isInPrison = isInPrison;
	}

	public Colonne getOld_col() {
		return old_col;
	}

	public void setOld_col(Colonne old_col) {
		this.old_col = old_col;
	}

	public void setCol(Colonne col) {
		this.col = col;
	}
	
	public int[] getInts() {
		int tab[] = {this.col.getId(), this.old_col.getId()};
		return tab;
	}
	
	public boolean[] getBools() {
		boolean tab[] = {this.movable, this.isInPrison, this.isReadyToLeave};
		return tab;
	}
}
