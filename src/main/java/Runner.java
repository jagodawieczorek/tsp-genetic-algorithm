import ga.GeneticAlgorithm;
import ga.PartiallyMappedCrossover;
import ga.Tournament;
import tsp.TSP;

/**
 * Runner class
 *
 * @author Jagoda Wieczorek
 */
public class Runner {
    public static void main(String[] args) {
        TSP tsp = new TSP("resources/tsp/gr96.tsp");
        GeneticAlgorithm geneticAlgorithm = GeneticAlgorithm.create(500, 500, 0.3f, 0.8f, tsp, new Tournament(180), new PartiallyMappedCrossover());
        geneticAlgorithm.run();
    }
}