import ga.*;
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
        GeneticAlgorithm geneticAlgorithm = GeneticAlgorithm.create(
                300,
                500,
                0.2f,
                0.5f,
                tsp,
                new Tournament(5),
                new PartiallyMappedCrossover(),
                new GraspAlgorithm(2)
        );
        Individual individual = geneticAlgorithm.run();
        Path pathObj = new Path(tsp.getPlaces(), individual.getGenome());
        ArrayList<Place> path = pathObj.getPath();
    }
}