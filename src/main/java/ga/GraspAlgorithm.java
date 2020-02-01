package ga;

import tsp.Place;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

/**
 * GRASP algorithm (Greedy randomized adaptive search procedure)
 * Semi greedy heuristic
 *
 * @author Jagoda Wieczorek
 */
public class GraspAlgorithm implements InitialGenomeAlgorithm {
    private int rclSize;
    private Random random;

    /**
     * @param rclSize Restricted candidate list (RCL) size
     */
    public GraspAlgorithm(int rclSize) {
        this.rclSize = rclSize;
        this.random = new Random();
    }

    @Override
    public ArrayList<Integer> initialize(int minGen, int maxGen, int current, TreeMap<Integer, Place> places) {
        ArrayList<Integer> genome = new ArrayList<>();
        genome.add(current);

        while (genome.size() <= places.get(current).getDistances().size()) {
            current = getNextForCurrentPlace(current, places, genome);
            genome.add(current);
        }

        return genome;
    }

    private int getNextForCurrentPlace(int current, TreeMap<Integer, Place> places, ArrayList<Integer> genome) {
        var distances = places.get(current).getDistances();
        ArrayList<Integer> rclList = new ArrayList<>();
        int count = 0;

        var iterator = distances.entrySet().iterator();
        // @TODO consider the performance when taken elements from the distances map are removed
        while (iterator.hasNext() && count < rclSize) {
            int key = iterator.next().getKey();
            if (!genome.contains(key)) {
                rclList.add(key);
                count++;
            }
        }

        return rclList.get(random.nextInt(rclList.size()));
    }
}
