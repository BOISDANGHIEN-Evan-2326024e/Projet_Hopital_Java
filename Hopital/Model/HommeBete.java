package Model;

public class HommeBete extends Creature{

	public HommeBete(String nom, String sexe, double poids, double taille, String age) {
		super(nom, sexe, poids, taille, age);
		
	}
	
	public void contaminer() {
		System.out.println(nom + " contamine son entourage");
	}

	@Override
	public Categorie getCategorie() {
		return Categorie.TRIAGE; // Homme-Bête est une créature de triage
	}

}
