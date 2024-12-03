package ServicesMedicaux;

import Creatures.Creature;

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
    public void reviserBudget() {
        int coefficientVentilation = ventilation * 5; // Chaque niveau de ventilation ajoute un bonus
        int coefficientTemperature = (temperature >= 19 && temperature <= 30) ? 300 : -200; // Bonus si température idéale
        int budgetService = capital + coefficientVentilation + coefficientTemperature;
        conversionNiveauBudget(budgetService);
        System.out.println("Révision du budget de la Crypte " + getBudget() + 
            ". Ventilation : " + ventilation + ", Température : " + temperature + "°C.");
        
    }

	@Override
	public void afficherDetails() {
		String emojiCreature = emoji();
        System.out.println(color.GREEN_BOLD + "\n🪦 Service : " + getNom() + color.RESET);
        System.out.println("  Capacité : " + getCapaciteMax());
        System.out.println("  Ventilation : " + ventilation);
        System.out.println("  Temperature : " + temperature);
        System.out.println("  Budget : " + getBudget() + " (" +getCapital()+")");
        for (Creature creature : getCreatures()) {
            System.out.println("     " + emojiCreature + " " + creature.getNom() + ", Moral: " + creature.getMoral() + " " + creature.getMaladies() );
        }
	}
    
    
}
