package Tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

import Creatures.Creature;
import Creatures.HommeBete;
import Creatures.Vampire;


class CreatureTests {

    @Test
    void testAttendreReduitMoralHommeBete() {
        List<Creature> creatures = new ArrayList<>();
        Creature hommeBete = new HommeBete ("Hercule", "Mâle", 80.0, 1.85, "30");
        creatures.add(hommeBete);
        hommeBete.attendre(creatures, 1);
        assertTrue(hommeBete.estEnVie());
        assertTrue(hommeBete.getMoral() < 1000);
    }

    @Test
    void testHurlerHommeBete() {
    	Creature hommeBete = new HommeBete ("Hercule", "Mâle", 80.0, 1.85, "30");
        hommeBete.hurler();
        assertDoesNotThrow(() -> hommeBete.hurler());
    }
    
    @Test
    void testAttendreReduitMoralVampire() {
        List<Creature> creatures = new ArrayList<>();
        Creature v = new Vampire ("Hercule", "Mâle", 80.0, 1.85, "30");
        creatures.add(vampire);
        vampire.attendre(creatures, 1);
        assertTrue(vampire.estEnVie());
        assertTrue(vampire.getMoral() < 1000);
    }

    @Test
    void testHurler() {
    	Creature vampire = new Vampire ("Hercule", "Mâle", 80.0, 1.85, "30");
        vampire.hurler();
        assertDoesNotThrow(() -> vampire.hurler());
    }
}
