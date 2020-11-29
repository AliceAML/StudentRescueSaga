
public class VueText implements Visible {
	
	Plateau plateau;
	
	String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

	
	public VueText(Plateau plateau) {
		this.plateau = plateau;
	}

	@Override
	public void afficherPlateau() {
		// ligne d'en-tête : une colonne = une lettre
		System.out.print("  "); // 2 espaces pour l'affichage des lignes
		for (int i = 0; i < this.plateau.width; i++) {
			System.out.print(" " + alphabet[i]);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afficherEnv() {
		// TODO Auto-generated method stub
		
	}
	
}
