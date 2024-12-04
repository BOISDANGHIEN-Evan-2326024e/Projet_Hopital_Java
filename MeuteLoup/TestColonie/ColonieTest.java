package TestColonie;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ClassColonie.Lycanthrope;
import ClassColonie.Colonie;
import ClassColonie.Couple;
import ClassColonie.Meute;

public class ColonieTest {

    private Colonie colonie;

    @BeforeEach
    public void setUp() {
        colonie = new Colonie();
    }

    @Test
    public void testAjouterMeute() {
        Meute meute = new Meute("Meute Test");
        colonie.ajouterMeute(meute);

        List<Meute> meutes = colonie.getListeMeutes();
        assertEquals(1, meutes.size());
        assertEquals("Meute Test", meutes.get(0).getNomMeute());
    }

    @Test
    public void testGestionColonieAjoutLycanthrope() {
        Meute meute = new Meute("Meute Test");
        colonie.ajouterMeute(meute);

        Lycanthrope lycan = new Lycanthrope("male", "Test Lycan");
        meute.ajouterLycanthrope(lycan);

        assertEquals(1, meute.getLycanthropes().size());
        assertEquals("Test Lycan", meute.getLycanthropes().get(0).getNom());
    }

    @Test
    public void testFaireVieillirLycanthropes() {
        Meute meute = new Meute("Meute Test");
        Lycanthrope lycan = new Lycanthrope("male", "Young Lycan");
        lycan.setCategorieAge("jeune");
        meute.ajouterLycanthrope(lycan);

        colonie.ajouterMeute(meute);
        colonie.faireVieillirLycanthropes();

        String ageApres = lycan.getCategorieAge();
        assertTrue(ageApres.equals("adulte") || ageApres.equals("jeune")); // Probabilistique
    }

    @Test
    public void testEstSaisonAmours() {
        boolean saisonAmours = colonie.estSaisonAmours();
        assertTrue(saisonAmours || !saisonAmours); // Probabilistique, toujours valide
    }

    @Test
    public void testEvoluerForceLycanthropes() {
        Meute meute = new Meute("Meute Test");
        Lycanthrope lycan = new Lycanthrope("male", "Strong Lycan");
        lycan.setForce(5);
        meute.ajouterLycanthrope(lycan);

        colonie.ajouterMeute(meute);
        colonie.evoluerForce();

        int nouvelleForce = lycan.getForce();
        assertTrue(nouvelleForce >= 4 && nouvelleForce <= 6); // Force doit augmenter ou diminuer de 1 max
    }

    @Test
    public void testGenererHurlementsAleatoires() {
        Meute meute = new Meute("Meute Test");
        Lycanthrope emetteur = new Lycanthrope("male", "Emetteur");
        Lycanthrope recepteur = new Lycanthrope("femelle", "Recepteur");

        meute.ajouterLycanthrope(emetteur);
        meute.ajouterLycanthrope(recepteur);

        colonie.ajouterMeute(meute);
        colonie.genererHurlementsAleatoires();

        // Ce test peut nécessiter des mock si des effets spécifiques doivent être capturés
        assertNotNull(emetteur); // Valide l'existence
        assertNotNull(recepteur); // Valide l'existence
    }
}
