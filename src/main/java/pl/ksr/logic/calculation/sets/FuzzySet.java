package pl.ksr.logic.calculation.sets;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.ksr.logic.calculation.functions.ComplementMembershipFunction;
import pl.ksr.logic.calculation.functions.IntersectMembershipFunction;
import pl.ksr.logic.calculation.functions.MembershipFunction;
import pl.ksr.logic.calculation.functions.UnionMembershipFunction;

import java.util.List;

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
            return membershipFunction.getAreaFunction();
        }
    }

    public double getCardinality(List<Double> databaseValues) {
        return databaseValues.stream()
                .mapToDouble(this::getMembershipDegree)
                .sum();
    }

    public ClassicSet getSupport() {
        if (universeOfDiscourse instanceof DiscreteSet) {
            List<Double> supportElements = ((DiscreteSet) universeOfDiscourse).getElements()
                    .stream()
                    .filter(val -> getMembershipDegree(val) > 0)
                    .toList();
            return new DiscreteSet(supportElements);
        } else {
            return new ContinuousSet(membershipFunction.getLeftLimit(), membershipFunction.getRightLimit());
        }
    }

    public ClassicSet getSupport(List<Double> databaseValues) {
        List<Double> list = databaseValues.stream()
                .filter(val -> getMembershipDegree(val) > 0)
                .toList();
        return new DiscreteSet(list);
    }

    public ClassicSet getAlphaCut(double alpha) {
        ClassicSet support = getSupport();
        if (support instanceof DiscreteSet) {
            List<Double> alphaCut = ((DiscreteSet) support).getElements()
                    .stream()
                    .filter(val -> getMembershipDegree(val) >= alpha)
                    .toList();
            return new DiscreteSet(alphaCut);
        } else {
            throw new UnsupportedOperationException("Not implemented yet."); // TODO: need implementation
        }
    }

    public double getDegreeOfFuzziness() {
        return getSupport().getSize() / universeOfDiscourse.getSize();
    }

    public FuzzySet complement(FuzzySet otherSet) {
        return new FuzzySet(new ComplementMembershipFunction(membershipFunction), universeOfDiscourse);
    }

    public FuzzySet union(FuzzySet otherSet) {
        return new FuzzySet(new UnionMembershipFunction(membershipFunction, otherSet.membershipFunction), universeOfDiscourse);
    }

    public FuzzySet intersect(FuzzySet otherSet) {
        return new FuzzySet(new IntersectMembershipFunction(membershipFunction, otherSet.membershipFunction), universeOfDiscourse);
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
