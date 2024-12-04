package Maladie;

public class Maladie {
    private String nomComplet;
    private String nomAbrege;
    private int niveauActuel;
    private int niveauMax;

    /**
     * Maladie
     * @param nomComplet
     * @param nomAbrege
     * @param niveauMax
     */
    public Maladie(String nomComplet, String nomAbrege, int niveauMax) {
        this.nomComplet = nomComplet;
        this.nomAbrege = nomAbrege;
        this.niveauActuel = 1;
        this.niveauMax = niveauMax;
    }

    /**
     * void diminuerNiveau
     */
    public void diminuerNiveau() {
        if (niveauActuel > 0) {
            niveauActuel--;
        }
    }

    /**
     * void augmenterNiveau
     */
    public void augmenterNiveau() {
        if (niveauActuel < niveauMax) {
            niveauActuel++;
        }
    }

    /**
     * void changerNiveauActuel
     * @param niveauActuelChang
     */
    public void changerNiveauActuel(int niveauActuelChang) {
        if (niveauActuelChang <= niveauMax) {
            this.niveauActuel = niveauActuelChang;
        }
    }

    /**
     * boolean estLethal
     * @return
     */
    public boolean estLethal() {
        return niveauActuel >= niveauMax;
    }

    /**
     * String getNomComplet
     * @return
     */
    public String getNomComplet() {
        return nomComplet;
    }

    /**
     * void setNomComplet
     * @param nomComplet
     */
    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    /**
     * String getNomAbrege
     * @return
     */
    public String getNomAbrege() {
        return nomAbrege;
    }

    /**
     * void setNomAbrege
     * @param nomAbrege
     */
    public void setNomAbrege(String nomAbrege) {
        this.nomAbrege = nomAbrege;
    }

    /**
     * int getNiveauActuel
     * @return
     */
    public int getNiveauActuel() {
        return niveauActuel;
    }

    /**
     * void setNiveauActuel
     * @param niveauActuel
     */
    public void setNiveauActuel(int niveauActuel) {
        this.niveauActuel = niveauActuel;
    }

    /**
     * int getNiveauMax
     * @return
     */
    public int getNiveauMax() {
        return niveauMax;
    }

    /**
     * void setNiveauMax
     * @param niveauMax
     */
    public void setNiveauMax(int niveauMax) {
        this.niveauMax = niveauMax;
    }

    /**
     * String toString
     */
	@Override
	public String toString() {
		return  nomAbrege + " " + niveauActuel
				+ "/" + niveauMax;
	}

    
}
