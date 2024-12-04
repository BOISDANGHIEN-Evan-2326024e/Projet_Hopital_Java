package Attente;

import java.util.List;

import Creatures.Creature;

public class AttenteVIPStrategy implements AttenteStrategy{
	
	/**
	 * Gere le moral des créatures VIP
	 */
	  @Override
	    public void attendre(Creature creature, List<Creature> autresCreatures, int round) {
	        double probabiliteColere = 5 * round;

	        if ((creature.getMoral() - 15 * probabiliteColere) < 0) {
	            creature.setMoral(0);
	        } else {
	            creature.setMoral(creature.getMoral() - 15 * probabiliteColere); // Diminuer fortement le moral si un VIP attend trop longtemps
	            System.out.println(creature.getNom() + " n'en peut plus d'attendre.");
	        }
	    }
}
