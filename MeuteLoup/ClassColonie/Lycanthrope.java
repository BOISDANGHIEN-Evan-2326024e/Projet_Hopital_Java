package ClassColonie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private String rangLettre;
    private List<Lycanthrope> listeDominance; // Liste des lycanthropes qui domine celui la
    private List<Lycanthrope> listeDomines; // Liste des lycanthropes que ce lycan domine
    private Random random=new Random();
    
    public Lycanthrope(String sexe,String Nom) {
        this.sexe = sexe;
        this.categorieAge = "jeune";
        this.force = random.nextInt(10);
        this.facteurDomination = 0;
        this.rang = 0;
        this.facteurImpetuosite = random.nextInt(4)+1;
        this.niveau = calculNiveau(); 
        this.meute = null; 
        this.Nom=Nom;
        this.rangLettre=null;
        this.listeDominance= new ArrayList<Lycanthrope>();
        this.listeDomines= new ArrayList<Lycanthrope>();
    }
    
    /**
     * Méthode pour afficher les caractéristiques
     */
    public void afficherCaracteristiques() {
        System.out.println("Nom : " + Nom + 
            ", Sexe : " + sexe + 
            ", Âge : " + categorieAge + 
            ", Force : " + force + 
            ", Facteur de domination : " + facteurDomination + 
            ", Rang : " + rang + 
            ", Niveau : " + niveau + 
            ", Facteur d’impétuosité : " + facteurImpetuosite + 
            ", Meute : " + (meute == null ? "Solitaire" : meute.getNomMeute()));
    }
    
    /**
     * Méthode pour hurler
     * @param hurlement
     */
    public void hurler(Hurlement hurlement) {
        System.out.println(this.Nom+" hurle : " + hurlement.getTypeHurlement());
    }
    
    /**
     * Méthode pour entendre un hurlement
     * @param hurlement
     * @param estMalade
     */
    public void entendreHurlement(Hurlement hurlement, boolean estMalade) {
        if (estMalade) {
            System.out.println(this.Nom+" est trop malade pour réagir au hurlement.");
        } else {
            System.out.println(this.Nom+" entend un hurlement : " + hurlement.getTypeHurlement());
        }
    }
    
    /**
     * Méthode pour se séparer de la meute
     */
    public void separationMeute() {
        if (meute != null) {
            System.out.println(this.Nom+" quitte la meute.");
            meute = null;
        } else {
            System.out.println(this.Nom+" est déjà solitaire.");
        }
    }
    
    /**
     * Méthode pour se transformer en humain
     */
    public void transformation() {
        meute=null;
    }
    
    /**
     * Méthode pour calculer le niveau
     * @return
     */
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
	
	public String getRangLettre() {
		return rangLettre;
	}

	public void setRangLettre(String rangLettre) {
		this.rangLettre = rangLettre;
	}
	
	
}