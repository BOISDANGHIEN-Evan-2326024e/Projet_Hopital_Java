package Model;



import java.util.List;
import java.util.Scanner;

public class Menu implements Runnable{
	private HopitalFantastique hopital;
	private Scanner scanner;

	public Menu(HopitalFantastique hopital) {
		this.hopital = hopital;
		this.scanner = new Scanner(System.in);
	}

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
				afficherOptions();
				int choix = lireChoix();

				switch (choix) {
				case 1 -> {
					soignerCreatures(medecin);
					actionsRestantes = 1;
				}
				case 2 -> reviserBudget(medecin);
				case 3 -> transfererCreature(medecin);
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


	private Medecin choisirMedecin() {
		List<Medecin> medecins = hopital.getMedecins();
		if (medecins.isEmpty()) {
			System.out.println("Aucun médecin disponible.");
			return null;
		}

		for (int i = 0; i < medecins.size(); i++) {
			System.out.println((i + 1) + ". " + medecins.get(i).getNom());
		}
		System.out.println((medecins.size() + 1) + ". Afficher les statistiques");
		System.out.println((medecins.size() + 2) + ". Afficher les détails des créatures");
		System.out.println((medecins.size() + 3) + ". Abandonner la partie");

		int choix = lireChoix();

		// Gérer les choix supplémentaires
		while (choix == medecins.size() + 1 || choix == medecins.size() + 2 || choix < 0 || choix > medecins.size() + 3) {
			if (choix == medecins.size() + 1) {
				hopital.afficherStatistiques();
			} else if (choix == medecins.size() + 2) {
				hopital.afficherDetailsCreatures();
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


	private void afficherOptions() {
		System.out.println("\nSélectionnez une action :");
		System.out.println("1. Soigner les créatures dans un service médical");
		System.out.println("2. Réviser le budget d'un service médical");
		System.out.println("3. Transférer une créature entre services médicaux");
		System.out.println("4. Fin des actions pour ce médecin");
	}

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

	private void soignerCreatures(Medecin medecin) {
		ServiceMedical service = choisirService();
		if (service != null) {
			medecin.soigner(service);
		}
	}

	private void reviserBudget(Medecin medecin) {
		ServiceMedical service = choisirService();
		if (service != null) {
			System.out.print("Entrez le nouveau budget pour le service : ");
			int nouveauBudget = scanner.nextInt();
			medecin.reviserBudget(service, nouveauBudget);
			System.out.println("Budget révisé pour le service : " + service.getNom());
		}
	}

	private void transfererCreature(Medecin medecin) {
		ServiceMedical serviceDepart = choisirService();
		if (serviceDepart != null && !serviceDepart.getCreatures().isEmpty()) {
			Creature creature = choisirCreature(serviceDepart);
			ServiceMedical serviceArrivee = choisirService();
			Class<?> typePremiereCreature = serviceArrivee.getCreatures().get(0).getClass();
			if (serviceArrivee != null && serviceArrivee != serviceDepart && creature.getClass().equals(typePremiereCreature)) {
				
				medecin.transfererCreature(creature, serviceDepart, serviceArrivee);
				System.out.println("Créature " + creature.nom + " transférée de " + 
						serviceDepart.getNom() + " à " + serviceArrivee.getNom());
			} else {
				System.out.println("Service d'arrivée invalide.");
			}
		} else {
			System.out.println("Service de départ invalide ou aucune créature disponible.");
		}
	}

	private ServiceMedical choisirService() {
		List<ServiceMedical> services = hopital.getServices();
		if (services.isEmpty()) {
			System.out.println("Aucun service médical disponible.");
			return null;
		}

		System.out.println("Sélectionnez un service médical :");
		for (int i = 0; i < services.size(); i++) {
			System.out.println((i + 1) + ". " + services.get(i).getNom());
		}

		int choix = lireChoix();
		if (choix > 0 && choix <= services.size()) {
			return services.get(choix - 1);
		} else {
			System.out.println("Choix invalide.");
			return null;
		}
	}

	private Creature choisirCreature(ServiceMedical service) {
		List<Creature> creatures = service.getCreatures();
		if (creatures.isEmpty()) {
			System.out.println("Aucune créature disponible dans ce service.");
			return null;
		}

		for (int i = 0; i < creatures.size(); i++) {
			System.out.println((i + 1) + ". " + creatures.get(i).nom);
		}

		int choix = lireChoix();
		if (choix > 0 && choix <= creatures.size()) {
			return creatures.get(choix - 1);
		} else {
			System.out.println("Choix invalide.");
			return null;
		}
	}
}
