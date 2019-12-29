package tsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static tsp.TSP.DisplayDataType.*;
import static tsp.TSP.EdgeWeightType.*;

/**
 * Symmetric Travelling salesman problem
 *
 * @author Jagoda Wieczorek
 */
public class TSP {
    private static final Logger LOGGER = Logger.getLogger(TSP.class.getName());
    private static final ArrayList<EdgeWeightType> ALLOWED_EDGE_WEIGHT_TYPES = new ArrayList<>(List.of(GEO));
    private static final ArrayList<DisplayDataType> ALLOWED_DISPLAY_DATA_TYPES = new ArrayList<>(List.of(COORD_DISPLAY));

    private String sourceFilename;
    private TreeMap<Integer, GeoPlace> places;
    private String name;
    private DisplayDataType displayDataType;
    private EdgeWeightType edgeWeightType;

    private Integer startingPlace;

    public TSP() {
        this.places = new TreeMap<>();
    }

    /**
     * @param sourceFilename File with places' coordinates
     */
    public TSP(String sourceFilename) {
        this.places = new TreeMap<>();
        this.setFromFile(sourceFilename);
        this.setStartingPlace();
    }

    /**
     * @param sourceFilename File with places' coordinates
     */
    public void setFromFile(String sourceFilename) {
        this.sourceFilename = sourceFilename;

        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("NAME:")) {
                    this.name = line.replace("NAME:", "").trim();
                } else if (line.startsWith("TYPE:")) {
                    String type = line.replace("TYPE:", "").trim();

                    if (!type.equalsIgnoreCase("TSP")) {
                        throw new IllegalArgumentException("Source file can't be of type: " + type);
                    }
                } else if (line.startsWith("EDGE_WEIGHT_TYPE:")) {
                    this.edgeWeightType = EdgeWeightType.valueOf(line.replace("EDGE_WEIGHT_TYPE:", "").trim());

                    if (!ALLOWED_EDGE_WEIGHT_TYPES.contains(this.edgeWeightType)) {
                        throw new IllegalArgumentException(edgeWeightType + " edge weight type is not allowed to use");
                    }
                } else if (line.startsWith("DISPLAY_DATA_TYPE:")) {
                    this.displayDataType = DisplayDataType.valueOf(line.replace("DISPLAY_DATA_TYPE:", "").trim());

                    if (!ALLOWED_DISPLAY_DATA_TYPES.contains(displayDataType)) {
                        throw new IllegalArgumentException(ALLOWED_DISPLAY_DATA_TYPES + " display data type is not allowed to use");
                    }
                } else if (line.trim().equals("NODE_COORD_SECTION")) {
                    String place;
                    while ((place = reader.readLine()) != null) {
                        String[] placeData = place.trim().split(" ");
                        places.put(Integer.parseInt(placeData[0]), new GeoPlace(Integer.parseInt(placeData[0]), Float.parseFloat(placeData[1]), Float.parseFloat(placeData[2])));
                    }
                }
            }

        } catch (IOException | IllegalArgumentException e) {
            LOGGER.log(Level.FINE, e.toString());
        }
    }

    public void setStartingPlace() {
        this.startingPlace = this.getGeoPlaces().firstKey();
    }

    public void calculateAndAssignDistances() {
        Set<Map.Entry<Integer, GeoPlace>> listOfGeoPlaces = places.entrySet();

        for (Map.Entry<Integer, GeoPlace> startPlace : listOfGeoPlaces) {
            try {
                for (Map.Entry<Integer, GeoPlace> endPlace : listOfGeoPlaces) {
                    if (!startPlace.getKey().equals(endPlace.getKey())) {
                        startPlace.getValue().setDistanceTo(endPlace.getValue());
                    }
                }
            } catch (IllegalArgumentException e) {
                LOGGER.log(Level.WARNING, e.toString());
            }
        }
    }

    /**
     * @return String Path to the source file
     */
    public String getSourceFilename() {
        return sourceFilename;
    }

    /**
     * @return List of places
     */
    public TreeMap<Integer, GeoPlace> getGeoPlaces() {
        return places;
    }

    /**
     * @return TSP name based on source file
     */
    public String getName() {
        return name;
    }

    /**
     * @return places as TreeMap
     */
    public TreeMap<Integer, GeoPlace> getPlaces() {
        return places;
    }

    /**
     * @return Used display data type e.g. COORD_DISPLAY
     */
    public DisplayDataType getDisplayDataType() {
        return displayDataType;
    }

    /**
     * @return Used edge weight type e.g. GEO
     */
    public EdgeWeightType getEdgeWeightType() {
        return edgeWeightType;
    }

    /**
     * @return Key of the starting place
     */
    public Integer getStartingPlace() {
        return startingPlace;
    }

    public enum EdgeWeightType {
        GEO
    }

    public enum DisplayDataType {
        COORD_DISPLAY
    }
}
