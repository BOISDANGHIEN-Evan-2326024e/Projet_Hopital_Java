package Simulation;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import Affichage.TextColor;
import Creatures.Creature;
import Creatures.Elfe;
import Creatures.HommeBete;
import Creatures.Medecin;
import Creatures.Nain;
import Creatures.Orque;
import Creatures.Reptilien;
import Creatures.Vampire;
import Creatures.Zombie;
import Enum.Categorie;
import Maladie.Maladie;
import ServicesMedicaux.CentreQuarantaine;
import ServicesMedicaux.Crypte;
import ServicesMedicaux.ServiceMedical;

public class HopitalFantastique {
	private String nom;
	private int maxServices;
	private List<ServiceMedical> services;
	private List<Medecin> medecins;
	private static Random random;
	private boolean pauseSimulation; // Variable pour contrôler la pause de la simulation
	private boolean jeuTermine;
	private int round;
	public int nbrMort;
	private static TextColor color = new TextColor();

	/**
	 * HopitalFantastique
	 * @param nom
	 * @param maxServices
	 */
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

	/**
	 * void pauseSimulation
	 */
	public synchronized void pauseSimulation() {
		this.pauseSimulation = true;
	}

	/**
	 * void resumeSimulation
	 */
	public synchronized void resumeSimulation() {
		this.pauseSimulation = false;
		notify();
	}

	/**
	 * boolean estJeuTermine
	 * @return
	 */
	public synchronized boolean estJeuTermine() {
		return jeuTermine;
	}

	/**
	 * void ajouterService
	 * @param service
	 */
	public void ajouterService(ServiceMedical service) {
		if (services.size() < maxServices) {
			services.add(service);
		} else {
			System.out.println("Capacité maximale de services atteinte !");
		}
	}
	
	/**
	 * void ajouterMedecin
	 * @param medecin
	 */
	public void ajouterMedecin(Medecin medecin) {
		medecins.add(medecin);
	}

	/**
	 * void afficherStatistiques
	 */
	public void afficherStatistiques() {
		int totalCreatures = 0;
		for (ServiceMedical service : services) {
			totalCreatures += service.getCreatures().size();
		}
		System.out.println(color.BRIGHT_BLUE +"Nombre de créatures dans l'hôpital : "+ color.RESET + totalCreatures);
		System.out.println(color.BRIGHT_BLUE + "Nombre de créatures mortes depuis le début : "+ color.RESET + color.RED + nbrMort + color.RESET);
	}

	/**
	 * void afficherDetailsCreatures
	 */
	public void afficherDetailsCreatures() {
		for (ServiceMedical service : services) {
			//System.out.println("Service: " + service.getNom());
			service.afficherDetails();
		}
	}
	
	/**
	 * void demarrerSimulation
	 */
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

					for (ServiceMedical service : new ArrayList<>(services)) {

						for (Creature creature : new ArrayList<>(service.getCreatures())) {
							//Si une maladie a atteint le niveau léthal, la créature meurt
							for (Maladie maladie : creature.getMaladies()) {
								if (maladie.estLethal()) {
									creature.trepasser(service);
								}
							}
							// Si la créature est morte est augmente le nbrMort sauf si c'est un zombie ou un vampire
							if (!creature.estEnVie())
							{
								creature.trepasser(service);
								if (!(creature instanceof Zombie) || !(creature instanceof Vampire)) {
									nbrMort += 1;
								}
							}
							// Si une creature n'a plus de maladies, elle quitte l'hopital
							if(creature.getMaladies().isEmpty()) 
							{
								service.retirerCreature(creature);
							}
						}
					}


					if (verifierFinDuJeu()) {
						System.out.println("\n=== Il y a eu trop de mort dans votre hopital ("+nbrMort+")... Le jeu est terminé. ===");
						jeuTermine = true;
						notify(); 
						return;
					}

				}

				System.out.println("\n=== Lancement de la Simulation : Tour "+ round +" ===");
				round++;
				gererMortsEtHeritage();

				long endTime = System.currentTimeMillis() + 8000; 
				//long endTime = System.currentTimeMillis() + 6000; 
				while (System.currentTimeMillis() < endTime) {
					try {
						Thread.sleep(4000); // Modification toutes les 10 secondes

						// Modifier aléatoirement l'état des créatures
						for (ServiceMedical service : services) {
							if (random.nextInt(100) >= 50) {
								continue; // Passe ce service si le tirage est supérieur ou égal à 50
							}
							Iterator<Creature> creatureIterator = service.getCreatures().iterator();
							while (creatureIterator.hasNext()) {
								Creature creature = creatureIterator.next();
								int action = random.nextInt(10);
								switch (action) {
								case 0, 1 -> {
									if (random.nextInt(100) < 20) {
										creature.tomberMalade(service);
									}
								}
								case 2 -> {
									creature.assignerStrategie();
									creature.attendre(service.getCreatures(), round);
								}
								case 3, 4 -> {
									if (!creature.getMaladies().isEmpty()) {
										creature.getMaladies().get(0).augmenterNiveau();
									}
								}
								//case 6 -> mangerPapierToilette(service, creature);
								case 8 -> sangAil(service);
								case 5 -> { 
									choisirJustificationeMoraleCreature("bonus");
									int augmentationMoral = random.nextInt(100) + 100; // Augmente entre 100 et 200 
									if (!service.getCreatures().isEmpty()) {
										System.out.println( service.getNom() + " : Le moral des créatures augmente !");
										for (Creature creatureService : service.getCreatures()) {
											creatureService.setMoral(creature.getMoral() + augmentationMoral);
										}
									}	
								}
								case 7, 6 -> {
									choisirJustificationeMoraleCreature("malus");
									int diminutionMoral = random.nextInt(100); // Augmente entre 0 a 100
									if (!service.getCreatures().isEmpty()) {
										System.out.println( service.getNom() + " : Le moral des créatures diminue !");
										for (Creature creatureService : service.getCreatures()) {
											creatureService.setMoral(creature.getMoral() - diminutionMoral);
										}
									}	    							}
								}
							}
						}

						for (ServiceMedical service : services) {
							if (random.nextInt(100) >= 50) {
								continue; // Passe ce service si le tirage est supérieur ou égal à 50
							}
							int action = random.nextInt(15); // Ajouté 5 possibilités d'action
							switch (action) {
							case 0, 12, 13 -> {
								// Ajout d’une créature

								Creature nouvelleCreature = genererNouvelleCreature(service);
								if (nouvelleCreature != null) {
									service.ajouterCreature(nouvelleCreature);
									System.out.println("Nouvelle créature " + nouvelleCreature.getNom() +" dans " + service.getNom() + (nouvelleCreature.getMaladies().isEmpty() ? " en bonne santé." : 
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
							case 3, 4, 5, 6 -> {
								// Événement : Réduction aléatoire du budget avec justification
								choisirJustificationCapital("malus");
								int reductionBudget = random.nextInt(200) + 100; // Réduit entre 100 et 300 crédits			
								if ((service.getBudget() - reductionBudget) > 0) {
									service.setCapital(service.getCapital() - reductionBudget);
									service.reviserBudget();
									System.out.println("Le budget du service " + service.getNom() + " a été réduit de " + reductionBudget + " crédits.");
								}
								else {
									service.setCapital(0);
									service.reviserBudget();
									System.out.println("Le budget du service " + service.getNom() + " est de 0... Ca commence à chauffer");
								}
							}
							case 7, 8 -> {
								// Événement : Augmentaiton aléatoire du budget avec justification
								choisirJustificationCapital("bonus");
								int augmentationBudget = random.nextInt(200) + 100; // Augmente entre 100 et 300 crédits
								service.setCapital(service.getCapital() + augmentationBudget);
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
									Random random = new Random();
									if (!service.getCreatures().isEmpty()) {
										Creature creatureASoigner = service.getCreatures().get(random.nextInt(service.getCreatures().size()));
										creatureASoigner.soigner(service);
										System.out.println("Aucun VIP ? Occupons nous du sous peuple : " + creatureASoigner.getNom() + " est soigné et quitte donc le service " + service.getNom());
									}
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


	/**
	 * void gererMortsEtHeritage
	 */
	public void gererMortsEtHeritage() {

		for (ServiceMedical service : services) {
			Iterator<Creature> iterator = service.getCreatures().iterator();
			while (iterator.hasNext()) {
				Creature creature = iterator.next();
				if (!creature.estEnVie()) {
					nbrMort++;
					iterator.remove(); // Supprime la créature morte
					lancerHeritage(service);  // Vérifie et lance l'événement "Héritage"
				}
			}
			int revenuTour = 100; // Revenu fixe par service
			service.setBudget(service.getBudget() + revenuTour);
		}

		if (nbrMort == 0) {
			System.out.println("Aucune créature n'est morte");
		}
		else {
			System.out.println("Dans cette partie, " + nbrMort + " créatures sont mortes");
		}
	}

	/**
	 * void lancerHeritage
	 * @param service
	 */
	public void lancerHeritage(ServiceMedical service) {

		if (random.nextInt(100) < 25) { 
			int montant = random.nextInt(100) + 50; 
			service.setBudget(service.getBudget() + montant);
			System.out.println("Héritage reçu ! +" + montant + " crédits ajoutés au budget.");
		}
	}

	/**
	 * boolean verifierFinDuJeu
	 * @return
	 */
	public boolean verifierFinDuJeu() {
		if (jeuTermine || nbrMort >= 5) {
			return true;
		}
		return false;
	}

	/**
	 * static Creature genererNouvelleCreature
	 * @param service
	 * @return
	 */
	public static Creature genererNouvelleCreature(ServiceMedical service) {
		ArrayList<String> noms;
		ArrayList<String> types;

		// Vérifie si le service est vide (aucune créature)
		boolean serviceVide = service.getCreatures().isEmpty();

		// Définir les noms et types disponibles en fonction du service
		if (service instanceof CentreQuarantaine) {
			noms = new ArrayList<>(List.of(
					"Tarasbroast", "Vamrgi", "Alecndou", "Ujloff", "Mabmpios", "Theecamp", "Shaberus", "Philisk", 
					"Kruana", "Edxl", "Mogdar", "Dugruk", "Kargath", "Grashnak", "Bolgor", "Thrall", "Zogoth", "Turgar", 
					"Loktar", "Goroth", "Orkkaz", "Morgath", "Drogan", "Kilgore", "Skarn", "Thrash", "Voktar", "Harnok", 
					"Rend", "Wargoth", "Krul", "Harnash", "Mokthar", "Thragor", "Karnak", "Blargh", "Guthar", "Druknar",
					"Graltor", "Zargrath", "Loknar", "Zogg", "Grashnak", "Molgar", "Trogdor", "Krangor", "Varg", "Krognar",
					"Gorthal", "Durzan", "Tarnok", "Blorg", "Vorgrath", "Nargul", "Drogath", "Orgrimmar", "Zalthar", "Trokthar"
					));
			types = serviceVide ? new ArrayList<>(List.of("Orque", "HommeBete", "Vampire")) 
					: new ArrayList<>(List.of(service.getCreatures().get(0).getClass().getSimpleName()));

		} else if (service instanceof Crypte) {
			noms = new ArrayList<>(List.of(
					"Grunt", "Gore", "Fang", "Shadow", "Moon", "Rotfang", "Blackveil", "Skullface", "Bonecrush", 
					"Deathfang", "Darkshade", "Blooddrinker", "Soulreaper", "Nightshade", "Raven", "Howler", "Shadovar", 
					"Varnel", "Zharak", "Kaelrin", "Morgrave", "Selzith", "Draven", "Korthul", "Xalthis", "Vilzith", 
					"Vorthak", "Malzrak", "Narzath", "Karnith", "Skelvin", "Razen", "Dreadnought", "Bloodshade", 
					"Soulfang", "Thalzon", "Xalvar", "Zarnak", "Krovath", "Morthas", "Velshar", "Zharak", "Xarnath",
					"Bloodfang", "Rotshade", "Soulblade", "Vexthar", "Korzath", "Malgrim", "Thrashveil", "Darkmourne", 
					"Soulreaver", "Nightfang", "Shadowclaw", "Vexoth", "Grimveil", "Bonefang", "Rotclaw", "Dreadshade"
					));
			types = serviceVide ? new ArrayList<>(List.of("Zombie", "Zombie", "Vampire")) 
					: new ArrayList<>(List.of(service.getCreatures().get(0).getClass().getSimpleName()));

		} else { // Service Standard
			noms = new ArrayList<>(List.of(
					"Elindir", "Thranduil", "Durin", "Fingolfin", "Ecthelion", "Gloin", "Dwalin", "Kili", "Fili", 
					"Balin", "Thorin", "Legolas", "Celeborn", "Galadriel", "Arwen", "Eowyn", "Elrohir", "Elladan", 
					"Isildur", "Anarion", "Aragorn", "Boromir", "Faramir", "Denethor", "Gimli", "Glorfindel", 
					"Cirdan", "Elrond", "Thranduil", "Tauriel", "Luthien", "Melian", "Thingol", "Finrod", "Feanor", 
					"Maedhros", "Maglor", "Caranthir", "Curufin", "Amrod", "Amras", "Turgon", "Idril", "Ecthelion", 
					"Eärendil", "Aegnor", "Angrod", "Haldir", "Oropher", "Gil-galad", "Ar-Pharazôn", "Tar-Miriel", 
					"Numenor", "Elros", "Barahir", "Beren", "Luthien", "Thingol", "Beleg", "Hurin", "Morwen", "Turin"
					));
			types = serviceVide ? new ArrayList<>(List.of("Nain", "Elfe", "Reptilien")) 
					: new ArrayList<>(List.of(service.getCreatures().get(0).getClass().getSimpleName()));
		}

		if (noms.isEmpty()) {
			return null; // Pas de noms disponibles, aucune créature ne sera créée
		}

		// Sélection aléatoire d'un nom
		int posNom = random.nextInt(noms.size());
		String nom = noms.get(posNom);
		noms.remove(posNom); // Supprime le nom utilisé de la liste

		// Sélection aléatoire d'un type
		int posType = random.nextInt(types.size());
		String type = types.get(posType);

		// Caractéristiques aléatoires de la créature
		String sexe = random.nextBoolean() ? "Mâle" : "Femelle";
		double poids = 50 + random.nextDouble() * 50; // Entre 50 et 100 kg
		double taille = 1.5 + random.nextDouble() * 0.5; // Entre 1.5 et 2.0 m
		String age = List.of("jeune", "adulte", "vieux").get(random.nextInt(3));

		// Création de la créature
		Creature creature = switch (type) {
		case "Orque" -> new Orque(type + " " + nom, sexe, poids, taille, age);
		case "HommeBete" -> new HommeBete(type + " " + nom, sexe, poids, taille, age);
		case "Vampire" -> new Vampire(type + " " + nom, sexe, poids, taille, age);
		case "Zombie" -> new Zombie(type + " " + nom, sexe, poids, taille, age);
		case "Nain" -> new Nain(type + " " + nom, sexe, poids, taille, age);
		case "Elfe" -> new Elfe(type + " " + nom, sexe, poids, taille, age);
		case "Reptilien" -> new Reptilien(type + " " + nom, sexe, poids, taille, age);
		//case "Lycanthor............
		default -> throw new IllegalArgumentException("Type de créature inconnu : " + type);
		};

		// Affecter une maladie dès la création
		creature.tomberMalade(service);
		return creature;
	}


	/**
	 * void choisirJustificationMoraleCreature
	 * @param bm
	 */
	public void choisirJustificationeMoraleCreature(String bm) {
		if (bm.equals("malus")) { // Comparaison correcte pour les chaînes
			String[] justifications = {
					"Panique générale dans le service : ",
					"Un cri terrifiant a retenti, semant la peur parmi les créatures : ",
					"Un accident a provoqué un effondrement partiel, créant un chaos généralisé : ",
					"Une créature enragée a semé la terreur : ",
					"Des bruits étranges résonnent dans le service, inquiétant tout le monde : ",
					"Une coupure d'électricité a plongé le service dans l'obscurité totale : ",
					"Un étrange gaz s'est répandu, rendant les créatures agitées : ",
					"Les créatures se sentent seules et abandonnées : ",
					"Un conflit a éclaté entre plusieurs créatures : "
			};
			String justification = justifications[random.nextInt(justifications.length)];
			System.out.print(justification);
		} else { // Bonus pour le moral
			String[] justifications = {
					"Le clown est passé, tout le monde l'adore : ",
					"Un banquet improvisé a ravi les créatures : ",
					"Une musique apaisante a calmé les esprits : ",
					"Un événement joyeux a redonné le sourire à tous : ",
					"Une créature charismatique a remonté le moral du groupe : ",
					"Les créatures ont reçu des cadeaux inattendus : ",
					"Une séance de détente et de méditation a été organisée : ",
					"Un feu de camp convivial a rassemblé tout le monde : ",
					"Les créatures se sentent en sécurité grâce à un nouveau médecin compétent : "
			};
			String justification = justifications[random.nextInt(justifications.length)];
			System.out.print(justification);
		}
	}



	/**
	 * void choisirJustificationCapital
	 * @param bm
	 */
	public void choisirJustificationCapital(String bm) {
		if (bm == "malus") {
			String[] justifications = {
					"Un voleur s'est emparé des caisses de l'hôpital : ",
					"Un orque en colère a provoqué un éboulement dans un service : ",
					"Des fournitures importantes ont été égarées : ",
					"Une erreur administrative a causé des pertes financières : ",
					"Une panne d'équipement a immobilisé un service : "
			};
			String justification = justifications[random.nextInt(justifications.length)];
			System.out.println(justification);
			
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
			System.out.println(justification);
			
		}
	}

	/**
	 *  void saintMedecin
	 * @param service
	 */
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
			creature.soigner(service);
			System.out.println("Le Saint medecin a soigné " + creature.getNom() + ".");
		}
	}


	/** 
	 * void sangAil
	 * @param service
	 */
	private void sangAil(ServiceMedical service) {
		boolean contientVampire = service.getCreatures().stream()
				.anyMatch(creature -> creature instanceof Vampire);
		if (contientVampire) {
			System.out.println("Sang à l'ail dans le service " + service.getNom() + " : Les maladies des créatures augmentent !");
			for (Creature creature : service.getCreatures()) {
				if (!creature.getMaladies().isEmpty()) {
					creature.getMaladies().forEach(Maladie::augmenterNiveau);
				}
			}
		}
	}


	/**
	 * Strong getNom
	 * @return
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * int getMaxServices
	 * @return
	 */
	public int getMaxServices() {
		return maxServices;
	}

	/**
	 * List<ServiceMedical> getServices
	 * @return
	 */
	public List<ServiceMedical> getServices() {
		return services;
	}

	/**
	 * List<Medecin> getMedecins
	 * @return
	 */
	public List<Medecin> getMedecins() {
		return medecins;
	}

	/**
	 * boolean isPauseSimulation
	 * @return
	 */
	public boolean isPauseSimulation() {
		return pauseSimulation;
	}

	/**
	 * void setPausesSimulation
	 * @param pauseSimulation
	 */
	public void setPauseSimulation(boolean pauseSimulation) {
		this.pauseSimulation = pauseSimulation;
	}

	/**
	 * boolean isJeuTermine
	 * @return
	 */
	public boolean isJeuTermine() {
		return jeuTermine;
	}

	/**
	 * void setJeuTermine
	 * @param jeuTermine
	 */
	public void setJeuTermine(boolean jeuTermine) {
		this.jeuTermine = jeuTermine;
	}

}
