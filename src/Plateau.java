/**
 * 
 * Classe qui contient les réaménagement de plateau 
 * (destroy, shiftDown, shiftLeft, sauvetage des animaux)
 *
 */
public class Plateau {
	protected Element[][] matriceElements;
	int width;
	int height;
	int animauxRestants; // nb d'animaux qu'il reste à sauver
	
	
	public Plateau(Element[][] matriceNiveau, int animauxDuNiveau) {
		
		this.width = matriceNiveau[0].length;
		this.height = matriceNiveau.length;
		
		this.animauxRestants = animauxDuNiveau;
		
		//création matrice avec "marge" de 2 case tout autour de la matrice de niveau
		this.matriceElements = new Element[height+2][width + 2];
		
		for (int y = 1; y < this.matriceElements.length-1; y++) {
			for (int x = 1; x < this.matriceElements[0].length-1; x++) {
				this.matriceElements[y][x] = matriceNiveau[y-1][x-1];
			}
		}
	}
	
	/**
	 * Détruit la boite aux coordonnées sélectionnées et tous les boites de la même couleur à côté d'elle
	 * @param boite
	 */
	public void destroy(int y, int x) {
		if (this.matriceElements[y][x] instanceof Boite) {
			int valeurBoite = ((Boite) matriceElements[y][x]).getCouleur(); // mémorise valeur dans boîte
			matriceElements[y][x] = null; // avant de l'effacer
			
			int[][] boitesAdja = {{y+1,x},{y-1,x},{y,x+1},{y,x-1}}; // coordonnées des boîtes adjacentes
			
			// pour chaque boite de la liste
			for (int[] coordo : boitesAdja) {
				if (this.matriceElements[coordo[0]][coordo[1]] instanceof Boite) { // si c'est une boite
					if (((Boite) this.matriceElements[coordo[0]][coordo[1]]).getCouleur() == valeurBoite) { // de la même couleur
						this.destroy(coordo[0], coordo[1]); // appel récursif de cette méthide
					}
				}
				
			}
		}
		this.shiftDown();
		this.shiftLeft();
	}
	
	/**
	 * réaménage le plateau en faisant descendre les éléments s'il y a du vide en dessous
	 */
	public void shiftDown() {
		for (int x=1; x < this.width+1; x++) { // pour chaque colonne
			for (int y=this.height; y>0; y--) { // pour chaque ligne, à partir de celle tout en bas et en remontant vers le haut
				if (this.matriceElements[y][x] == null) { // si la case est vide
					this.matriceElements[y][x] = this.matriceElements[y-1][x]; // on la remplace par celle du dessus
					this.matriceElements[y-1][x] = null;// et on vide celle du dessus
				}
			}
		}
		this.animauxSauves(); // vérifie si animaux ont été sauvés + les font disparaitre pour préparer le shiftLeft
	}
	
	/**
	 * réaménage le plateau en déplaçant les éléments vers la gauche si une colonne est vide 
	 */
	public void shiftLeft() {
		// pour chaque colonne, si elle est vide, on déplace toutes les colonnes d'un rang vers la gauche
		for (int x=1; x<this.width +1; x++) { // pour chaque colonne
			boolean isEmpty = true; 
			for (int y=1; y<this.height+1; y++) { // pour chaque case de cette colonne
				if (this.matriceElements[y][x] != null) { // si la case n'est pas vide
					isEmpty = false; // alors la colonne n'est pas vide
				}
			}
			
			if (isEmpty) { // si la colonne est vide
				for (int y=1; y<this.height+1; y++) { // pour chaque case de cette colonne
					this.matriceElements[y][x] = this.matriceElements[y][x+1];// on la remplace par la case à droite
					this.matriceElements[y][x+1] = null; // et on vide la case à droite
				}
			}
		}
	}
	
	/**
	 * fonction qui regarde si des animaux sont au sol
	 * les font disparaitre si c'est le cas
	 * renvoie le nb d'animaux sauvés à ce tour
	 * utilisé à la fin de shiftDown, avant shiftLeft
	 */
	public void animauxSauves() {
		for (int x=1; x<this.width+1; x++) {
			if (this.matriceElements[this.height][x] instanceof Animal) {
				this.animauxRestants--; // diminue nb d'animaux restants
				this.matriceElements[this.height][x] = null;
			}
		}
	}
}
