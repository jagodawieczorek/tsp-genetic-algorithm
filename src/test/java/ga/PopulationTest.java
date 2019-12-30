package ga;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Population test
 *
 * @author Jagoda Wieczorek
 */
@ExtendWith(MockitoExtension.class)
class PopulationTest {

    @Test
    @DisplayName("Should throw exception when created with negative population size")
    void shouldThrowException_whenCreatedWithNegativePopulationSize() {
        // given
        int populationSize = -10;
        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Population(populationSize, 1, null))
                .withMessage("Population size cannot be less or equal 0");
    }
}