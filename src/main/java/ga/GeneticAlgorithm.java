package ga;

import java.util.logging.Level;
import java.util.logging.Logger;

import tsp.TSP;

/**
 * Genetic Algorithm
 *
 * @author Jagoda Wieczorek
 */
public class GeneticAlgorithm {
	private static final Logger LOGGER = Logger.getLogger(TSP.class.getName());

	private final int populationSize;

	private final int numberOfGenerations;

	private final float mutationProbability;

	private final float crossoverProbability;

	private TSP tsp;

	private Selector selector;

	private Crossover crossover;

	private InitialGenomeAlgorithm initialGenomeAlgorithm;

	/**
	 * GA constructor for TSP
	 *
	 * @param populationSize
	 *                                 Size of the population (number of Individuals
	 *                                 in one generation)
	 * @param numberOfGenerations
	 *                                 Number of generations
	 * @param mutationProbability
	 *                                 Probability that mutation occurs
	 * @param crossoverProbability
	 *                                 Probability that crossover occurs
	 * @param tsp
	 *                                 Travelling salesman problem
	 * @param selector
	 *                                 Selector (e.g. Tournament / Roulette / .. )
	 * @param crossover
	 *                                 Crossover type (e.g. PMX)
	 * @deprecated Pass as the last constructor argument InitialGenomeAlgorithm
	 */
	public GeneticAlgorithm(final int populationSize, final int numberOfGenerations, final float mutationProbability, final float crossoverProbability, final TSP tsp, final Selector selector,
                            final Crossover crossover) {
		this(populationSize, numberOfGenerations, mutationProbability, crossoverProbability, tsp);
		this.selector = selector;
		this.crossover = crossover;
	}

	/**
	 * GA constructor for TSP
	 *
	 * @param populationSize
	 *                                   Size of the population (number of
	 *                                   Individuals in one generation)
	 * @param numberOfGenerations
	 *                                   Number of generations
	 * @param mutationProbability
	 *                                   Probability that mutation occurs
	 * @param crossoverProbability
	 *                                   Probability that crossover occurs
	 * @param tsp
	 *                                   Travelling salesman problem
	 * @param selector
	 *                                   Selector (e.g. Tournament / Roulette / .. )
	 * @param crossover
	 *                                   Crossover type (e.g. PMX)
	 * @param initialGenomeAlgorithm
	 *                                   Initial Genome Algorithm (e.g. Random,
	 *                                   GRASP, ...)
	 */
	public GeneticAlgorithm(final int populationSize, final int numberOfGenerations, final float mutationProbability, final float crossoverProbability, final TSP tsp, final Selector selector,
                            final Crossover crossover, final InitialGenomeAlgorithm initialGenomeAlgorithm) {
		this(populationSize, numberOfGenerations, mutationProbability, crossoverProbability, tsp, initialGenomeAlgorithm);
		this.selector = selector;
		this.crossover = crossover;
	}

	/**
	 * GA constructor for TSP
	 *
	 * @param populationSize
	 *                                 Size of the population (number of Individuals
	 *                                 in one generation)
	 * @param numberOfGenerations
	 *                                 Number of generations
	 * @param mutationProbability
	 *                                 Probability that mutation occurs
	 * @param crossoverProbability
	 *                                 Probability that crossover occurs
	 * @param tsp
	 *                                 Travelling salesman problem
	 * @deprecated Pass as the last constructor argument InitialGenomeAlgorithm
	 */
	public GeneticAlgorithm(final int populationSize, final int numberOfGenerations, final float mutationProbability, final float crossoverProbability, final TSP tsp) {
		this(populationSize, numberOfGenerations, mutationProbability, crossoverProbability);
		this.tsp = tsp;
	}

	/**
	 * GA constructor for TSP
	 *
	 * @param populationSize
	 *                                   Size of the population (number of
	 *                                   Individuals in one generation)
	 * @param numberOfGenerations
	 *                                   Number of generations
	 * @param mutationProbability
	 *                                   Probability that mutation occurs
	 * @param crossoverProbability
	 *                                   Probability that crossover occurs
	 * @param tsp
	 *                                   Travelling salesman problem
	 * @param initialGenomeAlgorithm
	 *                                   Initial Genome Algorithm (e.g. Random,
	 *                                   GRASP, ...)
	 */
	public GeneticAlgorithm(final int populationSize, final int numberOfGenerations, final float mutationProbability, final float crossoverProbability, final TSP tsp,
                            final InitialGenomeAlgorithm initialGenomeAlgorithm) {
		this(populationSize, numberOfGenerations, mutationProbability, crossoverProbability, initialGenomeAlgorithm);
		this.tsp = tsp;
	}

	/**
	 * GA constructor
	 *
	 * @param populationSize
	 *                                   Size of the population (number of
	 *                                   Individuals in one generation)
	 * @param numberOfGenerations
	 *                                   Number of generations
	 * @param mutationProbability
	 *                                   Probability that mutation occurs
	 * @param crossoverProbability
	 *                                   Probability that crossover occurs
	 * @param initialGenomeAlgorithm
	 *                                   Initial Genome Algorithm (e.g. Random,
	 *                                   GRASP, ...)
	 */
	public GeneticAlgorithm(final int populationSize, final int numberOfGenerations, final float mutationProbability, final float crossoverProbability,
                            final InitialGenomeAlgorithm initialGenomeAlgorithm) {
		this(populationSize, numberOfGenerations, mutationProbability, crossoverProbability);
		this.initialGenomeAlgorithm = initialGenomeAlgorithm;
	}

	/**
	 * GA constructor
	 *
	 * @param populationSize
	 *                                 Size of the population (number of Individuals
	 *                                 in one generation)
	 * @param numberOfGenerations
	 *                                 Number of generations
	 * @param mutationProbability
	 *                                 Probability that mutation occurs
	 * @param crossoverProbability
	 *                                 Probability that crossover occurs
	 */
	public GeneticAlgorithm(final int populationSize, final int numberOfGenerations, final float mutationProbability, final float crossoverProbability) {
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
		// @TODO refactor this
		this.initialGenomeAlgorithm = new RandomAlgorithm();
	}

	/**
	 *
	 * @param populationSize
	 *                                 Size of the population (number of Individuals
	 *                                 in one generation)
	 * @param numberOfGenerations
	 *                                 Number of generations
	 * @param mutationProbability
	 *                                 Probability that mutation occurs
	 * @param crossoverProbability
	 *                                 Probability that crossover occurs
	 * @param tsp
	 *                                 Travelling salesman problem
	 * @param selector
	 *                                 e.g. Tournament selector
	 * @param crossover
	 *                                 Crossover
	 * @return Genetic algorithm
	 * @deprecated Pass to the constructor InitialGenomeAlgorithm as the last
	 *             argument
	 */
	public static GeneticAlgorithm create(final int populationSize, final int numberOfGenerations, final float mutationProbability, final float crossoverProbability, final TSP tsp,
                                          final Selector selector, final Crossover crossover) {
		return new GeneticAlgorithm(populationSize, numberOfGenerations, mutationProbability, crossoverProbability, tsp, selector, crossover);
	}

	/**
	 *
	 * @param populationSize
	 *                                   Size of the population (number of
	 *                                   Individuals in one generation)
	 * @param numberOfGenerations
	 *                                   Number of generations
	 * @param mutationProbability
	 *                                   Probability that mutation occurs
	 * @param crossoverProbability
	 *                                   Probability that crossover occurs
	 * @param tsp
	 *                                   Travelling salesman problem
	 * @param selector
	 *                                   e.g. Tournament selector
	 * @param crossover
	 *                                   Crossover
	 * @param initialGenomeAlgorithm
	 *                                   Initial genome algorithm
	 * @return Genetic algorithm
	 */
	public static GeneticAlgorithm create(final int populationSize, final int numberOfGenerations, final float mutationProbability, final float crossoverProbability, final TSP tsp,
                                          final Selector selector, final Crossover crossover, final InitialGenomeAlgorithm initialGenomeAlgorithm) {
		return new GeneticAlgorithm(populationSize, numberOfGenerations, mutationProbability, crossoverProbability, tsp, selector, crossover,
				initialGenomeAlgorithm);
	}

	public Individual run() {
		// 1. initialize first population
		Population population = new Population(this.populationSize, this.tsp.getStartingPlace(), this.tsp.getPlaces(), this.initialGenomeAlgorithm);
		LOGGER.log(Level.INFO, population.toString());
		// 2. create generations based on previous one in a loop
		int currentGeneration = 0;

		while (currentGeneration < this.numberOfGenerations) {
			population = new Population(population, this.tsp.getPlaces(), this.mutationProbability, this.crossoverProbability, this.selector, this.crossover);
			LOGGER.log(Level.INFO, population.toString() + " generation number: " + currentGeneration);
			currentGeneration++;
		}

		return population.getBestIndividual();
	}
}
