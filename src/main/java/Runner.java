import java.util.ArrayList;

import ga.*;
import tsp.Path;
import tsp.Place;
import tsp.TSP;

/**
 * Runner class
 *
 * @author Jagoda Wieczorek
 */
public class Runner {
	public static void main(final String[] args) {
		final TSP tsp = new TSP("resources/tsp/gr96.tsp");
		final GeneticAlgorithm geneticAlgorithm = GeneticAlgorithm.create(300, 500, 0.2f, 0.5f, tsp, new Tournament(5), new PartiallyMappedCrossover(),
				new GraspAlgorithm(2));
		final Individual individual = geneticAlgorithm.run();
		final Path pathObj = new Path(tsp.getPlaces(), individual.getGenome());
		final ArrayList<Place> path = pathObj.getPath();
	}
}
