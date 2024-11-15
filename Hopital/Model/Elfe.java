package Model;


public class Elfe extends Creature {
    public Elfe(String nom, String sexe, double poids, double taille, String age) {
        super(nom, sexe, poids, taille, age);
    }

    @Override
    public void emporter() {
        System.out.println(nom + " démoralise son entourage !");
    }

    @Override
    public Categorie getCategorie() {
        return Categorie.VIP;
    }
    
}
