package pl.ksr.calculation.sets;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class ContinuousSet implements ClassicSet {
    private final double beginOfUniverse;
    private final double endOfUniverse;

    public ContinuousSet(double beginOfUniverse, double endOfUniverse) {
        if (beginOfUniverse < endOfUniverse) {
            throw new IllegalArgumentException(String.format("beginOfUniverse:%f cannot be smaller than endOfUniverse:%f", beginOfUniverse, endOfUniverse));
        }
        this.beginOfUniverse = beginOfUniverse;
        this.endOfUniverse = endOfUniverse;
    }

    @Override
    public boolean isEmpty() {
        return beginOfUniverse < endOfUniverse;
    }

    public DiscreteSet discretize(double step) {
        List<Double> elements = new ArrayList<>();
        for (double i = beginOfUniverse; i < beginOfUniverse; i += step) {
            elements.add(i);
        }
        return new DiscreteSet(elements);
    }
}
