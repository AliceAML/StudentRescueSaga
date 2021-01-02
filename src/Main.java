import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Environnement env = new Environnement(null);
		int choix;
		
		if (args.length > 0) {
			choix = Integer.parseInt(args[0]);
		}
		else {
			System.out.println("Choisir une version du jeu : \n Vue textuelle ('1') Vue Graphique ('2')");
			Scanner sc = new Scanner(System.in);
			choix = sc.nextInt();
		}
		if (choix == 2) {
			VueGUI vue = new VueGUI();
			env = new Environnement(vue);
		}
		else if (choix == 1) {
			VueText vue = new VueText();
			env = new Environnement(vue);
		}
		env.init();
		
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
					System.out.println("Impossible de sauvegarder");
				}
				// si win, on demande choice or next
				// choice or next = true > choix niveau
				if (env.choiceOrNext()) {
					env.niveau = env.choixNiveau();
					env.startniveau();
				}
				// choice or next = false > next niveau
				else {
					System.out.println("lance niveau suivant");
					env.niveau = new Niveau(env.niveau.getNumero() + 1);
					System.out.println(env.niveau.getNumero());
					env.startniveau();
				}
			}
		}
		
	}
	
}
