package pl.ksr.logic.summarization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.ksr.logic.calculation.sets.ClassicSet;
import pl.ksr.logic.calculation.sets.DiscreteSet;
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

    public double getCardinality() {
        ClassicSet universeOfDiscourse = fuzzySet.getUniverseOfDiscourse();
        if (universeOfDiscourse instanceof DiscreteSet) {
            return ((DiscreteSet) universeOfDiscourse).getElements()
                    .stream()
                    .mapToDouble(fuzzySet::getMembershipDegree)
                    .sum();
        } else {
            return fuzzySet.getMembershipFunction().getAreaFunction();
        }
    }
}
