package ga;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

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
		final int populationSize = -10;
		// then
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Population(populationSize, 1, null))
				.withMessage("Population size cannot be less or equal 0");
	}
}
