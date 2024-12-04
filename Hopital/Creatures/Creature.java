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
			// Appel de la méthode attendre de la stratégie
			attenteStrategy.attendre(this, autresCreatures, round);  
		}
	}

	/**
	 * void assignerStrategie
	 * Affecter la stratégie en fonction de la catégorie
	 */
	public void assignerStrategie() {
		if (this.getCategorie() == Categorie.TRIAGE) {
			setAttenteStrategy(new AttenteTriageStrategy());
		} else if (this.getCategorie() == Categorie.VIP) {
			setAttenteStrategy(new AttenteVIPStrategy());
		}
	}
	

	/**
	 * void hurler
	 */
	public void hurler() {
		System.out.println(getNom() + " hurle !");
	}

	/**
	 * Genere une maladie aléatoire pour l'affecter à une créature
	 * void tomberMaladeDebut
	 */
	public void tomberMaladeDebut() {
		Maladie maladie = genererMaladieAleatoire();
		maladies.add(maladie);
		setMoral(getMoral() - 10);
	}

	/**
	 * permet à une créature de tomber malade d'une maladie déja présente dans le service
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
	 * Quand une créature meurt, elle quitte le service
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
	 * Maladie genererMaladieAleatoire, genere son nom et son accronyme aléatoirement
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
