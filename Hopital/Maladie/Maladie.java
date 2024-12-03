package Maladie;

public class Maladie {
    private String nomComplet;
    private String nomAbrege;
    private int niveauActuel;
    private int niveauMax;

    public Maladie(String nomComplet, String nomAbrege, int niveauMax) {
        this.nomComplet = nomComplet;
        this.nomAbrege = nomAbrege;
        this.niveauActuel = 1;
        this.niveauMax = niveauMax;
    }

    public void diminuerNiveau() {
        if (niveauActuel > 0) {
            niveauActuel--;
        }
    }

    public void augmenterNiveau() {
        if (niveauActuel < niveauMax) {
            niveauActuel++;
        }
    }

    public void changerNiveauActuel(int niveauActuelChang) {
        if (niveauActuelChang <= niveauMax) {
            this.niveauActuel = niveauActuelChang;
        }
    }


    public boolean estLethal() {
        return niveauActuel >= niveauMax;
    }




    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getNomAbrege() {
        return nomAbrege;
    }

    public void setNomAbrege(String nomAbrege) {
        this.nomAbrege = nomAbrege;
    }

    public int getNiveauActuel() {
        return niveauActuel;
    }

    public void setNiveauActuel(int niveauActuel) {
        this.niveauActuel = niveauActuel;
    }

    public int getNiveauMax() {
        return niveauMax;
    }

    public void setNiveauMax(int niveauMax) {
        this.niveauMax = niveauMax;
    }

	@Override
	public String toString() {
		return  nomComplet + "(aka " + nomAbrege + ") " + niveauActuel
				+ "/" + niveauMax;
	}

    
}
