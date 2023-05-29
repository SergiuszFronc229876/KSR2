package pl.ksr.logic.summarization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.ksr.logic.calculation.sets.FuzzySet;

@AllArgsConstructor
@Getter
public class Label {
    private String name;
    private String linguisticVariableName;
    private FuzzySet fuzzySet;

    public double getMembership(Double x) {
        return fuzzySet.getMembershipDegree(x);
    }

}
