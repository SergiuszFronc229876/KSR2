package pl.ksr.logic.summarization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.ksr.logic.calculation.sets.FuzzySet;

@AllArgsConstructor
@Getter
public abstract class Quantifier {
    private String name;
    private FuzzySet fuzzySet;

    @Override
    public String toString() {
        return name;
    }
}
