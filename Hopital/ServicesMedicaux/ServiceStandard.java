package ServicesMedicaux;

import java.util.ArrayList;
import java.util.List;

import Creatures.Creature;

public class ServiceStandard extends ServiceMedical{


	
    public ServiceStandard(String nom, double superficie, int capaciteMax, int budget) {
		super(nom, superficie, capaciteMax, budget);
	}

	@Override
	public void reviserBudget() {
	    conversionNiveauBudget(getCapital());
	}

	@Override
	public void afficherDetails() {
		String emojiCreature = emoji();
        System.out.println(color.MAGENTA_BOLD+ "\n🩹 Service :  " + getNom() + color.RESET);
        System.out.println("  Capacité : " + getCapaciteMax());
        System.out.println("  Budget : " + getBudget() + " (" +getCapital()+")");
        for (Creature creature : getCreatures()) {
            System.out.println("     " + emojiCreature + " " + creature.getNom() + ", Moral: " + creature.getMoral() + " " + creature.getMaladies() );
        }
	}
	
	
}