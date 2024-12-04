package Creatures;

import Enum.Categorie;

public class Nain extends Creature{

	/**
	 * Nain
	 * @param nom
	 * @param sexe
	 * @param poids
	 * @param taille
	 * @param age
	 */
	public Nain(String nom, String sexe, double poids, double taille, String age) {
		super(nom, sexe, poids, taille, age);
	}
	
	/**
	 * Categorie getCategorie
	 */
	@Override
	public Categorie getCategorie() {
		return Categorie.VIP; // Nain est une créature VIP
	}
	
	

}
