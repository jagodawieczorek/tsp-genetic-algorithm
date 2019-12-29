package tsp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Travelling Salesman Problem class
 *
 * @author Jagoda Wieczorek
 */
class TSPTest {
    private static final String FILE_WITH_GROUP_OF_4_COORDINATES = "resources/tsp/test/gr4.tsp";
    private static final String FILE_WITH_GROUP_OF_137_COORDINATES = "resources/tsp/gr137.tsp";

    @Test
    @DisplayName("Create places map from test source file with 4 places")
    void setFromFile_4places() {
        // given
        TSP tsp = new TSP();

        // when
        tsp.setFromFile(FILE_WITH_GROUP_OF_4_COORDINATES);

        // then
        assertEquals("gr4", tsp.getName());
        TreeMap<Integer, GeoPlace> places = tsp.getGeoPlaces();
        assertEquals(4, places.size());

        assertEquals(71.17f, places.get(1).getLatitude());
        assertEquals(-156.47f, places.get(1).getLongitude());

        assertEquals(1.2441285563664188, places.get(1).getRadiansLatitude());
        assertEquals(-2.7363848451384367, places.get(1).getRadiansLongitude());

        assertEquals(64.51f, places.get(2).getLatitude());
        assertEquals(-147.43f, places.get(2).getLongitude());

        assertEquals(1.1202103206589311, places.get(2).getRadiansLatitude());
        assertEquals(-2.5781414439841943, places.get(2).getRadiansLongitude());

        Set<Map.Entry<Integer, GeoPlace>> listOfGeoPlaces = places.entrySet();

        for (Map.Entry<Integer, GeoPlace> place : listOfGeoPlaces) {
            assertEquals(place.getKey(), place.getValue().getId());
        }
    }

    @Test
    @DisplayName("Calculate and assign distances for 4 places")
    void calculateAndAssignDistances_4places() {
        // given
        TSP tsp = new TSP(FILE_WITH_GROUP_OF_4_COORDINATES);
        // when
        tsp.calculateAndAssignDistances();
        // then
        TreeMap<Integer, GeoPlace> places = tsp.getGeoPlaces();
        assertThat(places.get(1).getDistances()).containsOnlyKeys(2, 3, 4);
        assertThat(places.get(1).getDistances().get(2)).isNotNull();
        assertThat(places.get(1).getDistances().get(3)).isNotNull();
        assertThat(places.get(1).getDistances().get(4)).isNotNull();

        assertThat(places.get(2).getDistances()).containsOnlyKeys(1, 3, 4);
        assertThat(places.get(2).getDistances().get(1)).isNotNull();
        assertThat(places.get(2).getDistances().get(3)).isNotNull();
        assertThat(places.get(2).getDistances().get(4)).isNotNull();

        assertThat(places.get(1).getDistances().get(3)).isEqualTo(places.get(3).getDistances().get(1));
    }

    @Test
    @DisplayName("Calculate and assign distances for 137 places")
    void calculateAndAssignDistances_137places() {
        // given
        TSP tsp = new TSP(FILE_WITH_GROUP_OF_137_COORDINATES);
        // when
        tsp.calculateAndAssignDistances();
        // then
        TreeMap<Integer, GeoPlace> places = tsp.getGeoPlaces();
        assertThat(places.get(1).getDistances().size()).isEqualTo(136);
    }

    @Test
    @DisplayName("Assign starting place which is first place on the list")
    void setStartingPlace() {
        // given
        TSP tsp = new TSP();
        tsp.setFromFile(FILE_WITH_GROUP_OF_4_COORDINATES);
        // when
        tsp.setStartingPlace();
        // then
        assertThat(tsp.getStartingPlace()).isEqualTo(1);
    }
}