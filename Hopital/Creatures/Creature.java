package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Creature {
	protected String nom;
	protected String sexe;
	protected double poids;
	protected double taille;
	protected String age;
	protected int moral;
	protected List<Maladie> maladies;

	public Creature(String nom, String sexe, double poids, double taille, String age) {
		this.nom = nom;
		this.sexe = sexe;
		this.poids = poids;
		this.taille = taille;
		this.age = age;
		this.moral = 1000;
		this.maladies = new ArrayList<>();
	}

	public abstract Categorie getCategorie();

	public void attendre(List<Creature> autresCreatures, int round) {
		double probabiliteColere = 5 * round;

		if (getCategorie() == Categorie.TRIAGE) {
			if (autresCreatures.size() > 1) { // Si il n'est pas seul
				if ((moral - 5 * probabiliteColere) < 0) {
					moral = 0;
				}
				else {
					moral -= 5 * probabiliteColere;
				}
			} else {
				if ((moral - 10 * probabiliteColere) < 0) {
					moral = 0;
				}
				else {
					moral -= 10 * probabiliteColere;
					System.out.println(nom + " se sent seul.");
				}	
			}

		} else if (getCategorie() == Categorie.VIP) {
			if ((moral - 15 * probabiliteColere) < 0) {
				moral = 0;
			}
			else {
				moral -= 15 * probabiliteColere; // Diminuer fortement le moral si un VIP attend trop longtemps
				System.out.println(nom + " n'est peu plus d'attendre.");
			}
		}

	}

	public void hurler() {
		System.out.println(nom + " hurle !");
	}

	public void emporter() {};

	public void tomberMaladeDebut() {
		Maladie maladie = genererMaladieAleatoire();
		maladies.add(maladie);
		moral -= 10;
	}

	public void tomberMalade(ServiceMedical service) {
	    Maladie nouvelleMaladie = service.obtenirMaladieDepuisService();
	    if (nouvelleMaladie != null) {
	        maladies.add(nouvelleMaladie);
	        moral -= 50; // Réduit le moral en raison de la maladie
	    }
	    else {
	    	
	    }
	}
	
	public void soigner(ServiceMedical service) {
		service.retirerCreature(this);
	}

	public boolean estEnVie() {
		return moral > 0;
	}


	public void trepasser(ServiceMedical service) {
		moral = 0;
		service.retirerCreature(this);
	}

	public List<Maladie> getMaladies() {
		return maladies;
	}

	private Maladie genererMaladieAleatoire() {
		String[] nomsMaladiesComplets = {
				"Maladie débilitante chronique",
				"Syndrome fear of missing out",
				"Dépendance aux réseaux sociaux",
				"Porphyrie érythropoïétique congénitale",
				"Zoopathie paraphrénique lycanthropique",
				"Dépendance aux réseaux sociaux"
		};

		String[] nomsMaladiesAbreges = { "MDC", "FOMO", "DRS","PEC","ZPL","DRS" };

		// Choix aléatoire d'un nom et d'un niveau de gravité
		Random random = new Random();
		int posMaladie = random.nextInt(nomsMaladiesComplets.length);
		String nomComplet = nomsMaladiesComplets[posMaladie];
		String nomAbreges = nomsMaladiesAbreges[posMaladie];

		int graviteMax = random.nextInt(10) + 10; // Gravité entre 10 et 20

		// Retourne une nouvelle instance de Maladie
		return new Maladie(nomComplet, nomAbreges.substring(0, 3), graviteMax); // Nom abrégé : 3 premières lettres

	}
	
	

}
