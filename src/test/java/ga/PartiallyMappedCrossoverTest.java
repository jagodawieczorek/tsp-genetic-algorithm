package ga;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Partially mapped crossover tests
 *
 * @author Jagoda Wieczorek
 */
class PartiallyMappedCrossoverTest {
    @Test
    @DisplayName("Should return new valid child")
    void shouldReturnNewValidChild() {
        // given
        PartiallyMappedCrossover crossover = new PartiallyMappedCrossover();
        Individual parent1 = getParent(2, 4, 3, 1, 6, 5);
        Individual parent2 = getParent(4, 6, 5, 3, 1, 2);
        int breakpoint = 3;
        // when
        Individual child = crossover.perform(parent1, parent2, breakpoint);
        // then
        assertThat(child.getGenome()).isEqualTo(new ArrayList<>(List.of(4, 6, 5, 1, 2, 3)));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when breakpoint is out of bounds")
    void shouldThrowIllegalArgumentException_whenBreakpointIsOutOfBounds() {
        // given
        PartiallyMappedCrossover crossover = new PartiallyMappedCrossover();
        Individual parent1 = getParent(2, 4, 3, 1, 6, 5);
        Individual parent2 = getParent(4, 6, 5, 3, 1, 2);
        int breakpoint = 6;
        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> crossover.perform(parent1, parent2, breakpoint))
                .withMessage(String.format("Crossover breakpoint cannot be bigger than %s", parent1.getGenome().size() - 1));
    }

    private Individual getParent(int i, int i2, int i3, int i4, int i5, int i6) {
        return new Individual(new ArrayList<>(List.of(i, i2, i3, i4, i5, i6)));
    }
}