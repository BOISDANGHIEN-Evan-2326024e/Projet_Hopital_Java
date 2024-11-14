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

    public HopitalFantastique(String nom, int maxServices) {
        this.nom = nom;
        this.maxServices = maxServices;
        this.services = new ArrayList<>();
        this.medecins = new ArrayList<>();
        this.random = new Random();
        this.pauseSimulation = false;
    }

    public synchronized void pauseSimulation() {
        this.pauseSimulation = true;
    }

    public synchronized void resumeSimulation() {
        this.pauseSimulation = false;
        notify(); // Réveille le thread de simulation
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
        int totalCreatures = services.stream().mapToInt(s -> s.getCreatures().size()).sum();
        System.out.println("Nombre de créatures dans l'hôpital : " + totalCreatures);
    }

    public void afficherDetailsCreatures() {
        for (ServiceMedical service : services) {
            System.out.println("Service: " + service.getNom());
            service.afficherDetails();
        }
    }

    public void demarrerSimulation() {
        Thread simulationThread = new Thread(() -> {
            while (true) {
                synchronized (this) {
                    while (pauseSimulation) {
                        try {
                            wait(); // Attente pendant que la simulation est en pause
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                }

                try {
                    Thread.sleep(5000); // Intervalle de 5 secondes

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
        });

        simulationThread.start();
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
}
