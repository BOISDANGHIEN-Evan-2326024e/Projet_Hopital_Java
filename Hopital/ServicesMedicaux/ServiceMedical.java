package ServicesMedicaux;

import java.util.ArrayList;
import java.util.Random;

import Affichage.TextColor;
import Creatures.Creature;
import Maladie.Maladie;

import java.util.List;

public abstract class ServiceMedical {
    private String nom;
    private double superficie;
    private int capaciteMax;
    private List<Creature> creatures;
    protected int capital;
    protected String budget;
    protected static TextColor color = new TextColor();

    /**
     * ServiceMedical
     * @param nom
     * @param superficie
     * @param capaciteMax
     * @param capital
     */
    public ServiceMedical(String nom, double superficie, int capaciteMax, int capital) {
        this.nom = nom;
        this.superficie = superficie;
        this.capaciteMax = capaciteMax;
        this.creatures = new ArrayList<>();
        this.capital = capital;
    }

    /**
     * abstract void afficherDetails
     */
    public abstract void afficherDetails();

    public void ajouterCreature(Creature creature) {
        if (creatures.size() < capaciteMax) {
            creatures.add(creature);
        }
    }

    /**
     * void retirerCreature
     * @param creature
     */
    public void retirerCreature(Creature creature) {
        creatures.remove(creature);
    }

    /**
     * Maladie obtenirMaladieDepuisService
     * @return
     */
    public Maladie obtenirMaladieDepuisService() {
        Random random = new Random();

        // On parcourt les créatures dans le service
        for (Creature creature : creatures) {
            List<Maladie> maladies = creature.getMaladies();
            if (maladies != null && !maladies.isEmpty()) {
                // On récupère une maladie aléatoire de la créature
                Maladie maladieOriginale = maladies.get(random.nextInt(maladies.size()));
                
                // Créer une nouvelle instance de la maladie avec le même nom mais évolution différente
                Maladie nouvelleMaladie = new Maladie(maladieOriginale.getNomComplet(),
                                                       maladieOriginale.getNomAbrege(),
                                                       random.nextInt(5) + 10);  // Niveau de gravité aléatoire entre 10 et max 15

                // Retourne la nouvelle maladie
                return nouvelleMaladie;
            }
        }

        // Retourne null si aucune maladie n'est trouvée
        return null;
    }



    /**
     * List<Creature> getCreatures
     * @return
     */
    public List<Creature> getCreatures() {
        return creatures;
    }
    
    /**
     * int getCapacite
     * @return
     */
    public int getCapacite() {
    	return creatures.size();
    }

    /**
     * void setCreatures
     * @param creatures
     */
    public void setCreatures(List<Creature> creatures) {
        this.creatures = creatures;
    }

    /**
     * int getBudget
     * @return
     */
    public int getBudget() {
        return capital;
    }

    /**
     * void setBudget
     * @param budget
     */
    public void setBudget(int budget) {
        this.capital = budget;
    }

    /**
     * int getCapaciteMax
     * @return
     */
    public int getCapaciteMax() {
        return capaciteMax;
    }

    /**
     * double getSuperficie
     * @return
     */
    public double getSuperficie() {
        return superficie;
    }
    
    /**
     * void setSuperficie
     * @param superficie
     */
    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    /**
     * String getNom
     * @return
     */
    public String getNom() {
        return nom;
    }

    /**
     * void setNom
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     *  Méthode abstraite pour réviser le budget spécifique de chaque sous-classe 
     */
    public abstract void reviserBudget();
    
    
    /**
     * void conversionNiveauBudget
     * @param capitalService
     */
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

    /**
     * int getCapital
     * @return
     */
	public int getCapital() {
		return capital;
	}

	/**
	 * void setCapital
	 * @param capital
	 */
	public void setCapital(int capital) {
		this.capital = capital;
	}

	/**
	 * void setBudget
	 * @param budget
	 */
	public void setBudget(String budget) {
		this.budget = budget;
	}
	
	/**
	 * String emoji
	 * @return
	 */
	public String emoji() {
		if (this.getCreatures().size() == 0) {
			System.out.println("00000 pas de creatures jdegfuzejrghzeiokg");
			return null;
		}
		else {
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

}
