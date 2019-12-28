package tsp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Travelling salesman problem
 * used for reading source files and writing solution
 *
 * @author Jagoda Wieczorek
 */
public class TSP {
    private static final Logger LOGGER = Logger.getLogger(TSP.class.getName());
    private static final ArrayList<String> ALLOWED_EDGE_WEIGHT_TYPES = new ArrayList<>(List.of("GEO"));
    private static final ArrayList<String> ALLOWED_DISPLAY_DATA_TYPES = new ArrayList<>(List.of("COORD_DISPLAY"));

    private String sourceFilename;
    private ArrayList<Place> places;
    private String name;

    public TSP() {
        this.places = new ArrayList<>();
    }

    /**
     * @param sourceFilename File with places coordinates
     */
    public void setFromFile(String sourceFilename) {
        this.sourceFilename = sourceFilename;

        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilename))) {
            String line;
            String edgeWeightType;
            String displayDataType;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("NAME:")) {
                    this.name = line.replace("NAME:", "").trim();
                } else if (line.startsWith("TYPE:")) {
                    String type = line.replace("TYPE:", "").trim();

                    if (!type.equalsIgnoreCase("TSP")) {
                        throw new IllegalArgumentException("Source file can't be of type: " + type);
                    }
                } else if (line.startsWith("EDGE_WEIGHT_TYPE:")) {
                    edgeWeightType = line.replace("EDGE_WEIGHT_TYPE:", "").trim();

                    if (!ALLOWED_EDGE_WEIGHT_TYPES.contains(edgeWeightType)) {
                        throw new IllegalArgumentException(edgeWeightType + " edge weight type is not allowed to use");
                    }
                } else if (line.startsWith("DISPLAY_DATA_TYPE:")) {
                    displayDataType = line.replace("DISPLAY_DATA_TYPE:", "").trim();

                    if (!ALLOWED_DISPLAY_DATA_TYPES.contains(displayDataType)) {
                        throw new IllegalArgumentException(ALLOWED_DISPLAY_DATA_TYPES + " display data type is not allowed to use");
                    }
                } else if (line.trim().equals("NODE_COORD_SECTION")) {
                    String place;
                    while ((place = reader.readLine()) != null) {
                        String[] placeData = place.trim().split(" ");
                        places.add(new Place((Integer.parseInt(placeData[0])), Float.parseFloat(placeData[1]), Float.parseFloat(placeData[2])));
                    }
                }
            }

        } catch (IOException | IllegalArgumentException e) {
            LOGGER.log(Level.FINE, e.toString());
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
    public ArrayList<Place> getPlaces() {
        return places;
    }

    /**
     * @return TSP name based on source file
     */
    public String getName() {
        return name;
    }
}
