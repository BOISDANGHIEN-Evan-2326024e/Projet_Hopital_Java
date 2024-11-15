package Model;

public class Crypte extends ServiceMedical {
    private int ventilation;
    private double temperature;

    public Crypte(String nom, double superficie, int capaciteMax, String budget, int ventilation, double temperature) {
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
    public void reviserBudget(String nouveauBudget) {
        this.budget = nouveauBudget;
        System.out.println("Budget pour la crypte révisé. Ventilation: " + ventilation + ", Température: " + temperature);
    }
}
