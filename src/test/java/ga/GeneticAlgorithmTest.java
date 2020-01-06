package ga;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
        var populationSize = -1;
        var numberOfGenerations = 100;
        var mutationProbability = 0.1f;
        var crossoverProbability = 0.1f;
        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new GeneticAlgorithm(populationSize, numberOfGenerations, mutationProbability, crossoverProbability))
                .withMessage("Population size cannot be less or equal 0");
    }

    @Test
    @DisplayName("Should throw exception when is created with negative number of generations")
    void shouldThrowException_whenCreatedWithNegativeNumberOfGenerations() {
        // given
        var populationSize = 300;
        var numberOfGenerations = -6;
        var mutationProbability = 0.1f;
        var crossoverProbability = 0.1f;
        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new GeneticAlgorithm(populationSize, numberOfGenerations, mutationProbability, crossoverProbability))
                .withMessage("Number of generations cannot be less or equal 0");
    }

    @Test
    @DisplayName("Should throw exception when is created with invalid probability")
    void shouldThrowException_whenCreatedWithInvalidProbability() {
        // given
        var populationSize = 300;
        var numberOfGenerations = 100;
        var mutationProbability = 6f;
        var crossoverProbability = 0.1f;
        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new GeneticAlgorithm(populationSize, numberOfGenerations, mutationProbability, crossoverProbability))
                .withMessage("Probability has to be in range <0,1>");
    }
}