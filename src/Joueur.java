import java.io.Serializable;

/**
 * Classe qui permet de sauvegarder les parties
 * (nom du joueur, scores, progresion)
 *
 */
public class Joueur implements Serializable {

	private static final long serialVersionUID = 7403199348226761799L;
	
	private int[] topScores = new int[10]; // initialisé arbritrairement à 10, parce qu'on va probablement pas faire plus de 10 niveaux 
	private boolean[] niveauxDebloques = new boolean[10];
	private String nom;
		
	public Joueur(String nom) {
		this.nom = nom;
		niveauxDebloques[1] = true; // on débloque le niveau 1
	}
	
	public void update(int niveau, int score) {
		if (score > topScores[niveau]) { // on change si le score est plus élevé que celui stocké actuellement
			topScores[niveau] = score;
		}
		this.debloquer(niveau+1);
	}
	
	public void debloquer(int niveau) {
		niveauxDebloques[niveau] = true;
	}
	
	public boolean isDebloque(int niveau) {
		return niveauxDebloques[niveau];
	}
	
	public boolean isDebloque(String niveau) {
		return niveauxDebloques[Integer.valueOf(niveau)]; // surcharge car parfois plus pratique de mettre une string
	}
	
	
	public int getScore(int niveau) {
		return topScores[niveau];
	}
	
	public int getScore(String niveau) {
		return topScores[Integer.valueOf(niveau)];
	}
}
