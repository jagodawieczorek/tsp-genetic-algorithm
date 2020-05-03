package ga;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Genetic Algorithm test
 *
 * @author Jagoda Wieczorek
 */
class GeneticAlgorithmTest {

	@Test
	@DisplayName("Should throw exception when is created with negative number of population size")
	void shouldThrowException_whenCreatedWithNegativeNumberOfPopulationSize() {
		// given
		final var populationSize = -1;
		final var numberOfGenerations = 100;
		final var mutationProbability = 0.1f;
		final var crossoverProbability = 0.1f;
		// then
		assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> new GeneticAlgorithm(populationSize, numberOfGenerations, mutationProbability, crossoverProbability))
				.withMessage("Population size cannot be less or equal 0");
	}

	@Test
	@DisplayName("Should throw exception when is created with negative number of generations")
	void shouldThrowException_whenCreatedWithNegativeNumberOfGenerations() {
		// given
		final var populationSize = 300;
		final var numberOfGenerations = -6;
		final var mutationProbability = 0.1f;
		final var crossoverProbability = 0.1f;
		// then
		assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> new GeneticAlgorithm(populationSize, numberOfGenerations, mutationProbability, crossoverProbability))
				.withMessage("Number of generations cannot be less or equal 0");
	}

	@Test
	@DisplayName("Should throw exception when is created with invalid probability")
	void shouldThrowException_whenCreatedWithInvalidProbability() {
		// given
		final var populationSize = 300;
		final var numberOfGenerations = 100;
		final var mutationProbability = 6f;
		final var crossoverProbability = 0.1f;
		// then
		assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> new GeneticAlgorithm(populationSize, numberOfGenerations, mutationProbability, crossoverProbability))
				.withMessage("Probability has to be in range <0,1>");
	}
}
