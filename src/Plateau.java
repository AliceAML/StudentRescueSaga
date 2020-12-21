
/**
 * 
 * Classe qui contient les réaménagement de plateau 
 * (destroy, shiftDown, shiftLeft, sauvetage des animaux)
 *
 */
public class Plateau {
	protected Element[][] matriceElements; // protected car doit pouvoir être modifié par les vues
	private int width;
	private int height;
	private int animauxRestants; // nb d'animaux qu'il reste à sauver
	private Niveau niveau;
	private Visible vue;
	private int score = 0;
	private int boitesDetruites = 0; // utilisé pour calculer le score dans updateScore
	private int nbFusee = 0;
	private int nbMarteaux = 0;//instancié à 1 mais il faudrait ajouter l'info dans le level puis l'extraire dans le constructeur

	/**
	 * Constructeur de plateau à partir d'un niveau + une vue
	 * @param niveau
	 * @param vue
	 */
	public Plateau(Niveau niveau, Visible vue) {
		
		this.niveau = niveau;
		this.width = niveau.getMatrice()[0].length;
		this.height = niveau.getMatrice().length;
		this.vue = vue;
		this.animauxRestants = niveau.getAnimauxASauver();
		this.nbFusee = niveau.getNbFusees();
		this.nbMarteaux = niveau.getNbMarteaux();
		
		//création matrice avec "marge" de 2 case tout autour de la matrice de niveau
		this.matriceElements = new Element[height+2][getWidth() + 2];
		
		for (int y = 1; y < this.matriceElements.length-1; y++) {
			for (int x = 1; x < this.matriceElements[0].length-1; x++) {
				this.matriceElements[y][x] = niveau.getMatrice()[y-1][x-1];
			}
		}
	}
	
	public void setVue(Visible vue) {
		this.vue = vue;
	}
	
	public Element getCase(int x, int y) {
		return this.matriceElements[x][y];
	}
	
	public boolean isAlone(int y, int x) { 
		//on part avec 0 boites adjacentes
		int adja = 0;
		if (this.matriceElements[y][x] instanceof Boite) {
			int valeurBoite = ((Boite) matriceElements[y][x]).getCouleur();
			int[][] boitesAdja = {{y+1,x},{y-1,x},{y,x+1},{y,x-1}};
			for (int[] coordo : boitesAdja) {
				//on parcourt toutes les boites adjacentes.
				if (this.matriceElements[coordo[0]][coordo[1]] instanceof Boite) { // si c'est une boite
					if (((Boite) this.matriceElements[coordo[0]][coordo[1]]).getCouleur() == valeurBoite) {
						//si on en trouve au moins une de la même couleur :
						//bool devient false, la boite n'est pas seule
						adja ++;
					}
				}
			}	
		}		
		return (adja==0);
		//return false;
	}
	
	/**
	 * Détruit la boite aux coordonnées sélectionnées et tous les boites de la même couleur à côté d'elle
	 * @param boite
	 */
	public void destroy(int y, int x) { // FIXME il ne faut pas que ça marche si la boîte n'a pas de voisine de la même couleur !
		if (this.matriceElements[y][x] instanceof Boite) {
			int valeurBoite = ((Boite) matriceElements[y][x]).getCouleur(); // mémorise valeur dans boîte
			matriceElements[y][x] = null; // avant de l'effacer
			this.boitesDetruites++; // on compte la boite détruite
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
	
	public void fuseeDestroy(int y) {
		if (this.nbFusee > 0) {
			for (int i = 0; i <= this.height; i++) {
				if (this.matriceElements[i][y] instanceof Boite) {
					this.matriceElements[i][y] = null;
				}
				this.shiftDown();
				this.shiftLeft();
			}
			this.nbFusee--;
		}
	}
	
	//méthode destroy adaptée à marteau qui diminue le nb de marteaux.
	public void marteauDestroy(int y, int x) throws ArrayIndexOutOfBoundsException {
		if (this.nbMarteaux > 0) {
			this.destroy(y, x);
			this.nbMarteaux--;
		}
	}
	
	
	/**
	 * réaménage le plateau en faisant descendre les éléments s'il y a du vide en dessous
	 */
	public void shiftDown() {
		for (int x=1; x < this.getWidth()+1; x++) { // pour chaque colonne
			for (int y=this.height; y>0; y--) { // pour chaque ligne, à partir de celle tout en bas et en remontant vers le haut
				if (this.matriceElements[y][x] == null) { // si la case est vide
					// on la remplace par celle du dessus, sauf si c'est un obstacle
					if (!(this.matriceElements[y-1][x] instanceof Obstacle)) {
						this.matriceElements[y][x] = this.matriceElements[y-1][x]; 
						this.matriceElements[y-1][x] = null;// et on vide celle du dessus
					}
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
		for (int x=1; x<this.getWidth() +1; x++) { // pour chaque colonne
			boolean isEmpty = true; 
			for (int y=1; y<this.height+1; y++) { // pour chaque case de cette colonne
				if (this.matriceElements[y][x] != null && !(this.matriceElements[y][x+1] instanceof Obstacle)) { 
					// si la case n'est pas vide et pas un obstacle
					isEmpty = false; // alors la colonne n'est pas vide
				}
			}
			
			if (isEmpty) { // si la colonne est vide
				for (int y=1; y<this.height+1; y++) { // pour chaque case de cette colonne
					// on la remplace par la case à droite, sauf si c'est un obstacle
					if (!(this.matriceElements[y][x+1] instanceof Obstacle)) {
						this.matriceElements[y][x] = this.matriceElements[y][x+1];
						this.matriceElements[y][x+1] = null; // et on vide la case à droite
					}
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
		for (int x=1; x<this.getWidth()+1; x++) {
			if (this.matriceElements[this.height][x] instanceof Animal) {
				this.animauxRestants--; // diminue nb d'animaux restants
				this.matriceElements[this.height][x] = null;
				this.shiftDown(); // nécessaire au cas où il y a un autre animal sur celui qu'on vient de sauver
			}
		}
	}

	public int getWidth() {
		return width;
	}
	
	public void jouer() {
		//commencement de la partie : 
		try {
			this.vue.afficherPlateau();
		}
		catch (NullPointerException e) {
			System.out.println("Il faut set le plateau de la vue."); // FIXME pas de println dans le plateau
		}
		//nombre de moves executés
		int moves = 0;
		//tant qu'il reste des animaux :
		while (this.animauxRestants != 0 && !this.isGameOver()) {
			this.vue.move();
			// on met à jour le score après le move
			//on incrémente notre nombre de moves
			moves++;
			this.updateScore();
			//on affiche le plateau
			this.vue.afficherPlateau();	
		}
	}
	
	/**
	 * Le score vaut le nb de boites détruites au carré x 10
	 * + 1000 points par animal sauvé
	 */
	private void updateScore() {
		this.score = (this.boitesDetruites*this.boitesDetruites*10) + (this.niveau.getAnimauxASauver() - this.animauxRestants)*1000;
	}
	
	public int getScore() {
		return score;
	}

	public int getAnimauxRestants() {
		return animauxRestants;
	}
	
	public int getNbFusees() {
		return this.nbFusee;
	}
	
	public int getNbMarteaux() {
		return this.nbMarteaux;
	}
	
	/**
	 * Renvoie true s'il n'y a plus de boites de la même couleur adjacentes, et qu'il reste des animaux à sauver
	 * @return
	 */
	public boolean isGameOver() { // le niveau 0 permet de tester ça facilement
		if (this.animauxRestants == 0 || this.nbFusee!=0 || this.nbMarteaux!=0) {
			return false;
		}
		// on parcours tous les éléments
		for (int y = 1; y < this.matriceElements.length-1; y++) {
			for (int x = 1; x < this.matriceElements[0].length-1; x++) {
				if (this.matriceElements[y][x] instanceof Boite) { // si c'est une boîte de couleur
					int couleurBoite = ((Boite) this.matriceElements[y][x]).getCouleur(); // on stocke la couleur de la boite
					int[][] boitesAdja = {{y+1,x},{y-1,x},{y,x+1},{y,x-1}}; // coordonnées des boîtes adjacentes
					// on parcourt les boîtes adjacentes, si la couleur est la même on renvoie FALSE (un move est possible)
					for (int[] coordo : boitesAdja) {
						if (this.matriceElements[coordo[0]][coordo[1]] instanceof Boite 
								&& couleurBoite == ((Boite) this.matriceElements[coordo[0]][coordo[1]]).getCouleur()) {
							return false;
						}
					}
				}
				
			}
		}
		
		return true; // sinon on a game over
	}
	
	public boolean isWin() { 
		if (this.animauxRestants == 0) {
			return true;
		}
		return false;
	}
	
}
