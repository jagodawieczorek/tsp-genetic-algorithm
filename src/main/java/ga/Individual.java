package ga;

import tsp.Place;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
     * @param fitness individual's fitness (the lower the better)
     */
    public Individual(int fitness) {
        this.fitness = fitness;
    }

    /**
     * Constructor for Individual with defined genome
     *
     * @param genome individual's genome
     */
    public Individual(ArrayList<Integer> genome) {
        this.genome = genome;
    }

    /**
     * Constructor for Individual with defined genome
     *
     * @param genome individual's genome
     * @param places all available places
     */
    public Individual(ArrayList<Integer> genome, TreeMap<Integer, Place> places) {
        this.genome = genome;
        this.fitness = this.calculateFitness(places);
    }

    /**
     * Constructor for Individual with initial random genome based on provided places
     *
     * @param minGen      minimum gen value (first possible key)
     * @param maxGen      maximum gen value (last possible key)
     * @param startingGen starting gen
     * @param places      all available places
     * @throws IllegalArgumentException illegal argument passed as minGen, maxGen or startingGen
     */
    public Individual(int minGen, int maxGen, int startingGen, TreeMap<Integer, Place> places) throws IllegalArgumentException {
        this(minGen, maxGen, startingGen);

        this.genome = this.randomGenome(minGen, maxGen, startingGen);
        this.fitness = this.calculateFitness(places);
    }

    /**
     * Constructor for Individual with initial random genome based on provided places
     *
     * @param startingGen starting gen
     * @param places      all available places
     * @throws IllegalArgumentException illegal argument passed as minGen, maxGen or startingGen
     */
    public Individual(int startingGen, TreeMap<Integer, Place> places) throws IllegalArgumentException {
        this(places.firstKey(), places.lastKey(), startingGen);

        this.genome = this.randomGenome(minGen, maxGen, startingGen);
        this.fitness = this.calculateFitness(places);
    }


    /**
     * Constructor for Individual with initial random genome
     *
     * @param minGen      minimum gen value (first possible key)
     * @param maxGen      maximum gen value (last possible key)
     * @param startingGen starting gen
     * @throws IllegalArgumentException illegal argument passed as minGen, maxGen or startingGen
     */
    public Individual(int minGen, int maxGen, int startingGen) throws IllegalArgumentException {
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
     * @param minGen      minimum gen value (first possible key)
     * @param maxGen      maximum gen value (last possible key)
     * @param startingGen starting gen
     * @return random genome
     */
    public ArrayList<Integer> randomGenome(int minGen, int maxGen, int startingGen) {
        ArrayList<Integer> genome = new ArrayList<>();
        IntStream.rangeClosed(minGen, maxGen).forEach(genome::add);
        Collections.shuffle(genome);
        Collections.swap(genome, 0, genome.indexOf(startingGen));

        return genome;
    }

    /**
     * @return fitness (the lower the better)
     */
    public int calculateFitness(TreeMap<Integer, Place> places) {
        int cost = 0;
        Integer currentGen = startingGen;

        for (int i = 1; i < this.genome.size(); i++) {
            cost += places.get(currentGen).getDistanceTo(this.genome.get(i));
            currentGen = this.genome.get(i);
        }

        cost += places.get(currentGen).getDistanceTo(startingGen);

        return cost;
    }

    public void mutate() {
        Random random = new Random();
        ArrayList<Integer> newGenome = genome;

        Collections.swap(newGenome, random.nextInt(genome.size() - 1) + 1, random.nextInt(genome.size() - 1) + 1);

        genome = newGenome;
    }

    /**
     * @return Individual genome
     */
    public ArrayList<Integer> getGenome() {
        return genome;
    }

    /**
     * @return fitness
     */
    public int getFitness() {
        return fitness;
    }

    /**
     * @return starting gen (always the same)
     */
    public int getStartingGen() {
        return startingGen;
    }

    /**
     * @return minimum gen value
     */
    public int getMinGen() {
        return minGen;
    }

    /**
     * @return maximum gen value
     */
    public int getMaxGen() {
        return maxGen;
    }

    /**
     * Set fitness based on genome and places
     *
     * @param places all available places
     */
    public void setFitness(TreeMap<Integer, Place> places) {
        this.fitness = calculateFitness(places);
    }

    @Override
    public int compareTo(Individual individual) {
        return this.getFitness() >= individual.getFitness() ? this.getFitness() > individual.getFitness() ? -1 : 0 : 1;
    }
}