package Model;

import java.util.ArrayList;
import java.util.List;

public abstract class ServiceMedical {
    private String nom;
    private double superficie;
    private int capaciteMax;
    private List<Creature> creatures;
    private int budget;

    public ServiceMedical(String nom, double superficie, int capaciteMax, int budget) {
        this.nom = nom;
        this.superficie = superficie;
        this.capaciteMax = capaciteMax;
        this.creatures = new ArrayList<>();
        this.budget = budget;
    }

    public abstract void afficherDetails();

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
    
    public int getCapacite() {
    	return creatures.size();
    }

    public void setCreatures(List<Creature> creatures) {
        this.creatures = creatures;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
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
    public abstract void reviserBudget(int nouveauBudget);
}
