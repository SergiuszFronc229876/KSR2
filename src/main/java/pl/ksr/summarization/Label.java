package pl.ksr.summarization;

import lombok.AllArgsConstructor;
import pl.ksr.calculation.sets.FuzzySet;

@AllArgsConstructor
public class Label {
    private String name;
    private FuzzySet fuzzySet;
    private LinguisticVariable linguisticVariable;

    public double getMembership(Double x) {
        return fuzzySet.getMembershipDegree(x);
    }
}
