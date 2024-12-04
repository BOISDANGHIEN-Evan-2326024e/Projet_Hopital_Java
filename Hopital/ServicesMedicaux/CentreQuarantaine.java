package ServicesMedicaux;

import Creatures.Creature;

public class CentreQuarantaine extends ServiceMedical {
    private boolean isolation;

    /**
     * CentreQuarantaine
     * @param nom
     * @param superficie
     * @param capaciteMax
     * @param budget
     * @param isolation
     */
    public CentreQuarantaine(String nom, double superficie, int capaciteMax, int budget, boolean isolation) {
        super(nom, superficie, capaciteMax, budget);
        this.isolation = isolation;
    }

    /**
     * boolean isIsolation
     * @return
     */
    public boolean isIsolation() {
        return isolation;
    }

    /**
     * void setIsolation
     * @param isolation
     */
    public void setIsolation(boolean isolation) {
        this.isolation = isolation;
    }

    /**
     * void reviserBudget
     */
    @Override
    public void reviserBudget() {
        int coefficientIsolation = isolation ? 400 : -200; // Bonus si isolé, malus sinon
        int budgetService = capital + coefficientIsolation;
        conversionNiveauBudget(budgetService);
        System.out.println("Révision du budget du Centre de Quarantaine " + budget + 
            ". Isolation : " + (isolation ? "Oui" : "Non"));
    }
    
    /**
     * void afficherDetails
     */
	@Override
	public void afficherDetails() {
		String emojiCreature = emoji();
        System.out.println(color.RED_BOLD + "\n☢️ Service : " + getNom() + color.RESET);
        System.out.println("  Capacité : " + getCapaciteMax());
        System.out.println("  Isolation : " + isolation);
        System.out.println("  Budget : " + getBudget() + " (" +getCapital()+")");
        for (Creature creature : getCreatures()) {
            System.out.println("     " + emojiCreature + " " + creature.getNom() + ", Moral: " + creature.getMoral() + " " + creature.getMaladies() );
        }
	}
}
