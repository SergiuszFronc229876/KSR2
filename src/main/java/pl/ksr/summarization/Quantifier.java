package pl.ksr.summarization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.ksr.calculation.sets.FuzzySet;

@AllArgsConstructor
@Getter
public abstract class Quantifier {
    private String name;
    private FuzzySet fuzzySet;
}
