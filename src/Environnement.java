import java.util.Scanner;

public class Environnement {
	Visible vue;
	Niveau niveau;
	
	public Environnement(Visible vue) {
		this.vue = vue; // choix de la vue lors de la création de l'environnement
		this.vue.welcome();
	}
	
	/**
	 * Choix du niveau, reçois int renvoyé par la vue
	 * @return
	 */
	private Niveau choixNiveau() {
		// sc.close(); FIXME pourquoi jouer() ne marche plus si on close le scanner ?
		int n = this.vue.choixNiveau();
		Niveau niveau = new Niveau(n);
		this.niveau = niveau;
		return niveau;
	}
	
	/**
	 * Méthode qui lance le niveau choix (entré en paramètre)
	 */
	public void startniveau(Niveau niveau) {
		Plateau plateau = new Plateau(niveau, vue);
		this.vue.setPlateau(plateau); // il faut modifier le plateau à chaque fois qu'on (re)commence un niveau
		plateau.jouer();
	}	
	
	/**
	 * Méthode qui relance le même niveau si game over & le joueur choisi de rejouer
	 * @param niveau
	 * @return
	 */
	public boolean startAgain(Niveau niveau) {
		if (this.vue.getPlateauGameOver() && this.vue.startAgain()) {
			this.startniveau(niveau);
			return true;
		}
		return false;
	}
	
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
		
		VueText vue = new VueText();
		Environnement env = new Environnement(vue);
		env.choixNiveau();
		boolean exit = false;
		env.startniveau(env.niveau);
		// tant que exit est true, on continue.
		while (!exit) {
			if (env.vue.getPlateauGameOver()) {
				// si game over, on demande start again
				exit = env.startAgain(env.niveau);
				if (exit == false) {break;} //pour l'instant on sort juste de la boucle.
				//TODO trouver un moyen de revenir à l'accueuil si exit : false
				//TODO ajouter une commande exit à tous les scanners pour pouvoir exit à tout moment du jeu.
			}
			if (env.vue.getPlateauWin()) {
				// si win, on demande choice or next
				// choice or next = true > choix niveau
				if (env.choiceOrNext()) {
					env.niveau = env.choixNiveau();
					env.startniveau(env.niveau);
				}
				// choice or next = false > next niveau
				else {
					env.niveau = new Niveau(env.niveau.getNumero() + 1);
					env.startniveau(env.niveau);
				}
			}
		}
	}
}
