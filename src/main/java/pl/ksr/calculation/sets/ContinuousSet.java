package pl.ksr.calculation.sets;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
@ToString
public class ContinuousSet implements ClassicSet {
    private final double beginOfUniverse;
    private final double endOfUniverse;

    public ContinuousSet(double beginOfUniverse, double endOfUniverse) {
        if (beginOfUniverse >= endOfUniverse) {
            throw new IllegalArgumentException(String.format("endOfUniverse:%s must be greater than beginOfUniverse:%s", endOfUniverse, beginOfUniverse));
        }
        this.beginOfUniverse = beginOfUniverse;
        this.endOfUniverse = endOfUniverse;
    }

    @Override
    public boolean isEmpty() {
        return beginOfUniverse < endOfUniverse;
    }

    @Override
    public double getSize() {
        return endOfUniverse - beginOfUniverse;
    }

    public ContinuousSet complement(double newBeginOfUniverse, double newEndOfUniverse) {
        return new ContinuousSet(newBeginOfUniverse, newEndOfUniverse);
    }

    public ContinuousSet union(ContinuousSet otherSet) {
        if ((beginOfUniverse > otherSet.getBeginOfUniverse() && endOfUniverse > otherSet.getBeginOfUniverse()) ||
                (beginOfUniverse < otherSet.getEndOfUniverse() && endOfUniverse < otherSet.getEndOfUniverse())) {
            throw new IllegalArgumentException("Sets cannot be disjoint");
        }
        double newStart = Math.min(beginOfUniverse, otherSet.getBeginOfUniverse());
        double newEnd = Math.max(endOfUniverse, otherSet.getEndOfUniverse());
        return new ContinuousSet(newStart, newEnd);
    }

    public ContinuousSet intersection(ContinuousSet otherSet) {
        if ((beginOfUniverse > otherSet.getBeginOfUniverse() && endOfUniverse > otherSet.getBeginOfUniverse()) ||
                (beginOfUniverse < otherSet.getEndOfUniverse() && endOfUniverse < otherSet.getEndOfUniverse())) {
            throw new IllegalArgumentException("Sets cannot be disjoint");
        }
        double newStart = Math.max(beginOfUniverse, otherSet.getBeginOfUniverse());
        double newEnd = Math.min(endOfUniverse, otherSet.getBeginOfUniverse());
        return new ContinuousSet(newStart, newEnd);
    }

    public DiscreteSet discretize(double step) {
        Set<Double> elements = new HashSet<>();
        for (double i = beginOfUniverse; i < beginOfUniverse; i += step) {
            elements.add(i);
        }
        return new DiscreteSet(elements);
    }
}
