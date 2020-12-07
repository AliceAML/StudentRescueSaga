import java.util.Scanner;

public class Environnement {
	Visible vue;
	
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
	
	public static void main(String[] args) {
		VueText vue = new VueText();
		Environnement env = new Environnement(vue);
		Niveau niveau = env.choixNiveau();
		env.startniveau(niveau);
		while (env.vue.getPlateauGameOver() && env.startAgain(niveau)) {
			
		}
	}
}
