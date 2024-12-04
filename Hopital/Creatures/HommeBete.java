package Creatures;

import java.util.Random;

import Enum.Categorie;
import Maladie.Maladie;
import ServicesMedicaux.ServiceMedical;

public class HommeBete extends Creature{

	/**
	 * HommeBete
	 * @param nom
	 * @param sexe
	 * @param poids
	 * @param taille
	 * @param age
	 */
	public HommeBete(String nom, String sexe, double poids, double taille, String age) {
		super(nom, sexe, poids, taille, age);
		
	}
	
	/**
	 * void trepasser
	 * Si la crature meurt, elle contaminer son entourage avant de mourir
	 */
    @Override
    public void trepasser(ServiceMedical service) {
        setMoral(0);
        System.out.println(getNom() + " (Homme-Bête) est décédé.");
        contaminer(service);
        service.retirerCreature(this);
    }

    /**
     * void contaminer
     * Contamine son entourage, ses voisins attrapes une de ses maladies
     * @param service
     */
    private void contaminer(ServiceMedical service) {
        if (!maladies.isEmpty()) {
            System.out.println(getNom() + " contamine une autre créature dans le service " + service.getNom() + ".");
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

    /**
     * Categorie getCategorie
     */
	@Override
	public Categorie getCategorie() {
		return Categorie.TRIAGE; // Homme-Bête est une créature de triage
	}

}
