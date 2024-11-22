package Model;

public class Zombie extends Creature{
	public Zombie(String nom, String sexe, double poids, double taille, String age) {
		super(nom, sexe, poids, taille, age);
	}
	
	public void regenerer() {
		System.out.println(nom + " se régenere !");
	}

	@Override
	public Categorie getCategorie() {
		return Categorie.TRIAGE; // Zombie est une créature de triage
	}

}
