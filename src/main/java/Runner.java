import ga.GeneticAlgorithm;
import tsp.TSP;

/**
 * Runner class
 *
 * @author Jagoda Wieczorek
 */
public class Runner {
    public static void main(String[] args) {
        TSP tsp = new TSP("resources/tsp/gr137.tsp");
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(1000, 300, 0.3f, 0.5f, tsp);
        geneticAlgorithm.run();
    }
}