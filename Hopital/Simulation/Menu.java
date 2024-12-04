package Simulation;



import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Affichage.TextColor;
import Creatures.Creature;
import Creatures.Medecin;
import ServicesMedicaux.ServiceMedical;

public class Menu implements Runnable{
	private HopitalFantastique hopital;
	private Scanner scanner;
	private TextColor color;

	/**
	 * Menu
	 * @param hopital
	 */
	public Menu(HopitalFantastique hopital) {
		this.hopital = hopital;
		this.scanner = new Scanner(System.in);
	}
	
	/**
	 * void run
	 */
	@Override
	public void run() {
		while (true) {
			synchronized (hopital) {
				while (!hopital.isPauseSimulation()) {
					try {
						hopital.wait(); // Attend que la simulation se termine
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						return;
					}               
				}

				if (hopital.estJeuTermine()) {
					System.out.println("\n=== Fin du jeu : Toutes les créatures sont mortes. Merci d'avoir joué ! ===");
					return; // Arrête le thread du menu
				}
			}

			System.out.println("\n=== Tour du joueur ===");
			System.out.println("Sélectionnez un médecin pour prendre la main sur l'hôpital :");
			Medecin medecin = choisirMedecin();
			if (medecin == null) {
				hopital.setJeuTermine(true);
				hopital.verifierFinDuJeu();
				System.out.println("\n=== Vous avez abandonné. Le jeu est terminé. ===");
				return;
			}
			System.out.println("Vous avez choisi le médecin : " + medecin.getNom());
			int actionsRestantes = 3;
			long tempsLimite = System.currentTimeMillis() + 10000; // Limite de 10 secondes

			while (actionsRestantes > 0 && System.currentTimeMillis() < tempsLimite) {
				Boolean eco = actionsRestantes == 3;
				afficherOptions(medecin.isEcoPossible(), medecin.getServiceAssocie(), eco);
				int choix = lireChoix();

				switch (choix) {
				case 1-> {
					if (medecin.getServiceAssocie().getCapital() < 500) {
						actionsRestantes++;
					}
					else {
						soignerCreatures(medecin);
						medecin.getServiceAssocie().setCapital(medecin.getServiceAssocie().getCapital() - 500);
					}
				}
				case 2 -> {
					reviserBudget(medecin);
					medecin.getServiceAssocie().setCapital(medecin.getServiceAssocie().getCapital() + 100);
				}
				case 3 -> {
					if (medecin.getServiceAssocie().getCapital() < 500) {
						actionsRestantes++;
					}
					else {
						transfererCreature(medecin);
						medecin.getServiceAssocie().setCapital(medecin.getServiceAssocie().getCapital() - 200);
					}
				}
				case 5 -> {
					if (medecin.isEcoPossible() && actionsRestantes == 3) {
						medecin.reviserBudget(); // Révision du budget via économie
						medecin.getServiceAssocie().setCapital(medecin.getServiceAssocie().getCapital() + 1000);
						actionsRestantes = 1; // Fin des actions
						System.out.println("Fin des actions pour ce médecin.");
					}
					else {
						actionsRestantes++;
					}
				}
				default -> {
					System.out.println("Fin des actions pour ce médecin.");
					actionsRestantes = 1;
				}
				}
				actionsRestantes--;
			}

			// Réveille le thread de simulation
			hopital.resumeSimulation();
		}
	}
	
	/**
	 * void afficherMedecin
	 */
	private void afficherMedecin() {
		List<Medecin> medecins = hopital.getMedecins();


		// Vérifier si la liste des médecins est vide
		if (medecins.isEmpty()) {
			System.out.println(color.RED_BOLD + "🏚️ Aucun médecin disponible." + color.RESET);
			return;
		}

		// Parcourir et afficher les médecins
		for (int i = 0; i < medecins.size(); i++) {
			Medecin medecin = medecins.get(i);
			String emojiGenre = medecin.getSexe() == "Homme" ? "👨🏻‍⚕️" : "👩🏽‍⚕️"; // Emoji basé sur le genre
			String ecoStatus = medecin.isEcoPossible() 
					? color.GREEN + "Eco Possible" + color.RESET 
							: "";

			System.out.println("    " + color.YELLOW_BOLD + (i + 1) + ". " 
					+ emojiGenre + " " 
					+ color.YELLOW + medecin.getNom() + color.RESET
					+ " -- " 
					+  medecin.getServiceAssocie().getNom() 
					+ " -- " + ecoStatus);
		}

		// Options supplémentaires
		System.out.println("    " + color.BRIGHT_BLUE_BOLD + (medecins.size() + 1) + ". 📊 Afficher les statistiques" + color.RESET);
		System.out.println("    " + color.GREEN_BOLD + (medecins.size() + 2) + ". 📊 Afficher les détails des créatures" + color.RESET);
		System.out.println("    " + color.RED_BOLD + (medecins.size() + 3) + ". 🏚️ Abandonner la partie" + color.RESET);
	}
	
	/**
	 * Medecin choisirMedecin
	 * @return
	 */
	private Medecin choisirMedecin() {
		List<Medecin> medecins = hopital.getMedecins();
		if (medecins.isEmpty()) {
			return null;
		}

		afficherMedecin();

		int choix = lireChoix();

		// Gérer les choix supplémentaires
		while (choix == medecins.size() + 1 || choix == medecins.size() + 2 || choix < 0 || choix > medecins.size() + 3) {
			if (choix == medecins.size() + 1) {
				hopital.afficherStatistiques();
			} else if (choix == medecins.size() + 2) {
				hopital.afficherDetailsCreatures();
				afficherMedecin();
			}
			choix = lireChoix(); 
		}

		if (choix == medecins.size() + 3) { 
			return null;
		}

		if (choix > 0 && choix <= medecins.size()) {
			return medecins.get(choix - 1); // Retourne le médecin sélectionné
		}

		System.out.println("Choix invalide.");
		return null;
	}


	/**
	 * void afficherOption
	 * 
	 * @param ecoPossible
	 * @param service
	 * @param eco
	 */
	private void afficherOptions(boolean ecoPossible, ServiceMedical service, boolean eco ) {
		int argent = service.getCapital(); 
		System.out.println("\nSélectionnez une action :");
		System.out.println("Argent actuel : " + argent);

		if (argent >= 500) {
			System.out.println("    " + color.GREEN_BOLD + "💉 1. Soigner les créatures dans un service médical -- Coût 500 -- DISPONIBLE" + color.RESET);
		} else {
			System.out.println("    " + color.RED_BOLD + "❌ 1. Soigner les créatures dans un service médical -- Coût 500 -- IMPOSSIBLE" + color.RESET);
		}

		System.out.println("    " + color.YELLOW_BOLD + "✔️ 2. Réviser le budget d'un service médical -- Gain 100 -- DISPONIBLE" + color.RESET);

		if (argent >= 200) {
			System.out.println("    " + color.GREEN_BOLD + "✔️ 3. Transférer une créature entre services médicaux -- Coût 200 -- DISPONIBLE" + color.RESET);
		} else {
			System.out.println("    " + color.RED_BOLD + "❌ 3. Transférer une créature entre services médicaux -- Coût 200 -- IMPOSSIBLE" + color.RESET);
		}

		System.out.println("    " + color.BRIGHT_BLACK + "🛑 4. Fin des actions pour ce médecin" + color.RESET);
		if (ecoPossible && eco) {
			System.out.println("    " + color.CYAN + "💶 5. Economisez -- Gain 1000 -- (Attention cette action prend tout le tour) " + color.RESET);
		}
	}

	/**
	 * int lireChoix
	 * @return
	 */
	private int lireChoix() {
		int choix = -1;
		while (true) { 
			System.out.print("Votre choix : ");
			String input = scanner.nextLine(); 

			if (input.matches("\\d+")) { // Vérifie si l'entrée est composée uniquement de chiffres
				choix = Integer.parseInt(input); // Convertit la chaîne en entier
				break; 
			} else {
				System.out.println("Entrée invalide. Veuillez entrer un numéro valide.");
			}
		}
		return choix; 
	}

	/**
	 * void soignerCreatures
	 * @param medecin
	 */
	private void soignerCreatures(Medecin medecin) {
		ServiceMedical service = medecin.getServiceAssocie();
		if (service.getCreatures().isEmpty()) {
			System.out.println("Le service " + service.getNom() + " ne contient aucune créature.");
			return;
		}

		System.out.println("Sélectionnez une créature à soigner dans le service " + service.getNom() + " :");
		List<Creature> creatures = service.getCreatures();
		for (int i = 0; i < creatures.size(); i++) {
			System.out.println((i + 1) + ". " + creatures.get(i).getNom() + " (Moral: " + creatures.get(i).getMoral() + ")");
		}

		int choix = lireChoix();
		if (choix > 0 && choix <= creatures.size()) {
			Creature creatureChoisie = creatures.get(choix - 1);
			medecin.soigner(creatureChoisie);
		} else {
			System.out.println("    " + color.RED_BOLD + " ⛔Choix invalide." + color.RESET);
		}
	}



	/**
	 * void reviserBudget
	 * @param medecin
	 */
	private void reviserBudget(Medecin medecin) {
		ServiceMedical service = medecin.getServiceAssocie();
		if (service != null) {
			medecin.reviserBudget(service);
			System.out.println("Budget révisé pour le service : " + service.getNom());
		}
	}

	/**
	 * void transfererCreature
	 * @param medecin
	 */
	private void transfererCreature(Medecin medecin) {
		ServiceMedical serviceDepart = medecin.getServiceAssocie();

		if (serviceDepart != null && !serviceDepart.getCreatures().isEmpty()) {
			Creature creature = choisirCreature(serviceDepart);
			Class<?> typeCreature = creature.getClass();

			// Créer une liste de services compatibles pour le transfert
			List<ServiceMedical> servicesCompatibles = new ArrayList<>();
			for (ServiceMedical service : hopital.getServices()) {
				// Vérifier que le service d'arrivée a des créatures et que le type de la créature correspond
				if (!service.getCreatures().isEmpty() && service != serviceDepart) {
					Class<?> typePremiereCreature = service.getCreatures().get(0).getClass();
					if (typePremiereCreature.equals(typeCreature)) {
						servicesCompatibles.add(service);
					}
				}
			}

			// Vérifier s'il existe des services compatibles
			if (!servicesCompatibles.isEmpty()) {
				// Afficher les services compatibles pour le transfert
				System.out.println("Services compatibles pour le transfert :");
				for (int i = 0; i < servicesCompatibles.size(); i++) {
					ServiceMedical service = servicesCompatibles.get(i);
					System.out.println((i + 1) + ". " + service.getNom());
				}

				// Demander à l'utilisateur de choisir un service pour le transfert
				int choix = lireChoix();
				if (choix > 0 && choix <= servicesCompatibles.size()) {
					ServiceMedical serviceArrivee = servicesCompatibles.get(choix - 1);
					// Effectuer le transfert
					medecin.transfererCreature(creature, serviceDepart, serviceArrivee);
					System.out.println("Créature " + creature.getNom() + " transférée de " + serviceDepart.getNom() + " à " + serviceArrivee.getNom());
				} else {
					System.out.println("    " + color.RED_BOLD + "❌ Choix invalide. Le transfert a été annulé." + color.RESET);
				}
			} else {
				System.out.println("    " + color.RED_BOLD + "❌ Aucun service compatible trouvé pour ce transfert. Le transfert est annulé." + color.RESET);
			}
		} else {
			System.out.println("    " + color.RED_BOLD + "❌ Service de départ invalide ou aucune créature disponible." + color.RESET);
		}
	}


	/**
	 * ServiceMedical choisirService
	 * @return
	 */
	private ServiceMedical choisirService() {
		List<ServiceMedical> services = hopital.getServices();
		if (services.isEmpty()) {
			System.out.println("    " + color.RED_BOLD + "❌Aucun service médical disponible." + color.RESET);
			return null;
		}

		System.out.println("    " + color.YELLOW_BOLD + "Sélectionnez un service médical :");
		for (int i = 0; i < services.size(); i++) {
			System.out.println((i + 1) + ". " + services.get(i).getNom());
		}

		int choix = lireChoix();
		if (choix > 0 && choix <= services.size()) {
			return services.get(choix - 1);
		} else {
			System.out.println("    " + color.RED_BOLD + "❌ Choix invalide." + color.RESET);
			return null;
		}
	}

	/**
	 * Creature choisirCreature
	 * 
	 * @param service
	 * @return
	 */
	private Creature choisirCreature(ServiceMedical service) {
		List<Creature> creatures = service.getCreatures();
		if (creatures.isEmpty()) {
			System.out.println("    " + color.RED_BOLD + "❌ Aucune créature disponible dans ce service" + color.RESET);
			return null;
		}

		for (int i = 0; i < creatures.size(); i++) {
			System.out.println((i + 1) + ". " + creatures.get(i).getNom());
		}

		int choix = lireChoix();
		if (choix > 0 && choix <= creatures.size()) {
			return creatures.get(choix - 1);
		} else {
			System.out.println("    " + color.RED_BOLD + "❌ Choix invalide." + color.RESET);
			return null;
		}
	}
}