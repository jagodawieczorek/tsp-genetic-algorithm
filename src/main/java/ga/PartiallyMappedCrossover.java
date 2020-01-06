package ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * PMX - Partially Mapped Crossover
 *
 * @author Jagoda Wieczorek
 */
public class PartiallyMappedCrossover implements Crossover {

    @Override
    public Individual perform(Individual parent1, Individual parent2) {
        Random random = new Random();
        int breakpoint = random.nextInt(parent1.getGenome().size() - 1);

        return perform(parent1, parent2, breakpoint);
    }

    @Override
    public Individual perform(Individual parent1, Individual parent2, int breakpoint) {
        List<Integer> genome1 = new ArrayList<>(parent1.getGenome());
        List<Integer> genome2 = new ArrayList<>(parent2.getGenome());

        if (breakpoint > (genome1.size() - 1)) {
            throw new IllegalArgumentException(String.format("Crossover breakpoint cannot be bigger than %s", genome1.size() - 1));
        }

        for (int i = 0; i < breakpoint; i++) {
            int valueToSwap = genome2.get(i);
            Collections.swap(genome1, genome1.indexOf(valueToSwap), i);
        }

        return new Individual(new ArrayList<>(genome1));
    }
}
