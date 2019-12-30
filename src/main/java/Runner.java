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
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(300, 100, 0.1, 0.1, tsp);
        geneticAlgorithm.run();
    }
}