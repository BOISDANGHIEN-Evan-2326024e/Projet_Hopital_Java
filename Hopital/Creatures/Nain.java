package Model;

public class Nain extends Creature{

	public Nain(String nom, String sexe, double poids, double taille, String age) {
		super(nom, sexe, poids, taille, age);
	}
	
	@Override
	public Categorie getCategorie() {
		return Categorie.VIP; // Nain est une créature VIP
	}
	
	

}
