package pl.ksr.logic.calculation.sets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.ksr.logic.calculation.functions.MembershipFunction;

import java.util.List;

public class FuzzySetTest {
    private final MembershipFunction smallOddDigit = new MembershipFunction() {
        @Override
        public double getValue(double x) {
            return switch ((int) x) {
                case 1, 3 -> 1.0;
                case 5 -> 0.7;
                case 7 -> 0.3;
                default -> 0.0;
            };
        }

        @Override
        public double getAreaFunction() {
            throw new UnsupportedOperationException();
        }

        @Override
        public double getLeftLimit() {
            throw new UnsupportedOperationException();
        }

        @Override
        public double getRightLimit() {
            throw new UnsupportedOperationException();
        }
    };
    private final DiscreteSet universeOfDiscourse = new DiscreteSet(List.of(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0));
    private final FuzzySet fuzzySet = new FuzzySet(smallOddDigit, universeOfDiscourse);

    @Test
    public void cardinalityFuzzySet() {
        Assertions.assertEquals(3.0, fuzzySet.getCardinality());
    }

    @Test
    public void degreeOfFuzziness() {
        Assertions.assertEquals(0.4, fuzzySet.getDegreeOfFuzziness());
    }

    @Test
    public void cardinalityOfSupport() {
        Assertions.assertEquals(4, fuzzySet.getSupport().getSize());
    }

    @Test
    public void cardinalityOfUniverseOfDiscourse() {
        Assertions.assertEquals(10, fuzzySet.getUniverseOfDiscourse().getSize());
    }
}
