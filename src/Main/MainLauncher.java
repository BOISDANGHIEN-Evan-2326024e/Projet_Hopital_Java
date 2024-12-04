package Main;

import java.lang.reflect.Method;
import java.util.Scanner;

public class MainLauncher {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Demander à l'utilisateur quel module lancer
        System.out.println("Quelle simulation souhaitez-vous lancer ?");
        System.out.println("1 - Hopital fantastique : rentrez HF");
        System.out.println("2 - Colonie de Lycanthrope : rentrez C");

        String choix;
        String classNameToLaunch = "";

        // Boucle pour obtenir un choix valide
        while (true) {
            choix = scanner.next();
            if (choix.equals("HF")) {
                classNameToLaunch = "Simulation.Main"; // Package + classe principale
                break;
            } else if (choix.equals("C")) {
                classNameToLaunch = "ClassColonie.MainColonie"; // Package + classe principale
                break;
            } else {
                System.out.println("Choix invalide ! Veuillez entrer HF ou C.");
            }
        }

        try {
            // Charger la classe dynamiquement
            Class<?> mainClass = Class.forName(classNameToLaunch);

            // Récupérer la méthode `main`
            Method mainMethod = mainClass.getMethod("main", String[].class);

            // Appeler la méthode `main` avec un tableau d'arguments vide
            System.out.println("Lancement de : " + classNameToLaunch);
            mainMethod.invoke(null, (Object) new String[]{});
        } catch (Exception e) {
            System.err.println("Erreur lors du lancement : " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close(); // Fermer le scanner
    }
}
