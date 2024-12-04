package Creatures;

import ServicesMedicaux.ServiceMedical;

public class Medecin {
    private String nom;
    private String sexe;
    private int age;
    private ServiceMedical serviceAssocie;
    private boolean ecoPossible; // Indique si économiser est possible pour ce médecin

    /**
     * Medecin
     * @param nom
     * @param sexe
     * @param age
     * @param serviceAssocie
     */
    public Medecin(String nom, String sexe, int age, ServiceMedical serviceAssocie) {        this.nom = nom;
        this.sexe = sexe;
        this.age = age;
        this.serviceAssocie = serviceAssocie;
        this.ecoPossible = serviceAssocie.getBudget() < 1200; // Définir l'état initial
    }

    /**
     * void examiner
     * @param service
     */
    public void examiner(ServiceMedical service) {
        System.out.println("Examen du service : " + service.getNom());
        service.afficherDetails();
    }
    /**
     * void soigner
     * @param creature
     */
    public void soigner(Creature creature) {
        if (serviceAssocie.getCreatures().contains(creature)) {
            creature.soigner(serviceAssocie);
            System.out.println(nom + " a soigné " + creature.getNom() + ".");
            System.out.println(creature.getNom() + " a quitté le service " + serviceAssocie.getNom() + ".");
        } else {
            System.out.println("Erreur : La créature n'est pas dans le service associé au médecin.");
        }
    }
    
    /**
     * void reviserBudget
     */
    public void reviserBudget() {
        if (ecoPossible) {
            serviceAssocie.setBudget(serviceAssocie.getBudget() + 1000); // Ajoute 1000 au budget
            System.out.println(nom + " a économisé pour le service " + serviceAssocie.getNom() + 
                               ". Nouveau budget : " + serviceAssocie.getBudget());
            ecoPossible = false; // Une fois utilisé, l'économie devient impossible
        } else {
            System.out.println("L'économie n'est pas possible pour " + nom + ".");
        }
    }

    /**
     * void verifierEco
     */
  //Met à jour l'état ecoPossible si nécessaire 
    public void verifierEco() { 
        this.ecoPossible = serviceAssocie.getBudget() < 1200;
    }
    
    public void reviserBudget(ServiceMedical service) {
        service.reviserBudget();
    }

    /**
     * void transfererCreature
     * @param creature
     * @param serviceDepart
     * @param serviceArrivee
     */
    public void transfererCreature(Creature creature, ServiceMedical serviceDepart, ServiceMedical serviceArrivee) {
        serviceDepart.retirerCreature(creature);
        serviceArrivee.ajouterCreature(creature);
    }
    
    /**
     * boolean isEcoPossible
     * @return
     */
    public boolean isEcoPossible() {
		return ecoPossible;
	}

    /**
     * void setEcoPossible
     * @param ecoPossible
     */
	public void setEcoPossible(boolean ecoPossible) {
		this.ecoPossible = ecoPossible;
	}

	/**
	 * ServiceMedical getServiceAssocie
	 * @return
	 */
	public ServiceMedical getServiceAssocie() {
        return serviceAssocie;
    }

	/**
	 * void setServiceAssocie
	 * @param serviceAssocie
	 */
    public void setServiceAssocie(ServiceMedical serviceAssocie) {
        this.serviceAssocie = serviceAssocie;
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
	 * String getSexe
	 * @return
	 */
	public String getSexe() {
		return sexe;
	}

	/**
	 * void setSexe
	 * @param sexe
	 */
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	/**
	 * int getAge
	 * @return
	 */
	public int getAge() {
		return age;
	}

	/**
	 * void setAge
	 * @param age
	 */
	public void setAge(int age) {
		this.age = age;
	}
    
    
    
    
    

}
