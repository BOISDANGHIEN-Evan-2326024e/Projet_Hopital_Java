package Main;
import java.lang.reflect.Method;
import java.util.Scanner;

public class MainLauncher {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Demander à l'utilisateur quel module lancer
        System.out.println("Quel simulation souhaitez-vous lancer ?");
        System.out.println("1 - Hopital fantastique");
        System.out.println("2 - Colonie de Lycanthrope");
        int choix = scanner.nextInt();

        // Déterminer la classe à exécuter
        String classNameToLaunch;
        if (choix == 1) {
            classNameToLaunch = "Simulation.Main"; // Package + classe principale
        } else if (choix == 2) {
            classNameToLaunch = "ClassColonie.MainColonie"; // Package + classe principale
        } else {
            System.out.println("Choix invalide !");
            return;
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
    }
}
