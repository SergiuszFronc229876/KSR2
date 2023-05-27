package pl.ksr.calculation.sets;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.ksr.calculation.functions.MembershipFunction;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class FuzzySet {
    private final MembershipFunction membershipFunction;
    private final ClassicSet universeOfDiscourse;

    public double getMembershipDegree(double x) {
        return membershipFunction.getValue(x);
    }

    public double getCardinality() {
        if (universeOfDiscourse instanceof DiscreteSet) {
            return ((DiscreteSet) universeOfDiscourse).getElements()
                    .stream()
                    .mapToDouble(this::getMembershipDegree)
                    .sum();
        } else {
            throw new UnsupportedOperationException("Not implemented yet."); // TODO: need implementation
        }
    }

    public ClassicSet getSupport() {
        if (universeOfDiscourse instanceof DiscreteSet) {
            Set<Double> supportElements = ((DiscreteSet) universeOfDiscourse).getElements()
                    .stream()
                    .filter(val -> getMembershipDegree(val) > 0)
                    .collect(Collectors.toSet());
            return new DiscreteSet(supportElements);
        } else {
            throw new UnsupportedOperationException("Not implemented yet."); // TODO: need implementation
        }
    }

    public ClassicSet getAlphaCut(double alpha) {
        ClassicSet support = getSupport();

        if (support instanceof DiscreteSet) {
            Set<Double> alphaCut = ((DiscreteSet) support).getElements()
                    .stream()
                    .filter(val -> getMembershipDegree(val) >= alpha)
                    .collect(Collectors.toSet());
            return new DiscreteSet(alphaCut);
        } else {
            throw new UnsupportedOperationException("Not implemented yet."); // TODO: need implementation
        }
    }

    public boolean isEmpty() {
        return getSupport().isEmpty();
    }

    public boolean isConvex() {
        throw new UnsupportedOperationException("Not implemented yet."); // TODO: need implementation
    }

    public boolean isNormal() {
        return getHeight() == 1;
    }

    public double getHeight() {
        if (universeOfDiscourse instanceof DiscreteSet) {
            return ((DiscreteSet) universeOfDiscourse).getElements()
                    .stream()
                    .mapToDouble(this::getMembershipDegree)
                    .max()
                    .getAsDouble();
        } else {
            throw new UnsupportedOperationException("Not implemented yet."); // TODO: need implementation
        }
    }
}
