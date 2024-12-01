package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Couple {
    private Lycanthrope maleAlpha;   // Mâle α
    private Lycanthrope femelleAlpha; // Femelle α

    // Constructeur
    public Couple(Lycanthrope maleAlpha, Lycanthrope femelleAlpha) {
        this.maleAlpha = maleAlpha;
        this.femelleAlpha = femelleAlpha;
    }

    // Getter pour le mâle α
    public Lycanthrope getMaleAlpha() {
        return maleAlpha;
    }

    // Getter pour la femelle α
    public Lycanthrope getFemelleAlpha() {
        return femelleAlpha;
    }

    // Setter pour le mâle α
    public void setMaleAlpha(Lycanthrope maleAlpha) {
        this.maleAlpha = maleAlpha;
    }

    // Setter pour la femelle α
    public void setFemelleAlpha(Lycanthrope femelleAlpha) {
        this.femelleAlpha = femelleAlpha;
    }

    // Méthode pour afficher les caractéristiques du couple α
    public void afficherCaracteristiques() {
        System.out.println("===== Caractéristiques du couple α =====");
        if (maleAlpha != null) {
            System.out.println("===== Caractéristiques du male α =====");
            maleAlpha.afficherCaracteristiques();
        } else {
            System.out.println("Pas de mâle α actuellement.");
        }
        if (femelleAlpha != null) {
            System.out.println("===== Caractéristiques de la femelle α =====");
            femelleAlpha.afficherCaracteristiques();
        } else {
            System.out.println("Pas de femelle α actuellement.");
        }
    }

    public List<Lycanthrope> reproduction(boolean saisonDesAmours) {
        List<Lycanthrope> nouveauxLycanthropes = new ArrayList<>();
        if (!saisonDesAmours) {
            System.out.println("Ce n'est pas la saison des amours. La reproduction n'est pas possible.");
            return nouveauxLycanthropes;
        }

        if (maleAlpha != null && femelleAlpha != null) {
            System.out.println("Reproduction initiée entre le mâle α et la femelle α.");

            // Génération aléatoire du nombre de jeunes lycanthropes (entre 1 et 7)
            Random random = new Random();
            int nbJeunes = random.nextInt(7) + 1; // Génère un nombre entre 1 et 7
            System.out.println("Le couple α a donné naissance à une portée de " + nbJeunes + " jeunes lycanthropes.");

            // Détermination du rang des nouveaux-nés (β s'il n'y a pas de β dans la meute, sinon γ)
            int rangDesJeunes = verifierPresenceRangBeta(nouveauxLycanthropes) ? 3 : 2;

            for (int i = 0; i < nbJeunes; i++) {
                Lycanthrope jeune = new Lycanthrope(null, i, i, i, i, null);
                jeune.setSexe(random.nextBoolean() ? "male" : "femelle");
                jeune.setCategorieAge("jeune");
                jeune.setForce(random.nextInt(10) + 1); // Force aléatoire entre 1 et 10
                jeune.setRang(rangDesJeunes); 
                jeune.setFacteurDomination(0); // Par défaut, le facteur de domination est neutre
                jeune.setFacteurImpetuosite(random.nextInt(10) + 1); // Impétuosité aléatoire entre 1 et 10
                jeune.setNom("lycan "+i);

                // Ajouter le nouveau lycanthrope à la liste des nouveaux-nés
                nouveauxLycanthropes.add(jeune);
                System.out.println("Un jeune lycanthrope de rang " + (rangDesJeunes == 2 ? "β" : "γ") + " est né.");
            }
        } else {
            System.out.println("Impossible d'effectuer la reproduction : couple α incomplet.");
        }

        return nouveauxLycanthropes;
    }

    // Méthode pour vérifier s'il existe déjà des lycanthropes de rang β
    private boolean verifierPresenceRangBeta(List<Lycanthrope> lycanthropes) {
        for (Lycanthrope lycan : lycanthropes) {
            if (lycan.getRang() == 9) {
                return true;
            }
        }
        return false;
    }

    // Méthode pour reconstituer le couple α après une domination
    public void reconstituerCoupleAlpha(List<Lycanthrope> lycanthropes) {
        // Trouver le nouveau mâle α : le mâle adulte avec la plus grande force
        Lycanthrope nouveauMaleAlpha = lycanthropes.stream()
                .filter(lycan -> "mâle".equals(lycan.getSexe()) && "adulte".equals(lycan.getCategorieAge()))
                .max((l1, l2) -> Integer.compare(l1.getForce(), l2.getForce()))
                .orElse(null);

        // Trouver la nouvelle femelle α : la femelle adulte avec le plus haut niveau
        Lycanthrope nouvelleFemelleAlpha = lycanthropes.stream()
                .filter(lycan -> "femelle".equals(lycan.getSexe()) && "adulte".equals(lycan.getCategorieAge()))
                .max((l1, l2) -> Integer.compare(l1.getNiveau(), l2.getNiveau()))
                .orElse(null);

        // Mettre à jour le rang de domination de l'ancienne femelle α
        if (femelleAlpha != null && nouveauMaleAlpha != null) {
            femelleAlpha.setRang(nouveauMaleAlpha.getRang());
        }

        // Mettre à jour le couple α
        this.maleAlpha = nouveauMaleAlpha;
        this.femelleAlpha = nouvelleFemelleAlpha;

        System.out.println("Le couple α a été reconstitué.");
    }
}

