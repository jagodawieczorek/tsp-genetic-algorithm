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

        List<Individual> randomIndividuals = Population.getRandomSublist(individuals, param[0]);

        return Collections.max(randomIndividuals);
    }
}
