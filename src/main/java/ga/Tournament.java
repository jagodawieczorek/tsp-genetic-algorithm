package ga;

import java.util.Collections;
import java.util.List;

/**
 * Tournament selector
 *
 * @author Jagoda Wieczorek
 */
public class Tournament implements Selector {
    private int[] params;

    public Tournament(int... params) {
        if (params.length < 1) {
            throw new IllegalArgumentException("Size of a sublist of individuals for tournament has to be provided");
        }
        this.params = params;
    }

    @Override
    public Individual select(List<Individual> individuals, int... params) {
        if (params.length < 1) {
            throw new IllegalArgumentException("Size of a sublist of individuals for tournament has to be provided");
        }

        List<Individual> randomIndividuals = Population.getRandomSublist(individuals, params[0]);

        return Collections.max(randomIndividuals);
    }

    @Override
    public Individual select(List<Individual> individuals) {
        return this.select(individuals, getParams());
    }

    @Override
    public int[] getParams() {
        return params;
    }
}
