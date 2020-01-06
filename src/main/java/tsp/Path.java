package tsp;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Path class
 *
 * @author Jagoda Wieczorek
 */
public class Path {
    private ArrayList<Integer> order;
    private TreeMap<Integer, Place> places;
    private ArrayList<Place> path;

    public Path(TreeMap<Integer, Place> places, ArrayList<Integer> order) {
        this.places = places;
        this.order = order;
        this.path = new ArrayList<>();

        for (Integer index : order) {
            this.path.add(places.get(index));
        }
    }

    public ArrayList<Place> getPath() {
        return path;
    }
}
