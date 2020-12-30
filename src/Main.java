import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("Choisir une version du jeu : /n Vue textuelle ('1') Vue Graphique ('2')");
		Scanner sc = new Scanner(System.in);
		Environnement env = new Environnement(null);
		if (sc.nextInt() == 2) {
			VueGUI vue = new VueGUI();
			env = new Environnement(vue);
		}
		else if (sc.nextInt() == 1) {
			VueText vue = new VueText();
			env = new Environnement(vue);
		}
		env.init();
		env.startniveau();
		
	}
	
}
