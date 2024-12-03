package Creatures;

import Enum.Categorie;

public class Reptilien extends Creature{
	public Reptilien(String nom, String sexe, double poids, double taille, String age) {
		super(nom, sexe, poids, taille, age);
	}
	
	@Override
	public Categorie getCategorie() {
		return Categorie.VIP; // Reptilien est une créature VIP
	}

}
