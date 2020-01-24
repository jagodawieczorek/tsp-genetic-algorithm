package ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.IntStream;

public class RandomAlgorithm implements InitialGenomeAlgorithm {
    @Override
    public ArrayList<Integer> initialize(int minGen, int maxGen, int startingGen, int... params) {
        ArrayList<Integer> genome = new ArrayList<>();
        IntStream.rangeClosed(minGen, maxGen).forEach(genome::add);
        Collections.shuffle(genome);
        Collections.swap(genome, 0, genome.indexOf(startingGen));

        return genome;
    }
}
