package Creatures;

import Enum.Categorie;
import ServicesMedicaux.ServiceMedical;

public class Elfe extends Creature {
	public Elfe(String nom, String sexe, double poids, double taille, String age) {
		super(nom, sexe, poids, taille, age);
	}

	public void demoraliser() {
		System.out.println(getNom() + " démoralise son entourage !");
	}


    @Override
    public void trepasser(ServiceMedical service) {
        setMoral(0);
        System.out.println(getNom() + " (Elfe) est décédé.");
        demoraliser(service);
        service.retirerCreature(this);
    }

    private void demoraliser(ServiceMedical service) {
        System.out.println(getNom() + " démoralise les autres créatures dans le service " + service.getNom() + ".");
        for (Creature creature : service.getCreatures()) {
            if (!creature.equals(this)) {
            	setMoral(getMoral() - 200);
            }
        }
    }
    
	@Override
	public Categorie getCategorie() {
		return Categorie.VIP; // Elfe est une créature VIP
	}

}
