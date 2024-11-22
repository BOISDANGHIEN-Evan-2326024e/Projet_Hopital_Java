package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Colonie {

    private List<Meute> listeMeutes; // Liste des meutes dans la colonie
    private Random random; // Générateur aléatoire pour diverses actions

    public Colonie() {
        this.listeMeutes = new ArrayList<>();
        this.random = new Random();
    }


    public void ajouterMeute(Meute meute) {
        listeMeutes.add(meute);
    }

    public void afficherLycanthropes() {
        System.out.println("===== Lycanthropes dans la colonie =====");
        for (Meute meute : listeMeutes) {
            System.out.println("Meute :");
            meute.afficherCaracteristiques();
        }
    }

    // Méthode principale (point d'entrée de la simulation)
    public void gestionColonie() {
        System.out.println("Début de la simulation de la colonie...");

        while (true) { // Boucle infinie pour la simulation
            try {
                Thread.sleep(10000); // Pause pour simuler le passage du temps (10 secondes ici)

                // 1. Créer une nouvelle meute si nécessaire
                if (doitCreerNouvelleMeute()) {
                    creerNouvelleMeute();
                }

                // 2. Vérifier si c'est la saison des amours et lancer la reproduction
                if (estSaisonAmours()) {
                    lancerReproduction();
                }

                // 3. Faire évoluer la hiérarchie des meutes
                evoluerHierarchieMeutes();

                // 4. Faire vieillir certains lycanthropes
                faireVieillirLycanthropes();

                // 5. Générer des hurlements aléatoires entre lycanthropes
                genererHurlementsAleatoires();

                // 6. Transformer quelques lycanthropes en humains
                transformerLycanthropes();

            } catch (InterruptedException e) {
                System.out.println("Simulation interrompue.");
                break;
            }
        }
    }

    // Déterminer si une nouvelle meute doit être créée
    private boolean doitCreerNouvelleMeute() {
        for (Meute meute : listeMeutes) {
            if (meute.getLycanthropes().size() > 12) {
                return true;
            }
        }
        if(listeMeutes.size()==0) {
        	return true;
        }
        return false;
    }

    // Créer une nouvelle meute
    private void creerNouvelleMeute() {
        Meute nouvelleMeute = new Meute(null); // A MODIFIER EN METTANT NOM A LA MAIN
        System.out.println("Une nouvelle meute a été créée !");
        listeMeutes.add(nouvelleMeute);
    }

    // Déterminer si c'est la saison des amours
    private boolean estSaisonAmours() {
        // 10% de chance que ce soit la saison des amours
        return random.nextInt(100) < 10;
    }

    // Lancer la reproduction dans toutes les meutes
    private void lancerReproduction() {
        System.out.println("C'est la saison des amours !");
        for (Meute meute : listeMeutes) {
            meute.reproduction();
        }
    }

    // Faire évoluer la hiérarchie des meutes
    private void evoluerHierarchieMeutes() {
        System.out.println("Évolution naturelle des hiérarchies dans les meutes...");
        for (Meute meute : listeMeutes) {
            meute.decroitreRangsNaturellement();
        }
    }

    // Faire vieillir certains lycanthropes
    private void faireVieillirLycanthropes() {
        System.out.println("Les lycanthropes vieillissent...");
        for (Meute meute : listeMeutes) {
            for (Lycanthrope lycan : meute.getLycanthropes()) {
            	if(lycan.getCategorieAge()=="jeune") {
            		lycan.setCategorieAge("adulte");
            	}
            	if(lycan.getCategorieAge()=="adulte") {
            		lycan.setCategorieAge("vieux");
            	}
            	else {
            		continue;
            	}
            }
        }
    }

    // Générer des hurlements aléatoires entre certains lycanthropes
    private void genererHurlementsAleatoires() {
        System.out.println("Des hurlements aléatoires résonnent dans la colonie...");
        for (Meute meute : listeMeutes) {
            List<Lycanthrope> lycanthropes = meute.getLycanthropes();
            if (lycanthropes.size() > 1) {
                // Sélectionner deux lycanthropes au hasard pour l'interaction
                Lycanthrope emetteur = lycanthropes.get(random.nextInt(lycanthropes.size()));
                Lycanthrope recepteur = lycanthropes.get(random.nextInt(lycanthropes.size()));
                if (!emetteur.equals(recepteur)) {
                    Hurlement hurlement = new Hurlement("domination", emetteur);
                    recepteur.entendreHurlement(hurlement, false);// A MODIFIER
                }
            }
        }
    }

    // Transformer quelques lycanthropes en humains
    private void transformerLycanthropes() {
        System.out.println("Quelques lycanthropes se transforment en humains...");
        for (Meute meute : listeMeutes) {
            for (Lycanthrope lycan : meute.getLycanthropes()) {
                if (random.nextInt(100) < 5) { // 5 % de chance de transformation
                    lycan.transformation();
                }
            }
        }
    }
}

