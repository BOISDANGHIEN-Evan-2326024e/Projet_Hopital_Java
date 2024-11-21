package Model;

public class CentreQuarantaine extends ServiceMedical {
    private boolean isolation;

    public CentreQuarantaine(String nom, double superficie, int capaciteMax, int budget, boolean isolation) {
        super(nom, superficie, capaciteMax, budget);
        this.isolation = isolation;
    }

    public boolean isIsolation() {
        return isolation;
    }

    public void setIsolation(boolean isolation) {
        this.isolation = isolation;
    }

    @Override
    public void reviserBudget(int nouveauBudget) {
    	setBudget(nouveauBudget);
        /*if (isolation) {
        	System.out.println("Budget pour le centre de quarantaine révisé. Isolation: activée");
        }
        System.out.println("Budget pour le centre de quarantaine révisé. Isolation: désactivée");*/
    }
    
	@Override
	public void afficherDetails() {
        System.out.println("Service : " + getNom());
        System.out.println("Capacité : " + getCapaciteMax());
        System.out.println("Isolation : " + isolation);
        for (Creature creature : getCreatures()) {
            System.out.println(creature.nom + ", Moral: " + creature.moral + creature.getMaladies() );
        }
	}
}
