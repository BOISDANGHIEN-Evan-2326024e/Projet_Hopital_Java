package Creatures;

import ServicesMedicaux.ServiceMedical;

public class Medecin {
    private String nom;
    private String sexe;
    private int age;
    private ServiceMedical serviceAssocie;
    private boolean ecoPossible; // Indique si économiser est possible pour ce médecin

    public Medecin(String nom, String sexe, int age, ServiceMedical serviceAssocie) {        this.nom = nom;
        this.sexe = sexe;
        this.age = age;
        this.serviceAssocie = serviceAssocie;
        this.ecoPossible = serviceAssocie.getBudget() < 1200; // Définir l'état initial
    }

    public void examiner(ServiceMedical service) {
        System.out.println("Examen du service : " + service.getNom());
        service.afficherDetails();
    }

    public void soigner(Creature creature) {
        if (serviceAssocie.getCreatures().contains(creature)) {
            creature.soigner(serviceAssocie);
            System.out.println(nom + " a soigné " + creature.getNom() + ".");
            System.out.println(creature.getNom() + " a quitté le service " + serviceAssocie.getNom() + ".");
        } else {
            System.out.println("Erreur : La créature n'est pas dans le service associé au médecin.");
        }
    }
    
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

    // Met à jour l'état ecoPossible si nécessaire
    public void verifierEco() {
        this.ecoPossible = serviceAssocie.getBudget() < 1200;
    }
    
    public void reviserBudget(ServiceMedical service) {
        service.reviserBudget();
    }

    public void transfererCreature(Creature creature, ServiceMedical serviceDepart, ServiceMedical serviceArrivee) {
        serviceDepart.retirerCreature(creature);
        serviceArrivee.ajouterCreature(creature);
    }
    
    

    public boolean isEcoPossible() {
		return ecoPossible;
	}

	public void setEcoPossible(boolean ecoPossible) {
		this.ecoPossible = ecoPossible;
	}

	public ServiceMedical getServiceAssocie() {
        return serviceAssocie;
    }

    public void setServiceAssocie(ServiceMedical serviceAssocie) {
        this.serviceAssocie = serviceAssocie;
    }
    
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
    
    
    
    
    

}
