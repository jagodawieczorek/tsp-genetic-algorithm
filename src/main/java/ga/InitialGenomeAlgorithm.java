package ga;

import java.util.ArrayList;
import java.util.TreeMap;

import tsp.Place;

public interface InitialGenomeAlgorithm {
	ArrayList<Integer> initialize(int minGen, int maxGen, int startingGen, TreeMap<Integer, Place> places);
}
