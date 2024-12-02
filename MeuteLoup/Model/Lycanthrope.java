package Model;

import java.util.ArrayList;
import java.util.List;

public class Lycanthrope {
    private String sexe; // Sexe du lycanthrope : "male" ou "femelle"
    private String categorieAge; // Catégorie d’âge : "jeune", "adulte", "vieux"
    private int force; // Force du lycanthrope
    private int facteurDomination; // Différence entre les dominations exercées et subies
    private int rang; // Rang de domination
    private int niveau; // Niveau calculé du lycanthrope
    private int facteurImpetuosite; // Facteur d’impétuosité
    private Meute meute; // Meute à laquelle il appartient (null s’il est solitaire)
    private String Nom; // Nom du lycanthrope
    private List<Lycanthrope> listeDominance; // Liste des lycanthropes qui dominent celui la
    private List<Lycanthrope> listeDomines; // Liste des lycanthropes que ce lycan dominent
    
    public Lycanthrope(String sexe, int force, int facteurDomination, int rang, int facteurImpetuosite,String Nom) {
        this.sexe = sexe;
        this.categorieAge = "jeune";
        this.force = force;
        this.facteurDomination = facteurDomination;
        this.rang = rang;
        this.facteurImpetuosite = facteurImpetuosite;
        this.niveau = calculNiveau(); 
        this.meute = null; 
        this.Nom=Nom;
        this.listeDominance= new ArrayList<Lycanthrope>();
        this.listeDomines= new ArrayList<Lycanthrope>();
    }
    
    // Méthode pour afficher les caractéristiques
    public void afficherCaracteristiques() {
    	System.out.println("Nom :" + Nom);
        System.out.println("Sexe : " + sexe);
        System.out.println("Catégorie d'âge : " + categorieAge);
        System.out.println("Force : " + force);
        System.out.println("Facteur de domination : " + facteurDomination);
        System.out.println("Rang : " + rang);
        System.out.println("Niveau : " + niveau);
        System.out.println("Facteur d’impétuosité : " + facteurImpetuosite);
        System.out.println("Meute : " + (meute == null ? "Solitaire" : meute.getNomMeute()));
    }
    
    // Méthode pour hurler
    public void hurler(Hurlement hurlement) {
        System.out.println(this.Nom+" hurle : " + hurlement.getTypeHurlement());
    }
    
    // Méthode pour entendre un hurlement (si le lycanthrope n’est pas malade)
    public void entendreHurlement(Hurlement hurlement, boolean estMalade) {
        if (estMalade) {
            System.out.println(this.Nom+" est trop malade pour réagir au hurlement.");
        } else {
            System.out.println(this.Nom+" entend un hurlement : " + hurlement.getTypeHurlement());
        }
    }
    
    // Méthode pour se séparer de la meute
    public void separationMeute() {
        if (meute != null) {
            System.out.println(this.Nom+" quitte la meute.");
            meute = null;
        } else {
            System.out.println(this.Nom+" est déjà solitaire.");
        }
    }
    
    // Méthode pour se transformer en humain
    public void transformation() {
        System.out.println(this.Nom+" se transforme en humain.");
        meute=null;
    }
    
    // Méthode pour calculer le niveau
    private int calculNiveau() {
        int niveauAge;
        int niveauSexe;
        if(this.sexe=="male") {
        	niveauSexe=3;
        }
        niveauSexe=1;
        switch (categorieAge) {
            case "jeune":
                niveauAge = 1;
                break;
            case "adulte":
                niveauAge = 2;
                break;
            case "vieux":
                niveauAge = 3;
                break;
            default:
                throw new IllegalArgumentException("Catégorie d'âge invalide.");
        }
        return niveauAge + force + facteurDomination + rang + niveauSexe ;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getCategorieAge() {
        return categorieAge;
    }

    public void setCategorieAge(String categorieAge) {
        this.categorieAge = categorieAge;
        this.niveau = calculNiveau(); 
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
        this.niveau = calculNiveau(); 
    }

    public int getFacteurDomination() {
        return facteurDomination;
    }

    public void setFacteurDomination(int facteurDomination) {
        this.facteurDomination = facteurDomination;
        this.niveau = calculNiveau(); 
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
        this.niveau = calculNiveau(); 
    }

    public int getFacteurImpetuosite() {
        return facteurImpetuosite;
    }

    public void setFacteurImpetuosite(int facteurImpetuosite) {
        this.facteurImpetuosite = facteurImpetuosite;
    }

    public Meute getMeute() {
        return meute;
    }

    public void setMeute(Meute meute) {
        this.meute = meute;
    }

	public int getNiveau() {
		return niveau;
	}

	public String getNom() {
		return Nom;
	}

	public void setNom(String nom) {
		Nom = nom;
	}

	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}

	public List<Lycanthrope> getListeDominance() {
		return listeDominance;
	}

	public void setListeDominance(List<Lycanthrope> listeDominance) {
		this.listeDominance = listeDominance;
	}

	public List<Lycanthrope> getListeDomines() {
		return listeDomines;
	}

	public void setListeDomines(List<Lycanthrope> listeDomines) {
		this.listeDomines = listeDomines;
	}
	
	
}

