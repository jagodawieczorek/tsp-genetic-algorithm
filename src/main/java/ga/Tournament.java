package ga;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Tournament selector
 *
 * @author Jagoda Wieczorek
 */
public class Tournament implements Selector {
    @Override
    public Individual select(List<Individual> individuals, int... param) {
        if (param.length < 1) {
            throw new IllegalArgumentException("Size of a sublist of individuals for tournament has to be provided");
        }

        List<Individual> randomIndividuals = getRandomSublist(individuals, param[0]);

        return Collections.max(randomIndividuals);
    }

    /**
     * Random sublist generator using Fisher-Yates-Durstenfeld shuffle algorithm
     *
     * @param individuals initial list with individuals
     * @param count       size of a sublist on output
     * @return sublist with random individuals
     */
    private List<Individual> getRandomSublist(List<Individual> individuals, int count) {
        int size = individuals.size();

        if (size < count) {
            throw new IllegalArgumentException(String.format("The size of the population (%s) is to small to pick %s individuals", individuals.size(), count));
        }

        Random random = new Random();

        for (int i = size - 1; i >= size - count; --i) {
            Collections.swap(individuals, i, random.nextInt(i + 1));
        }

        return individuals.subList(size - count, size);
    }
}
