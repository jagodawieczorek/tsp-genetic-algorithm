package ga;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tournament test
 *
 * @author Jagoda Wieczorek
 */
class TournamentTest {

	@Test
	@DisplayName("Should select best individual from the whole provided sample list")
	void shouldSelectBestIndividualFromTheWholeList() {
		// given
		final ArrayList<Individual> individuals = getIndividuals();
		final int size = individuals.size();
		final Tournament tournament = new Tournament(size);
		// when
		final Individual individual = tournament.select(individuals);
		// then
		assertThat(individual.getFitness()).isEqualTo(100);
	}

	@Test
	@DisplayName("Should throw an IllegalArgumentException when amount of tournament competitors is not provided")
	void shouldThrowAnIllegalArgumentException_whenCountIsNotProvided() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(Tournament::new)
				.withMessage("Size of a sublist of individuals for tournament has to be provided");
	}

	@Test
	@DisplayName("Should throw an IllegalArgumentException when amount of tournament competitors is too big")
	void shouldThrowAnIllegalArgumentException_whenCountIsBiggerThanInitialListSize() {
		// given
		final int size = 10;
		final Tournament tournament = new Tournament(size);
		final ArrayList<Individual> individuals = getIndividuals();
		// then
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> tournament.select(individuals))
				.withMessage(String.format("The size of the population (%s) is to small to pick %s individuals", individuals.size(), size));
	}

	/**
	 * @return Sample list of individuals
	 */
	private ArrayList<Individual> getIndividuals() {
		return new ArrayList<>(List.of(new Individual(500), new Individual(400), new Individual(300), new Individual(200), new Individual(100)));
	}
}
