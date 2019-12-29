package ga;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Individual
 *
 * @author Jagoda Wieczorek
 */
class IndividualTest {

    @Test
    @DisplayName("Create random genome")
    void randomGenome() {
        // given
        Individual individual = new Individual();
        // when
        ArrayList<Integer> genome = individual.randomGenome(1, 137, 1);
        // then
        assertThat(genome.size()).isEqualTo(136);
    }
}