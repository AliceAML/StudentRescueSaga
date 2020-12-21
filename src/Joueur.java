
public class Joueur {
	private int[] topScores = new int[10]; // initialisé arbritrairement à 10, parce qu'on va probablement pas faire plus de 10 niveaux 
	private String nom;
	
	public Joueur(String nom) {
		this.nom = nom;
	}
	
	public void update(int niveau, int score) {
		topScores[niveau] = score;
	}
}
