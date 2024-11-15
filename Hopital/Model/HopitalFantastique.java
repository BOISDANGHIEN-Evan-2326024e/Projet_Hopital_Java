package Model;


import java.util.ArrayList;
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
    
    public HopitalFantastique(String nom, int maxServices) {
        this.nom = nom;
        this.maxServices = maxServices;
        this.services = new ArrayList<>();
        this.medecins = new ArrayList<>();
        this.random = new Random();
        this.pauseSimulation = false;
        this.jeuTermine = false;
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
                    if (verifierFinDuJeu()) {
                        System.out.println("\n=== Toutes les créatures sont mortes. Le jeu est terminé. ===");
                        jeuTermine = true;
                        notify(); // Informe le thread du joueur
                        return;
                    }
                }

                long endTime = System.currentTimeMillis() + 10000; // 10 secondes de simulation
                while (System.currentTimeMillis() < endTime) {
                    try {
                        Thread.sleep(2000); // Modification toutes les 2 secondes

                        // Modifier aléatoirement l'état des créatures et services
                        for (ServiceMedical service : services) {
                            for (Creature creature : service.getCreatures()) {
                                int action = random.nextInt(3);
                                switch (action) {
                                    case 0 -> creature.tomberMalade(new Maladie("Maladie aléatoire", "MA", 5));
                                    case 1 -> creature.attendre(service.getCreatures());
                                    case 2 -> {
                                        if (!creature.getMaladies().isEmpty()) {
                                            creature.getMaladies().get(0).augmenterNiveau();
                                        }
                                    }
                                }
                            }
                        }

                        for (ServiceMedical service : services) {
                            int action = random.nextInt(3);
                            switch (action) {
                                case 0 -> service.reviserBudget("Budget révisé");
                                case 1 -> {
                                    if (service instanceof CentreQuarantaine quarantaine) {
                                        quarantaine.setIsolation(random.nextBoolean());
                                    } else if (service instanceof Crypte crypte) {
                                        crypte.setVentilation(random.nextInt(5) + 1);
                                        crypte.setTemperature(15 + random.nextDouble() * 10);
                                    }
                                }
                                case 2 -> service.soignerCreatures();
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

    private boolean verifierFinDuJeu() {
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
