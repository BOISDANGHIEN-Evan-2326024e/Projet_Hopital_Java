package Model;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class HopitalFantastique {
	private String nom;
	private int maxServices;
	private List<ServiceMedical> services;
	private List<Medecin> medecins;
	private Random random;
	private boolean pauseSimulation; // Variable pour contrôler la pause de la simulation
	private boolean jeuTermine;
	private int round;

	public HopitalFantastique(String nom, int maxServices) {
		this.nom = nom;
		this.maxServices = maxServices;
		this.services = new ArrayList<>();
		this.medecins = new ArrayList<>();
		this.random = new Random();
		this.pauseSimulation = false;
		this.jeuTermine = false;
		this.round = 1;
	}

	public synchronized void pauseSimulation() {
		this.pauseSimulation = true;
	}

	public synchronized void resumeSimulation() {
		this.pauseSimulation = false;
		notify(); // Réveille le thread de simulation
	}

	public synchronized boolean estJeuTermine() {
		return jeuTermine;
	}

	public void ajouterService(ServiceMedical service) {
		if (services.size() < maxServices) {
			services.add(service);
		} else {
			System.out.println("Capacité maximale de services atteinte !");
		}
	}

	public void ajouterMedecin(Medecin medecin) {
		medecins.add(medecin);
	}

	public void afficherStatistiques() {
		int totalCreatures = 0;
		for (ServiceMedical service : services) {
			totalCreatures += service.getCreatures().size();
		}
		System.out.println("Nombre de créatures dans l'hôpital : " + totalCreatures);
	}

	public void afficherDetailsCreatures() {
		for (ServiceMedical service : services) {
			//System.out.println("Service: " + service.getNom());
			service.afficherDetails();
		}
	}

	public void demarrerSimulation() {
		Thread simulationThread = new Thread(() -> {
			while (true) {
				synchronized (this) {
					while (pauseSimulation) {
						try {
							wait(); // Attend que le joueur termine ses actions
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							return;
						}
					}
					if (verifierVictoire()) {
						System.out.println("\n=== Félicitations ! Toutes les maladies ont été éradiquées. Vous avez gagné ! ===");
						jeuTermine = true;
						notify(); // Informe le thread du joueur
						return;
					}

	                if (verifierDefaiteDestruction()) {
	                    jeuTermine = true;
	                    notify();
	                    return;
	                }
	                
					if (verifierFinDuJeu()) {
						System.out.println("\n=== Toutes les créatures sont mortes. Le jeu est terminé. ===");
						jeuTermine = true;
						notify(); // Informe le thread du joueur
						return;
					}
				}
				round++;
				gererMortsEtHeritage();

				long endTime = System.currentTimeMillis() + 6000; // 10 secondes de simulation
				while (System.currentTimeMillis() < endTime) {
					try {
						Thread.sleep(2000); // Modification toutes les 2 secondes

						// Modifier aléatoirement l'état des créatures et services
						for (ServiceMedical service : services) {
							for (Creature creature : service.getCreatures()) {
								int action = random.nextInt(3);
								switch (action) {
								case 0 -> { 
									if (random.nextInt(100) < 20) { 
										creature.tomberMalade();
									}
								}
								case 1 -> creature.attendre(service.getCreatures(), round);
								case 2 -> {
									if (!creature.getMaladies().isEmpty()) {
										creature.getMaladies().get(0).augmenterNiveau();
									}
									else {

									}
								}
								}
							}
						}

						for (ServiceMedical service : services) {
							int action = random.nextInt(10); // Ajouté 5 possibilités d'action
							switch (action) {
							case 0 -> {
								// Ajout d’une créature

								Creature nouvelleCreature = genererNouvelleCreature(service);
								if (nouvelleCreature != null) {
									service.ajouterCreature(nouvelleCreature);
									System.out.println("Nouvelle créature dans " + service.getNom() + (nouvelleCreature.getMaladies().isEmpty() ? " en bonne santé." : 
										" avec la maladie : " + nouvelleCreature.getMaladies().get(0).getNomComplet()));

								}

							}
							case 1  -> {
								// Événement : Fuite de gaz
								if (service instanceof CentreQuarantaine quarantaine && quarantaine.isIsolation()) {
									quarantaine.setIsolation(false);
									System.out.println("Une fuite de gaz a détruit l'isolation dans le service : " + service.getNom());
								}
							}
							case 2-> {
								// Événement : Ventilation bouchée
								if (service instanceof Crypte crypte) {
									int nouvelleVentilation = Math.max(0, crypte.getVentilation() - random.nextInt(3) - 1); // Réduit de 1 à 3
									crypte.setVentilation(nouvelleVentilation);
									System.out.println("La ventilation est bouchée dans le service : " + service.getNom() +  ". Niveau de ventilation : " + nouvelleVentilation);
								}
							}
							case 3 | 4 | 5 | 6 -> {
								// Événement : Réduction aléatoire du budget avec justification
								choisirJustification("malus");
								int reductionBudget = random.nextInt(200) + 50; // Réduit entre 50 et 250 crédits				
								if ((service.getBudget() - reductionBudget) > 0) {
									service.reviserBudget(service.getBudget() - reductionBudget);
									System.out.println(" Le budget du service " + service.getNom() + " a été réduit de " + reductionBudget + " crédits.");
								}
								else {
									service.reviserBudget(0);
									System.out.println(" Le budget du service " + service.getNom() + " est de 0... Ca commence à chauffer");
								}
							}
							case 7 | 8 -> {
								// Événement : Augmentaiton aléatoire du budget avec justification
								choisirJustification("bonus");
								int augmentationBudget = random.nextInt(200) + 50; // Réduit entre 50 et 250 crédits
								System.out.println(" Le budget du service " + service.getNom() + " a été augmenté de " + augmentationBudget + " crédits.");
							}
							case 9 -> {
								System.out.println("Vous avez énormément de chance aujourd'hui !!!! Le service " + service.getNom() + " vient de subir la visite d'un grand médecin gratuitement ! Tout le service est soigné :D");
								service.soignerCreatures();
							}
							}



						}
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						return;
					}
				}

				// Pause la simulation et réveille le thread du joueur
				pauseSimulation();
				synchronized (this) {
					notify(); // Signale au thread du joueur que son tour est venu
				}
			}
		});

		simulationThread.start();
	}

	public void gererMortsEtHeritage() {
		int mortsDansCetteSimulation = 0;

		for (ServiceMedical service : services) {
			Iterator<Creature> iterator = service.getCreatures().iterator();
			while (iterator.hasNext()) {
				Creature creature = iterator.next();
				if (!creature.estEnVie()) {
					mortsDansCetteSimulation++;
					iterator.remove(); // Supprime la créature morte
					lancerHeritage(service);  // Vérifie et lance l'événement "Héritage"
				}
			}
			int revenuTour = 100; // Revenu fixe par service
			service.setBudget(service.getBudget() + revenuTour);
		}


		if (mortsDansCetteSimulation == 0) {
			System.out.println("Aucune créature n'est morte");
		}
	}

	public void lancerHeritage(ServiceMedical service) {

		if (random.nextInt(100) < 25) { 
			int montant = random.nextInt(100) + 50; 
			service.setBudget(service.getBudget() + montant);
			System.out.println("Héritage reçu ! +" + montant + " crédits ajoutés au budget.");
		}
	}

	public boolean verifierFinDuJeu() {
		if (jeuTermine) {
			return true;
		}
		for (ServiceMedical service : services) {
			for (Creature creature : service.getCreatures()) {
				if (creature.estEnVie()) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean verifierDefaiteDestruction() {
		int servicesDetruits = 0; // Compteur des services détruits
		for (ServiceMedical service : services) {
			if (service.estDetruit()) { // Vérifie si le service est détruit
				servicesDetruits++;
			}
		}
		if (servicesDetruits >= 3) {
			System.out.println("\n=== Défaite : Trois services médicaux sont détruits. Vous avez perdu ! ===");
			return true;
		}
		return false;
	}

	public boolean verifierVictoire() {
		//Verifie si le joueur à gagné et met à jour l'état des créatures

		for (ServiceMedical service : services) {
			for (Creature creature : service.getCreatures()) {		
				creature.estEnVie();
				if (!creature.getMaladies().isEmpty()) {
					return false; // Il reste des maladies
				}
			}
		}
		return true; // Aucune maladie détectée
	}


	public Creature genererNouvelleCreature(ServiceMedical service) {
		//SI on a plus de noms dans le tableau, on return null donc aucune créature ne sera créé
		ArrayList<String> noms;
		ArrayList<String> types;
		String sexe = new Random().nextBoolean() ? "Mâle" : "Femelle";
		double poids = 50 + random.nextDouble() * 50; // Entre 50 et 100 kg
		double taille = 1.5 + random.nextDouble() * 0.5; // Entre 1.5 et 2.0 m
		ArrayList<String> ages = new ArrayList<>(List.of("jeune", "adulte", "vieux"));
		String age = ages.get(random.nextInt(ages.size()));

		if (service instanceof CentreQuarantaine) {
			noms = new ArrayList<>(List.of("Tarasbroast", "Vamrgi", "Alecndou", "Ujloff", "Mabmpios", "theecamp", "shaberus", "philisk", "kruana", "edxl"));
			//types =  new ArrayList<>(List.of("Orque", "Homme-bêtes", "Lycanthrope", "Vampire"));
			//FORCE ORQUE OU ELFE
			types =  new ArrayList<>(List.of("Orque"));
		}
		else {
			noms = new ArrayList<>(List.of("Grunt", "Gore", "Fang", "Shadow", "Moon"));
			//types =  new ArrayList<>(List.of("Zombie", "Vampire"));
			//FORCE ORQUE OU ELFE
			types =  new ArrayList<>(List.of("Elfe"));
		}
		if (!noms.isEmpty()) {
			int posNom = random.nextInt(noms.size());
			String nom = noms.get(posNom);
			noms.remove(posNom);

			int posType = random.nextInt(types.size());
			String type = types.get(posType);
			Creature creature;

			//FORCE ORQUE OU ELFE


			switch (type) {
			case "Orque" -> creature = new Orque(nom, sexe, poids, taille, age);
			//case "Homme-bêtes" -> creature = new HommeBêtes(nom, sexe, poids, taille, age);
			//case "Lycanthrope" -> creature = new Lycanthrope(nom, sexe, poids, taille, age);
			//case "Vampire" -> creature = new Vampire(nom, sexe, poids, taille, age);
			//case "Zombie" -> creature = new Zombie(nom, sexe, poids, taille, age);
			case "Elfe" -> creature = new Elfe(nom, sexe, poids, taille, age);

			default -> throw new IllegalStateException("Type de créature inconnu");
			}

			// Ajouter une maladie aléatoire dans certains cas
			if (random.nextBoolean()) { // 50% de chance
				creature.tomberMalade();
			}

			return creature;
		}	
		return null;
	}

	public void choisirJustification(String bm) {
		if (bm == "malus") {
			String[] justifications = {
					"Un voleur s'est emparé des caisses de l'hôpital : ",
					"Un orque en colère a provoqué un éboulement dans un service : ",
					"Des fournitures importantes ont été égarées : ",
					"Une erreur administrative a causé des pertes financières : ",
					"Une panne d'équipement a immobilisé un service : "
			};
			String justification = justifications[random.nextInt(justifications.length)];
			System.out.print(justification);
		}
		else {
			String[] justifications = {
					"Un riche donateur anonyme a offert une grosse somme : ",
					"Un trésor caché a été découvert sous le lit d'un pensionnaire : ",
					"Il y a eu un don mais pas de sang ! : ",
					"On a eu pitié de vous... MAIS : ",
					"Le roi des elfes a accordé une subvention royale : ",
					"Un partenariat avec un laboratoire a rapporté des fonds : "
			};
			String justification = justifications[random.nextInt(justifications.length)];
			System.out.print(justification);
		}
	}




	public String getNom() {
		return nom;
	}

	public int getMaxServices() {
		return maxServices;
	}

	public List<ServiceMedical> getServices() {
		return services;
	}

	public List<Medecin> getMedecins() {
		return medecins;
	}

	public boolean isPauseSimulation() {
		return pauseSimulation;
	}

	public void setPauseSimulation(boolean pauseSimulation) {
		this.pauseSimulation = pauseSimulation;
	}

	public boolean isJeuTermine() {
		return jeuTermine;
	}

	public void setJeuTermine(boolean jeuTermine) {
		this.jeuTermine = jeuTermine;
	}




}
