package Controller;

import Model.*;

public class Main {
    public static void main(String[] args) {
        // Création de l'hôpital fantastique
        HopitalFantastique hopital = new HopitalFantastique("Hôpital Fantastique", 5);

        // Création de services médicaux
        CentreQuarantaine centreQuarantaine = new CentreQuarantaine("Centre de Quarantaine", 200, 10, 5000, true);
        Crypte crypte = new Crypte("Crypte", 150, 5, 5000, 3, 18.0);

        // Ajout des services à l'hôpital
        hopital.ajouterService(centreQuarantaine);
        hopital.ajouterService(crypte);

        // Création de créatures et ajout aux services
        Creature orque1 = new Orque("Orque Grunt", "Mâle", 90.5, 1.8, "jeune");
        Creature orque2 = new Orque("Orque Gore", "Femelle", 85.0, 1.75, "jeune");
        orque2.tomberMalade();
        Creature elfe1 = new Elfe("Elfe Sombre", "Femelle", 70.0, 1.6, "jeune");
        Creature elfe2 = new Elfe("Elfe Nocturne", "Mâle", 65.0, 1.65, "jeune");

        // Ajout des créatures dans les services
        centreQuarantaine.ajouterCreature(orque1);
        centreQuarantaine.ajouterCreature(orque2);
        crypte.ajouterCreature(elfe1);
        crypte.ajouterCreature(elfe2);

        // Création de médecins
        Medecin medecin1 = new Medecin("Docteur Malthus", "Mâle", 45);
        Medecin medecin2 = new Medecin("Docteur Sélène", "Femelle", 50);

        // Ajout des médecins à l'hôpital
        hopital.ajouterMedecin(medecin1);
        hopital.ajouterMedecin(medecin2);

        // Affichage des informations initiales
        System.out.println("=== Informations Initiales de l'Hôpital ===");
        hopital.afficherStatistiques();
        hopital.afficherDetailsCreatures();

        // Démarrer la simulation
        System.out.println("\n=== Démarrage de la Simulation ===");
        hopital.demarrerSimulation();
        
        // Démarrer le menu pour permettre à l'utilisateur de prendre la main
        Menu menu = new Menu(hopital);
        new Thread(menu).start(); // Lance le menu en ligne de commande
    }
}
