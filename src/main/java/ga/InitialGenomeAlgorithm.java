package ga;

import tsp.Place;

import java.util.ArrayList;
import java.util.TreeMap;

public interface InitialGenomeAlgorithm {
    ArrayList<Integer> initialize(int minGen, int maxGen, int startingGen, TreeMap<Integer, Place> places);
}