import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		//création d'un environnement selon la vue désirée
		//* on peu soit lancer le main sans argument et choisir 1 ou 2 dans le terminal
		//* ou alors, le main contient lui-même un argument 1 ou 2.
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
		//on lance ensuite l'initialisation puis le jeu lui même
		//(game() étant capable de revenir directement à l'étape du choix des niveaux)
		env.init();
		env.game();
	}
}
