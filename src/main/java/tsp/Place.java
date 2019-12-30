package tsp;

import java.util.TreeMap;

/**
 * Place on the map
 *
 * @author Jagoda Wieczorek
 */
public class Place {
    private static final Double PI = 3.141592;
    private static final Float EARTH_EQUATORIAL_RADIUS = 6378.388f;

    private int id;
    private Float latitude;
    private Float longitude;
    private Double radiansLatitude;
    private Double radiansLongitude;
    private TreeMap<Integer, Integer> distances;

    /**
     * @param id        ID from source file
     * @param latitude  latitude of the place
     * @param longitude longitude of the place
     */
    public Place(int id, Float latitude, Float longitude) {
        this.id = id;
        this.latitude = latitude;
        this.setRadiansLatitude(latitude);
        this.longitude = longitude;
        this.setRadiansLongitude(longitude);
        this.distances = new TreeMap<>();
    }

    /**
     * @param latitude Latitude of this place (not radians)
     */
    private void setRadiansLatitude(Float latitude) {
        int deg = Math.round(latitude);
        float min = latitude - deg;

        this.radiansLatitude = PI * (deg + 5.0 * min / 3.0) / 180.0;
    }

    /**
     * @param longitude Longitude of this place (not radians)
     */
    private void setRadiansLongitude(Float longitude) {
        int deg = Math.round(longitude);
        float min = longitude - deg;

        this.radiansLongitude = PI * (deg + 5.0 * min / 3.0) / 180.0;
    }

    /**
     * @param Place Second Place
     */
    public void setDistanceTo(Place Place) throws IllegalArgumentException {
        if (Place.getId() == this.getId()) {
            throw new IllegalArgumentException("Cannot set distance to a place with the same ID");
        } else if (Place.getDistances().containsKey(this.getId())) {
            this.getDistances().put(Place.getId(), Place.getDistanceTo(this));
        } else {
            double q1 = Math.cos(this.getRadiansLongitude() - Place.getRadiansLongitude());
            double q2 = Math.cos(this.getRadiansLatitude() - Place.getRadiansLatitude());
            double q3 = Math.cos(this.getRadiansLatitude() + Place.getRadiansLatitude());
            int distance = (int) (EARTH_EQUATORIAL_RADIUS * Math.acos(0.5 * ((1.0 + q1) * q2 - (1.0 - q1) * q3)) + 1.0);
            this.getDistances().put(Place.getId(), distance);
        }
    }

    /**
     * @param Place Second Place to which the distance is measured
     * @return Distance to second Place
     */
    public Integer getDistanceTo(Place Place) {
        return this.getDistances().get(Place.getId());
    }

    /**
     * @param PlaceId Second Place ID to which the distance is measured
     * @return Distance to second Place
     */
    public Integer getDistanceTo(int PlaceId) {
        return this.getDistances().get(PlaceId);
    }

    /**
     * @return Place id according to the source file
     */
    public int getId() {
        return id;
    }

    /**
     * @return latitude of the place
     */
    public Float getLatitude() {
        return latitude;
    }

    /**
     * @return longitude of the place
     */
    public Float getLongitude() {
        return longitude;
    }

    /**
     * @return Radians latitude
     */
    public Double getRadiansLatitude() {
        return radiansLatitude;
    }

    /**
     * @return Radians Longitude
     */
    public Double getRadiansLongitude() {
        return radiansLongitude;
    }

    public TreeMap<Integer, Integer> getDistances() {
        return distances;
    }
}
