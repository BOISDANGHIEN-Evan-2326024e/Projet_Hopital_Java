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
    public void reviserBudget() {
        int coefficientIsolation = isolation ? 400 : -200; // Bonus si isolé, malus sinon
        int budgetService = capital + coefficientIsolation;
        conversionNiveauBudget(budgetService);
        System.out.println("Révision du budget du Centre de Quarantaine " + budget + 
            ". Isolation : " + (isolation ? "Oui" : "Non"));
    }
    
	@Override
	public void afficherDetails() {
		String emojiCreature = emoji();
        System.out.println(color.RED_BOLD + "\n☢️ Service : " + getNom() + color.RESET);
        System.out.println("  Capacité : " + getCapaciteMax());
        System.out.println("  Isolation : " + isolation);
        System.out.println("  Budget : " + getBudget() + " (" +getCapital()+")");
        for (Creature creature : getCreatures()) {
            System.out.println("     " + emojiCreature + " " + creature.nom + ", Moral: " + creature.moral + " " + creature.getMaladies() );
        }
	}
}
