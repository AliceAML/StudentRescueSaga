import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class VueText implements Visible {
	
	private Plateau plateau;
	private Joueur joueur;
	
	String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private Scanner scanReponse = new Scanner(System.in);

	public VueText() {
		this.plateau = null; // la vue n'a pas de plateau à l'initialisation, elle s'ouvre sur l'environnement
		this.joueur = new Joueur("John Doe");
	}
//	
//	public VueText(Plateau plateau) {
//get		this.plateau = plateau;
//	}
	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}
	
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
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
		System.out.println("Fusées : " + this.plateau.getNbFusees() + " Marteaux : " + this.plateau.getNbMarteaux());

		
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
	
	public void move() throws ArrayIndexOutOfBoundsException {
		//TODO il faudrait qu'on puisse écrire fusee, marteau et
		// même les coordonnées de plusieurs façon différentes pour éviter les misspell errors

		//on vient chercher la String de cordonnées lue dans le scanner
		System.out.println("Coordonnées à détruire (A1, C7...) : ");
		String reponse = scanReponse.next().toUpperCase();
		// normalisation de reponse pour supprimer les accents
		//on stockera les deux indexs correspondants dans un int[]
		//on regarde d'abord si l'utilisateur veut utiliser des coups spéciaux ou des commandes
		
		
		if (reponse.equals("HELP")) {
			this.help();
			this.afficherPlateau();
			this.move();
		}
		else if (reponse.equals("EXIT")) {
			this.exit();
		}
		else if (reponse.equals("FUSEE") || reponse.equals("FUSÉE")) {
			if (this.plateau.getNbFusees() > 0) {
				String colonne = scanReponse.next().toUpperCase();
				int y = alphabet.indexOf(colonne)+1;
				try {
					this.plateau.fuseeDestroy(y);
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Cette colonne n'existe pas");
					this.move();
				}
			}
			else {
				System.out.println("Vous n'avez plus de fusées...");
				this.move();
			}
			//FIXME le plateau s'affiche une nouvelle fois après le message alors qu'il faudrait juste remettre .move(). 
		}
		
		else if (reponse.equals("MARTEAU")) {
			if (this.plateau.getNbMarteaux() > 0) {
				String coordonnées = scanReponse.next();
				int[] coord = new int[2];
				coord[1] = alphabet.indexOf(coordonnées.charAt(0))+1;
				coord[0] = Integer.valueOf(coordonnées.substring(1));
				try {
					this.plateau.marteauDestroy(coord[0], coord[1]);
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Cette case n'existe pas.");
					this.move();
				}
			}
			else {
				System.out.println("Vous n'avez plus de marteaux...");
				this.move();
				//FIXME destroy est appelé sur les coordonnées sans raison apparente du coup le programme affiche l'erreur case isolée
			}
		}
		//if(reponse.length() == 2) {
		else {
			try {
				int[] coord = new int[2];
				coord[1] = alphabet.indexOf(reponse.charAt(0))+1;
				coord[0] = Integer.valueOf(reponse.substring(1));
				try {
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
					else {
						//on vérifie que la boite ne soit pas seule.
						if (!this.plateau.isAlone(coord[0], coord[1])) {
							this.plateau.destroy(coord[0], coord[1]);
							}
						else {
							System.out.println("Cette boîte est isolée et ne peut être détruite.");
							this.move();
						}
						}
						
				}
				catch (ArrayIndexOutOfBoundsException e) { // cas où l'utilisateur choisit une case hors du plateau
					System.out.println("Cette case n'existe pas.");
					this.move();
				}
			}
			catch (NumberFormatException e) {
				System.out.println("Désolée, je n'ai pas compris.");
				this.move();
			}
		}
	}

	@Override
	public void help() {
		System.out.println("\n  / // / ___   / /   ___ \n" + 
				" / _  / / -_) / /   / _ \\\n" + 
				"/_//_/  \\__/ /_/   / .__/\n" + 
				"                  /_/   ");
		System.out.println("Il faut détruire les blocs de chiffres pour faire descendre les @"); // TODO compléter l'aide
		System.out.println("Pour détruire un bloc et les blocs adjacents de la même couleur, taper les coordonnées \"A5"
				+ "\"");
		System.out.println("Pour utiliser la fusée qui détruit une colonne, taper \"fusee A\"");
		System.out.println("Pour utiliser le marteau qui détruit un bloc isolé, taper \"marteau A2\"");
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
		// TODO plutôt this.welcome() ? comment rebooter l'env ?...
		// FIXME ça ne marche pas !
	}

	@Override
	public boolean getPlateauGameOver() {
		return this.plateau.isGameOver();
	}
	
	public boolean getPlateauWin() {
		return this.plateau.isWin();
	}
	
	private static void displayLevels() { // TODO ajouter les scores
		
		System.out.println("Niveaux disponibles : ");
		File levels = new File("./levels/"); // FIXME 2 points ou 1 ? pas pareil dans eclipse et dans le terminal !
		// regarder ici : https://stackoverflow.com/questions/437382/how-do-relative-file-paths-work-in-eclipse
		// faire en sorte que ça marche aussi sur Windows avec file separator
		// + utiliser getResourceAsStream
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
		else if (ans.equals("n")) {
			return false;
		}
		else {return this.startAgain();}
		
	}
	
	public boolean choiceOrNext() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Choisir un niveau ou continuer ? (new/next) ");
		String ans = sc.next();
		if (ans.equals("next")) { // si next > on lance le prochain niveauo
			return false;
		}
		
		else if (ans.equals("new")) {
			return true; //si new > n renvoie true pour déclencer un choixNiveau
		}
		//sinon, mauvaise saisie, on redemande
		else {
			System.out.println("je n'ai pas compris");
			return this.choiceOrNext();
		}
	}
}
