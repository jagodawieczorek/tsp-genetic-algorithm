package ga;

import tsp.Place;
import tsp.TSP;

import java.util.*;
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
    private List<Individual> bestIndividuals;
    private Individual bestIndividual;
    private Individual worstIndividual;
    private int avgFitness;

    public Population() {
    }

    public Population(Population population, TreeMap<Integer, Place> places, float mutationProbability, float crossoverProbability, Selector selector, Crossover crossover) {
        individuals = new ArrayList<>();
        Random random = new Random();
        int populationSize = population.getIndividuals().size();

        while (individuals.size() < populationSize) {
            Individual individual = selector.select(population.getBestIndividuals());

            if (crossoverProbability > random.nextFloat()) {
                Individual parent2 = selector.select(population.getBestIndividuals());
                individual = crossover.perform(individual, parent2);
            }

            if (mutationProbability > random.nextFloat()) {
                individual.mutate();
            }

            individual.setFitness(places);

            individuals.add(individual);
        }

//        populationSize = population.getIndividuals().size();
//
//        while (individuals.size() < populationSize) {
//            individuals.add(new Individual(population.getBestIndividual().getStartingGen(), places));
//        }

        evaluate();
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
            this.bestIndividual = bestIndividual;
            this.worstIndividual = worstIndividual;
            this.avgFitness = sum / individuals.size();
            this.setBestIndividuals(0.1f);
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

    public void setBestIndividuals(float part) {
        ArrayList<Individual> sortedIndividuals = individuals;
        Collections.sort(sortedIndividuals);
        int lastIndividualIndex = (int) (part * sortedIndividuals.size());

        bestIndividuals = sortedIndividuals.subList(lastIndividualIndex, individuals.size());
    }

    /**
     * @return the best individual's fitness in the whole population
     */
    public int getBestFitness() {
        return bestIndividual.getFitness();
    }

    /**
     * @return the worst individual's fitness in the whole population
     */
    public int getWorstFitness() {
        return worstIndividual.getFitness();
    }

    /**
     * @return average fitness of the population
     */
    public int getAvgFitness() {
        return avgFitness;
    }

    /**
     * @return the best individual in the population
     */
    public Individual getBestIndividual() {
        return bestIndividual;
    }

    /**
     * @return the worst individual in the population
     */
    public Individual getWorstIndividual() {
        return worstIndividual;
    }

    public List<Individual> getBestIndividuals() {
        return bestIndividuals;
    }

    /**
     * Random sublist generator using Fisher-Yates-Durstenfeld shuffle algorithm
     *
     * @param individuals initial list with individuals
     * @param count       size of a sublist on output
     * @return sublist with random individuals
     */
    public static List<Individual> getRandomSublist(List<Individual> individuals, int count) {
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

    @Override
    public String toString() {
        return "Population{" +
                "bestFitness=" + bestIndividual.getFitness() +
                ", worstFitness=" + worstIndividual.getFitness() +
                ", avgFitness=" + avgFitness +
                '}';
    }
}
