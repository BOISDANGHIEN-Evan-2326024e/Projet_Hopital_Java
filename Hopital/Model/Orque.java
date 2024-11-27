package Model;

public class Orque extends Creature {
    public Orque(String nom, String sexe, double poids, double taille, String age) {
        super(nom, sexe, poids, taille, age);
    }
    
    public void contaminer() {
		System.out.println(nom + " contamine son entourage");
	}

    @Override
    public Categorie getCategorie() {
        return Categorie.TRIAGE; // Orque est une créature de triage
    }
    
}
