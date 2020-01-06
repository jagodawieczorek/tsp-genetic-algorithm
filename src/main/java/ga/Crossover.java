package ga;

import java.util.Random;

/**
 * Crossover interface
 *
 * @author Jagoda Wieczorek
 */
public interface Crossover {
    Individual perform(Individual parent1, Individual parent2);

    Individual perform(Individual parent1, Individual parent2, int breakpoint);
}
