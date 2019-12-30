package ga;

import tsp.Place;
import tsp.TSP;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Population class
 *
 * @author Jagoda Wieczorek
 */
public class Population {
    private static final Logger LOGGER = Logger.getLogger(TSP.class.getName());

    private ArrayList<Individual> individuals;
    private int bestFitness;
    private int worstFitness;
    private int avgFitness;

    public Population() {
    }

    /**
     * Constructor for random population with predefined size
     *
     * @param size Population size
     */
    public Population(int size, int startingPlace, TreeMap<Integer, Place> places) throws IllegalArgumentException {
        if (size <= 0) {
            throw new IllegalArgumentException("Population size cannot be less or equal 0");
        }

        individuals = new ArrayList<>();
        initializeRandom(size, startingPlace, places);
        evaluate();
    }

    /**
     * Initialize random population
     *
     * @param size          size of population
     * @param startingPlace key of starting place
     * @param places        places on the map
     */
    private void initializeRandom(int size, int startingPlace, TreeMap<Integer, Place> places) {
        for (int i = 0; i < size; i++) {
            individuals.add(new Individual(startingPlace, places));
        }
    }

    private void evaluate() {
        int sum = 0;
        Individual bestIndividual = null;
        Individual worstIndividual = null;

        for (Individual individual : individuals) {
            if (bestIndividual == null || individual.compareTo(bestIndividual) > 0) {
                bestIndividual = individual;
            }

            if (worstIndividual == null || individual.compareTo(worstIndividual) < 0) {
                worstIndividual = individual;
            }

            sum += individual.getFitness();
        }

        try {
            this.bestFitness = bestIndividual.getFitness();
            this.worstFitness = worstIndividual.getFitness();
            this.avgFitness = sum / individuals.size();
        } catch (NullPointerException e) {
            LOGGER.log(Level.FINE, e.toString());
        }
    }

    /**
     * @return individuals in population
     */
    public ArrayList<Individual> getIndividuals() {
        return individuals;
    }

    /**
     * @return the best individual's fitness in the whole population
     */
    public int getBestFitness() {
        return bestFitness;
    }

    /**
     * @return the worst individual's fitness in the whole population
     */
    public int getWorstFitness() {
        return worstFitness;
    }

    /**
     * @return average fitness of the population
     */
    public int getAvgFitness() {
        return avgFitness;
    }

    @Override
    public String toString() {
        return "Population{" +
                "bestFitness=" + bestFitness +
                ", worstFitness=" + worstFitness +
                ", avgFitness=" + avgFitness +
                '}';
    }
}
