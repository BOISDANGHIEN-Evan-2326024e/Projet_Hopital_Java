package Model;

public class Crypte extends ServiceMedical {
    private int ventilation;
    private double temperature;

    public Crypte(String nom, double superficie, int capaciteMax, int budget, int ventilation, double temperature) {
        super(nom, superficie, capaciteMax, budget);
        this.ventilation = ventilation;
        this.temperature = temperature;
    }

    public int getVentilation() {
        return ventilation;
    }

    public void setVentilation(int ventilation) {
        this.ventilation = ventilation;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public void reviserBudget(int nouveauBudget) {
        setBudget(nouveauBudget);
        //System.out.println("Budget pour la crypte révisé. Ventilation: " + ventilation + ", Température: " + temperature);
    }

	@Override
	public void afficherDetails() {
        System.out.println("Service : " + getNom());
        System.out.println("Capacité : " + getCapaciteMax());
        System.out.println("Ventilation : " + ventilation);
        System.out.println("Temperature : " + temperature);
        for (Creature creature : getCreatures()) {
            System.out.println(creature.nom + ", Moral: " + creature.moral + creature.getMaladies() );
        }
	}
    
    
}
