package pl.ksr.summarization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.ksr.calculation.sets.FuzzySet;

@AllArgsConstructor
@Getter
public class Label {
    private String name;
    private String linguisticVariable;
    private FuzzySet fuzzySet;

    public double getMembership(Double x) {
        return fuzzySet.getMembershipDegree(x);
    }

}
