package Tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

import Creatures.Creature;
import Creatures.HommeBete;
import Creatures.Vampire;


class TestsCreatures {

/**
 * Tests Homme Bete
 */
    @Test
    void testAttendreReduitMoralHommeBete() {
        List<Creature> creatures = new ArrayList<>();
        Creature hommeBete = new HommeBete ("Hercule", "Mâle", 120.0, 1.95, "30");
        creatures.add(hommeBete);
        hommeBete.attendre(creatures, 1);
        assertTrue(hommeBete.estEnVie());
        assertTrue(hommeBete.getMoral() < 1000);
    }

    @Test
    void testHurlerHommeBete() {
    Creature hommeBete = new HommeBete ("Hercule", "Mâle", 80.0, 1.85, "100");
        hommeBete.hurler();
        assertDoesNotThrow(() -> hommeBete.hurler());
    }
    
    /**
     * Tests Vampire    
     */
    @Test
    void testAttendreReduitMoralVampire() {
        List<Creature> creatures = new ArrayList<>();
        Creature vampire = new Vampire ("Hercule", "Mâle", 80.0, 1.85, "100");
        creatures.add(vampire);
        vampire.attendre(creatures, 1);
        assertTrue(vampire.estEnVie());
        assertTrue(vampire.getMoral() < 1000);
    }

    @Test
    void testCatégorie() {
    Creature vampire = new Vampire ("Dracula", "Mâle", 80.0, 1.85, "100");
        assertEquals("VIP", vampire.getCategorie().toString());
    }
}