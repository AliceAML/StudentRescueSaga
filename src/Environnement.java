import java.io.*;

/**
 * Classe qui gère le déroulement du jeu
 *
 */
public class Environnement {
	Visible vue;
	Niveau niveau;
	Plateau plateau;
	Joueur joueur;
	
	/**
	 * Constructeur de l'environnement
	 * @param vue (text ou graphique)
	 * @throws IOException 
	 */
	public Environnement(Visible vue) throws IOException {
		this.vue = vue; // choix de la vue lors de la création de l'environnement
		// les autres attributs sont initialisés par d'autres fonctions
	}
	
	/**
	 * Init permet de mettre en place les différents attributs : joueur, niveau, plateau.
	 * et de lancer le début d'une partie (welcome, choixJoueur, etc.)
	 */
	public void init() {
		
		//lance welcome pour les deux vues
		this.vue.welcome();
		String nomJoueur = "";
		if (vue instanceof VueGUI) {
			while (((VueGUI) vue).next != true) {
				//avec vueGUI on attend l'event du bouton start qui change le booleen next.
				System.out.print("");
			}
		}
		
		//lance choixJoueur pour les deux vues. 
		this.vue.choixJoueur();
		if (vue instanceof VueGUI) {
			while (((VueGUI) vue).next != true) {
				//si vueGUI, même opération avec next.
				System.out.print("");
			}
		}
		//on extrait le nom du joueur assigné à un attribut playerName de l'une des deux vues. 	
		nomJoueur = vue.getPlayerName();
		
		// si le joueur n'existe pas encore, on le crée.
		if (!this.loadJoueur(nomJoueur)) { // si on ne peut pas charger le joueur (sinon ça le charge directement)
				this.joueur = new Joueur(nomJoueur); // on en crée un nouveau
		}
		// on définit l'attribut joueur de la vue pour pouvoir l'utiliser par la suite.
		this.vue.setJoueur(this.joueur);
		
		//on lance displaylevels pour les deux vues. 
		this.choixNiveau();
	}

	
	/**
	 * Choix du niveau, reçoit int renvoyé par la vue
	 * @return niveau actuel de type Niveau
	 */
	public Niveau choixNiveau() {
		int n = this.vue.choixNiveau(); // demande à la vue le choix de l'utilisateur
		Niveau niveau = new Niveau(n); // crée un Niveau à partir de cet entier
		this.niveau = niveau; // assigne niveau en attribut de l'environnement
		return niveau;
	}
	
	/**
	 * lance le niveau choisi
	 */
	public void startniveau() {
		Plateau plateau = new Plateau(niveau, vue); // nouveau plateau pour le nouveau niveau
		this.vue.setPlateau(plateau); // il faut modifier le plateau à chaque fois qu'on (re)commence un niveau
		this.plateau = plateau;
		this.plateau.jouer(); 
	}	
	
	/**
	 * Méthode qui demande si le joueur veut rejouer
	 * @param niveau
	 * @return booléan
	 */
	public boolean startAgain() {
		if (this.plateau.isGameOver() && this.vue.startAgain()) { // si on est gameOver et que le joueur a choisi de rejouer 
			this.startniveau(); // on relance le niveau
			return true; // et on renvoie true
		}
		return false; // sinon false
	}
	
	/**
	 * Si le niveau est gagné, on propose à l'utilisateur d'avancer au niveau suivant
	 * Où de choisir un autre niveau
	 * @return
	 */
	public boolean choiceOrNext() {
		//si true, l'utilisateur à choisit "choix"
		if (this.vue.choiceOrNext()) {
			return true;
		}
		//si false, l'utilisateur à choisit "next"
		return false;
	}
	
	/**
	 * Méthode qui sauvegarde la partie en cours (= l'objet Joueur)
	 * @throws IOException
	 */
	public void save() throws IOException {
		FileOutputStream fileOut = new FileOutputStream("../sauvegardes/" + this.joueur.getNom().toLowerCase() + ".ser");
	    ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    out.writeObject(this.joueur);
	    out.close();
	}
	
	/**
	 * Charge la sauvegarde d'un joueur
	 * @param name
	 * @return booléen true si le chargement a fonctionné
	 */
	private boolean loadJoueur(String name) {
		try {
			FileInputStream fileIn = new FileInputStream("../sauvegardes/" + name.toLowerCase() + ".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);

		    this.joueur = (Joueur) in.readObject();
		    in.close();
		    fileIn.close();
		    return true;
		}
		catch (IOException e) {
			return false; // false si le fichier n'existe pas
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	    
	}
	
	/**
	 * Getter de l'attribut jouer
	 * @return Joueur this.joueur
	 */
	public Joueur getJoueur() {
		return this.joueur;
	}
	
	
	public void game() {
		boolean exit = false;
		this.startniveau();
		while (!exit) {
			if (this.plateau.exit) {
				this.plateau.exit = false;
				this.niveau = this.choixNiveau();
				this.startniveau();
			}
			if (this.plateau.isGameOver()) {
				// si game over, on demande start again
				exit = this.startAgain();
				if (exit == false) {break;} //TODO pour l'instant on sort juste de la boucle. - esk ça sort vraiment de la boucle ?
			}
			if (this.plateau.isWin()) {
				this.joueur.update(this.niveau.getNumero(), this.plateau.getScore());
				try {
					this.save();
				} catch (IOException e) {
					System.out.println("Impossible de sauvegarder");
				}
				// si win, on demande choice or next
				// choice or next = true > choix niveau
				if (this.choiceOrNext()) {
					this.niveau = this.choixNiveau();
					this.startniveau();
				}
				// choice or next = false > next niveau
				else {
					System.out.println("lance niveau suivant");
					this.niveau = new Niveau(this.niveau.getNumero() + 1);
					System.out.println(this.niveau.getNumero());
					this.startniveau();
				}
			}
		}
	}
}
