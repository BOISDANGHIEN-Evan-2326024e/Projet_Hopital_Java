package Model;

public class Zombie extends Creature{
	public Zombie(String nom, String sexe, double poids, double taille, String age) {
		super(nom, sexe, poids, taille, age);
	}
	
	@Override
    public void trepasser(ServiceMedical service) {
        moral = 0;
        System.out.println(nom + " (Zombie) est décédé mais il se régénère immédiatement après sa mort !");
        regenerer();
    }

    private void regenerer() {
        moral = 1000; 
    }

	@Override
	public Categorie getCategorie() {
		return Categorie.TRIAGE; 
	}

}
