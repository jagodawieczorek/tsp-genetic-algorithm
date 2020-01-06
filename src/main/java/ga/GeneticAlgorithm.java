package ga;

import tsp.TSP;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Genetic Algorithm
 *
 * @author Jagoda Wieczorek
 */
public class GeneticAlgorithm {
    private static final Logger LOGGER = Logger.getLogger(TSP.class.getName());
    private int populationSize;
    private int numberOfGenerations;
    private float mutationProbability;
    private float crossoverProbability;
    private TSP tsp;
    private Selector selector;
    private Crossover crossover;

    /**
     * GA constructor for TSP
     *
     * @param populationSize       Size of the population (number of Individuals in one generation)
     * @param numberOfGenerations  Number of generations
     * @param mutationProbability  Probability that mutation occurs
     * @param crossoverProbability Probability that crossover occurs
     * @param tsp                  Travelling salesman problem
     * @param selector             Selector (e.g. Tournament / Roulette / .. )
     * @param crossover            Crossover type (e.g. PMX)
     */
    public GeneticAlgorithm(int populationSize, int numberOfGenerations, float mutationProbability, float crossoverProbability, TSP tsp, Selector selector, Crossover crossover) {
        this(populationSize, numberOfGenerations, mutationProbability, crossoverProbability, tsp);
        this.selector = selector;
        this.crossover = crossover;
    }

    /**
     * GA constructor for TSP
     *
     * @param populationSize       Size of the population (number of Individuals in one generation)
     * @param numberOfGenerations  Number of generations
     * @param mutationProbability  Probability that mutation occurs
     * @param crossoverProbability Probability that crossover occurs
     * @param tsp                  Travelling salesman problem
     */
    public GeneticAlgorithm(int populationSize, int numberOfGenerations, float mutationProbability, float crossoverProbability, TSP tsp) {
        this(populationSize, numberOfGenerations, mutationProbability, crossoverProbability);
        this.tsp = tsp;
    }

    /**
     * GA constructor
     *
     * @param populationSize       Size of the population (number of Individuals in one generation)
     * @param numberOfGenerations  Number of generations
     * @param mutationProbability  Probability that mutation occurs
     * @param crossoverProbability Probability that crossover occurs
     */
    public GeneticAlgorithm(int populationSize, int numberOfGenerations, float mutationProbability, float crossoverProbability) {
        if (populationSize <= 0) {
            throw new IllegalArgumentException("Population size cannot be less or equal 0");
        }

        if (numberOfGenerations <= 0) {
            throw new IllegalArgumentException("Number of generations cannot be less or equal 0");
        }

        if (mutationProbability < 0 || mutationProbability > 1 || crossoverProbability < 0 || crossoverProbability > 1) {
            throw new IllegalArgumentException("Probability has to be in range <0,1>");
        }

        this.populationSize = populationSize;
        this.numberOfGenerations = numberOfGenerations;
        this.mutationProbability = mutationProbability;
        this.crossoverProbability = crossoverProbability;
    }

    public static GeneticAlgorithm create(int populationSize, int numberOfGenerations, float mutationProbability, float crossoverProbability, TSP tsp, Selector selector, Crossover crossover) {
        return new GeneticAlgorithm(populationSize, numberOfGenerations, mutationProbability, crossoverProbability, tsp, selector, crossover);
    }

    public Individual run() {
        // 1. initialize random population
        Population population = new Population(populationSize, tsp.getStartingPlace(), tsp.getPlaces());
        LOGGER.log(Level.INFO, population.toString());
        // 2. create generations based on previous one in a loop
        int currentGeneration = 0;

        while (currentGeneration < numberOfGenerations) {
            population = new Population(population, tsp.getPlaces(), mutationProbability, crossoverProbability, selector, crossover);
            LOGGER.log(Level.INFO, population.toString());
            currentGeneration++;
        }

        return population.getBestIndividual();
    }
}
