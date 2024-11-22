package Model;

public class Vampire extends Creature{
	public Vampire(String nom, String sexe, double poids, double taille, String age) {
		super(nom, sexe, poids, taille, age);
	}

	public void demoraliser() {
		System.out.println(nom + " démoralise son entourage !");
	}
	
	public void regenerer() {
		System.out.println(nom + " se régenere !");
	}
	
	@Override
	public Categorie getCategorie() {
		return Categorie.VIP; 
	}

}
