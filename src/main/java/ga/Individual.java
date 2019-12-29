package ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Individual in Genetic Algorithm
 *
 * @author Jagoda Wieczorek
 */
public class Individual {
    private ArrayList<Integer> genome;
    private int fitness;
    private int startingGen;
    private int minGen;
    private int maxGen;

    public Individual() {
    }

    /**
     * @param minGen      minimum gen value (first possible key)
     * @param maxGen      maximum gen value (last possible key)
     * @param startingGen starting gen
     */
    public Individual(int minGen, int maxGen, int startingGen) {
        this.startingGen = startingGen;
        this.minGen = minGen;
        this.maxGen = maxGen;
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
        IntStream.rangeClosed(minGen, maxGen).filter(i -> i != startingGen).forEach(genome::add);
        Collections.shuffle(genome);

        return genome;
    }

    /**
     * @return Individual genome - list of places' keys without starting place key (which is always the same)
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
     * @return starting gen (always the same)SS
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
}