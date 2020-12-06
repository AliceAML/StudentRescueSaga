import java.util.Scanner;

public class Environnement {
	Visible vue;
	
	public Environnement(Visible vue) {
		this.vue = vue; // choix de la vue lors de la création de l'environnement
	}
	
	private Niveau choixNiveau() {
		//on demande au joueur de selectionner un niveau 
		// ces 3 lignes correspondent à une fonction de la vue "choixNiveau"
		Scanner sc = new Scanner(System.in);
		System.out.println("Choisissez un niveau : "); // TODO affichage + interaction à bouger dans vue
		String n = sc.next();
		
		Niveau niveau = new Niveau(Integer.valueOf(n));
		return niveau;
	}
	
	public void startniveau() {
		Niveau niveau = this.choixNiveau();
		Plateau plateau = new Plateau(niveau, vue);
		this.vue.setPlateau(plateau); // il faut modifier le plateau à chaque fois qu'on (re)commence un niveau
		plateau.jouer();
	}
	
	
	
	public static void main(String[] args) {
		VueText vue = new VueText();
		Environnement env = new Environnement(vue);
		env.startniveau();
	}
}
