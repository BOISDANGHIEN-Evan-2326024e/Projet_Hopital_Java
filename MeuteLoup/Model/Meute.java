package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Meute {
    private Couple coupleAlpha; // Couple α (chef de la meute)
    private List<Lycanthrope> lycanthropes; // Liste des lycanthropes de la meute
    private String nomMeute;
    private Random random=new Random();
    
    public Meute(String nomMeute) {
        this.lycanthropes = new ArrayList<>();
        this.coupleAlpha = null; // Pas de couple α par défaut
        this.nomMeute=nomMeute;
    }


    public void afficherCaracteristiques() {
        System.out.println("===== Caractéristiques de la meute =====");
        System.out.println("Nombre de lycanthropes dans la meute : " + lycanthropes.size());
        System.out.println();
        if (coupleAlpha != null) {
            System.out.println("Couple α : ");
            coupleAlpha.afficherCaracteristiques();
        } else {
            System.out.println("Pas de couple α.");
        }
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
    	triInsertionForce();
    	for(int k=0;k<this.lycanthropes.size();k++) {
    		this.lycanthropes.get(k).setRang(this.lycanthropes.size()-k);
    	}
    }

    // Méthode pour constituer un nouveau couple α
    public void constituerCoupleAlpha(Lycanthrope maleAlpha, Lycanthrope femelleAlpha) {
        // Vérification si les lycanthropes sont éligibles
        if (maleAlpha != null && femelleAlpha != null) {
            if (coupleAlpha != null) {
                System.out.println("\nAncien couple α déchu.");
                if(!femelleAlpha.equals(coupleAlpha.getFemelleAlpha())) {
                	coupleAlpha.getFemelleAlpha().setFacteurDomination(coupleAlpha.getMaleAlpha().getFacteurDomination());
                }
            }
            coupleAlpha = new Couple(maleAlpha, femelleAlpha);
            coupleAlpha.setMaleAlpha(maleAlpha);
            coupleAlpha.setFemelleAlpha(femelleAlpha);
            System.out.println("\nNouveau couple α constitué.");
        } else {
            System.out.println("Impossible de constituer le couple α : vérifiez les candidats.");
        }
    }
    
    public boolean coupleAlphaExiste() {
    	if(this.coupleAlpha==null) {
    		return false;
    	}
    	return true;
    }

    // Méthode pour ajouter un lycanthrope à la meute
    public void ajouterLycanthrope(Lycanthrope lycanthrope) {
        lycanthropes.add(lycanthrope);
        System.out.println(lycanthrope.getNom()+" ajouté à la meute : "+this.getNomMeute());
    }

    // Méthode pour enlever un lycanthrope de la meute
    public void enleverLycanthrope(Lycanthrope lycanthrope) {
        if (lycanthropes.remove(lycanthrope)) {
            System.out.println(lycanthrope.getNom()+" retiré de la meute : "+this.getNomMeute());
        } else {
            System.out.println(lycanthrope.getNom()+" non trouvé dans la meute : "+this.getNomMeute());
        }
    }

    // Méthode pour lancer une reproduction des lycanthropes
    public void reproduction() {
        if (coupleAlpha != null) {
            coupleAlpha.reproduction(this);
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

	public String getNomMeute() {
		return nomMeute;
	}


	public void setNomMeute(String nomMeute) {
		this.nomMeute = nomMeute;
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
                System.out.println(lycan.getNom()+" déclaré ω : " + lycan.getSexe());
            }
        }
    }
    
    public void triInsertionForce() {
        for (int i = 1; i < this.lycanthropes.size(); i++) {
            Lycanthrope current = this.lycanthropes.get(i); // Élément à insérer
            int j = i - 1;

            // Déplacer les éléments ayant un facteur de domination plus petit pour faire de la place
            while (j >= 0 && this.lycanthropes.get(j).getFacteurDomination() < current.getFacteurDomination()) {
                this.lycanthropes.set(j + 1, this.lycanthropes.get(j));
                j--;
            }

            // Placer l'élément à sa position correcte
            this.lycanthropes.set(j + 1, current);
        }
    }

    
    public void rangToRangLettre() {
    	int taille=this.lycanthropes.size();
    	int quart=taille/4;
    	if(taille>0) {
    		this.lycanthropes.get(0).setRangLettre("Alpha");
    	}
    	for (int i = 1; i < quart; i++) {
    		this.lycanthropes.get(i).setRangLettre("Bêta");  // 1er quart
        }

        for (int i = quart; i < 2 * quart; i++) {
        	this.lycanthropes.get(i).setRangLettre("Gamma"); // 2e quart
        }

        for (int i = 2 * quart; i < 3 * quart; i++) {
        	this.lycanthropes.get(i).setRangLettre("Delta"); // 3e quart
        }

        for (int i = 3 * quart; i < taille - 1; i++) {
        	this.lycanthropes.get(i).setRangLettre("Delta"); // Dernier quart, sauf le dernier
        }

        if (taille > 1) {
        	this.lycanthropes.get(taille -1).setRangLettre("Omega"); // Dernier loup est Oméga
        }
    }
    
    public void afficherHierarchie() {
        // Grouper les loups par rôle
        System.out.println("\n Hiérarchie de la meute : "+this.getNomMeute());
        List<Lycanthrope> lycan=this.lycanthropes;
        afficherRole("Alpha", lycan);
        afficherRole("Bêta", lycan);
        afficherRole("Gamma", lycan);
        afficherRole("Delta", lycan);
        afficherRole("Omega", lycan);
    }

    private void afficherRole(String role, List<Lycanthrope> lycan) {
        System.out.println("\n" + role + "s :");
        lycan.stream()
                .filter(loup -> loup.getRangLettre().equals(role))
                .forEach(loup -> loup.afficherCaracteristiques());
    }
}