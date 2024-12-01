package Model;

import java.util.Random;

public class HommeBete extends Creature{

	public HommeBete(String nom, String sexe, double poids, double taille, String age) {
		super(nom, sexe, poids, taille, age);
		
	}
	
    @Override
    public void trepasser(ServiceMedical service) {
        moral = 0;
        System.out.println(nom + " (Homme-Bête) est décédé.");
        contaminer(service);
        service.retirerCreature(this);
    }

    private void contaminer(ServiceMedical service) {
        if (!maladies.isEmpty()) {
            System.out.println(nom + " contamine une autre créature dans le service " + service.getNom() + ".");
            for (Creature creature : service.getCreatures()) {
                if (!creature.equals(this)) {
                	Random random = new Random();
                    Maladie maladie = maladies.get(random.nextInt(maladies.size()));
                    creature.getMaladies().add(maladie);
                    break; // Contamine une seule créature
                }
            }
        }
    }

	@Override
	public Categorie getCategorie() {
		return Categorie.TRIAGE; // Homme-Bête est une créature de triage
	}

}
