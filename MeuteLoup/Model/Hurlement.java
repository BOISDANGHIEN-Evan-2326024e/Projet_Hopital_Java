package Model;

public class Hurlement {

    private String typeHurlement; // Type de hurlement (appartenance, domination, soumission, agressivité)
    private Lycanthrope emetteur; // Le lycanthrope qui émet le hurlement

    // Constructeur
    public Hurlement(String typeHurlement, Lycanthrope emetteur) {
        this.typeHurlement = typeHurlement;
        this.emetteur = emetteur;
    }

    // Getters et Setters
    public String getTypeHurlement() {
        return typeHurlement;
    }

    public void setTypeHurlement(String typeHurlement) {
        this.typeHurlement = typeHurlement;
    }

    public Lycanthrope getEmetteur() {
        return emetteur;
    }

    public void setEmetteur(Lycanthrope emetteur) {
        this.emetteur = emetteur;
    }

    // Méthode pour afficher les caractéristiques d'un hurlement
    public void afficherCaracteristiques() {
        System.out.println("===== Hurlement =====");
        System.out.println("Type d'hurlement : " + typeHurlement);
        if (emetteur != null) {
            System.out.println("Émetteur : ");
            emetteur.afficherCaracteristiques();
        } else {
            System.out.println("Aucun émetteur spécifié.");
        }
    }

    // Méthode pour déclencher une réponse en fonction du type d'hurlement
    public void repondreHurlement(Meute meute) {
        switch (typeHurlement.toLowerCase()) {
            case "appartenance":
                System.out.println("Réponse des membres de la meute à un hurlement d'appartenance.");
                for (Lycanthrope membre : meute.getLycanthropes()) {
                    if (!membre.equals(emetteur)) { 
                        membre.hurler(new Hurlement("appartenance", membre));
                    }
                }
                break;

            case "domination":
                System.out.println("Un hurlement de domination a été émis.");
                break;

            case "soumission":
                System.out.println("Un hurlement de soumission a été émis.");
                break;

            case "agressivité":
                System.out.println("Un hurlement d'agressivité a été émis.");
                break;
            default:
                System.out.println("Type d'hurlement inconnu.");
                break;
        }
    }
}
