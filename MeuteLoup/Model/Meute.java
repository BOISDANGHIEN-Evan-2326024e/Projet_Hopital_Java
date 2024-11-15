package Model;

import java.util.ArrayList;
import java.util.List;

public class Meute {
    private Couple coupleAlpha; // Couple α (chef de la meute)
    private List<Lycanthrope> lycanthropes; // Liste des lycanthropes de la meute

    public Meute() {
        this.lycanthropes = new ArrayList<>();
        this.coupleAlpha = null; // Pas de couple α par défaut
    }


    public void afficherCaracteristiques() {
        System.out.println("===== Caractéristiques de la meute =====");
        if (coupleAlpha != null) {
            System.out.println("Couple α : ");
            coupleAlpha.afficherCaracteristiques();
        } else {
            System.out.println("Pas de couple α.");
        }
        System.out.println("Nombre de lycanthropes dans la meute : " + lycanthropes.size());
    }

    // Méthode pour afficher les caractéristiques des lycanthropes de la meute
    public void afficherCaracteristiquesLycanthropes() {
        System.out.println("===== Caractéristiques des lycanthropes =====");
        for (Lycanthrope lycan : lycanthropes) {
            lycan.afficherCaracteristiques();
            System.out.println("--------------------------------------------");
        }
    }

    // Méthode pour créer une nouvelle hiérarchie de meute
    public void creerHierarchie() {
    	//Fais une tri à bulles !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        lycanthropes.sort((l1, l2) -> {
            // Tri des lycanthropes par leur rang en ordre lexicographique
            return l1.getRang() - l2.getRang();
        });
        System.out.println("Hiérarchie de la meute mise à jour.");
    }

    // Méthode pour constituer un nouveau couple α
    public void constituerCoupleAlpha(Lycanthrope maleAlpha, Lycanthrope femelleAlpha) {
        // Vérification si les lycanthropes sont éligibles
        if (maleAlpha != null && femelleAlpha != null) {
            if (coupleAlpha != null) {
                System.out.println("Ancien couple α déchu.");
            }
            coupleAlpha = new Couple(maleAlpha, femelleAlpha);
            coupleAlpha.setMaleAlpha(maleAlpha);
            coupleAlpha.setFemelleAlpha(femelleAlpha);
            System.out.println("Nouveau couple α constitué.");
        } else {
            System.out.println("Impossible de constituer le couple α : vérifiez les candidats.");
        }
    }

    // Méthode pour ajouter un lycanthrope à la meute
    public void ajouterLycanthrope(Lycanthrope lycanthrope) {
        lycanthropes.add(lycanthrope);
        System.out.println("Lycanthrope ajouté à la meute.");
    }

    // Méthode pour enlever un lycanthrope de la meute
    public void enleverLycanthrope(Lycanthrope lycanthrope) {
        if (lycanthropes.remove(lycanthrope)) {
            System.out.println("Lycanthrope retiré de la meute.");
        } else {
            System.out.println("Lycanthrope non trouvé dans la meute.");
        }
    }

    // Méthode pour lancer une reproduction des lycanthropes
    public void reproduction() {
        if (coupleAlpha != null) {
            coupleAlpha.reproduction(true);
        } else {
            System.out.println("Pas de couple α pour initier la reproduction.");
        }
    }

    public Couple getCoupleAlpha() {
		return coupleAlpha;
	}


	public void setCoupleAlpha(Couple coupleAlpha) {
		this.coupleAlpha = coupleAlpha;
	}


	public List<Lycanthrope> getLycanthropes() {
		return lycanthropes;
	}


	public void setLycanthropes(List<Lycanthrope> lycanthropes) {
		this.lycanthropes = lycanthropes;
	}


	// Méthode pour déclarer les lycanthropes ω
    public void declarerLycanthropesOmega() {
        // Calcul de la moyenne de force
        int totalForce = 0;
        for (Lycanthrope lycan : lycanthropes) {
            totalForce += lycan.getForce();
        }
        double moyenneForce = totalForce / (double) lycanthropes.size();

        for (Lycanthrope lycan : lycanthropes) {
            if ("adulte".equals(lycan.getCategorieAge()) && lycan.getForce() < moyenneForce) {
                lycan.setRang(26); // Rang ω (dernier rang)
                System.out.println("Lycanthrope déclaré ω : " + lycan.getSexe());
            }
        }
    }

    // Méthode pour faire décroître les rangs naturellement
    public void decroitreRangsNaturellement() {
        for (Lycanthrope lycan : lycanthropes) {
            if (lycan.getFacteurDomination() < 0 && lycan.getRang() > 1) {
                lycan.setRang(lycan.getRang() + 1); // Décroît le rang (descend dans la hiérarchie)
                System.out.println("Lycanthrope " + lycan.getSexe() + " a perdu un rang.");
            }
        }
    }
}
