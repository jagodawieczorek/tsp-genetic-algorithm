package ga;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tsp.GeoPlace;
import tsp.TSP;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Individual
 *
 * @author Jagoda Wieczorek
 */
class IndividualTest {

    @Test
    @DisplayName("Should throw IllegalArgumentException when created with maxGen < minGen")
    void shouldThrowException_whenCreatedWithMaxGenValueLowerThanMinGenValue() {
        // given
        int minGen = 1;
        int maxGen = 0;
        int startingGen = 1;
        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Individual(minGen, maxGen, startingGen))
                .withMessage("Maximum gen value cannot be lower than minimum gen value");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when starting gen < min gen")
    void shouldThrowException_whenCreatedWithStartingGenLowerThanMinGen() {
        // given
        int minGen = 1;
        int maxGen = 20;
        int startingGen = 0;
        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Individual(minGen, maxGen, startingGen))
                .withMessage("Starting gen has to be in a range <minimum gen value, maximum gen value>");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when starting gen > max gen")
    void shouldThrowException_whenCreatedWithStartingGenBiggerThanMaxGen() {
        // given
        int minGen = 1;
        int maxGen = 20;
        int startingGen = 40;
        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Individual(minGen, maxGen, startingGen))
                .withMessage("Starting gen has to be in a range <minimum gen value, maximum gen value>");
    }

    @Test
    @DisplayName("Should create random genome")
    void shouldRandomGenome() {
        // given
        Individual individual = new Individual();
        // when
        ArrayList<Integer> genome = individual.randomGenome(1, 137, 1);
        // then
        assertThat(genome.size()).isEqualTo(137);
    }

    @Test
    @DisplayName("Should calculate fitness")
    void shouldCalculateFitness() {
        // given
        Individual individual = new Individual(1, 4, 1);
        TreeMap<Integer, GeoPlace> places = places();
        // when
        int cost = individual.calculateFitness(places);
        // then
        assertThat(cost).isPositive();
    }

    /**
     * @return places
     */
    private TreeMap<Integer, GeoPlace> places() {
        TreeMap<Integer, GeoPlace> places = new TreeMap<>(
                Map.of(
                        1, new GeoPlace(1, 71.17f, -156.47f),
                        2, new GeoPlace(2, 64.51f, -147.43f),
                        3, new GeoPlace(3, 61.13f, -149.53f),
                        4, new GeoPlace(4, 58.20f, -134.27f)
                )
        );

        return TSP.calculateDistances(places);
    }
}