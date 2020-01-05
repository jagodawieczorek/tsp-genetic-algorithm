package ga;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tournament test
 *
 * @author Jagoda Wieczorek
 */
class TournamentTest {

    @Test
    void shouldSelectBestIndividualFromTheWholeList() {
        // given
        Tournament tournament = new Tournament();
        ArrayList<Individual> individuals = getIndividuals();
        int size = individuals.size();
        // when
        Individual individual = tournament.select(individuals, size);
        // then
        assertThat(individual.getFitness()).isEqualTo(100);
    }

    @Test
    void shouldThrowAnIllegalArgumentException_whenCountIsNotProvided() {
        // given
        Tournament tournament = new Tournament();
        ArrayList<Individual> individuals = getIndividuals();
        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> tournament.select(individuals))
                .withMessage("Size of a sublist of individuals for tournament has to be provided");
    }

    @Test
    void shouldThrowAnIllegalArgumentException_whenCountIsBiggerThanInitialListSize() {
        // given
        Tournament tournament = new Tournament();
        ArrayList<Individual> individuals = getIndividuals();
        int size = 10;
        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> tournament.select(individuals, size))
                .withMessage(String.format("The size of the population (%s) is to small to pick %s individuals", individuals.size(), size));
    }

    /**
     * @return Sample list of individuals
     */
    private ArrayList<Individual> getIndividuals() {
        return new ArrayList<>(
                List.of(
                        new Individual(500),
                        new Individual(400),
                        new Individual(300),
                        new Individual(200),
                        new Individual(100)
                )
        );
    }
}