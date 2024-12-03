package Creatures;
import java.util.Random;

import Enum.Categorie;
import Maladie.Maladie;
import ServicesMedicaux.ServiceMedical;

public class Vampire extends Creature{
	public Vampire(String nom, String sexe, double poids, double taille, String age) {
		super(nom, sexe, poids, taille, age);
	}

	@Override
	public void trepasser(ServiceMedical service) {
		setMoral(0);
		System.out.println(getNom() + " (Vampire) est décédé.");
		demoraliser(service);
		contaminer(service);
		regenerer();
	}

	private void demoraliser(ServiceMedical service) {
		System.out.println(getNom() + " démoralise les autres créatures dans le service " + service.getNom() + ".");
		for (Creature creature : service.getCreatures()) {
			if (!creature.equals(this)) {
				setMoral(getMoral() - 30); // Réduit le moral des autres de 30
			}
		}
	}

	private void contaminer(ServiceMedical service) {
		if (!maladies.isEmpty()) {
			System.out.println(getNom() + " contamine une autre créature dans le service " + service.getNom() + ".");
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
		System.out.println(getNom() + " se régénère immédiatement après sa mort !");
		setMoral(1000);
	}

	@Override
	public Categorie getCategorie() {
		return Categorie.VIP; 
	}

}
