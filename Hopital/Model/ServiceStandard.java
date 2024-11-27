package Model;

public class ServiceStandard extends ServiceMedical{
	
    public ServiceStandard(String nom, double superficie, int capaciteMax, int budget) {
		super(nom, superficie, capaciteMax, budget);
	}

	@Override
    public void reviserBudget(int nouveauBudget) {
        setBudget(nouveauBudget);
    }

	@Override
	public void afficherDetails() {
        System.out.println("Service : " + getNom());
        System.out.println("Capacité : " + getCapaciteMax());
        for (Creature creature : getCreatures()) {
            System.out.println(creature.nom + ", Moral: " + creature.moral + creature.getMaladies() );
        }
	}
}