package Model;

import java.util.ArrayList;
import java.util.List;

public abstract class ServiceMedical {
    protected String nom;
    protected double superficie;
    protected int capaciteMax;
    protected List<Creature> creatures;
    protected String budget;

    public ServiceMedical(String nom, double superficie, int capaciteMax, String budget) {
        this.nom = nom;
        this.superficie = superficie;
        this.capaciteMax = capaciteMax;
        this.creatures = new ArrayList<>();
        this.budget = budget;
    }

    public void afficherDetails() {
        System.out.println("Service : " + nom);
        System.out.println("Capacité : " + capaciteMax);
        for (Creature creature : creatures) {
            System.out.println(creature.getNom() + ", Moral: " + creature.getMoral());
        }
    }

    public void ajouterCreature(Creature creature) {
        if (creatures.size() < capaciteMax) {
            creatures.add(creature);
        }
    }

    public void retirerCreature(Creature creature) {
        creatures.remove(creature);
    }

    public void soignerCreatures() {
        for (Creature creature : creatures) {
            creature.soigner();
        }
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    public void setCreatures(List<Creature> creatures) {
        this.creatures = creatures;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    /* Méthode abstraite pour réviser le budget spécifique de chaque sous-classe */
    public abstract void reviserBudget(String nouveauBudget);
}
