package Creatures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Attente.*;
import Enum.Categorie;
import Maladie.Maladie;
import ServicesMedicaux.ServiceMedical;

public abstract class Creature {
	private String nom;
	protected String sexe;
	protected double poids;
	protected double taille;
	protected String age;
	private double moral;
	protected List<Maladie> maladies;
    private AttenteStrategy attenteStrategy;  // Le champ de stratégie

	public Creature(String nom, String sexe, double poids, double taille, String age) {
		this.setNom(nom);
		this.sexe = sexe;
		this.poids = poids;
		this.taille = taille;
		this.age = age;
		this.setMoral(1000);
		this.maladies = new ArrayList<>();
	}

	public abstract Categorie getCategorie();

	public void setAttenteStrategy(AttenteStrategy strategie) {
        this.attenteStrategy = strategie;
    }

    public void attendre(List<Creature> autresCreatures, int round) {
        if (attenteStrategy != null) {
            attenteStrategy.attendre(this, autresCreatures, round);  // Appel de la méthode attendre de la stratégie
        }
    }


    // Exemple de méthode pour affecter la stratégie en fonction de la catégorie
    public void assignerStrategie() {
        if (this.getCategorie() == Categorie.TRIAGE) {
            setAttenteStrategy(new AttenteTriageStrategy());
        } else if (this.getCategorie() == Categorie.VIP) {
            setAttenteStrategy(new AttenteVIPStrategy());
        }
    }
    /*
	public void attendre(List<Creature> autresCreatures, int round) {
		double probabiliteColere = 5 * round;

		if (getCategorie() == Categorie.TRIAGE) {
			if (autresCreatures.size() > 1) { // Si il n'est pas seul
				if ((getMoral() - 5 * probabiliteColere) < 0) {
					setMoral(0);
				}
				else {
					setMoral(getMoral() - 5 * probabiliteColere);
				}
			} else {
				if ((getMoral() - 10 * probabiliteColere) < 0) {
					setMoral(0);
				}
				else {
					setMoral(getMoral() - 10 * probabiliteColere);
					System.out.println(getNom() + " se sent seul.");
				}	
			}

		} else if (getCategorie() == Categorie.VIP) {
			if ((getMoral() - 15 * probabiliteColere) < 0) {
				setMoral(0);
			}
			else {
				setMoral(getMoral() - 15 * probabiliteColere); // Diminuer fortement le moral si un VIP attend trop longtemps
				System.out.println(getNom() + " n'est peu plus d'attendre.");
			}
		}

	}*/

	public void hurler() {
		System.out.println(getNom() + " hurle !");
	}



	public void tomberMaladeDebut() {
		Maladie maladie = genererMaladieAleatoire();
		maladies.add(maladie);
		setMoral(getMoral() - 10);
	}

	public void tomberMalade(ServiceMedical service) {
	    Maladie nouvelleMaladie = service.obtenirMaladieDepuisService();
	    if (nouvelleMaladie != null) {
	        maladies.add(nouvelleMaladie);
	        setMoral(getMoral() - 50); // Réduit le moral en raison de la maladie
	    }
	    else {
	    	
	    }
	}
	
	public void soigner(ServiceMedical service) {
		service.retirerCreature(this);
	}

	public boolean estEnVie() {
		return getMoral() > 0;
	}


	public void trepasser(ServiceMedical service) {
		setMoral(0);
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

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public double getMoral() {
		return moral;
	}

	public void setMoral(double moral) {
		this.moral = moral;
	}
	
	

}
