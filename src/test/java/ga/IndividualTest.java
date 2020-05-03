package ga;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tsp.Place;
import tsp.TSP;

/**
 * Tests for Individual
 *
 * @author Jagoda Wieczorek
 */
@ExtendWith(MockitoExtension.class)
class IndividualTest {
	@InjectMocks
	private Individual individual;

	@Mock
	private Individual individual2;

	@Test
	@DisplayName("Should throw IllegalArgumentException when created with maxGen < minGen")
	void shouldThrowException_whenCreatedWithMaxGenValueLowerThanMinGenValue() {
		// given
		final int minGen = 1;
		final int maxGen = 0;
		final int startingGen = 1;
		// then
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Individual(minGen, maxGen, startingGen))
				.withMessage("Maximum gen value cannot be lower than minimum gen value");
	}

	@Test
	@DisplayName("Should throw IllegalArgumentException when starting gen < min gen")
	void shouldThrowException_whenCreatedWithStartingGenLowerThanMinGen() {
		// given
		final int minGen = 1;
		final int maxGen = 20;
		final int startingGen = 0;
		// then
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Individual(minGen, maxGen, startingGen))
				.withMessage("Starting gen has to be in a range <minimum gen value, maximum gen value>");
	}

	@Test
	@DisplayName("Should throw IllegalArgumentException when starting gen > max gen")
	void shouldThrowException_whenCreatedWithStartingGenBiggerThanMaxGen() {
		// given
		final int minGen = 1;
		final int maxGen = 20;
		final int startingGen = 40;
		// then
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Individual(minGen, maxGen, startingGen))
				.withMessage("Starting gen has to be in a range <minimum gen value, maximum gen value>");
	}

	@Test
	@DisplayName("Should create random genome")
	void shouldRandomGenome() {
		// given
		final Individual individual = new Individual();
		// when
		final ArrayList<Integer> genome = individual.randomGenome(1, 137, 1);
		// then
		assertThat(genome.size()).isEqualTo(137);
	}

	@Test
	@DisplayName("Should calculate fitness")
	void shouldCalculateFitness() {
		// given
		final Individual individual = new Individual(1, 4, 1);
		final TreeMap<Integer, Place> places = places();
		// when
		final int cost = individual.calculateFitness(places);
		// then
		assertThat(cost).isPositive();
	}

	@Test
	@DisplayName("Should compare 2 individuals with different fitness")
	void shouldCompareTo_2differentFitness() {
		// given
		when(this.individual2.getFitness()).thenReturn(1200);
		// when
		final int result = this.individual.compareTo(this.individual2);
		// then
		assertThat(result).isEqualTo(1);
	}

	@Test
	@DisplayName("Should compare 2 individuals with the same fitness")
	void shouldCompareTo_theSameFitness() {
		// given
		when(this.individual2.getFitness()).thenReturn(0);
		// when
		final int result = this.individual.compareTo(this.individual2);
		// then
		assertThat(result).isEqualTo(0);
	}

	@DisplayName("Should perform individual's mutation")
	@RepeatedTest(10)
	void shouldMutate() {
		// given
		final Individual individual = new Individual(new ArrayList<>(List.of(1, 2, 3, 4, 5)));
		// when
		individual.mutate();
		// then
		assertThat(individual.getGenome().get(0)).isEqualTo(1);
		assertThat(individual.getGenome()).containsExactlyInAnyOrder(1, 2, 3, 4, 5);
	}

	/**
	 * @return places
	 */
	private TreeMap<Integer, Place> places() {
		final TreeMap<Integer, Place> places = new TreeMap<>(Map.of(1, new Place(1, 71.17f, -156.47f), 2, new Place(2, 64.51f, -147.43f), 3,
				new Place(3, 61.13f, -149.53f), 4, new Place(4, 58.20f, -134.27f)));

		return TSP.calculateDistances(places);
	}
}
