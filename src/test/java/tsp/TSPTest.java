package tsp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Travelling Salesman Problem class
 *
 * @author Jagoda Wieczorek
 */
class TSPTest {

    @Test
    void setFromFile_4places() {
        TSP tsp = new TSP();
        tsp.setFromFile("resources/tsp/test/gr4.tsp");
        assertEquals("gr4", tsp.getName());
        ArrayList<Place> places = tsp.getPlaces();
        assertEquals(4, places.size());
        assertEquals(1, places.get(0).getId());
        assertEquals(71.17f, places.get(0).getLatitude());
        assertEquals(-156.47f, places.get(0).getLongitude());
    }
}