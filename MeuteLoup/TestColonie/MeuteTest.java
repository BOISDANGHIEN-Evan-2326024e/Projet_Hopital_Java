package TestColonie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ClassColonie.Lycanthrope;
import ClassColonie.Couple;
import ClassColonie.Meute;

class MeuteTest {

    @Test
    void testCreationMeute() {
        Meute meute = new Meute("Lunaire");
        assertEquals("Lunaire", meute.getNomMeute(), "Le nom de la meute devrait être 'Lunaire'.");
        assertTrue(meute.getLycanthropes().isEmpty(), "La liste des lycanthropes devrait être vide à la création.");
        assertEquals(null, meute.getCoupleAlpha(), "La meute ne devrait pas avoir de couple α à la création.");
    }

    @Test
    void testAjoutLycanthrope() {
        Meute meute = new Meute("Lunaire");
        Lycanthrope lycan = new Lycanthrope("male", "Fenrir");

        meute.ajouterLycanthrope(lycan);
        assertEquals(1, meute.getLycanthropes().size(), "La meute devrait contenir un lycanthrope après l'ajout.");
        assertEquals(meute, lycan.getMeute(), "Le lycanthrope devrait être associé à la meute.");
        assertTrue(meute.getLycanthropes().contains(lycan), "Le lycanthrope ajouté devrait être présent dans la liste des membres.");
    }

    @Test
    void testEnleverLycanthrope() {
        Meute meute = new Meute("Lunaire");
        Lycanthrope lycan = new Lycanthrope("male", "Fenrir");
        meute.ajouterLycanthrope(lycan);

        meute.enleverLycanthrope(lycan);
        assertTrue(meute.getLycanthropes().isEmpty(), "La liste des lycanthropes devrait être vide après la suppression.");
    }

    @Test
    void testConstituerCoupleAlpha() {
        Meute meute = new Meute("Lunaire");
        Lycanthrope maleAlpha = new Lycanthrope("male", "Fenrir");
        Lycanthrope femelleAlpha = new Lycanthrope("female", "Lupa");

        meute.constituerCoupleAlpha(maleAlpha, femelleAlpha);
        Couple couple = meute.getCoupleAlpha();

        assertNotNull(couple, "Un couple α devrait être constitué.");
        assertEquals(maleAlpha, couple.getMaleAlpha(), "Le mâle α devrait être 'Fenrir'.");
        assertEquals(femelleAlpha, couple.getFemelleAlpha(), "La femelle α devrait être 'Lupa'.");
    }

    @Test
    void testDeclarerLycanthropesOmega() {
        Meute meute = new Meute("Lunaire");
        Lycanthrope lycan1 = new Lycanthrope("male", "Fenrir");
        lycan1.setForce(3);
        lycan1.setCategorieAge("adulte");

        Lycanthrope lycan2 = new Lycanthrope("female", "Lupa");
        lycan2.setForce(7);
        lycan2.setCategorieAge("adulte");

        meute.ajouterLycanthrope(lycan1);
        meute.ajouterLycanthrope(lycan2);

        meute.declarerLycanthropesOmega();
        assertEquals(26, lycan1.getRang(), "Le rang de 'Fenrir' devrait être ω.");
        assertTrue(lycan2.getRang() != 26, "'Lupa' ne devrait pas être ω.");
    }

    @Test
    void testTriInsertionForce() {
        Meute meute = new Meute("Lunaire");

        Lycanthrope lycan1 = new Lycanthrope("male", "Fenrir");
        lycan1.setFacteurDomination(3);

        Lycanthrope lycan2 = new Lycanthrope("female", "Lupa");
        lycan2.setFacteurDomination(7);

        meute.ajouterLycanthrope(lycan1);
        meute.ajouterLycanthrope(lycan2);

        meute.triInsertionForce();

        assertEquals(lycan2, meute.getLycanthropes().get(0), "Le lycanthrope avec le plus grand facteur de domination devrait être en tête.");
        assertEquals(lycan1, meute.getLycanthropes().get(1), "Le lycanthrope avec le plus petit facteur de domination devrait être en dernier.");
    }

    @Test
    void testRangToRangLettre() {
        Meute meute = new Meute("Lunaire");

        Lycanthrope lycan1 = new Lycanthrope("male", "Fenrir");
        Lycanthrope lycan2 = new Lycanthrope("female", "Lupa");
        Lycanthrope lycan3 = new Lycanthrope("male", "Ulric");
        Lycanthrope lycan4 = new Lycanthrope("female", "Sylva");

        meute.ajouterLycanthrope(lycan1);
        meute.ajouterLycanthrope(lycan2);
        meute.ajouterLycanthrope(lycan3);
        meute.ajouterLycanthrope(lycan4);

        meute.triInsertionForce();
        meute.rangToRangLettre();

        assertEquals("Alpha", lycan1.getRangLettre(), "Le premier lycanthrope devrait être α.");
        assertEquals("Omega", lycan4.getRangLettre(), "Le dernier lycanthrope devrait être ω.");
    }
}
