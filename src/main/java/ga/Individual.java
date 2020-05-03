package ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.IntStream;

import tsp.Place;

/**
 * Individual in Genetic Algorithm
 *
 * @author Jagoda Wieczorek
 */
public class Individual implements Comparable<Individual> {
	private ArrayList<Integer> genome;

	private int fitness;

	private int startingGen;

	private int minGen;

	private int maxGen;

	public Individual() {
	}

	/**
	 * Constructor for Individual with defined fitness
	 *
	 * @param fitness
	 *                    individual's fitness (the lower the better)
	 */
	public Individual(final int fitness) {
		this.fitness = fitness;
	}

	/**
	 * Constructor for Individual with defined genome
	 *
	 * @param genome
	 *                   individual's genome
	 */
	public Individual(final ArrayList<Integer> genome) {
		this.genome = genome;
		this.startingGen = genome.get(0);
	}

	/**
	 * Constructor for Individual with defined genome
	 *
	 * @param genome
	 *                   individual's genome
	 * @param places
	 *                   all available places
	 */
	public Individual(final ArrayList<Integer> genome, final TreeMap<Integer, Place> places) {
		this.genome = genome;
		this.fitness = this.calculateFitness(places);
	}

	/**
	 * Constructor for Individual with initial random genome based on provided
	 * places
	 *
	 * @param startingGen
	 *                        starting gen
	 * @param places
	 *                        all available places
	 * @throws IllegalArgumentException
	 *                                      illegal argument passed as minGen,
	 *                                      maxGen or startingGen
	 */
	public Individual(final int startingGen, final TreeMap<Integer, Place> places) throws IllegalArgumentException {
		this(places.firstKey(), places.lastKey(), startingGen);

		this.genome = this.randomGenome(this.minGen, this.maxGen, startingGen);
		this.fitness = this.calculateFitness(places);
	}

	/**
	 * Constructor for Individual with initial random genome
	 *
	 * @param minGen
	 *                        minimum gen value (first possible key)
	 * @param maxGen
	 *                        maximum gen value (last possible key)
	 * @param startingGen
	 *                        starting gen
	 * @throws IllegalArgumentException
	 *                                      illegal argument passed as minGen,
	 *                                      maxGen or startingGen
	 */
	public Individual(final int minGen, final int maxGen, final int startingGen) throws IllegalArgumentException {
		if (maxGen < minGen) {
			throw new IllegalArgumentException("Maximum gen value cannot be lower than minimum gen value");
		}

		if (startingGen < minGen || startingGen > maxGen) {
			throw new IllegalArgumentException("Starting gen has to be in a range <minimum gen value, maximum gen value>");
		}

		this.minGen = minGen;
		this.maxGen = maxGen;
		this.startingGen = startingGen;
		this.genome = this.randomGenome(minGen, maxGen, startingGen);
	}

	/**
	 * Constructor for Individual with initial random genome based on provided
	 * places
	 *
	 * @param startingGen
	 *                        starting gen
	 * @param places
	 *                        all available places
	 * @throws IllegalArgumentException
	 *                                      illegal argument passed as minGen,
	 *                                      maxGen or startingGen
	 */
	public Individual(final int startingGen, final TreeMap<Integer, Place> places, final InitialGenomeAlgorithm initialGenomeAlgorithm) throws IllegalArgumentException {
		this(places.firstKey(), places.lastKey(), startingGen, initialGenomeAlgorithm, places);

		this.genome = this.initializeGenome(this.minGen, this.maxGen, startingGen, initialGenomeAlgorithm, places);
		this.fitness = this.calculateFitness(places);
	}

	/**
	 * Constructor for Individual with initial random genome
	 *
	 * @param minGen
	 *                        minimum gen value (first possible key)
	 * @param maxGen
	 *                        maximum gen value (last possible key)
	 * @param startingGen
	 *                        starting gen
	 * @throws IllegalArgumentException
	 *                                      illegal argument passed as minGen,
	 *                                      maxGen or startingGen
	 */
	public Individual(final int minGen, final int maxGen, final int startingGen, final InitialGenomeAlgorithm initialGenomeAlgorithm, final TreeMap<Integer, Place> places)
			throws IllegalArgumentException {
		if (maxGen < minGen) {
			throw new IllegalArgumentException("Maximum gen value cannot be lower than minimum gen value");
		}

		if (startingGen < minGen || startingGen > maxGen) {
			throw new IllegalArgumentException("Starting gen has to be in a range <minimum gen value, maximum gen value>");
		}

		this.minGen = minGen;
		this.maxGen = maxGen;
		this.startingGen = startingGen;
		this.genome = this.initializeGenome(minGen, maxGen, startingGen, initialGenomeAlgorithm, places);
	}

	/**
	 * @param minGen
	 *                        minimum gen value (first possible key)
	 * @param maxGen
	 *                        maximum gen value (last possible key)
	 * @param startingGen
	 *                        starting gen
	 * @return random genome
	 * @deprecated use initializeGenome instead
	 */
	public ArrayList<Integer> randomGenome(final int minGen, final int maxGen, final int startingGen) {
		final ArrayList<Integer> genome = new ArrayList<>();
		IntStream.rangeClosed(minGen, maxGen).forEach(genome::add);
		Collections.shuffle(genome);
		Collections.swap(genome, 0, genome.indexOf(startingGen));

		return genome;
	}

	/**
	 * @param minGen
	 *                                   minimum gen value (first possible key)
	 * @param maxGen
	 *                                   maximum gen value (last possible key)
	 * @param startingGen
	 *                                   starting gen
	 * @param initialGenomeAlgorithm
	 *                                   InitialGenomeAlgorithm implementation
	 * @param places
	 *                                   places
	 * @return genome
	 */
	public ArrayList<Integer> initializeGenome(final int minGen, final int maxGen, final int startingGen, final InitialGenomeAlgorithm initialGenomeAlgorithm,
                                               final TreeMap<Integer, Place> places) {
		return initialGenomeAlgorithm.initialize(minGen, maxGen, startingGen, places);
	}

	/**
	 * @return fitness (the lower the better)
	 */
	public int calculateFitness(final TreeMap<Integer, Place> places) {
		int cost = 0;
		Integer currentGen = this.startingGen;

		for (int i = 1; i < this.genome.size(); i++) {
			cost += places.get(currentGen).getDistanceTo(this.genome.get(i));
			currentGen = this.genome.get(i);
		}

		cost += places.get(currentGen).getDistanceTo(this.startingGen);

		return cost;
	}

	public void mutate() {
		final Random random = new Random();
		final ArrayList<Integer> newGenome = this.genome;

		Collections.swap(newGenome, random.nextInt(this.genome.size() - 1) + 1, random.nextInt(this.genome.size() - 1) + 1);

        this.genome = newGenome;
	}

	/**
	 * @return Individual genome
	 */
	public ArrayList<Integer> getGenome() {
		return this.genome;
	}

	/**
	 * @return fitness
	 */
	public int getFitness() {
		return this.fitness;
	}

	/**
	 * @return starting gen (always the same)
	 */
	public int getStartingGen() {
		return this.startingGen;
	}

	/**
	 * @return minimum gen value
	 */
	public int getMinGen() {
		return this.minGen;
	}

	/**
	 * @return maximum gen value
	 */
	public int getMaxGen() {
		return this.maxGen;
	}

	/**
	 * Set fitness based on genome and places
	 *
	 * @param places
	 *                   all available places
	 */
	public void setFitness(final TreeMap<Integer, Place> places) {
		this.fitness = calculateFitness(places);
	}

	@Override
	public int compareTo(final Individual individual) {
		return this.getFitness() >= individual.getFitness() ? this.getFitness() > individual.getFitness() ? -1 : 0 : 1;
	}
}
