import java.util.Scanner;

public class Environnement {
	Visible vue;
	Niveau niveau;
	Plateau plateau;
	Joueur joueur;
	
	/**
	 * Constructeur de l'environnement
	 * @param vue (text ou graphique)
	 */
	public Environnement(Visible vue, Joueur joueur) {
		this.joueur = joueur;
		this.vue = vue; // choix de la vue lors de la création de l'environnement
		this.vue.welcome();
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
	
	public static void main(String[] args) {
//		VueText vue = new VueText();
//		Environnement env = new Environnement(vue);
//		Niveau niveau = env.choixNiveau();
//		env.startniveau(niveau);
//		while (env.vue.getPlateauGameOver() && env.startAgain(niveau)) {}
//		while (env.vue.getPlateauWin()) {
//			if (env.choiceOrNext()) {
//				Niveau NewNiveau = env.choixNiveau();
//				env.startniveau(NewNiveau);
//			}
//			else {
//				Niveau NextNiveau = new Niveau(niveau.getNumero() + 1);
//				env.startniveau(NextNiveau);
//			}
//		}
		
		Joueur jojo = new Joueur("Jojo"); // TODO méthode qui demande le nom du joueur
		VueText vue = new VueText(); // création de la vue
		vue.setJoueur(jojo);
		Environnement env = new Environnement(vue, jojo); // création de l'environnement avec cette vue en attribut
		env.choixNiveau();
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
				if (exit == false) {break;} //pour l'instant on sort juste de la boucle. - esk ça sort vraiment de la boucle ?
				//TODO trouver un moyen de revenir à l'accueil si exit : false -- plutôt true, non ?
				//TODO ajouter une commande exit à tous les scanners pour pouvoir exit à tout moment du jeu.
			}
			if (env.plateau.isWin()) {
				env.joueur.update(env.niveau.getNumero(), env.plateau.getScore());
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
