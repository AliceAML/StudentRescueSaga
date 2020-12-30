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
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class VueGUI extends JFrame implements Visible {
	
	private Joueur joueur;
	private Plateau plateau;
	public String playerName = "";
	private int numNiveau;
	public boolean next = false;
	
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
		
		Plateau scopePlateau = this.plateau;
		
		
		JPanel all = new JPanel(new BorderLayout());

		GridLayout gridPlateau = new GridLayout(6, 5); //this.plateau.getHeight(), this.plateau.getWidth());
		
		JPanel fenetre = new JPanel();
		fenetre.setLayout(gridPlateau);
        Color lightblue = new Color(51, 204, 255);
		fenetre.setBackground(lightblue);
		fenetre.setAlignmentX(30);
		fenetre.setLocation(20, 20);
		Border fborder;
		fborder = BorderFactory.createLineBorder(Color.green, 20);
		fenetre.setBorder(fborder);
		
		JPanel entete = new JPanel();
		entete.setBackground(Color.yellow);
		JLabel niveau = new JLabel("Niveau " + String.valueOf(this.numNiveau), JLabel.CENTER);
		niveau.setFont(new Font("SansSerif", Font.PLAIN, 20));
		entete.add(niveau);
		
		JPanel leftMenu = new JPanel(new GridLayout(4, 2));
		leftMenu.setBackground(Color.pink);
		JLabel joueurX = new JLabel("  Joueur : ");
		joueurX.setFont(new Font("SansSerif", Font.PLAIN, 20));
		JLabel joueurY = new JLabel("  " + this.playerName + " ");
		joueurY.setFont(new Font("SansSerif", Font.PLAIN, 20));
		leftMenu.add(joueurX);
		leftMenu.add(joueurY);
		ImageIcon hammer = new ImageIcon("../images/hammer.png");
		JButton marteaux = new JButton(hammer);
		marteaux.setBorderPainted(false);
        marteaux.setBackground(Color.pink);
		JLabel nbMarteaux = new JLabel(" " + String.valueOf(this.plateau.getNbMarteaux() + " X"));
		leftMenu.add(nbMarteaux);
		leftMenu.add(marteaux);
		
		ImageIcon rocket = new ImageIcon("../images/rocket.png");
		JButton fusees = new JButton(rocket);
		fusees.setBorderPainted(false);
        fusees.setBackground(Color.pink);
		JLabel nbFusees = new JLabel(" " + String.valueOf(this.plateau.getNbFusees()+ " X"));
		leftMenu.add(nbFusees);
		leftMenu.add(fusees);
		
		ImageIcon coin = new ImageIcon("../images/coin.png");
		JLabel score = new JLabel(coin);
		JLabel nbScore = new JLabel(" " + String.valueOf(this.plateau.getScore() + " X"));
		leftMenu.add(nbScore);
		leftMenu.add(score);
		
		
		
		all.add(entete, BorderLayout.NORTH);
		all.add(fenetre, BorderLayout.CENTER);
		all.add(leftMenu, BorderLayout.WEST);
		
		this.add(all, "all");
		
		
		
		

		
		//for (int y = 1; y < this.plateau.matriceElements.length - 1; y++) {
		//	for (int x = 1; x < this.plateau.matriceElements[0].length - 1; x++) {
		for (int y = this.plateau.getHeight()-5; y < this.plateau.matriceElements.length - 1; y++) {
			for (int x = 1; x < 6; x++) {		
				
				
				if (this.plateau.matriceElements[y][x] == null) {
					fenetre.add(new JLabel(""));
				}
				else {
					final int xf = x;
					final int yf = y;
					if (this.plateau.matriceElements[y][x].toString().contentEquals("/")) {
						//on crée une image à partir du path et on la met dans un JButton
						//les 5 lignes d'après servent à enlever le fond et les bords des boutons
						//pour que seule l'image aparaisse (comme dans un JLabel mais sous forme de bouton).
						ImageIcon image = new ImageIcon("../images/rock.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
	                    logo.setBackground(lightblue);
	                    logo.setEnabled(false);
						fenetre.add(logo);
					}
					if (this.plateau.matriceElements[y][x].toString().contentEquals("1")) {
						ImageIcon image = new ImageIcon("../images/virus_navy.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
						logo.setBackground(lightblue);
	                    logo.addActionListener(event -> {
	                    	this.plateau.destroy(yf, xf);
	                    	this.afficherPlateau();});
	                    
						fenetre.add(logo);
					}
					if (this.plateau.matriceElements[y][x].toString().contentEquals("2")) {
						ImageIcon image = new ImageIcon("../images/virus_green.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
						logo.setBackground(lightblue);
	                    logo.addActionListener(event -> {
	                    	this.plateau.destroy(yf, xf);
	                    	this.afficherPlateau();});
						fenetre.add(logo);
					}
					if (this.plateau.matriceElements[y][x].toString().contentEquals("3")) {
						ImageIcon image = new ImageIcon("../images/virus_yellow.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
						logo.setBackground(lightblue);
	                    logo.addActionListener(event -> {
	                    	this.plateau.destroy(yf, xf);
	                    	this.afficherPlateau();});
						fenetre.add(logo);
					}
					if (this.plateau.matriceElements[y][x].toString().contentEquals("4")) {
						ImageIcon image = new ImageIcon("../images/virus_purple.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
						logo.setBackground(lightblue);
	                    logo.addActionListener(event -> {
	                    	this.plateau.destroy(yf, xf);
	                    	this.afficherPlateau();});
						fenetre.add(logo);
					}
					if (this.plateau.matriceElements[y][x].toString().contentEquals("5")) {
						ImageIcon image = new ImageIcon("../images/virus_red.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
						logo.setBackground(lightblue);
	                    logo.addActionListener(event -> {
	                    	this.plateau.destroy(yf, xf);
	                    	this.afficherPlateau();});
						fenetre.add(logo);
					}
					if (this.plateau.matriceElements[y][x].toString().contentEquals("@")) {
						ImageIcon image = new ImageIcon("../images/monkey.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
						logo.setBackground(lightblue);
						fenetre.add(logo);
					}
					//else {
					//	fenetre.add(new JLabel(this.plateau.matriceElements[y][x].toString()));
					//}
					
				}
			}
		}
		
		fenetre.setPreferredSize(new Dimension(600, 400));
        fenetre.setMaximumSize(fenetre.getPreferredSize()); 
        fenetre.setMinimumSize(fenetre.getPreferredSize());
		
		((CardLayout) this.getContentPane().getLayout()).show(this.getContentPane(), "all");

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

		Color lightblue = new Color(51, 204, 255);
		
		// PANEL TITRE
		JPanel titre = new JPanel();
		titre.setBackground(lightblue);
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

		
		
		JButton startButton = new JButton("START");
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.setPreferredSize(new Dimension(20, 10));
		startButton.setBackground(lightblue);
		JButton scores = new JButton("SCORES");
		scores.setAlignmentX(Component.CENTER_ALIGNMENT);
		scores.setPreferredSize(new Dimension(20, 10));
		scores.setBackground(lightblue);
		JButton help = new JButton("HELP");
		help.setAlignmentX(Component.CENTER_ALIGNMENT);
		help.setPreferredSize(new Dimension(20, 10));
		help.setBackground(lightblue);
		
		JPanel startPanel = new JPanel();
		startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
		startPanel.setBackground(Color.pink);
		startPanel.add(startButton);
		startPanel.add(scores);
		startPanel.add(help);
		
		// PANEL WELCOME
		JPanel panel = new JPanel(new GridLayout(3,1));
		panel.setBackground(Color.yellow);
		panel.add(but);
		panel.add(titre);
		panel.add(startPanel);
		
		add(panel, "Welcome");
		
		//cliquer sur start active directement la fonction choixJoueur et ouvre la fenetre correspondante.
		startButton.addActionListener(event -> {this.next = true;}); // FIXME ça ne marche pas :(
		
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
		this.displayLevels();
		return this.numNiveau;
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
		this.next = false;
		JLabel consigne = new JLabel("<html>Choisissez<br>votre<br>joueur</html>", JLabel.CENTER);
		consigne.setFont(new Font("SansSerif", Font.BOLD, 40));
		JTextField nomJoueur = new JTextField("Joueur", JTextField.CENTER);
		Font font = new Font("SansSerif", Font.BOLD, 20);
		nomJoueur.setFont(font);
		nomJoueur.setBackground(new Color(51, 204, 255));
		nomJoueur.setPreferredSize(new Dimension( 100, 30 ));
		JButton done = new JButton("START");
		done.setBackground(new Color(51, 204, 255));
		
		JPanel panelChoixJoueur = new JPanel();
		panelChoixJoueur.setBackground(Color.pink);
		JPanel text = new JPanel();
		text.setBackground(Color.yellow);
		panelChoixJoueur.add(text, JPanel.BOTTOM_ALIGNMENT);
		text.add(consigne);
		text.add(nomJoueur);
		text.add(done);
		add(panelChoixJoueur, "choixJoueur");
		((CardLayout) this.getContentPane().getLayout()).show(this.getContentPane(), "choixJoueur"); // méthode pour afficher une autre "carte" 
		

		done.addActionListener( e-> {
			System.out.println(nomJoueur.getText());
			this.playerName = nomJoueur.getText();
			this.next = true;
		});

		//Solution approximative : on assigne this.joueur directement ici
		//(car c'est le seul endroit où on peut accéder au String du JTextField)
		//pour ce faire, la classe possède un argument String playerName auquel on peut acceder dans l'expression lambda
		
		return this.playerName;
		}
		
		
		
		
		

	@Override
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}
	
	

	@Override
	public void displayLevels() {
		this.next = false;		
		
		File levels = new File("../levels/");
		ArrayList<String> listLevelNames = new ArrayList<String>();
		for (File level : levels.listFiles()) {
			listLevelNames.add(level.getName());
		}
		Collections.sort(listLevelNames);
		
		
		JLabel niveaux = new JLabel("Choisissez un niveau :", JLabel.CENTER);
		niveaux.setFont(new Font("SansSerif", Font.BOLD, 40));
		GridLayout gridNiveaux = new GridLayout(listLevelNames.size()+1, 0);
		JPanel panelChoixNiveau = new JPanel(gridNiveaux);
		panelChoixNiveau.setBackground(new Color(51, 204, 255));
		panelChoixNiveau.add(niveaux);
		
		for (String level : listLevelNames) {
			JButton lev = new JButton(level);
			lev.setBackground(Color.pink);
			lev.setOpaque(true);
			panelChoixNiveau.add(lev);
			if (!this.joueur.isDebloque(level)) { // si le niveau est bloqué
				//lev.setEnabled(false); //à remettre pour griser les niveaux bloqués
			}
			//ici, on crée le plateau correspondant au numéro du niveau que l'on obtient.
			//le choix envoie directement la prochaine fenetre (afficherPlateau). 
			lev.addActionListener( e-> {
				VueGUI.this.numNiveau = Integer.valueOf(lev.getText());
				this.next = true;
				//Niveau niveau = new Niveau(numNiveau);
				//this.plateau = new Plateau(niveau, this);
				//setPlateau(this.plateau);
				//this.afficherPlateau();
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
