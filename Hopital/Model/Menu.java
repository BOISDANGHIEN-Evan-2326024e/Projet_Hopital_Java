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
            if (medecin == null)
            {
            	hopital.setJeuTermine(true);
            }
            else {
                System.out.println("Vous avez choisi le médecin : " + medecin.getNom());
                int actionsRestantes = 3;
                long tempsLimite = System.currentTimeMillis() + 10000; // Limite de 10 secondes

                while (actionsRestantes > 0 && System.currentTimeMillis() < tempsLimite) {
                    afficherOptions();
                    int choix = lireChoix();

                    switch (choix) {
                        case 1 -> soignerCreatures(medecin);
                        case 2 -> reviserBudget(medecin);
                        case 3 -> transfererCreature(medecin);
                        default -> System.out.println("Fin des actions pour ce médecin.");
                    }
                    actionsRestantes--;
                }
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
        System.out.println((medecins.size()+1) + ". " +"Abandonner la partie");

        int choix = lireChoix();
        if (choix > 0 && choix <= medecins.size()) {
            return medecins.get(choix - 1);
        } 
        else if (choix == medecins.size()+1){
        	return null;
        }
        else {
            System.out.println("Choix invalide.");
            return null;
        }
    }

    private void afficherOptions() {
        System.out.println("\nSélectionnez une action :");
        System.out.println("1. Soigner les créatures dans un service médical");
        System.out.println("2. Réviser le budget d'un service médical");
        System.out.println("3. Transférer une créature entre services médicaux");
        System.out.println("4. Fin des actions pour ce médecin");
    }

    private int lireChoix() {
        System.out.print("Votre choix : ");
        return scanner.nextInt();
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
            String nouveauBudget = scanner.next();
            medecin.reviserBudget(service, nouveauBudget);
            System.out.println("Budget révisé pour le service : " + service.getNom());
        }
    }

    private void transfererCreature(Medecin medecin) {
        ServiceMedical serviceDepart = choisirService();
        if (serviceDepart != null && !serviceDepart.getCreatures().isEmpty()) {
            Creature creature = choisirCreature(serviceDepart);
            ServiceMedical serviceArrivee = choisirService();
            if (serviceArrivee != null && serviceArrivee != serviceDepart) {
                medecin.transfererCreature(creature, serviceDepart, serviceArrivee);
                System.out.println("Créature " + creature.getNom() + " transférée de " + 
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
            System.out.println((i + 1) + ". " + creatures.get(i).getNom());
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
