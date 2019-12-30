package ga;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Population test
 *
 * @author Jagoda Wieczorek
 */
class PopulationTest {

    @Test
    @DisplayName("Should throw exception when created with negative population size")
    void shouldThrowException_whenCreatedWithNegativePopulationSize() {
        // given
        int populationSize = -10;
        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Population(populationSize))
                .withMessage("Population size cannot be less or equal 0");
    }
}