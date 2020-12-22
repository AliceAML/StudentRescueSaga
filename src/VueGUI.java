import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class VueGUI extends JFrame implements Visible {
	
	private Joueur joueur;
	private Plateau plateau;
	
	public VueGUI() {
		setSize(600, 800);
		setTitle("Pet Rescue Saga");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new CardLayout());
	}

	// réinitialise la fenêtre
	private void clear() {
		this.getContentPane().removeAll();
	}
	
	@Override
	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;

	}
	
	@Override
	public void afficherPlateau() {
		// TODO Auto-generated method stub

	}

	@Override
	public void miseAjourPlateau() {
		// TODO Auto-generated method stub

	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public void help() {
		// TODO Auto-generated method stub

	}

	@Override
	public void welcome() {
//		this.clear();
		
		// PANEL TITRE
		JPanel titre = new JPanel();
		
		JLabel titreText = new JLabel("<html>Student<br>Rescue<br>Saga</html>", JLabel.CENTER);
		titreText.setFont(new Font("SansSerif", Font.BOLD, 60));
//		titre.setHorizontalAlignment(0);
		
		ImageIcon image = new ImageIcon("../images/student.png");
		JLabel logo = new JLabel(image, JLabel.CENTER);
		
		JLabel but = new JLabel("Détruisez les blocs pour sauver les étudiants !", JLabel.CENTER);
		but.setFont(new Font("SansSerif", Font.PLAIN, 20));

		
		titre.add(logo);
		titre.add(titreText);
		
		
		JButton startButton = new JButton("start");
		startButton.addActionListener(event -> {this.choixJoueur();}); // FIXME ça ne marche pas :(
		JPanel startPanel = new JPanel();
		startPanel.add(startButton);
		
		// PANEL WELCOME
		JPanel panel = new JPanel(new GridLayout(3,1));
		panel.add(but);
		panel.add(titre);
		panel.add(startPanel);
		
		add(panel, "Welcome");
		
		
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getPlateauWin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getPlateauGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int choixNiveau() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean startAgain() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean choiceOrNext() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Retourne le nom du joueur pour que l'env load la sauvegarde
	 */
	@Override
	public String choixJoueur() {
		JLabel consigne = new JLabel("Choisissez votre joueur");
		JPanel panelChoixJoueur = new JPanel();
		panelChoixJoueur.add(consigne);
		add(panelChoixJoueur, "choixJoueur");
		((CardLayout) this.getContentPane().getLayout()).show(this.getContentPane(), "choixJoueur"); // méthode pour afficher une autre "carte" 
		return null;
	}

	@Override
	public void setJoueur(Joueur joueur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayLevels() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		VueGUI testVue = new VueGUI();
		testVue.welcome();
	}

}
