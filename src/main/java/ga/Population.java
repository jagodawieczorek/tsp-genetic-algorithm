package ga;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import tsp.Place;
import tsp.TSP;

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

	public Population(final Population population, final TreeMap<Integer, Place> places, final float mutationProbability, final float crossoverProbability, final Selector selector,
                      final Crossover crossover) {
        this.individuals = new ArrayList<>();
		final Random random = new Random();
		final int populationSize = population.getIndividuals().size();

		while (this.individuals.size() < populationSize) {
			Individual individual = selector.select(population.getBestIndividuals());

			if (crossoverProbability > random.nextFloat()) {
				final Individual parent2 = selector.select(population.getBestIndividuals());
				individual = crossover.perform(individual, parent2);
			}

			if (mutationProbability > random.nextFloat()) {
				individual.mutate();
			}

			individual.setFitness(places);

            this.individuals.add(individual);
		}

		evaluate();
	}

	/**
	 * Constructor for random population with predefined size
	 *
	 * @param size
	 *                 Population size
	 */
	public Population(final int size, final int startingPlace, final TreeMap<Integer, Place> places) throws IllegalArgumentException {
		if (size <= 0) {
			throw new IllegalArgumentException("Population size cannot be less or equal 0");
		}

        this.individuals = new ArrayList<>();
		initializeRandom(size, startingPlace, places);
		evaluate();
	}

	/**
	 *
	 * @param size
	 *                                   population size
	 * @param startingPlace
	 *                                   starting place
	 * @param places
	 *                                   map of places
	 * @param initialGenomeAlgorithm
	 *                                   InitialGenomeAlgorithm implementation
	 * @throws IllegalArgumentException
	 *                                      exception
	 */
	public Population(final int size, final int startingPlace, final TreeMap<Integer, Place> places, final InitialGenomeAlgorithm initialGenomeAlgorithm)
			throws IllegalArgumentException {
		if (size <= 0) {
			throw new IllegalArgumentException("Population size cannot be less or equal 0");
		}

        this.individuals = new ArrayList<>();
		initialize(size, startingPlace, places, initialGenomeAlgorithm);
		evaluate();
	}

	/**
	 * Initialize random population
	 *
	 * @param size
	 *                          size of population
	 * @param startingPlace
	 *                          key of starting place
	 * @param places
	 *                          places on the map
	 * @deprecated use initialize method instead
	 */
	private void initializeRandom(final int size, final int startingPlace, final TreeMap<Integer, Place> places) {
		for (int i = 0; i < size; i++) {
            this.individuals.add(new Individual(startingPlace, places));
		}
	}

	/**
	 * @param size
	 *                                   size of population
	 * @param startingPlace
	 *                                   key of starting place
	 * @param places
	 *                                   places on the map
	 * @param initialGenomeAlgorithm
	 *                                   InitialGenomeAlgorithm implementation
	 */
	private void initialize(final int size, final int startingPlace, final TreeMap<Integer, Place> places, final InitialGenomeAlgorithm initialGenomeAlgorithm) {
		for (int i = 0; i < size; i++) {
            this.individuals.add(new Individual(startingPlace, places, initialGenomeAlgorithm));
		}
	}

	private void evaluate() {
		int sum = 0;
		Individual bestIndividual = null;
		Individual worstIndividual = null;

		for (final Individual individual: this.individuals) {
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
			this.avgFitness = sum / this.individuals.size();
			this.setBestIndividuals(0.1f);
		} catch (final NullPointerException e) {
			LOGGER.log(Level.FINE, e.toString());
		}
	}

	/**
	 * @return individuals in population
	 */
	public ArrayList<Individual> getIndividuals() {
		return this.individuals;
	}

	public void setBestIndividuals(final float part) {
		final ArrayList<Individual> sortedIndividuals = this.individuals;
		Collections.sort(sortedIndividuals);
		final int lastIndividualIndex = (int) (part * sortedIndividuals.size());

        this.bestIndividuals = sortedIndividuals.subList(lastIndividualIndex, this.individuals.size());
	}

	/**
	 * @return the best individual's fitness in the whole population
	 */
	public int getBestFitness() {
		return this.bestIndividual.getFitness();
	}

	/**
	 * @return the worst individual's fitness in the whole population
	 */
	public int getWorstFitness() {
		return this.worstIndividual.getFitness();
	}

	/**
	 * @return average fitness of the population
	 */
	public int getAvgFitness() {
		return this.avgFitness;
	}

	/**
	 * @return the best individual in the population
	 */
	public Individual getBestIndividual() {
		return this.bestIndividual;
	}

	/**
	 * @return the worst individual in the population
	 */
	public Individual getWorstIndividual() {
		return this.worstIndividual;
	}

	public List<Individual> getBestIndividuals() {
		return this.bestIndividuals;
	}

	/**
	 * Random sublist generator using Fisher-Yates-Durstenfeld shuffle algorithm
	 *
	 * @param individuals
	 *                        initial list with individuals
	 * @param count
	 *                        size of a sublist on output
	 * @return sublist with random individuals
	 */
	public static List<Individual> getRandomSublist(final List<Individual> individuals, final int count) {
		final int size = individuals.size();

		if (size < count) {
			throw new IllegalArgumentException(String.format("The size of the population (%s) is to small to pick %s individuals", individuals.size(), count));
		}

		final Random random = new Random();

		for (int i = size - 1; i >= size - count; --i) {
			Collections.swap(individuals, i, random.nextInt(i + 1));
		}

		return individuals.subList(size - count, size);
	}

	@Override
	public String toString() {
		return "Population{" + "bestFitness=" + this.bestIndividual.getFitness() + ", worstFitness=" + this.worstIndividual.getFitness() + ", avgFitness=" + this.avgFitness
				+ '}';
	}
}
