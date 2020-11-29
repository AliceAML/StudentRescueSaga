import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

//ajout d'un petit commentaire pour tester
// petite réponse pour tester

/**
 * Charge les niveaux depuis les fichiers dans une matrice 
 * Contient aussi les phases du jeu au sein d'un niveau ?
 * 	création plateau
 * 	actions du joueur
 * 	fin de la partie
 * 
 *
 */
public class Niveau {
	Element[][] matriceElements; // matrice de boites
	int numeroNiveau;
	Plateau plateau;
	int animauxASauver;  // nombre d'animaux dans ce niveau (ne changera pas)
	
	public Niveau(int numeroNiveau) {		
		
		this.numeroNiveau = numeroNiveau;
		
		// chemin du fichier niveau sous forme de String et de Path
		String adresse = "./levels/" + Integer.toString(this.numeroNiveau);
		Path path = Paths.get(adresse);
		
		
		
		// ouverture du fichier dans un scanner
		try {
			
			// compte nombre de lignes
			// source : https://mkyong.com/java/how-to-get-the-total-number-of-lines-of-a-file-in-java/
			long lines = Files.lines(path).count() - 1;	 // moins 1 car la dernière ligne contient le "sol" ____		
						
			Scanner sc = new Scanner(new File(adresse));
			sc.useDelimiter("\n"); // découpage ligne par ligne
			
			
			String firstRowString = sc.next(); // on récupère première ligne dans une String pour déterminer sa longueur (= la largeur du niveau)
			int width = firstRowString.length()/2 +1;
			Scanner row = new Scanner(firstRowString); 	// on charge la première ligne dans un scanner
			// initialisation de la matrice à la bonne taille
			matriceElements = new Element[(int) lines][width];

			int x = 0;
			int y = 0;
			
			// boucle qui lit toutes les lignes
			while (sc.hasNext()) { 
				while (row.hasNext()) { // boucle de lecture de ligne
					String elementCode = row.next(); 
					if (elementCode.matches("[1-9]")) { // si c'est un chiffre entre 1 et 9, c'est une boite de couleur
						matriceElements[y][x] = new Boite(Integer.parseInt(elementCode));
					} else if (elementCode.equals("a")) {
						matriceElements[y][x] = new Animal();
						this.animauxASauver++; // on compte les animaux pour savoir quand terminer le niveau
					}
					x++; // élément suivant de la ligne
				}
				y++; // ligne suivante
				x = 0; // on réinitialise la largeur
				row = new Scanner(sc.next());
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(String.format("Fichier niveau non trouvé (niveau n° %d)",numeroNiveau));
		} catch (IOException e) {
			System.out.println("Ce niveau n'existe pas.");
		}
		
		this.plateau = new Plateau(matriceElements, animauxASauver);
	}

	public String toString() {
		String aAfficher = String.format("niveau n° %d, %d x %d", numeroNiveau, matriceElements.length, matriceElements[0].length);
		
		return aAfficher;
	}
	
	public static void main(String[] args) {
		Niveau niveau1 = new Niveau(1);
		VueText vueNiveau1 = new VueText(niveau1.plateau);
		vueNiveau1.afficherPlateau();
		System.out.println("Indiquez les coordonnées à détruire : ('D5' 'C6' 'A1' ?)");
		//nombre de moves executés
		int moves = 0;
		//tant qu'il reste des animaux :
		while (niveau1.plateau.animauxRestants != 0) {
			System.out.println();
			//on choisit une cas à cliquer 
			vueNiveau1.move();
			//on incrémente notre nombre de moves
			moves++;
			//on affiche le plateau
			vueNiveau1.afficherPlateau();
			//on affiche le nombre d'animaux restants si != 0.
			if (niveau1.plateau.animauxRestants != 0) {
				System.out.println(String.format("Il reste %d animaux à sauver !", niveau1.plateau.animauxRestants));
			}
		}
		
		System.out.println(String.format("C'est gagné ! Nombre de moves : %d", moves));
		
		
		
		
//		System.out.println();
//		System.out.println("Test destroy 6,2");
//		niveau1.plateau.destroy(6, 2);
//		vueNiveau1.afficherPlateau();
//		System.out.println(String.format("Il reste %d animaux à sauver !", niveau1.plateau.animauxRestants));
//		System.out.println();
//		
//		System.out.println("Test destroy 3,4");
//		niveau1.plateau.destroy(3, 4);
//		vueNiveau1.afficherPlateau();
//		System.out.println(String.format("Il reste %d animaux à sauver !", niveau1.plateau.animauxRestants));
//		System.out.println();
//		
//		System.out.println("Test destroy 7,3 (test shiftLeft)" );
//		niveau1.plateau.destroy(7, 3);
//		vueNiveau1.afficherPlateau();
//		System.out.println(String.format("Il reste %d animaux à sauver !", niveau1.plateau.animauxRestants));
//
//		System.out.println();
//		
//		System.out.println("Test destroy 6,2 (test animauxSauves)" );
//		niveau1.plateau.destroy(6, 2);
//		vueNiveau1.afficherPlateau();
//		System.out.println(String.format("Il reste %d animaux à sauver !", niveau1.plateau.animauxRestants));
//		
//		System.out.println("Test destroy 6,2" );
//		niveau1.plateau.destroy(6, 2);
//		vueNiveau1.afficherPlateau();
//		System.out.println(String.format("Il reste %d animaux à sauver !", niveau1.plateau.animauxRestants));
//		
//		System.out.println("Test destroy 6,2" );
//		niveau1.plateau.destroy(6, 2);
//		vueNiveau1.afficherPlateau();
//		System.out.println(String.format("Il reste %d animaux à sauver !", niveau1.plateau.animauxRestants));
	}
	
}
