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

	/**
	 * Creature
	 * @param nom
	 * @param sexe
	 * @param poids
	 * @param taille
	 * @param age
	 */
	public Creature(String nom, String sexe, double poids, double taille, String age) {
		this.setNom(nom);
		this.sexe = sexe;
		this.poids = poids;
		this.taille = taille;
		this.age = age;
		this.setMoral(1000);
		this.maladies = new ArrayList<>();
	}

	/**
	 * Categorie getCategorie
	 * @return
	 */
	public abstract Categorie getCategorie();

	/**
	 * void setAttenteStrategy
	 * @param strategie
	 */
	public void setAttenteStrategy(AttenteStrategy strategie) {
		this.attenteStrategy = strategie;
	}

	/**
	 * void attendre
	 * @param autresCreatures
	 * @param round
	 */
	public void attendre(List<Creature> autresCreatures, int round) {
		if (attenteStrategy != null) {
			attenteStrategy.attendre(this, autresCreatures, round);  // Appel de la méthode attendre de la stratégie
		}
	}

	/**
	 * void assignerStrategie
	 */
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

	/**
	 * void hurler
	 */
	public void hurler() {
		System.out.println(getNom() + " hurle !");
	}

	/**
	 * void tomberMaladeDebut
	 */
	public void tomberMaladeDebut() {
		Maladie maladie = genererMaladieAleatoire();
		maladies.add(maladie);
		setMoral(getMoral() - 10);
	}

	/**
	 * void tomberMalade
	 * @param service
	 */
	public void tomberMalade(ServiceMedical service) {
		Maladie nouvelleMaladie = service.obtenirMaladieDepuisService();
		if (nouvelleMaladie != null) {
			maladies.add(nouvelleMaladie);
			setMoral(getMoral() - 50); // Réduit le moral en raison de la maladie
		}
		else {

		}
	}

	/**
	 * void soigner
	 * @param service
	 */
	public void soigner(ServiceMedical service) {
		service.retirerCreature(this);
	}

	/**
	 * boolean estEnVie
	 * @return
	 */
	public boolean estEnVie() {
		return getMoral() > 0;
	}

	/**
	 * void trepasser
	 * @param service
	 */
	public void trepasser(ServiceMedical service) {
		setMoral(0);
		service.retirerCreature(this);
	}

	/**
	 * List<Maladie> getMaladies
	 * @return
	 */
	public List<Maladie> getMaladies() {
		return maladies;
	}

	/**
	 * Maladie genererMaladieAleatoire
	 * @return
	 */
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

		int graviteMax = random.nextInt(5) + 5; // Gravité entre 5 et 15

		// Retourne une nouvelle instance de Maladie
		return new Maladie(nomComplet, nomAbreges.substring(0, 3), graviteMax); // Nom abrégé : 3 premières lettres

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
	 * double getMoral
	 * @return
	 */
	public double getMoral() {
		return moral;
	}

	/**
	 * void setMoral
	 * @param moral
	 */
	public void setMoral(double moral) {
		this.moral = moral;
	}



}
