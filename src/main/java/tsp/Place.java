package tsp;

import ga.Individual;

/**
 * Place on the map
 *
 * @author Jagoda Wieczorek
 */
public class Place implements Individual {
    private int id;
    private Float latitude;
    private Float longitude;

    /**
     * @param id        ID from source file
     * @param latitude  Latitude of the place
     * @param longitude Longitude of the place
     */
    public Place(int id, Float latitude, Float longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return Place id according to the source file
     */
    public int getId() {
        return id;
    }

    /**
     * @return Latitude of the place
     */
    public Float getLatitude() {
        return latitude;
    }

    /**
     * @return Longitude of the place
     */
    public Float getLongitude() {
        return longitude;
    }
}
