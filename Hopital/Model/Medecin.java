package Model;

public class Medecin {
    private String nom;
    private String sexe;
    private int age;

    public Medecin(String nom, String sexe, int age) {
        this.nom = nom;
        this.sexe = sexe;
        this.age = age;
    }

    public void examiner(ServiceMedical service) {
        System.out.println("Examen du service : " + service.getNom());
        service.afficherDetails();
    }

    public void soigner(ServiceMedical service) {
        System.out.println(nom + " soigne les créatures du service " + service.getNom());
        service.soignerCreatures();
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
