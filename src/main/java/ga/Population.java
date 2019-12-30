package ga;

import tsp.GeoPlace;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Population class
 *
 * @author Jagoda Wieczorek
 */
public class Population {
    private ArrayList<Individual> individuals;

    /**
     * Constructor for random population with predefined size
     *
     * @param size Population size
     */
    public Population(int size) throws IllegalArgumentException {
        if (size <= 0) {
            throw new IllegalArgumentException("Population size cannot be less or equal 0");
        }

        // @TODO initialize random population
    }
}
