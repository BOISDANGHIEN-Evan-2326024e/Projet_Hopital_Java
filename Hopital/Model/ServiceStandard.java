package Model;

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
        System.out.println("\nService : " + getNom());
        System.out.println("Capacité : " + getCapaciteMax());
        System.out.println("Budget : " + getBudget() + " (" +getCapital()+")");
        for (Creature creature : getCreatures()) {
            System.out.println(creature.nom + ", Moral: " + creature.moral + " " + creature.getMaladies() );
        }
	}
}