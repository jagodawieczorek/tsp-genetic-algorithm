package ga;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

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
		final PartiallyMappedCrossover crossover = new PartiallyMappedCrossover();
		final Individual parent1 = getParent1();
		final Individual parent2 = getParent2();
		final int breakpoint = 3;
		// when
		final Individual child = crossover.perform(parent1, parent2, breakpoint);
		// then
		assertThat(child.getGenome()).isEqualTo(new ArrayList<>(List.of(4, 6, 5, 1, 2, 3)));
	}

	@Test
	@DisplayName("Should throw IllegalArgumentException when breakpoint is out of bounds")
	void shouldThrowIllegalArgumentException_whenBreakpointIsOutOfBounds() {
		// given
		final PartiallyMappedCrossover crossover = new PartiallyMappedCrossover();
		final Individual parent1 = getParent1();
		final Individual parent2 = getParent2();
		final int breakpoint = 6;
		// then
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> crossover.perform(parent1, parent2, breakpoint))
				.withMessage(String.format("Crossover breakpoint cannot be bigger than %s", parent1.getGenome().size() - 1));
	}

	@DisplayName("Should NOT throw any exceptions using random method for breakpoint")
	@RepeatedTest(20)
	void shouldNotThrowAnyException_usingRandomMethodForBreakpoint() {
		// given
		final PartiallyMappedCrossover crossover = new PartiallyMappedCrossover();
		final Individual parent1 = getParent1();
		final Individual parent2 = getParent2();
		// when
		final Individual individual = crossover.perform(parent1, parent2);
		// then
		assertThat(individual).isNotNull();
	}

	private Individual getParent2() {
		return getParent(4, 6, 5, 3, 1, 2);
	}

	private Individual getParent1() {
		return getParent(2, 4, 3, 1, 6, 5);
	}

	private Individual getParent(final int i, final int i2, final int i3, final int i4, final int i5, final int i6) {
		return new Individual(new ArrayList<>(List.of(i, i2, i3, i4, i5, i6)));
	}
}
