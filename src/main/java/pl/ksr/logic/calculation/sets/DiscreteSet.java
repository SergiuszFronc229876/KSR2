package pl.ksr.logic.calculation.sets;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;


@Getter
@EqualsAndHashCode
@ToString
public class DiscreteSet implements ClassicSet {
    private final List<Double> elements;

    public DiscreteSet(List<Double> elements) {
        if (elements.isEmpty()) {
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
}
