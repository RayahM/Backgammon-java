import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Classe Backgammon. Initialise le jeu.
 * 
 * @author (Emeric de Bernis)
 */
public class Backgammon extends World {
	
	private HashMap<Integer, Colonne> columns;
	private ArrayList<Pion> pionsBlancs;
	private ArrayList<Pion> pionsNoirs;
	private GameManager manager;
	private ArrayList<De> dice;
	private HashMap<String, Colonne> prisons;
	private HashMap<String, Colonne> sorties;
	private RollDiceBouton rolldiceBouton;
	private PassBouton passBouton;
	private SaveBouton saveBouton;
	private LoadBouton loadBouton;
	private Texte text;

	// Crée le monde et instancie le GameManager
	public Backgammon() {
		super(800, 667, 1);
		init();
		this.manager = new GameManager(this);
	}
	
	// Initialise les objets colonne, les coordonées de chaque objet, peuple le plateau.
	public void init() {
		this.columns = new HashMap<Integer, Colonne>();
		this.pionsBlancs = new ArrayList<Pion>();
		this.pionsNoirs = new ArrayList<Pion>();
		this.dice = new ArrayList<De>();
		this.prisons = new HashMap<String, Colonne>();
		this.sorties = new HashMap<String, Colonne>();
		
		// Ajout des colonnes
		for (int i = 1; i<25; i++) {
			this.columns.put(i, new Colonne(i));
		}
		// Ajout des 2 prisons
		this.prisons.put("blanc", new Colonne(0));
		this.prisons.put("noir", new Colonne(25));
		// Ajout des 2 sorties
		this.sorties.put("blanc", new Colonne(25));
		this.sorties.put("noir", new Colonne(0));
		
		setCoordinates();
		populate();
		initMovablesReadyToLeave();
	}
	
	// Peuple le plateau en fonction des positions initiales des pions pour le Backgammon
	public void populate() {
		
		for (int i = 1; i<6; i++) {
			PionNoir pion = new PionNoir(this, this.columns.get(13));
			this.pionsNoirs.add(pion);
			this.addObject(pion, this.columns.get(13).getXcoordinate(), this.columns.get(13).getYcoordinates().get(i));
		}
		
		for (int i = 1; i<4; i++) {
			PionBlanc pion = new PionBlanc(this, this.columns.get(17));
			this.pionsBlancs.add(pion);
			this.addObject(pion, this.columns.get(17).getXcoordinate(), this.columns.get(17).getYcoordinates().get(i));
		}
		
		for (int i = 1; i<6; i++) {
			PionBlanc pion = new PionBlanc(this, this.columns.get(19));
			this.pionsBlancs.add(pion);
			this.addObject(pion, this.columns.get(19).getXcoordinate(), this.columns.get(19).getYcoordinates().get(i));
		}
		
		for (int i = 1; i<3; i++) {
			PionNoir pion = new PionNoir(this, this.columns.get(24));
			this.pionsNoirs.add(pion);
			this.addObject(pion, this.columns.get(24).getXcoordinate(), this.columns.get(24).getYcoordinates().get(i));
		}
		
		for (int i = 1; i<6; i++) {
			PionBlanc pion = new PionBlanc(this, this.columns.get(12));
			this.pionsBlancs.add(pion);
			this.addObject(pion, this.columns.get(12).getXcoordinate(), this.columns.get(12).getYcoordinates().get(i));
		}
		
		for (int i = 1; i<4; i++) {
			PionNoir pion = new PionNoir(this, this.columns.get(8));
			this.pionsNoirs.add(pion);
			this.addObject(pion, this.columns.get(8).getXcoordinate(), this.columns.get(8).getYcoordinates().get(i));
		}
		
		for (int i = 1; i<6; i++) {
			PionNoir pion = new PionNoir(this, this.columns.get(6));
			this.pionsNoirs.add(pion);
			this.addObject(pion, this.columns.get(6).getXcoordinate(), this.columns.get(6).getYcoordinates().get(i));
		}
		
		for (int i = 1; i<3; i++) {
			PionBlanc pion = new PionBlanc(this, this.columns.get(1));
			this.pionsBlancs.add(pion);
			this.addObject(pion, this.columns.get(1).getXcoordinate(), this.columns.get(1).getYcoordinates().get(i));
		}
		
		// Ajout des 2 dés
		this.dice.add(new De(45, 250));
		this.addObject(this.dice.get(0), this.dice.get(0).getXcoordinate(), this.dice.get(0).getYcoordinate());
		
		this.dice.add(new De(45, 350));
		this.addObject(this.dice.get(1), this.dice.get(1).getXcoordinate(), this.dice.get(1).getYcoordinate());
		
		// Bouton "Lancer les dés"
		this.rolldiceBouton = new RollDiceBouton(this, 160, 630);
		this.addObject(rolldiceBouton, rolldiceBouton.getXcoordinate(), rolldiceBouton.getYcoordinate());
		
		// Bouton "Passer"
		this.passBouton = new PassBouton(this, 320, 630);
		this.addObject(passBouton, passBouton.getXcoordinate(), passBouton.getYcoordinate());
		
		// Bouton "Sauver"
		this.saveBouton = new SaveBouton(this, 45, 40);
		this.addObject(saveBouton, saveBouton.getXcoordinate(), saveBouton.getYcoordinate());
		
		// Bouton "Charger"
		this.loadBouton = new LoadBouton(this, 45, 90);
		this.addObject(loadBouton, loadBouton.getXcoordinate(), loadBouton.getYcoordinate());
		
		// Champ de texte pour le jeu
		this.text = new Texte("blanc_turn_text", 550, 632);
		this.addObject(text, text.getXcoordinate(), text.getYcoordinate());
	}
	
	// Initialise les coordonnées des colonnes du plateau.
	public void setCoordinates() {
		
		this.columns.get(1).setXcoordinate(691); this.columns.get(24).setXcoordinate(691);
		this.columns.get(2).setXcoordinate(646); this.columns.get(23).setXcoordinate(646);
		this.columns.get(3).setXcoordinate(602); this.columns.get(22).setXcoordinate(602);
		this.columns.get(4).setXcoordinate(557); this.columns.get(21).setXcoordinate(557);
		this.columns.get(5).setXcoordinate(512); this.columns.get(20).setXcoordinate(512);
		this.columns.get(6).setXcoordinate(468); this.columns.get(19).setXcoordinate(468);
		this.columns.get(7).setXcoordinate(333); this.columns.get(18).setXcoordinate(333);
		this.columns.get(8).setXcoordinate(289); this.columns.get(17).setXcoordinate(289);
		this.columns.get(9).setXcoordinate(245); this.columns.get(16).setXcoordinate(245);
		this.columns.get(10).setXcoordinate(200); this.columns.get(15).setXcoordinate(200);
		this.columns.get(11).setXcoordinate(156); this.columns.get(14).setXcoordinate(156);
		this.columns.get(12).setXcoordinate(112); this.columns.get(13).setXcoordinate(112);
		
		for (int i=1; i<13; i++) {
			this.columns.get(i).getYcoordinates().put(1, 560);
			this.columns.get(i).getYcoordinates().put(2, 515);
			this.columns.get(i).getYcoordinates().put(3, 470);
			this.columns.get(i).getYcoordinates().put(4, 425);
			this.columns.get(i).getYcoordinates().put(5, 380);
		}
		
		for (int i=13; i<25; i++) {
			this.columns.get(i).getYcoordinates().put(1, 45);
			this.columns.get(i).getYcoordinates().put(2, 90);
			this.columns.get(i).getYcoordinates().put(3, 135);
			this.columns.get(i).getYcoordinates().put(4, 180);
			this.columns.get(i).getYcoordinates().put(5, 225);
		}
		
		this.prisons.get("blanc").setXcoordinate(400);
		this.prisons.get("noir").setXcoordinate(400);
		this.prisons.get("blanc").getYcoordinates().put(1, 340); this.prisons.get("noir").getYcoordinates().put(1, 270);
		this.prisons.get("blanc").getYcoordinates().put(2, 370); this.prisons.get("noir").getYcoordinates().put(2, 240);
		this.prisons.get("blanc").getYcoordinates().put(3, 400); this.prisons.get("noir").getYcoordinates().put(3, 210);
		this.prisons.get("blanc").getYcoordinates().put(3, 430); this.prisons.get("noir").getYcoordinates().put(3, 180);
		this.prisons.get("blanc").getYcoordinates().put(3, 460); this.prisons.get("noir").getYcoordinates().put(3, 150);
		
		this.sorties.get("blanc").setXcoordinate(757);
		this.sorties.get("noir").setXcoordinate(757);
		this.sorties.get("blanc").getYcoordinates().put(1, 40); this.sorties.get("noir").getYcoordinates().put(1, 567);
		this.sorties.get("blanc").getYcoordinates().put(2, 56); this.sorties.get("noir").getYcoordinates().put(2, 551);
		this.sorties.get("blanc").getYcoordinates().put(3, 72); this.sorties.get("noir").getYcoordinates().put(3, 535);
		this.sorties.get("blanc").getYcoordinates().put(4, 88); this.sorties.get("noir").getYcoordinates().put(4, 519);
		this.sorties.get("blanc").getYcoordinates().put(5, 104); this.sorties.get("noir").getYcoordinates().put(5, 503);
		this.sorties.get("blanc").getYcoordinates().put(6, 120); this.sorties.get("noir").getYcoordinates().put(6, 487);
		this.sorties.get("blanc").getYcoordinates().put(7, 136); this.sorties.get("noir").getYcoordinates().put(7, 471);
		this.sorties.get("blanc").getYcoordinates().put(8, 152); this.sorties.get("noir").getYcoordinates().put(8, 455);
		this.sorties.get("blanc").getYcoordinates().put(9, 168); this.sorties.get("noir").getYcoordinates().put(9, 439);
		this.sorties.get("blanc").getYcoordinates().put(10, 184); this.sorties.get("noir").getYcoordinates().put(10, 423);
		this.sorties.get("blanc").getYcoordinates().put(11, 200); this.sorties.get("noir").getYcoordinates().put(11, 407);
		this.sorties.get("blanc").getYcoordinates().put(12, 216); this.sorties.get("noir").getYcoordinates().put(12, 391);
		this.sorties.get("blanc").getYcoordinates().put(13, 232); this.sorties.get("noir").getYcoordinates().put(13, 375);
		this.sorties.get("blanc").getYcoordinates().put(14, 248); this.sorties.get("noir").getYcoordinates().put(14, 359);
		this.sorties.get("blanc").getYcoordinates().put(15, 264); this.sorties.get("noir").getYcoordinates().put(15, 343);
	}
	
	// Rend les pions situés sur le dessus des colonnes mobiles
	public void initMovablesReadyToLeave() {
		
		this.columns.get(1).getLast().setMovable(true);
		this.columns.get(6).getLast().setMovable(true);
		this.columns.get(8).getLast().setMovable(true);
		this.columns.get(12).getLast().setMovable(true);
		this.columns.get(13).getLast().setMovable(true);
		this.columns.get(17).getLast().setMovable(true);
		this.columns.get(19).getLast().setMovable(true);
		this.columns.get(24).getLast().setMovable(true);
		
		// Modifie le booleen readyToLeave pour les pions concernés
		Iterator<Pion> it6 = this.columns.get(6).getIterator();
		while (it6.hasNext()) {
			it6.next().setReadyToLeave(true);
		}
		Iterator<Pion> it19 = this.columns.get(19).getIterator();
		while (it19.hasNext()) {
			it19.next().setReadyToLeave(true);
		}
	}

	public HashMap<Integer, Colonne> getColumns() {
		return columns;
	}
	
	// Place le pion au premier plan du plateau
	public void putActorToTheTop(Actor actor) {
		int X = actor.getX();
		int Y = actor.getY();
		this.removeObject(actor);
		this.addObject(actor, X, Y);
	}
	
	public void removeAllPions() {
		for (Pion pion : this.getPionsBlancs()) this.removeObject(pion);
		for (Pion pion : this.getPionsNoirs()) this.removeObject(pion);
		for (int i = 1; i<25; i++) {
			this.getColumns().get(i).setPion_list(new ArrayList<Pion>()); 
			this.getColumns().get(i).setCurrentRank(0);
		}
		this.pionsBlancs.clear();
		this.pionsNoirs.clear();
		this.prisons.get("blanc").setPion_list(new ArrayList<Pion>());
		this.prisons.get("noir").setPion_list(new ArrayList<Pion>());
		this.sorties.get("blanc").setPion_list(new ArrayList<Pion>());
		this.sorties.get("noir").setPion_list(new ArrayList<Pion>());
	}

	public GameManager getManager() {
		return manager;
	}

	public ArrayList<De> getDice() {
		return dice;
	}

	public HashMap<String, Colonne> getPrisons() {
		return prisons;
	}

	public HashMap<String, Colonne> getSorties() {
		return sorties;
	}

	public RollDiceBouton getBouton() {
		return rolldiceBouton;
	}

	public Texte getText() {
		return text;
	}

	public ArrayList<Pion> getPionsBlancs() {
		return pionsBlancs;
	}

	public ArrayList<Pion> getPionsNoirs() {
		return pionsNoirs;
	}

	public PassBouton getPassBouton() {
		return passBouton;
	}

	public RollDiceBouton getRolldiceBouton() {
		return rolldiceBouton;
	}
}
