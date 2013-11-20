import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Classe Loader. Permet le chargement d'une partie.
 * 
 * @author (Emeric de Bernis)
 */
public class Loader {

	private Backgammon world;
	private BufferedReader reader;
	
	public Loader(Backgammon world) {
		this.world = world;
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("SAV Extension", "sav");
		chooser.setFileFilter(filter);
		chooser.showOpenDialog(new JFrame());
		try {
			this.reader = new BufferedReader(new FileReader(chooser.getSelectedFile().getPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadGame() {
		
		this.world.removeAllPions();
		
		try {
			// Chargement du tour de jeu
			String turn = this.reader.readLine();
			if (turn.contains("blanc")) this.world.getManager().setTurn("blanc");
			else this.world.getManager().setTurn("noir");
			this.world.getText().ecrire(turn+"_turn_text");
			
			// Chargement des entiers du manager
			String line_int = this.reader.readLine();
			String[] mots_int = line_int.split(";");
			this.world.getManager().setNumberOfBlackOut(Integer.parseInt(mots_int[0]));
			this.world.getManager().setNumberOfWhiteOut(Integer.parseInt(mots_int[1]));
			
			// Chargement des booléens du manager
			String line_bool = this.reader.readLine();
			String[] mots_bool = line_bool.split(";");
			this.world.getManager().setBlackCanLeave(Boolean.parseBoolean(mots_bool[0]));
			this.world.getManager().setWhiteCanLeave(Boolean.parseBoolean(mots_bool[1]));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		// Chargement des données concernant les 15 pions blancs
		for (int i=0; i<15; i++) {
			try {
				String line = this.reader.readLine();
				String[] mots = line.split(";");
				
				// Si le pion était en prison
				if (Boolean.parseBoolean(mots[3])) {
					PionBlanc pion = new PionBlanc(this.world, this.world.getPrisons().get("blanc"));
					this.world.getPionsBlancs().add(pion);
					pion.setMovable(Boolean.parseBoolean(mots[2]));
					pion.setInPrison(Boolean.parseBoolean(mots[3]));
					pion.setReadyToLeave(Boolean.parseBoolean(mots[4]));
					this.world.addObject(pion, this.world.getPrisons().get("blanc").getXcoordinate(), this.world.getPrisons().get("blanc").getYcoordinates().get(pion.getGraphic_col_rank()));
				}
				else if (Integer.parseInt(mots[0]) == 0 || Integer.parseInt(mots[0]) == 25) {
					PionBlanc pion = new PionBlanc(this.world, this.world.getSorties().get("blanc"));
					this.world.getPionsBlancs().add(pion);
					pion.setMovable(Boolean.parseBoolean(mots[2]));
					pion.setInPrison(Boolean.parseBoolean(mots[3]));
					pion.setReadyToLeave(Boolean.parseBoolean(mots[4]));
					this.world.addObject(pion, this.world.getSorties().get("blanc").getXcoordinate(), this.world.getSorties().get("blanc").getYcoordinates().get(this.world.getManager().getNumberOut(pion.getColor())));
				}
				else {
					PionBlanc pion = new PionBlanc(this.world, this.world.getColumns().get(Integer.parseInt(mots[0])));
					this.world.getPionsBlancs().add(pion);
					pion.setOld_col(this.world.getColumns().get(Integer.parseInt(mots[1])));
					pion.setMovable(Boolean.parseBoolean(mots[2]));
					pion.setInPrison(Boolean.parseBoolean(mots[3]));
					pion.setReadyToLeave(Boolean.parseBoolean(mots[4]));
					this.world.addObject(pion, this.world.getColumns().get(Integer.parseInt(mots[0])).getXcoordinate(), this.world.getColumns().get(Integer.parseInt(mots[0])).getYcoordinates().get(pion.getGraphic_col_rank()));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Chargement des données concernant les 15 pions noirs
		for (int i=0; i<15; i++) {
			try {
				String line = this.reader.readLine();
				String[] mots = line.split(";");
				
				// Si le pion était en prison
				if (Boolean.parseBoolean(mots[3])) {
					PionNoir pion = new PionNoir(this.world, this.world.getPrisons().get("noir"));
					this.world.getPionsNoirs().add(pion);
					pion.setMovable(Boolean.parseBoolean(mots[2]));
					pion.setInPrison(Boolean.parseBoolean(mots[3]));
					pion.setReadyToLeave(Boolean.parseBoolean(mots[4]));
					this.world.addObject(pion, this.world.getPrisons().get("noir").getXcoordinate(), this.world.getPrisons().get("noir").getYcoordinates().get(pion.getGraphic_col_rank()));
				}
				else if (Integer.parseInt(mots[0]) == 0 || Integer.parseInt(mots[0]) == 25) {
					PionNoir pion = new PionNoir(this.world, this.world.getSorties().get("noir"));
					this.world.getPionsNoirs().add(pion);
					pion.setMovable(Boolean.parseBoolean(mots[2]));
					pion.setInPrison(Boolean.parseBoolean(mots[3]));
					pion.setReadyToLeave(Boolean.parseBoolean(mots[4]));
					this.world.addObject(pion, this.world.getSorties().get("noir").getXcoordinate(), this.world.getSorties().get("noir").getYcoordinates().get(this.world.getManager().getNumberOut(pion.getColor())));
				}
				else {
					PionNoir pion = new PionNoir(this.world, this.world.getColumns().get(Integer.parseInt(mots[0])));
					this.world.getPionsNoirs().add(pion);
					pion.setOld_col(this.world.getColumns().get(Integer.parseInt(mots[1])));
					pion.setMovable(Boolean.parseBoolean(mots[2]));
					pion.setInPrison(Boolean.parseBoolean(mots[3]));
					pion.setReadyToLeave(Boolean.parseBoolean(mots[4]));
					this.world.addObject(pion, this.world.getColumns().get(Integer.parseInt(mots[0])).getXcoordinate(), this.world.getColumns().get(Integer.parseInt(mots[0])).getYcoordinates().get(pion.getGraphic_col_rank()));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		JOptionPane.showMessageDialog(null, "La partie a été chargée", "Chargement", JOptionPane.INFORMATION_MESSAGE);
	}
}
