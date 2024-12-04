package Creatures;

import Enum.Categorie;
import ServicesMedicaux.ServiceMedical;

public class Zombie extends Creature{
	
	/**
	 * Zombie
	 * @param nom
	 * @param sexe
	 * @param poids
	 * @param taille
	 * @param age
	 */
	public Zombie(String nom, String sexe, double poids, double taille, String age) {
		super(nom, sexe, poids, taille, age);
	}
	
	/**
	 * void trepasser
	 * Si un zombie meurt, il se regenere
	 */
	@Override
    public void trepasser(ServiceMedical service) {
        setMoral(0);
        System.out.println(getNom() + " (Zombie) est décédé mais il se régénère immédiatement après sa mort !");
        regenerer();
    }

	/**
	 * void regenerer
	 */
    private void regenerer() {
        setMoral(1000); 
    }

    /**
     * Categorie getCategorie
     */
	@Override
	public Categorie getCategorie() {
		return Categorie.TRIAGE; 
	}

}
