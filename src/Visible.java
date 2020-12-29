import java.io.IOException;

/**
 * Interface avec les fonctions nécessaires pour chacun des vues (texte ou graphique)
 * 
 */
public interface Visible {


	/** 
	 * Méthode qui affiche le plateau
	 * @throws IOException 
	 */
	public void afficherPlateau();
	
	/**
	 * méthode qui met à jour le plateau
	 */
	public void miseAjourPlateau();
	
	/**
	 * méthode qui affiche l'environnement
	 */
//	public void afficherEnv();
	
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
	 * @throws IOException 
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
	
	/**
	 * renvoie isWin du plateau
	 * @return
	 */
	public boolean getPlateauWin();
	
	/**
	 * renvoie isGameOver du plateau
	 * @return
	 */
	public boolean getPlateauGameOver();
	
	/**
	 * demande le choix du niveau et le renvoie
	 * @return
	 */
	public int choixNiveau();
	
	
	/**
	 * demande si on veut rejouer
	 * @return
	 */
	public boolean startAgain(); 
	
	/**
	 * demande si on veut choisir un niveau ou faire le prochainj
	 * @return
	 */
	public boolean choiceOrNext();

	public String choixJoueur();

	void setJoueur(Joueur joueur); 
	
	
	void displayLevels();
	

}
