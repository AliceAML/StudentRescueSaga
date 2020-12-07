import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class VueText implements Visible {
	
	private Plateau plateau;
	
	String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private Scanner scanReponse = new Scanner(System.in);

	public VueText() {
		this.plateau = null; // la vue n'a pas de plateau à l'initialisation, elle s'ouvre sur l'environnement
	}
//	
//	public VueText(Plateau plateau) {
//get		this.plateau = plateau;
//	}
	
	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}

	@Override
	public void afficherPlateau() {
		// ligne d'en-tête : une colonne = une lettre
		System.out.print("  "); // 2 espaces pour l'affichage des lignes
		for (int i = 0; i < this.plateau.getWidth(); i++) {
			System.out.print(" " + alphabet.charAt(i));
		}
		System.out.println();
		
		// affichage du plateau
		for (int y = 1; y < plateau.matriceElements.length - 1; y++) {
			System.out.print(y + " "); // affiche numéro de ligne
			for (int x = 1; x < plateau.matriceElements[0].length - 1; x++) {
				if (plateau.matriceElements[y][x] == null) {
					System.out.print("  ");
				} else {
					System.out.print(" " + plateau.matriceElements[y][x].toString());
				}
			}
			System.out.print("  " + y); // ré-affiche numéro de ligne
			System.out.println();
		}
		// copie de la ligne d'en-tête en dessous : une colonne = une lettre
		System.out.print("  "); // 2 espaces pour l'affichage des lignes
		for (int i = 0; i < this.plateau.getWidth(); i++) {
			System.out.print(" " + alphabet.charAt(i));
		}
		System.out.println();
		if (this.plateau.getAnimauxRestants() == 0) {
			System.out.println(String.format("BRAVO ! Vous avez sauvé tous les animaux.\n Score : %d", this.plateau.getScore()));
		}
		else if (this.plateau.isGameOver()) {
			System.out.println("GAME OVER - aucun move disponible.");
		}
		else {
			System.out.println(String.format("Score : %d -- Animaux à sauver : %d \n", this.plateau.getScore(), this.plateau.getAnimauxRestants()));
		}
		
	}

	@Override
	public void miseAjourPlateau() {
		this.afficherPlateau(); // mise à jour = nouvel affichage
		
	}

	@Override
	public void afficherEnv() {
		// TODO définir afficherEnv

	}

	@Override
	
	public void move() {
		// FIXME il faudrait ouvrir le scanner ici ? et le fermer ?...
		
		//on vient chercher la String de cordonnées lue dans le scanner
		System.out.println("Coordonnées à détruire (A1, C7...) : ");
		String reponse = scanReponse.next();
		//on stocke les deux indexs correspondants dans un int[]
		if (reponse.equals("help")) {
			this.help();
			this.afficherPlateau();
			this.move();
		}
		int[] coord = new int[2];
		coord[1] = alphabet.indexOf(reponse.charAt(0))+1;
		coord[0] = Integer.valueOf(reponse.substring(1));
		//on donne les indexs respectifs en argument à destroy si la case est destroyable.
		//si elle contient un animal : message d'erreur, on run move() à nouveau
		if (this.plateau.getCase(coord[0], coord[1]) instanceof Animal) {
			System.out.println("cette case contient un animal");
			this.move();
		}
		//si elle est vide : message d'erreur, on run move() à nouveau
		else if (this.plateau.getCase(coord[0], coord[1]) == null) {
			System.out.println("cette case est deja vide");
			this.move();
		}
		else if (this.plateau.getCase(coord[0], coord[1]) instanceof Obstacle) {
			System.out.println("On ne peut pas détruire un obstacle.");
			this.move();
		}
		//si elle contient une couleur, on la destroy()
		else {this.plateau.destroy(coord[0], coord[1]);}
	}

	@Override
	public void help() {
		System.out.println("\n  / // / ___   / /   ___ \n" + 
				" / _  / / -_) / /   / _ \\\n" + 
				"/_//_/  \\__/ /_/   / .__/\n" + 
				"                  /_/   ");
		System.out.println("Il faut détruire les blocs de chiffres pour faire descendre les @"); // TODO compléter l'aide
		System.out.println("Pour utiliser la fusée qui détruit une colonne, taper \"fusée A\"");
		System.out.println("Pour utiliser le marteau qui détruit tous les blocs d'un chiffre, taper \"marteau 2\"");
		System.out.println("\nAppuyer sur entrée pour reprendre le jeu");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
//		sc.close(); // FIXME si je close ça fait buguer move !
		
	}

	@Override
	public void welcome() {
		System.out.println("       __                                   \n" + 
				"      / /  ____ _ _   __  ____ _            \n" + 
				" __  / /  / __ `/| | / / / __ `/            \n" + 
				"/ /_/ /  / /_/ / | |/ / / /_/ /             \n" + 
				"\\____/__ \\__,_/  |___/  \\__,_/              \n" + 
				"   / __ \\  ___    _____  _____  __  __  ___ \n" + 
				"  / /_/ / / _ \\  / ___/ / ___/ / / / / / _ \\\n" + 
				" / _, _/ /  __/ (__  ) / /__  / /_/ / /  __/\n" + 
				"/_/_|_|_ \\___/ /____/  \\___/  \\__,_/  \\___/ \n" + 
				"  / ___/  ____ _   ____ _  ____ _           \n" + 
				"  \\__ \\  / __ `/  / __ `/ / __ `/           \n" + 
				" ___/ / / /_/ /  / /_/ / / /_/ /            \n" + 
				"/____/  \\__,_/   \\__, /  \\__,_/             \n" + 
				"                /____/                      \n" + 
				"\n" + 
				"");
		System.out.println("Détruisez tous les blocs de chiffres \npour faire tomber les @ en bas du plateau !\n");
		
	}

	@Override
	public void exit() {
		// TODO définir exit() > renvoie à l'environnement...
		
	}

	@Override
	public boolean getPlateauGameOver() {
		return this.plateau.isGameOver();
	}
	
	private static void displayLevels() {
		System.out.println("Niveaux disponibles : ");
		File levels = new File("./levels/");
		ArrayList<String> listLevelNames = new ArrayList<String>(); // liste pour stocker noms des niveaux
		for (File level : levels.listFiles()) {
			listLevelNames.add(level.getName());
		}
		Collections.sort(listLevelNames);
		for (String level : listLevelNames) {
			System.out.println(level);
		}
	}

	@Override
	public int choixNiveau() {
		displayLevels();
		//on demande au joueur de selectionner un niveau 
		// ces 3 lignes correspondent à une fonction de la vue "choixNiveau"
		Scanner sc = new Scanner(System.in);
		System.out.println("Choisissez un niveau : "); // TODO affichage + interaction à bouger dans vue
		int n = sc.nextInt();
//		sc.close(); // FIXME pourquoi ne peut-on pas fermer le scanner ? ça case jouer()
		return n;
	}

	@Override
	public boolean startAgain() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Réessayer ? (o/n) ");
		String ans = sc.next();
		if (ans.equals("o")) { // si o > on renvoie true pour déclencer un redémarrage dans env
			return true;
		}
		return false;
	}
	
}
