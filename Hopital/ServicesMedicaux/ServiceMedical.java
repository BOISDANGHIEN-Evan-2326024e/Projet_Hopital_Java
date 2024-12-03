package Model;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public abstract class ServiceMedical {
    private String nom;
    private double superficie;
    private int capaciteMax;
    private List<Creature> creatures;
    protected int capital;
    protected String budget;
    protected static TextColor color = new TextColor();

    public ServiceMedical(String nom, double superficie, int capaciteMax, int capital) {
        this.nom = nom;
        this.superficie = superficie;
        this.capaciteMax = capaciteMax;
        this.creatures = new ArrayList<>();
        this.capital = capital;
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

    
    public Maladie obtenirMaladieDepuisService() {
        Random random = new Random();
        for (Creature creature : creatures) {
            List<Maladie> maladies = creature.getMaladies();
            if (maladies != null && !maladies.isEmpty()) {
                return maladies.get(random.nextInt(maladies.size())); // Maladie aléatoire
            }
        }
        return null; // Retourne null si aucune maladie n'est trouvée
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
        return capital;
    }

    public void setBudget(int budget) {
        this.capital = budget;
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
    public abstract void reviserBudget();
    
    
    protected void conversionNiveauBudget(int capitalService) {
        int tranche = capitalService / 400; // Division pour déterminer la tranche

        switch (tranche) {
            case 0 -> budget = "inexistant";  // < 400
            case 1 -> budget = "insuffisant"; // [400, 800[
            case 2 -> budget = "faible";      // [800, 1200[
            case 3 -> budget = "médiocre";    // [1200, 1600[
            case 4 -> budget = "suffisant";   // [1600, 2000[
            case 5 -> budget = "bon";         // [2000, 2400[
            default -> budget = "excellent";  // >= 2400
        }

        System.out.println("Le niveau du budget pour le service " + nom + " est : " + budget);
    }

	public int getCapital() {
		return capital;
	}

	public void setCapital(int capital) {
		this.capital = capital;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}
	
	public String emoji() {
		String type = this.getCreatures().get(0).getClass().getSimpleName();
		String emoji;
		emoji = switch (type) {
	        case "Orque" ->  "🧌";
	        case "HommeBete" ->  "👨";
	        case "Vampire" ->  "🧛";
	        case "Zombie" ->  "🧟";
	        case "Nain" ->  "👶";
	        case "Elfe" ->  "🧝‍♀️";
	        case "Reptilien" ->  "🐊";
	        case "Lycanthrope" -> "🐺";
	        default -> throw new IllegalArgumentException("Type de créature inconnu : " + type);
	    };
	    return emoji;
	}

}
