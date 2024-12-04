package Creatures;

import Enum.Categorie;

public class Reptilien extends Creature{
	
	/**
	 * Reptilien
	 * @param nom
	 * @param sexe
	 * @param poids
	 * @param taille
	 * @param age
	 */
	public Reptilien(String nom, String sexe, double poids, double taille, String age) {
		super(nom, sexe, poids, taille, age);
	}
	
	/**
	 * Categorie getCategorie
	 */
	@Override
	public Categorie getCategorie() {
		return Categorie.VIP; // Reptilien est une créature VIP
	}

}
