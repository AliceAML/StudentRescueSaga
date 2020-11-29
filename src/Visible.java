/**
 * Interface avec les fonctions nécessaires pour chacun des vues (texte ou graphique)
 * 
 */
public interface Visible {
	/** 
	 * Méthode qui affiche le plateau
	 */
	public void afficherPlateau();
	
	/**
	 * méthode qui met à jour le plateau
	 */
	public void miseAjourPlateau();
	
	/**
	 * méthode qui affiche l'environnement
	 */
	public void afficherEnv();
	
	/**
	 * méthode qui renvoie les coordonnées de la boîte sélectionnée par le joueur
	 */
	public void move();
}
