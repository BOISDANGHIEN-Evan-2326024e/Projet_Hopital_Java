package TestColonie;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import ClassColonie.Lycanthrope;
import ClassColonie.Colonie;
import ClassColonie.Couple;
import ClassColonie.Meute;

public class CoupleTest {

    private Couple couple;
    private Lycanthrope maleAlpha;
    private Lycanthrope femelleAlpha;
    private Meute meute;

    @BeforeEach
    public void setUp() {
        maleAlpha = new Lycanthrope("male", "Alpha Male");
        femelleAlpha = new Lycanthrope("femelle", "Alpha Female");
        couple = new Couple(maleAlpha, femelleAlpha);
        meute = new Meute("Meute Test");
    }

    @Test
    public void testGettersSetters() {
        assertEquals(maleAlpha, couple.getMaleAlpha());
        assertEquals(femelleAlpha, couple.getFemelleAlpha());

        Lycanthrope newMaleAlpha = new Lycanthrope("male", "New Alpha Male");
        Lycanthrope newFemelleAlpha = new Lycanthrope("femelle", "New Alpha Female");

        couple.setMaleAlpha(newMaleAlpha);
        couple.setFemelleAlpha(newFemelleAlpha);

        assertEquals(newMaleAlpha, couple.getMaleAlpha());
        assertEquals(newFemelleAlpha, couple.getFemelleAlpha());
    }

    @Test
    public void testAfficherCaracteristiques() {
        // Appel pour vérifier qu'il n'y a pas d'exceptions lors de l'exécution
        couple.afficherCaracteristiques();
        couple.setMaleAlpha(null);
        couple.afficherCaracteristiques();
    }

    @Test
    public void testReproductionSansCoupleAlpha() {
        couple.setMaleAlpha(null);
        List<Lycanthrope> nouveauNes = couple.reproduction(meute);

        assertTrue(nouveauNes.isEmpty());
        assertTrue(meute.getLycanthropes().isEmpty());
    }

    @Test
    public void testReconstituerCoupleAlpha() {
        Lycanthrope strongMale = new Lycanthrope("mâle", "Strong Male");
        strongMale.setForce(15);
        strongMale.setCategorieAge("adulte");

        Lycanthrope skilledFemale = new Lycanthrope("femelle", "Skilled Female");
        skilledFemale.setNiveau(12);
        skilledFemale.setCategorieAge("adulte");

        List<Lycanthrope> lycanthropes = new ArrayList<>();
        lycanthropes.add(strongMale);
        lycanthropes.add(skilledFemale);

        couple.reconstituerCoupleAlpha(lycanthropes);

        assertEquals(strongMale, couple.getMaleAlpha());
        assertEquals(skilledFemale, couple.getFemelleAlpha());
    }

    @Test
    public void testReconstituerCoupleAlphaSansAdulte() {
        Lycanthrope youngMale = new Lycanthrope("mâle", "Young Male");
        youngMale.setForce(10);
        youngMale.setCategorieAge("jeune");

        Lycanthrope youngFemale = new Lycanthrope("femelle", "Young Female");
        youngFemale.setNiveau(8);
        youngFemale.setCategorieAge("jeune");

        List<Lycanthrope> lycanthropes = new ArrayList<>();
        lycanthropes.add(youngMale);
        lycanthropes.add(youngFemale);

        couple.reconstituerCoupleAlpha(lycanthropes);

        assertNull(couple.getMaleAlpha());
        assertNull(couple.getFemelleAlpha());
    }
}
