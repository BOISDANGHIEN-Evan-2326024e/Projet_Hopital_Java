package Simulation;

import java.util.ArrayList; 

import java.util.List;
import java.util.Random;

import Affichage.TextColor;
import Creatures.Creature;
import Creatures.Medecin;
import ServicesMedicaux.CentreQuarantaine;
import ServicesMedicaux.Crypte;
import ServicesMedicaux.ServiceMedical;
import ServicesMedicaux.ServiceStandard;


public class Main {
	private static TextColor color = new TextColor();
	/*
	 * Lance la simulation de l'hopital fantastique
	 */
	public static void main(String[] args) {

		HopitalFantastique hopital = new HopitalFantastique("Hôpital Fantastique", 10);		
		Random random = new Random();

		// Création des services
		for (int i = 0; i < 3; i++) {
			CentreQuarantaine quarantaine = new CentreQuarantaine("Quarantaine " + (i + 1), 200, 10, 200, true);
			Medecin medecin = new Medecin("Dr. " + genererNomMedecin(), "Homme", 40 + random.nextInt(20), quarantaine);
			hopital.ajouterService(quarantaine);
			hopital.ajouterMedecin(medecin);
			ajouterCreaturesAuService(quarantaine, 5 + random.nextInt(3));

		}

		for (int i = 0; i < 3; i++) {
			Crypte crypte = new Crypte("Crypte " + (i + 1), 150, 5, 2500, 3, 18.0);;
			Medecin medecin = new Medecin("Dr. " + genererNomMedecin(), "Femme", 35 + random.nextInt(20), crypte);
			hopital.ajouterService(crypte);
			hopital.ajouterMedecin(medecin);
			ajouterCreaturesAuService(crypte, 5 + random.nextInt(3));

		}

		for (int i = 0; i < 2; i++) {
			ServiceStandard standard = new ServiceStandard("Standard " + (i + 1), 400, 8, 2500);
			Medecin medecin = new Medecin("Dr. " + genererNomMedecin(), "Homme", 50 + random.nextInt(15), standard);
			hopital.ajouterService(standard);
			hopital.ajouterMedecin(medecin);
			ajouterCreaturesAuService(standard, 5 + random.nextInt(3));

		}


		// Affichage des informations initiales
		System.out.println(color.BRIGHT_BLUE_BOLD  +"=== Informations Initiales de l'Hôpital ===\n" + color.RESET);

		hopital.afficherStatistiques();
		hopital.afficherDetailsCreatures();

		// Démarrer la simulation
		System.out.println(color.GREEN_BOLD  +"\n=== Démarrage de la Simulation ===" + color.RESET);
		hopital.demarrerSimulation();

		// Démarrer le menu pour permettre à l'utilisateur de prendre la main
		Menu menu = new Menu(hopital);
		// Lance le menu en ligne de commande
		new Thread(menu).start(); 
	}
	/*
	 * Ajoute des Créature malades dès le début
	 */
	private static void ajouterCreaturesAuService(ServiceMedical service, int nombre) {
		for (int i = 0; i < nombre; i++) {
			Creature creature = HopitalFantastique.genererNouvelleCreature(service);
			creature.tomberMaladeDebut(); 
			service.ajouterCreature(creature);
		}
	}

	/*
	 * Genere un nom aléatoire pour le medecin
	 */
	public static String genererNomMedecin() {
		List<String> nomsMedecinsDisponibles = new ArrayList<>(List.of(
				"Albano", "Serre", "Boisdanghien", "Nevot", 
				"Delacroix", "Dubois", "Moreau", "Fontaine", "Chevalier",
				"Lemoine", "Roche", "Perrin", "Blanc", "Martin",
				"Durand", "Leroux", "Noir", "Benoit", "Lemoine"
				));
		if (nomsMedecinsDisponibles.isEmpty()) {
			return "Bob"; // Nom par défaut si la liste est vide
		}

		Random random = new Random();
		int index = random.nextInt(nomsMedecinsDisponibles.size());
		String nom = nomsMedecinsDisponibles.get(index);
		nomsMedecinsDisponibles.remove(index); // Supprime le nom utilisé pour eviter les doublons
		return nom;
	}


}
