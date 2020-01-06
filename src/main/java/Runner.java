import ga.GeneticAlgorithm;
import ga.Individual;
import ga.PartiallyMappedCrossover;
import ga.Tournament;
import tsp.Path;
import tsp.Place;
import tsp.TSP;

import java.util.ArrayList;

/**
 * Runner class
 *
 * @author Jagoda Wieczorek
 */
public class Runner {
    public static void main(String[] args) {
        TSP tsp = new TSP("resources/tsp/gr96.tsp");
        GeneticAlgorithm geneticAlgorithm = GeneticAlgorithm.create(400, 1000, 0.4f, 0.8f, tsp, new Tournament(10), new PartiallyMappedCrossover());
        Individual individual = geneticAlgorithm.run();
        Path pathObj = new Path(tsp.getPlaces(), individual.getGenome());
        ArrayList<Place> path = pathObj.getPath();
    }
}