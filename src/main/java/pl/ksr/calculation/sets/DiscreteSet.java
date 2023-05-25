package pl.ksr.calculation.sets;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;


@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class DiscreteSet implements ClassicSet {
    private final List<Double> elements;

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }
}
