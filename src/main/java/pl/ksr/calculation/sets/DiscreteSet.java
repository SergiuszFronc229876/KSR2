package pl.ksr.calculation.sets;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@Getter
@EqualsAndHashCode
@ToString
public class DiscreteSet implements ClassicSet {
    private final Set<Double> elements;

    public DiscreteSet(Set<Double> elements) {
        if(elements.isEmpty()) {
            throw new IllegalArgumentException("Set cannot be empty");
        }
        this.elements = elements;
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public double getSize() {
        return elements.size();
    }

    public DiscreteSet complement(double beginOfUniverse, double endOfUniverse, double step) {
        Set<Double> complementValues = new HashSet<>();
        for (double value = beginOfUniverse; value <= endOfUniverse; value += step) {
            if (!elements.contains(value)) {
                complementValues.add(value);
            }
        }
        return new DiscreteSet(complementValues);
    }

    public DiscreteSet union(DiscreteSet otherSet) {
        Set<Double> unionValues = new HashSet<>(elements);
        unionValues.addAll(otherSet.getElements());
        return new DiscreteSet(unionValues);
    }

    public DiscreteSet intersection(DiscreteSet otherSet) {
        Set<Double> intersectValues = new HashSet<>(elements);
        intersectValues.retainAll(otherSet.getElements());
        return new DiscreteSet(intersectValues);
    }
}
