package ga;

import java.util.ArrayList;

public interface InitialGenomeAlgorithm {
    ArrayList<Integer> initialize(int minGen, int maxGen, int startingGen, int... params);
}