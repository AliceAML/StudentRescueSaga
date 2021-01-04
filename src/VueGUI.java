import java.awt.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;
import javax.swing.border.Border;

public class VueGUI extends JFrame implements Visible {
	
	private Joueur joueur;
	private Plateau plateau;
	public String playerName = "";
	private int numNiveau;
	public boolean next = false; // booléen qui sert à "bloquer" l'avancement de l'affichage. Se débloque quand l'utilisateur clique sur un bouton.
	public int toDestroyX = 0; public int toDestroyY = 0; //pour sauvegarder les valeurs des coordonnées à détruire.
	public boolean useFusee = false; //boolen coup spécial fusée
	public boolean useMarteau = false;
	public boolean choiceOrNext = false;
	
	public VueGUI() {
		setSize(800, 800);
		setTitle("Student Rescue Saga");
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
		this.numNiveau = this.plateau.getNiveau().getNumero();

	}
	
	@Override
	public void afficherPlateau(){
		this.next = false;
		this.useFusee = false;
		this.useMarteau = false;
		
		//premier JPanel qui contiendra le tout
		JPanel all = new JPanel(new BorderLayout());
		
		//plateau en lui même : de hauteur 6 et de largeur 5 car on n'affiche pas l'intégralité du plateau.
		GridLayout gridPlateau = new GridLayout(6, 5); 
		
		//fenetre va contenir le plateau représenté par gridPlateau
		JPanel fenetre = new JPanel();
		fenetre.setLayout(gridPlateau);
        Color lightblue = new Color(51, 204, 255);
		fenetre.setBackground(lightblue);
		fenetre.setAlignmentX(30);
		fenetre.setLocation(20, 20);
		Border fborder = BorderFactory.createLineBorder(Color.green, 20);
		fenetre.setBorder(fborder);
		
		//entête sert uniquement à afficher le numéro du niveau
		JPanel entete = new JPanel();
		entete.setBackground(Color.yellow);
		JLabel niveau = new JLabel("Niveau " + String.valueOf(this.numNiveau), JLabel.CENTER);
		niveau.setFont(new Font("SansSerif", Font.PLAIN, 20));
		entete.add(niveau);
		
		//leftMenu contiendra toutes les informations necessaires + les boutons attribués aux coups spéciaux 
		JPanel leftMenu = new JPanel(new GridLayout(4, 2));
		leftMenu.setBackground(Color.pink);
		JLabel joueurX = new JLabel("  Joueur : ");
		joueurX.setFont(new Font("SansSerif", Font.PLAIN, 20));
		JLabel joueurY = new JLabel("  " + this.playerName + " ");
		joueurY.setFont(new Font("SansSerif", Font.PLAIN, 20));
		leftMenu.add(joueurX);
		leftMenu.add(joueurY);
		//bouton à cliquer pour utiliser le marteau :
		ImageIcon hammer = new ImageIcon("../images/hammer.png");
		JButton marteaux = new JButton(hammer);
		marteaux.setBorderPainted(false);
        marteaux.setBackground(Color.pink);
        //l'event permet uniquement de passer le booleen useMarteau à true pour envoyer l'information à l'environnement. 
        marteaux.addActionListener(event -> {this.useMarteau = true;});
		JLabel nbMarteaux = new JLabel(" " + String.valueOf(this.plateau.getNbMarteaux() + " X"));
		leftMenu.add(nbMarteaux);
		leftMenu.add(marteaux);
		//bouton à cliquer pour utiliser la fusée.
		ImageIcon rocket = new ImageIcon("../images/rocket.png");
		JButton fusees = new JButton(rocket);
		fusees.setBorderPainted(false);
        fusees.setBackground(Color.pink);
        fusees.addActionListener(event -> {this.useFusee = true;});
		JLabel nbFusees = new JLabel(" " + String.valueOf(this.plateau.getNbFusees()+ " X"));
		leftMenu.add(nbFusees);
		leftMenu.add(fusees);
		//représentation du score :
		ImageIcon coin = new ImageIcon("../images/coin.png");
		JLabel score = new JLabel(coin);
		JLabel nbScore = new JLabel(" " + String.valueOf(this.plateau.getScore() + " X"));
		leftMenu.add(nbScore);
		leftMenu.add(score);
		
		
		all.add(entete, BorderLayout.NORTH);
		all.add(fenetre, BorderLayout.CENTER);
		all.add(leftMenu, BorderLayout.WEST);
		this.add(all, "all");
		
		/*
		 * Cette seconde partie de la fonction sert à remplire "fenetre" et "gridPlateau" avec les boutons correspondant aux cases.
		 * Chaque bouton contient une image selon la couleur de la case et est clicable ou non.
		 * Leurs actionListeners mettent à jour les coordonnées de la case à détruire + le booleen next.
		 */

		for (int y = this.plateau.getHeight()-5; y < this.plateau.matriceElements.length - 1; y++) {
			for (int x = 1; x < 6; x++) {		
				
				//ajout des cases vides
				if (this.plateau.matriceElements[y][x] == null) {
					fenetre.add(new JLabel(""));
				}
				//ajout des cases normales :
				else {
					//on passe les variables x et y en final pour pouvoir y acceder dans nos expressions lambda.
					final int xf = x;
					final int yf = y;
					//case fixe (obstacle) avec image de caillou.
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
					//case bleue
					if (this.plateau.matriceElements[y][x].toString().contentEquals("1")) {
						ImageIcon image = new ImageIcon("../images/virus_navy.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
						logo.setBackground(lightblue);
	                    logo.addActionListener(event -> {
	                    	this.toDestroyX = xf; this.toDestroyY = yf;
	                    	this.next = true;});
	                    
						fenetre.add(logo);
					}
					//case verte
					if (this.plateau.matriceElements[y][x].toString().contentEquals("2")) {
						ImageIcon image = new ImageIcon("../images/virus_green.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
						logo.setBackground(lightblue);
	                    logo.addActionListener(event -> {
	                    	System.out.println(" " + yf + xf);
	                    	this.toDestroyX = xf; this.toDestroyY = yf;
	                    	this.next = true;});
						fenetre.add(logo);
					}
					//case jaune
					if (this.plateau.matriceElements[y][x].toString().contentEquals("3")) {
						ImageIcon image = new ImageIcon("../images/virus_yellow.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
						logo.setBackground(lightblue);
	                    logo.addActionListener(event -> {
	                    	this.toDestroyX = xf; this.toDestroyY = yf;
	                    	this.next = true;});
						fenetre.add(logo);
					}
					//case violette
					if (this.plateau.matriceElements[y][x].toString().contentEquals("4")) {
						ImageIcon image = new ImageIcon("../images/virus_purple.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
						logo.setBackground(lightblue);
	                    logo.addActionListener(event -> {
	                    	this.toDestroyX = xf; this.toDestroyY = yf;
	                    	this.next = true;});
						fenetre.add(logo);
					}
					//case rouge
					if (this.plateau.matriceElements[y][x].toString().contentEquals("5")) {
						ImageIcon image = new ImageIcon("../images/virus_red.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
						logo.setBackground(lightblue);
	                    logo.addActionListener(event -> {
	                    	this.toDestroyX = xf; this.toDestroyY = yf;
	                    	this.next = true;});
						fenetre.add(logo);
					}
					//case "animal" représentée par un étudiant. Ne peut pas être cliquée.
					if (this.plateau.matriceElements[y][x].toString().contentEquals("@")) {
						ImageIcon image = new ImageIcon("../images/studentf.png");
						JButton logo = new JButton(image);
						logo.setBorderPainted(false);
						logo.setBackground(lightblue);
						fenetre.add(logo);
					}
				}
			}
		}
		//on ajoute enfin le plateau et on appelle le cardlayout qui permet de changer de fenetre.
		fenetre.setPreferredSize(new Dimension(600, 400));
        fenetre.setMaximumSize(fenetre.getPreferredSize()); 
        fenetre.setMinimumSize(fenetre.getPreferredSize());
		
		((CardLayout) this.getContentPane().getLayout()).show(this.getContentPane(), "all");

	}


	@Override
	public void move() {
		//tant que rien ne se passe, this.next reste false
		while (this.next != true) {
			System.out.print("");
		}
		//si la fusée a été cliquée, on destroy la colonne choisie
		if (this.useFusee == true) {
			this.plateau.fuseeDestroy(this.toDestroyX);
		}
		//si le marteau a été cliqué, on destroy la case choisie. 
		else if (this.useMarteau == true) {
			this.plateau.destroy(this.toDestroyY, this.toDestroyX);
		}
		//sinon on destroy normalement la case choisie.
		else if (!this.plateau.isAlone(this.toDestroyY, this.toDestroyX)) {
			this.plateau.destroy(this.toDestroyY, this.toDestroyX);
		}
		
		
		
		
		

	}

	@Override
	public void help() {
		// TODO Auto-generated method stub
		// il faut l'associer au bouton help de welcome mais il faudrait également créer une nouvelle fenêtre. 
		
	}

	//affiche la première fenetre.
	@Override
	public void welcome() {

		Color lightblue = new Color(51, 204, 255);
		
		//PANEL TITRE
		JPanel titre = new JPanel();
		titre.setBackground(lightblue);
		JLabel titreText = new JLabel("<html>Student<br>Rescue<br>Saga</html>", JLabel.CENTER);
		titreText.setFont(new Font("SansSerif", Font.BOLD, 60));
				
		ImageIcon image = new ImageIcon("../images/student.png");
		JLabel logo = new JLabel(image, JLabel.CENTER);
		
		titre.add(logo);
		titre.add(titreText);
	
		JLabel but = new JLabel("Détruis les virus pour sauver les étudiants !", JLabel.CENTER);
		but.setFont(new Font("SansSerif", Font.PLAIN, 20));

		
		//les seuls éléments clicables sont le bouton start et help.
		JButton startButton = new JButton("START");
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.setPreferredSize(new Dimension(20, 10));
		startButton.setBackground(lightblue);

		JButton help = new JButton("HELP");
		help.setAlignmentX(Component.CENTER_ALIGNMENT);
		help.setPreferredSize(new Dimension(20, 10));
		help.setBackground(lightblue);
		
		JPanel startPanel = new JPanel();
		startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
		startPanel.setBackground(Color.pink);
		startPanel.add(startButton);
		startPanel.add(help);
		
		//panel global
		JPanel panel = new JPanel(new GridLayout(3,1));
		panel.setBackground(Color.yellow);
		panel.add(but);
		panel.add(titre);
		panel.add(startPanel);
		
		add(panel, "Welcome");
		
		//le bouton start fait passer next a true pour que l'environnement puisse continuer ses tâches.
		startButton.addActionListener(event -> {this.next = true;});
		
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
		while (this.next != true) {
			System.out.print("");
		}
		return this.numNiveau;
	}

	@Override
	public boolean startAgain() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean choiceOrNext() {
		this.next = false;
		
		JLabel niveau = new JLabel("Niveau " + Integer.toString(this.numNiveau), JLabel.CENTER);
		niveau.setFont(new Font("SansSerif", Font.BOLD, 30));
		
		JLabel titre = new JLabel("SCORE", JLabel.CENTER);
		titre.setFont(new Font("SansSerif", Font.BOLD, 20));
	
		
		JLabel score = new JLabel(Integer.toString(this.plateau.getScore()), JLabel.CENTER);
		score.setFont(new Font("SansSerif", Font.BOLD, 40));
		
		int best = this.joueur.getScore(this.numNiveau);
		JLabel meilleurScore;
		if (this.plateau.getScore() >= best) {
			meilleurScore = new JLabel("Bravo, c'est votre meilleur score ! " + Integer.toString(best), JLabel.CENTER);
		}
		else {
			meilleurScore = new JLabel("C'est en dessous de votre meilleur score : " + Integer.toString(best), JLabel.CENTER);
		}


		JPanel text = new JPanel(new GridLayout(5,1));
		text.add(niveau);
		text.add(titre);
		text.add(score);
		text.add(meilleurScore);
		
		JPanel buttons = new JPanel();
		
		JButton choice = new JButton("Revenir au menu");
		JButton next = new JButton("Niveau suivant");
		
		buttons.add(choice);
		buttons.add(next);
		
		choice.setBackground(new Color(51, 204, 255));
		next.setBackground(Color.pink);


		text.add(buttons);
		
		text.setBackground(Color.yellow);
		
		buttons.setBackground(Color.yellow);

		
		JPanel choicePanel = new JPanel(new BorderLayout());
		
		choicePanel.add(text, BorderLayout.CENTER);
		
		add(choicePanel, "choiceorNext");
		((CardLayout) this.getContentPane().getLayout()).show(this.getContentPane(), "choiceorNext"); // méthode pour afficher une autre "carte" 
	
		
		choice.addActionListener( e-> {
			this.choiceOrNext = true;
			this.next = true;
		});
		
		next.addActionListener( e-> {
			this.choiceOrNext = false;
			this.next = true;
		});
		
		while (this.next != true) {
			System.out.print("");
		}
		
		return this.choiceOrNext;
	}
	
	/**
	 * Retourne le nom du joueur pour que l'env load la sauvegarde
	 */
	@Override
	public String choixJoueur() {
		//on initialise toujours next à false
		this.next = false;
		
		//On insière dans un JPanel "panelChoixJoueur" :
		//- un Jpanel text contenant les informations et la saisie du texte
		//- un bouton done qui fait passer next à true 
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
		//l'environnement pourra alors accéder à this.playerName pour initialiser le jouer. 
		return this.playerName;
		}
		
		
		
		
		

	@Override
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}
	
	

	@Override
	public void displayLevels() {
		this.next = false;		
		
		//on parcour notre liste de niveaux et on les affiche en tant que bouton. 
		//chacun permettra de passer next à true + de garder la valeur du niveau en mémoire pour l'environnement.
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
				lev.setEnabled(false); //à remettre pour griser les niveaux bloqués
			}
			lev.addActionListener( e-> {
				VueGUI.this.numNiveau = Integer.valueOf(lev.getText());
				this.next = true;});
		}
		
		add(panelChoixNiveau, "choixNiveau");
		((CardLayout) this.getContentPane().getLayout()).show(this.getContentPane(), "choixNiveau");
		}

	@Override
	public String getPlayerName() {
		return this.playerName;
	}

}
