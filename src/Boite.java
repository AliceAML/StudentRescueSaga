/**
 * 
 * boites de couleurs qui remplissent le plateau
 *
 */
public class Boite extends Element {
	private int couleur;
	Plateau plateau;

	public Boite(int couleur) {
		this.couleur = couleur;
	}
	
	public int getCouleur() {
		return this.couleur;
	}
	
	public String toString() {
		return Integer.toString(couleur);
	}
}
