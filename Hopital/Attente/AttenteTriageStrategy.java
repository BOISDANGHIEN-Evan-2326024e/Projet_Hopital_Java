package Attente;

import java.util.List;

import Creatures.Creature;

public class AttenteTriageStrategy implements AttenteStrategy{

	/**
	 * Gere le moral des créatures du Triage
	 */
    @Override
    public void attendre(Creature creature, List<Creature> autresCreatures, int round) {
        double probabiliteColere = 5 * round;
        
        if (autresCreatures.size() > 1) { // Si la créature n'est pas seule
            if ((creature.getMoral() - 5 * probabiliteColere) < 0) {
                creature.setMoral(0);
            } else {
                creature.setMoral(creature.getMoral() - 5 * probabiliteColere);
            }
        } else {
            if ((creature.getMoral() - 10 * probabiliteColere) < 0) {
                creature.setMoral(0);
            } else {
                creature.setMoral(creature.getMoral() - 10 * probabiliteColere);
                System.out.println(creature.getNom() + " se sent seul.");
            }
        }
    }
}
