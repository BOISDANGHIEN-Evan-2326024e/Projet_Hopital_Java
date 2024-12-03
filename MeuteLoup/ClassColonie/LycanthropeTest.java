package ClassColonie;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class LycanthropeTest {

    @Test
    void testCreationLycanthrope() {
        Lycanthrope lycan = new Lycanthrope("male", "Fenrir");
        assertEquals("male", lycan.getSexe(), "Le sexe du lycanthrope devrait être 'male'.");
        assertEquals("jeune", lycan.getCategorieAge(), "La catégorie d'âge initiale devrait être 'jeune'.");
        assertEquals("Fenrir", lycan.getNom(), "Le nom du lycanthrope devrait être 'Fenrir'.");
        assertEquals(0, lycan.getFacteurDomination(), "Le facteur de domination initial devrait être 0.");
        assertNull(lycan.getMeute(), "Le lycanthrope devrait être solitaire par défaut.");
    }

    @Test
    void testSetCategorieAge() {
        Lycanthrope lycan = new Lycanthrope("male", "Fenrir");
        lycan.setCategorieAge("adulte");
        assertEquals("adulte", lycan.getCategorieAge(), "La catégorie d'âge devrait être mise à jour à 'adulte'.");
    }

    @Test
    void testSetForce() {
        Lycanthrope lycan = new Lycanthrope("male", "Fenrir");
        lycan.setForce(8);
        assertEquals(8, lycan.getForce(), "La force devrait être mise à jour à 8.");
    }


    @Test
    void testSeparationMeute() {
        Lycanthrope lycan = new Lycanthrope("male", "Fenrir");
        Meute meute = new Meute("Lunaire");
        lycan.setMeute(meute);
        assertNotNull(lycan.getMeute(), "Le lycanthrope devrait appartenir à une meute.");

        lycan.separationMeute();
        assertNull(lycan.getMeute(), "Le lycanthrope devrait être solitaire après séparation de la meute.");
    }

    @Test
    void testHurler() {
        Lycanthrope lycan = new Lycanthrope("male", "Fenrir");
        Hurlement hurlement = new Hurlement("Alerte", lycan);
        lycan.hurler(hurlement);
        // Test manuel ici, mais vous pouvez vérifier que le hurlement est bien appelé sans exception.
    }

    @Test
    void testEntendreHurlement() {
        Lycanthrope lycan = new Lycanthrope("male", "Fenrir");
        Hurlement hurlement = new Hurlement("Rassemblement", lycan);

        lycan.entendreHurlement(hurlement, false);
        // Test manuel ici aussi pour vérifier l'affichage attendu.
    }
}
