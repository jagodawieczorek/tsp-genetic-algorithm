package ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.stream.IntStream;

import tsp.Place;

public class RandomAlgorithm implements InitialGenomeAlgorithm {
	@Override
	public ArrayList<Integer> initialize(final int minGen, final int maxGen, final int startingGen, final TreeMap<Integer, Place> places) {
		final ArrayList<Integer> genome = new ArrayList<>();
		IntStream.rangeClosed(minGen, maxGen).forEach(genome::add);
		Collections.shuffle(genome);
		Collections.swap(genome, 0, genome.indexOf(startingGen));

		return genome;
	}
}
