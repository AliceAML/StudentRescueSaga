import java.util.Scanner;

public class VueText implements Visible {
	
	Plateau plateau;
	
	String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private Scanner scanReponse = new Scanner(System.in);

	
	public VueText(Plateau plateau) {
		this.plateau = plateau;
	}

	@Override
	public void afficherPlateau() {
		// ligne d'en-tête : une colonne = une lettre
		System.out.print("  "); // 2 espaces pour l'affichage des lignes
		for (int i = 0; i < this.plateau.width; i++) {
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
			System.out.println();
		}
		
	}

	@Override
	public void miseAjourPlateau() {
		this.afficherPlateau(); // mise à jour = nouvel affichage
		
	}

	@Override
	public void afficherEnv() {
		// TODO Auto-generated method stub

	}

	@Override
	
	public void move() {
		
		//on vient chercher la String de cordonnées lue dans le scanner
		System.out.println("COORDONNEES : ");
		String reponse = scanReponse.next();
		//on stocke les deux indexs correspondants dans un int[][]
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
		if (this.plateau.getCase(coord[0], coord[1]) == null) {
			System.out.println("cette case est deja vide");
			this.move();
		}
		//si elle contient une couleur, on la distroy()
		this.plateau.destroy(coord[0], coord[1]);
	}
	
}
