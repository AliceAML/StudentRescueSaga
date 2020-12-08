
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;


//ajout d'un petit commentaire pour tester
// petite réponse pour tester
//réponse en plus pour retester

/**
 * Charge les niveaux depuis les fichiers dans une matrice 
 * Contient aussi les phases du jeu au sein d'un niveau ?
 * création plateau
 * actions du joueur
 * fin de la partie
 * 
 *
 */
public class Niveau {
	private Element[][] matriceElements; // matrice de boites
	private int numeroNiveau;
	private int animauxASauver;  // nombre d'animaux dans ce niveau (ne changera pas)
	private int moves;
	private int nbFusees;
	private int nbMarteaux;
	
	/**
	 * Constructeur de niveau. Sert à charger un niveau depuis un fichier.
	 * @param numeroNiveau
	 */
	public Niveau(int numeroNiveau) {		
		
		this.numeroNiveau = numeroNiveau;
		
		// chemin du fichier niveau sous forme de String et de Path
		String adresse = "./levels/" + Integer.toString(this.numeroNiveau);
		Path path = Paths.get(adresse);
		
		// ouverture du fichier dans un scanner
		try {
			
			// compte nombre de lignes
			// source : https://mkyong.com/java/how-to-get-the-total-number-of-lines-of-a-file-in-java/
			long lines = Files.lines(path).count() - 2;	 // moins 2 car les deux dernières lignes contiennent le "sol" ____ puis fusees/marteaux		
						
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
					} else if (elementCode.equals("/")) {
						matriceElements[y][x] = new Obstacle();
					}
					x++; // élément suivant de la ligne
				}
				y++; // ligne suivante
				x = 0; // on réinitialise la largeur
				row = new Scanner(sc.next());
			}
			this.moves = this.animauxASauver + y; //la formule est (nombre de blocs explosés au carré) fois 10
			
			//pour chercher le nombre de fusees et de marteaux :
			//méthode trouvée sur StackOverflow pour lire directement la dernière ligne
			List<String> allLines = Files.readAllLines(path);
			String lastLine = allLines.get(allLines.size()-1);
		    this.nbFusees = Character.getNumericValue(lastLine.charAt(0));
		    this.nbMarteaux = Character.getNumericValue(lastLine.charAt(1));
			
		} catch (FileNotFoundException e) {
			System.out.println(String.format("Fichier niveau non trouvé (niveau n° %d)",numeroNiveau));
		} catch (IOException e) {
			System.out.println("Ce niveau n'existe pas.");
		}		
	}

	public String toString() {
		String aAfficher = String.format("niveau n° %d, %d x %d", numeroNiveau, matriceElements.length, matriceElements[0].length);
		
		return aAfficher;
	}
	
	protected Element[][] getMatrice() {
		return matriceElements.clone();
	}
	
	protected int getAnimauxASauver() {
		return animauxASauver;
	}

	public int getMoves() {
		return moves;
	}
	
	public int getNumero() {
		return this.numeroNiveau;
	}
	
	public int getNbFusees() {
		return this.nbFusees;
	}
	
	public int getNbMarteaux() {
		return this.nbMarteaux;
	}
}
