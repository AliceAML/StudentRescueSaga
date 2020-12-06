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
	
	/**
	 * méthode qui affiche le panneau d'aide avec les règles du jeu et contrôles
	 * ne pas oublier de permettre au joueur de quitter l'aide
	 */
	public void help();
	
	/**
	 * Affichage à l'accueil du joueur ??
	 */
	public void welcome();
	
	/**
	 * permet de choisir le plateau
	 * @param plateau
	 */
	public void setPlateau(Plateau plateau);
	
	/**
	 * méthode qui permet de quitter un niveau pour se rendre à l'accueil (demander confirmation)
	 */
	public void exit();
}
