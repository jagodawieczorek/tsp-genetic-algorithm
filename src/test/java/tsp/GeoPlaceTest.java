package tsp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Class description...
 *
 * @author Jagoda Wieczorek
 */
class GeoPlaceTest {

    @Test
    @DisplayName("Set geographical distance from one place to another")
    void setDistanceTo() {
        // given
        GeoPlace startGeoPlace = new GeoPlace(1, 71.17f, -156.47f);
        GeoPlace endGeoPlace = new GeoPlace(2, 64.51f, -147.43f);
        // when
        startGeoPlace.setDistanceTo(endGeoPlace);
        // then
        assertThat(startGeoPlace.getDistances().get(endGeoPlace.getId())).isEqualTo(876);
    }
}