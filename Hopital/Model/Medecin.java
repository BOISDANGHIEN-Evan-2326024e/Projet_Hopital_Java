package Model;

public class Medecin {
    private String nom;
    private String sexe;
    private int age;
    private ServiceMedical serviceAssocie;

    public Medecin(String nom, String sexe, int age, ServiceMedical serviceAssocie) {        this.nom = nom;
        this.sexe = sexe;
        this.age = age;
        this.serviceAssocie = serviceAssocie;
    }

    public void examiner(ServiceMedical service) {
        System.out.println("Examen du service : " + service.getNom());
        service.afficherDetails();
    }

    public void soigner(Creature creature) {
        if (serviceAssocie.getCreatures().contains(creature)) {
            creature.soigner();
            System.out.println(nom + " a soigné " + creature.nom + ".");
            serviceAssocie.retirerCreature(creature); // La créature quitte le service après avoir été soignée
            System.out.println(creature.nom + " a quitté le service " + serviceAssocie.getNom() + ".");
        } else {
            System.out.println("Erreur : La créature n'est pas dans le service associé au médecin.");
        }
    }

    
    public void reviserBudget(ServiceMedical service, int nouveauBudget) {
        service.reviserBudget(nouveauBudget);
    }

    public void transfererCreature(Creature creature, ServiceMedical serviceDepart, ServiceMedical serviceArrivee) {
        if (serviceDepart.getCreatures().contains(creature) && serviceArrivee.getCreatures().contains(creature)) {
            serviceDepart.retirerCreature(creature);
            serviceArrivee.ajouterCreature(creature);
        }
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
