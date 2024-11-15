package Model;

public class CentreQuarantaine extends ServiceMedical {
    private boolean isolation;

    public CentreQuarantaine(String nom, double superficie, int capaciteMax, String budget, boolean isolation) {
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
    public void reviserBudget(String nouveauBudget) {
        this.budget = nouveauBudget;
        if (isolation) {
        	System.out.println("Budget pour le centre de quarantaine révisé. Isolation: activée");
        }
        System.out.println("Budget pour le centre de quarantaine révisé. Isolation: désactivée");
    }
}
