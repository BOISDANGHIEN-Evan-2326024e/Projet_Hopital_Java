package Model;
import java.util.Random;

public class Vampire extends Creature{
	public Vampire(String nom, String sexe, double poids, double taille, String age) {
		super(nom, sexe, poids, taille, age);
	}

	@Override
	public void trepasser(ServiceMedical service) {
		moral = 0;
		System.out.println(nom + " (Vampire) est décédé.");
		demoraliser(service);
		contaminer(service);
		regenerer();
	}

	private void demoraliser(ServiceMedical service) {
		System.out.println(nom + " démoralise les autres créatures dans le service " + service.getNom() + ".");
		for (Creature creature : service.getCreatures()) {
			if (!creature.equals(this)) {
				moral = moral - 30; // Réduit le moral des autres de 30
			}
		}
	}

	private void contaminer(ServiceMedical service) {
		if (!maladies.isEmpty()) {
			System.out.println(nom + " contamine une autre créature dans le service " + service.getNom() + ".");
			for (Creature creature : service.getCreatures()) {
				if (!creature.equals(this)) {
					Random random = new Random();
                    Maladie maladie = maladies.get(random.nextInt(maladies.size()));
                    creature.getMaladies().add(maladie);
					break; // Contamine une seule créature
				}
			}
		}
	}

	private void regenerer() {
		System.out.println(nom + " se régénère immédiatement après sa mort !");
		moral = 1000;
	}

	@Override
	public Categorie getCategorie() {
		return Categorie.VIP; 
	}

}
