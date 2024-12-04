package Creatures;
import java.util.Random;

import Enum.Categorie;
import Maladie.Maladie;
import ServicesMedicaux.ServiceMedical;

public class Vampire extends Creature{
	
	/**
	 * Vampire
	 * @param nom
	 * @param sexe
	 * @param poids
	 * @param taille
	 * @param age
	 */
	public Vampire(String nom, String sexe, double poids, double taille, String age) {
		super(nom, sexe, poids, taille, age);
	}

	/**
	 * Si un vampire meurt, il contamine et demoralise son entourage  et se regenere
	 */
	@Override
	public void trepasser(ServiceMedical service) {
		setMoral(0);
		System.out.println(getNom() + " (Vampire) est décédé.");
		demoraliser(service);
		contaminer(service);
		regenerer();
	}

	/**
	 * void demoraliser
	 * @param service
	 */
	private void demoraliser(ServiceMedical service) {
		System.out.println(getNom() + " démoralise les autres créatures dans le service " + service.getNom() + ".");
		for (Creature creature : service.getCreatures()) {
			if (!creature.equals(this)) {
				setMoral(getMoral() - 30); // Réduit le moral des autres de 30
			}
		}
	}

	/**
     * Une créature peut démoraliser son entourage, le moral de toutes les crétures diminuent
	 * @param service
	 */
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

	/**
	 * Si un vampire meurt, il se regenere
	 */
	private void regenerer() {
		System.out.println(getNom() + " se régénère immédiatement après sa mort !");
		setMoral(1000);
	}

	/**
	 * void Categorie getCategorie
	 */
	@Override
	public Categorie getCategorie() {
		return Categorie.VIP; 
	}

}
