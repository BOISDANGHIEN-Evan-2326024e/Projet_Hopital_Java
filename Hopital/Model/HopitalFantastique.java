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
	public int nbrMort;

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
		System.out.println("Nombre de créatures mortes depuis le début : " + nbrMort);
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
					
					for (ServiceMedical service : services) {
						for (Creature creature : service.getCreatures()) {
							if (!creature.estEnVie())
							{
								nbrMort += 1;
							}
							if(creature.getMaladies().isEmpty()) 
							{
								service.retirerCreature(creature);
							}
						}
					}

	                
					if (verifierFinDuJeu()) {
						System.out.println("\n=== Il y a eu trop de mort dans votre hopital ("+nbrMort+")... Le jeu est terminé. ===");
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
										creature.tomberMalade(service);
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
							int action = random.nextInt(15); // Ajouté 5 possibilités d'action
							switch (action) {
							case 0 | 12 | 13 -> {
								// Ajout d’une créature

								Creature nouvelleCreature = genererNouvelleCreature(service);
								if (nouvelleCreature != null) {
									service.ajouterCreature(nouvelleCreature);
									System.out.println("Nouvelle créature " + nouvelleCreature.nom +" dans " + service.getNom() + (nouvelleCreature.getMaladies().isEmpty() ? " en bonne santé." : 
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
								
								//Le saint médecin soigne des créatures VIP 
								List<ServiceMedical> servicesVIP = new ArrayList<>();
								for (ServiceMedical serviceVIP : services) {
								    for (Creature creature : serviceVIP.getCreatures()) {
								        if (creature.getCategorie() == Categorie.VIP) {
								            servicesVIP.add(serviceVIP); // Ajoute le service à la liste si une créature VIP est trouvée
								            break; // Pas besoin de vérifier les autres créatures de ce service
								        }
								    }
								}
								if (servicesVIP != null && !servicesVIP.isEmpty()) {
								    Random random = new Random();
								    ServiceMedical serviceVIP = servicesVIP.get(random.nextInt(servicesVIP.size()));
								    System.out.println("Les VIP avant votre vie :D : Le saint médecin rend visite au service " + serviceVIP.getNom());
								    saintMedecin(serviceVIP);
								} else {
								    System.out.println("La liste des services VIP est vide ou nulle.");
								}
								
																
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
		if (jeuTermine || nbrMort >= 5) {
			return true;
		}
		return false;
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

			creature.tomberMalade(service);
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

	public void saintMedecin(ServiceMedical service) {
	    List<Creature> creatures = service.getCreatures();

	    if (creatures.isEmpty()) {
	        System.out.println("Le service " + service.getNom() + " ne contient aucune créature.");
	        return;
	    }

	    Random random = new Random();
	    int nombreCreaturesSoignees = Math.min(3, creatures.size()); // Soigne au maximum 3 créatures
	    List<Creature> creaturesChoisies = new ArrayList<>();

	    // Sélection aléatoire de 3 créatures
	    for (int i = 0; i < nombreCreaturesSoignees; i++) {
	        Creature creature;
	        do {
	            creature = creatures.get(random.nextInt(creatures.size()));
	        } while (creaturesChoisies.contains(creature)); // Assure que chaque créature est unique
	        creaturesChoisies.add(creature);
	    }

	    // Soigne les créatures choisies
	    for (Creature creature : creaturesChoisies) {
	        creature.soigner();
	        System.out.println("Le Saint medecin a soigné " + creature.nom + ".");
	        service.retirerCreature(creature); // La créature quitte le service après le soin
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
