package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ServicesMedicaux.Crypte;
import ServicesMedicaux.CentreQuarantaine;

class TestsServices {

/**
 * Test crypte
 */
@Test
    void testInitialisationCrypte() {
Crypte crypte = new Crypte("Crypte Test", 500, 50, 1000, 3, 25);
        assertEquals(3, crypte.getVentilation());
        assertEquals(25, crypte.getTemperature(), 0.01);
    }

    @Test
    void testSetVentilation() {
    Crypte crypte = new Crypte("Crypte Test", 500, 50, 1000, 3, 25);
        crypte.setVentilation(5);
        assertEquals(5, crypte.getVentilation());
    }

    @Test
    void testSetTemperature() {
    Crypte crypte = new Crypte("Crypte Test", 500, 50, 1000, 3, 25);
        crypte.setTemperature(22);
        assertEquals(22, crypte.getTemperature(), 0.01);
    }

    @Test
    void testReviserBudgetCrypte() {
    Crypte crypte = new Crypte("Crypte Test", 500, 50, 1000, 3, 25);
        crypte.reviserBudget();
        assertTrue(crypte.getBudget() > 0);
    }
    
    /**
     * Test centre de quarantaine 
     */
    @Test
    void testInitialisationCentreQuarantaine() {
    CentreQuarantaine centre = new CentreQuarantaine("Quarantaine Test", 800, 100, 1500, true);
        assertTrue(centre.isIsolation());
    }

    @Test
    void testSetIsolation() {
    CentreQuarantaine centre = new CentreQuarantaine("Quarantaine Test", 800, 100, 1500, true);    
        centre.setIsolation(false);
        assertFalse(centre.isIsolation());
    }

    @Test
    void testReviserBudgetCentreQuarantaine() {
    CentreQuarantaine centre = new CentreQuarantaine("Quarantaine Test", 800, 100, 1500, true);
        centre.reviserBudget();
        assertTrue(centre.getBudget() > 0);
    }

}