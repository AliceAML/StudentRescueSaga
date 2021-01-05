import java.io.*;

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
	}

	
	/**
	 * Choix du niveau, reçois int renvoyé par la vue
	 * @return
	 */
	public Niveau choixNiveau() {
		int n = this.vue.choixNiveau();
		Niveau niveau = new Niveau(n);
		this.niveau = niveau;
		return niveau;
	}
	
	/**
	 * Méthode qui lance le niveau choix (entré en paramètre)
	 * @throws IOException 
	 */
	public void startniveau() {
		Plateau plateau = new Plateau(niveau, vue);
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
		if (this.plateau.isGameOver() && this.vue.startAgain()) {
			this.startniveau();
			return true;
		}
		return false;
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
	
	public void save() throws IOException {
		FileOutputStream fileOut = new FileOutputStream("../sauvegardes/" + this.joueur.getNom().toLowerCase() + ".ser");
	    ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    out.writeObject(this.joueur);
	    out.close();
	}
	
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
	
	public Joueur getJoueur() {
		return this.joueur;
	}
	
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
