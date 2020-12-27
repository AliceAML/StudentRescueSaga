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
		this.vue.welcome();
		
		String nomJoueur = this.vue.choixJoueur();
		if (!this.loadJoueur(nomJoueur)) { // si on ne peut pas charger le joueur (sinon ça le charge)
			this.joueur = new Joueur(nomJoueur); // on en crée un nouveau
		}
	}

	
	/**
	 * Choix du niveau, reçois int renvoyé par la vue
	 * @return
	 */
	private Niveau choixNiveau() {
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
	
	private void save() throws IOException {
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
		
	public static void main(String[] args) throws IOException {
		Visible vue;
		if (args.length == 0) { // GUI si pas d'arg
			vue = new VueGUI();
		}
		else { // vue text si un arg
			vue = new VueText(); // création de la vue
		}
		Environnement env = new Environnement(vue); // création de l'environnement avec cette vue en attribut
		vue.setJoueur(env.getJoueur());
		env.choixNiveau(); // FIXME problème, dans la vue graphique, ça devrait être déclenché par l'utilisateur, qui cliquerait sur start 
		boolean exit = false;
		env.startniveau();
		// tant que exit est false, on continue.
		while (!exit) {
			if (env.plateau.exit) {
				env.plateau.exit = false;
				env.niveau = env.choixNiveau();
				env.startniveau();
			}
			if (env.plateau.isGameOver()) {
				// si game over, on demande start again
				exit = env.startAgain();
				if (exit == false) {break;} //TODO pour l'instant on sort juste de la boucle. - esk ça sort vraiment de la boucle ?
			}
			if (env.plateau.isWin()) {
				env.joueur.update(env.niveau.getNumero(), env.plateau.getScore());
				try {
					env.save();
				} catch (IOException e) {
					System.out.println("impossile de sauvegarder");
				}
				// si win, on demande choice or next
				// choice or next = true > choix niveau
				if (env.choiceOrNext()) {
					env.niveau = env.choixNiveau();
					env.startniveau();
				}
				// choice or next = false > next niveau
				else {
					env.niveau = new Niveau(env.niveau.getNumero() + 1);
					env.startniveau();
				}
			}
		}
	}
}
