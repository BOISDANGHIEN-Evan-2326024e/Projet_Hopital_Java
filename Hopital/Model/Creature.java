package Model;

import java.util.ArrayList;
import java.util.List;

public abstract class Creature {
    protected String nom;
    protected String sexe;
    protected double poids;
    protected double taille;
    protected String age;
    protected int moral;
    protected List<Maladie> maladies;

    public Creature(String nom, String sexe, double poids, double taille, String age) {
        this.nom = nom;
        this.sexe = sexe;
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.moral = 100;
        this.maladies = new ArrayList<>();
    }

    public abstract Categorie getCategorie();
    
    public void attendre(List<Creature> autresCreatures) {
        if (getCategorie() == Categorie.TRIAGE) {
        	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            boolean autreMembreDeTriage = autresCreatures.stream().anyMatch(creature -> creature.getClass().equals(this.getClass()));

            if (autreMembreDeTriage) {
                System.out.println(nom + " attend patiemment avec un autre membre de son espèce.");
            } else {
                moral -= 5; // Diminuer le moral si la créature est seule
                System.out.println(nom + " devient impatient.");
            }
        } else if (getCategorie() == Categorie.VIP) {
            moral -= 10; // Diminuer fortement le moral si un VIP attend trop longtemps
            System.out.println(nom + " est un client VIP et devient très impatient.");
        }

        if (moral < 20) {
            hurler();
        }
    }

    public void hurler() {
        System.out.println(nom + " hurle !");
    }

    public void emporter() {};

    public void tomberMalade(Maladie maladie) {
        maladies.add(maladie);
        moral -= 10;
    }

    public void soigner() {
        if (!maladies.isEmpty()) {
            maladies.remove(0);
            moral += 15;
        }
    }

    public boolean estEnVie() {
        return moral > 0;
    }


    public void trepasser() {
        moral = 0;
    }

    public List<Maladie> getMaladies() {
        return maladies;
    }

    public void setMaladies(List<Maladie> maladies) {
        this.maladies = maladies;
    }

    public int getMoral() {
        return moral;
    }

    public void setMoral(int moral) {
        this.moral = moral;
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
