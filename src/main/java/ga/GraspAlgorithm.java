package ga;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

import tsp.Place;

/**
 * GRASP algorithm (Greedy randomized adaptive search procedure) Semi greedy
 * heuristic
 *
 * @author Jagoda Wieczorek
 */
public class GraspAlgorithm implements InitialGenomeAlgorithm {
	private final int rclSize;

	private final Random random;

	/**
	 * @param rclSize
	 *                    Restricted candidate list (RCL) size
	 */
	public GraspAlgorithm(final int rclSize) {
		this.rclSize = rclSize;
		this.random = new Random();
	}

	@Override
	public ArrayList<Integer> initialize(final int minGen, final int maxGen, int current, final TreeMap<Integer, Place> places) {
		final ArrayList<Integer> genome = new ArrayList<>();
		genome.add(current);

		while (genome.size() <= places.get(current).getDistances().size()) {
			current = getNextForCurrentPlace(current, places, genome);
			genome.add(current);
		}

		return genome;
	}

	private int getNextForCurrentPlace(final int current, final TreeMap<Integer, Place> places, final ArrayList<Integer> genome) {
		final var distances = places.get(current).getDistances();
		final ArrayList<Integer> rclList = new ArrayList<>();
		int count = 0;

		final var iterator = distances.entrySet().iterator();
		// @TODO consider the performance when taken elements from the distances map are
		// removed
		while (iterator.hasNext() && count < this.rclSize) {
			final int key = iterator.next().getKey();
			if (!genome.contains(key)) {
				rclList.add(key);
				count++;
			}
		}

		return rclList.get(this.random.nextInt(rclList.size()));
	}
}
