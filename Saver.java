import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * Classe Saver. Permet de sauvegarder une partie.
 * 
 * @author (Emeric de Bernis)
 */
public class Saver {

	private Backgammon world;
	private BufferedWriter writer;
	
	public Saver(Backgammon world) {
		try {
			String filename = JOptionPane.showInputDialog("Veuillez rentrer le nom du fichier de sauvegarde : ");
			FileWriter fstream = new FileWriter(filename+".sav");
			this.writer = new BufferedWriter(fstream);
			this.world = world;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveGame() {
		
		try {
			this.writer.write(this.world.getManager().getTurn());
			this.writer.newLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// Enregistrement des entiers du manager
		for (int i : this.world.getManager().getInts()) {
			try {
				this.writer.write(String.valueOf(i));
				this.writer.write(';');
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			this.writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Enregistrement des booléens du manager
		for (boolean bool : this.world.getManager().getBools()) {
			try {
				if (bool)this.writer.write("true");
				else this.writer.write("false");
				this.writer.write(';');
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			this.writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Enregistrement des données concernant les pions blancs
		for (Pion pion : this.world.getPionsBlancs()) {
			for (int i : pion.getInts()) {
				try {
					this.writer.write(String.valueOf(i));
					this.writer.write(';');
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			for (boolean bool : pion.getBools()) {
				try {
					if (bool)this.writer.write("true");
					else this.writer.write("false");
					this.writer.write(';');
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				this.writer.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// Enregistrement des données concernant les pions noirs
		for (Pion pion : this.world.getPionsNoirs()) {
			for (int i : pion.getInts()) {
				try {
					this.writer.write(String.valueOf(i));
					this.writer.write(';');
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			for (boolean bool : pion.getBools()) {
				try {
					if (bool)this.writer.write("true");
					else this.writer.write("false");
					this.writer.write(';');
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				this.writer.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, "La partie a été sauvegardée", "Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
	}
}
