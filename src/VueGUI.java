import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.*;

public class VueGUI extends JFrame implements Visible {
	
	private Joueur joueur;
	private Plateau plateau;
	private String playerName;
	private int numNiveau;
	
	public VueGUI() {
		setSize(800, 800);
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
	public void afficherPlateau(){
		
		GridLayout gridPlateau = new GridLayout(this.plateau.getWidth(), this.plateau.getHeight());
		JLabel niveau = new JLabel("Niveau " + String.valueOf(this.numNiveau), JLabel.CENTER);
		niveau.setFont(new Font("SansSerif", Font.PLAIN, 20));
		
		JPanel fenetre = new JPanel();
		fenetre.setLayout(gridPlateau);
		Color lightblue = new Color(51, 204, 255);
		fenetre.setBackground(lightblue);
		fenetre.setAlignmentX(30);
		JPanel entete = new JPanel();
		
		entete.add(niveau);
		add(entete, "entête");
		fenetre.setLocation(20, 20);
		add(fenetre, "fenetre");
		
		
		
		for (int y = 1; y < this.plateau.matriceElements.length - 1; y++) {
			for (int x = 1; x < this.plateau.matriceElements[0].length - 1; x++) {
				if (this.plateau.matriceElements[y][x] == null) {
					fenetre.add(new JLabel(""));
				}
				else {
					if (this.plateau.matriceElements[y][x].toString().contentEquals("/")) {
						//on crée une image à partir du path et on la met dans un JButton
						//les 5 lignes d'après servent à enlever le fond et les bords des boutons
						//pour que seule l'image aparaisse (comme dans un JLabel mais sous forme de bouton).
						ImageIcon image = new ImageIcon("../images/hammer.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
	                    logo.setBackground(lightblue);
	                    logo.setFocusPainted(false);
	                    logo.setOpaque(true);
	                    logo.setMargin(new Insets(10, 10, 10, 10));
						fenetre.add(logo);
					}
					if (this.plateau.matriceElements[y][x].toString().contentEquals("1")) {
						ImageIcon image = new ImageIcon("../images/virus_navy.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
						logo.setBackground(lightblue);
						logo.setFocusPainted(false);
	                    logo.setOpaque(true);
	                    logo.setMargin(new Insets(10, 10, 10, 10));
						fenetre.add(logo);
					}
					if (this.plateau.matriceElements[y][x].toString().contentEquals("2")) {
						ImageIcon image = new ImageIcon("../images/virus_green.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
						logo.setBackground(lightblue);
	                    logo.setFocusPainted(false);
	                    logo.setOpaque(true);
	                    logo.setMargin(new Insets(10, 10, 10, 10));
						fenetre.add(logo);
					}
					if (this.plateau.matriceElements[y][x].toString().contentEquals("3")) {
						ImageIcon image = new ImageIcon("../images/virus_yellow.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
						logo.setBackground(lightblue);
	                    logo.setFocusPainted(false);
	                    logo.setOpaque(true);
	                    logo.setMargin(new Insets(10, 10, 10, 10));
						fenetre.add(logo);
					}
					if (this.plateau.matriceElements[y][x].toString().contentEquals("4")) {
						ImageIcon image = new ImageIcon("../images/virus_purple.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
						logo.setBackground(lightblue);
	                    logo.setFocusPainted(false);
	                    logo.setOpaque(true);
	                    logo.setMargin(new Insets(10, 10, 10, 10));
						fenetre.add(logo);
					}
					if (this.plateau.matriceElements[y][x].toString().contentEquals("5")) {
						ImageIcon image = new ImageIcon("../images/virus_red.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
						logo.setBackground(lightblue);
	                    logo.setFocusPainted(false);
	                    logo.setOpaque(true);
	                    logo.setMargin(new Insets(10, 10, 10, 10));
						fenetre.add(logo);
					}
					if (this.plateau.matriceElements[y][x].toString().contentEquals("@")) {
						ImageIcon image = new ImageIcon("../images/monkey.png");
						JButton logo = new JButton(image);
						fenetre.add(logo);
					}
					//else {
					//	fenetre.add(new JLabel(this.plateau.matriceElements[y][x].toString()));
					//}
					
				}
			}
		}
		
		
		
		((CardLayout) this.getContentPane().getLayout()).show(this.getContentPane(), "fenetre");

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
		
		titre.add(logo);
		
		titre.add(logo);
		titre.add(titreText);
	
		JLabel but = new JLabel("Détruisez les blocs pour sauver les étudiants !", JLabel.CENTER);
		but.setFont(new Font("SansSerif", Font.PLAIN, 20));

		
		
		JButton startButton = new JButton("start");
		
		JPanel startPanel = new JPanel();
		startPanel.add(startButton);
		
		// PANEL WELCOME
		JPanel panel = new JPanel(new GridLayout(3,1));
		panel.add(but);
		panel.add(titre);
		panel.add(startPanel);
		
		add(panel, "Welcome");
		
		//cliquer sur start active directement la fonction choixJoueur et ouvre la fenetre correspondante.
		startButton.addActionListener(event -> {this.choixJoueur();}); // FIXME ça ne marche pas :(
		
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
		JLabel consigne = new JLabel("Choisissez votre joueur", JLabel.CENTER);
		JTextField nomJoueur = new JTextField("joueur", JTextField.CENTER);
		JButton done = new JButton("done");
		
		JPanel panelChoixJoueur = new JPanel();
		panelChoixJoueur.add(consigne);
		panelChoixJoueur.add(nomJoueur);
		panelChoixJoueur.add(done);
		add(panelChoixJoueur, "choixJoueur");
		((CardLayout) this.getContentPane().getLayout()).show(this.getContentPane(), "choixJoueur"); // méthode pour afficher une autre "carte" 
		

		done.addActionListener( e-> {
			VueGUI.this.playerName = nomJoueur.getText();
			this.displayLevels();
		});

		//Solution approximative : on assigne this.joueur directement ici
		//(car c'est le seul endroit où on peut accéder au String du JTextField)
		//pour ce faire, la classe possède un argument String playerName auquel on peut acceder dans l'expression lambda
		
		this.joueur = new Joueur(playerName);
		return null;
		}
		
		
		
		
		

	@Override
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}
	
	

	@Override
	public void displayLevels() {
				
		
		File levels = new File("../levels/");
		ArrayList<String> listLevelNames = new ArrayList<String>();
		for (File level : levels.listFiles()) {
			listLevelNames.add(level.getName());
		}
		Collections.sort(listLevelNames);
		
		
		JLabel niveaux = new JLabel("Choisissez un niveau :", JLabel.CENTER);
		GridLayout gridNiveaux = new GridLayout(listLevelNames.size(), 0);
		JPanel panelChoixNiveau = new JPanel(gridNiveaux);
		panelChoixNiveau.add(niveaux);
		
		for (String level : listLevelNames) {
			JButton lev = new JButton(level);
			panelChoixNiveau.add(lev);
			if (!this.joueur.isDebloque(level)) { // si le niveau est bloqué
				//lev.setEnabled(false); //à remettre pour griser les niveaux bloqués
			}
			//ici, on crée le plateau correspondant au numéro du niveau que l'on obtient.
			//le choix envoie directement la prochaine fenetre (afficherPlateau). 
			lev.addActionListener( e-> {
				VueGUI.this.numNiveau = Integer.valueOf(lev.getText());
				Niveau niveau = new Niveau(numNiveau);
				this.plateau = new Plateau(niveau, this);
				setPlateau(this.plateau);
				this.afficherPlateau();
			});
			}
		
		add(panelChoixNiveau, "choixNiveau");
		((CardLayout) this.getContentPane().getLayout()).show(this.getContentPane(), "choixNiveau");
		
		
		
		}
		
	
	public static void main(String[] args) throws IOException {
		VueGUI testVue = new VueGUI();
		//testVue.setJoueur(new Joueur(choixJoueur()));
		testVue.welcome();
	}

}
