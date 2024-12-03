package Model;


public class Elfe extends Creature {
	public Elfe(String nom, String sexe, double poids, double taille, String age) {
		super(nom, sexe, poids, taille, age);
	}

	public void demoraliser() {
		System.out.println(nom + " démoralise son entourage !");
	}


    @Override
    public void trepasser(ServiceMedical service) {
        moral = 0;
        System.out.println(nom + " (Elfe) est décédé.");
        demoraliser(service);
        service.retirerCreature(this);
    }

    private void demoraliser(ServiceMedical service) {
        System.out.println(nom + " démoralise les autres créatures dans le service " + service.getNom() + ".");
        for (Creature creature : service.getCreatures()) {
            if (!creature.equals(this)) {
            	moral = moral - 200;
            }
        }
    }
    
	@Override
	public Categorie getCategorie() {
		return Categorie.VIP; // Elfe est une créature VIP
	}

}
