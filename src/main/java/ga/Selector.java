package ga;

import java.util.ArrayList;
import java.util.List;

/**
 * Selector interface
 *
 * @author Jagoda Wieczorek
 */
interface Selector {
    /**
     * @param individuals List of individuals
     * @param params      List of params
     * @return winner individual
     */
    Individual select(List<Individual> individuals, int... params);
}
