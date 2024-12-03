package Attente;

import java.util.List;

import Creatures.Creature;

public interface AttenteStrategy {
    void attendre(Creature creature, List<Creature> autresCreatures, int round);
}
