package pl.ksr.logic.summarization.forms;

import pl.ksr.logic.calculation.sets.FuzzySet;
import pl.ksr.logic.model.CarDetails;
import pl.ksr.logic.summarization.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SecondFormSingleSubjectSummary implements SingleSubjectSummary {
    private final MeasureWeights weights;
    private final Quantifier quantifier;
    private final List<Label> qualifiers;
    private final List<Label> summarizers;
    private final List<CarDetails> cars;

    public SecondFormSingleSubjectSummary(MeasureWeights weights, Quantifier quantifier, List<Label> qualifiers, List<Label> summarizers, List<CarDetails> cars) {
        this.weights = weights;
        this.quantifier = quantifier;
        this.qualifiers = qualifiers;
        this.summarizers = summarizers;
        this.cars = cars;
    }

    @Override
    public double calculateQuality() {
        Map<String, Double> measures = calculateMeasures();
        return measures.entrySet().stream()
                .mapToDouble(e -> e.getValue() * weights.getWeights().get(e.getKey()))
                .sum();
    }

    public double getDegreeOfTruth_T1() {
        double r = 0.0;
        double m = 0.0;

        for (CarDetails c : cars) {
            double intersectedQualifiers = and(qualifiers, c);
            r += Math.min(and(summarizers, c), intersectedQualifiers);
            m += intersectedQualifiers;
        }

        if (quantifier.getClass().equals(AbsoluteQuantifier.class)) {
            m = 1;
        }
        return quantifier.getFuzzySet().getMembershipDegree(r / m);
    }

    public double getDegreeOfImprecision_T2() {
        double multiply = 1.0;
        for (Label summarizer : summarizers) {
            multiply = multiply * summarizer.getFuzzySet().getDegreeOfFuzziness();
        }
        double res = Math.pow(multiply, 1 / (double) summarizers.size());
        return 1.0 - res;
    }

    public double getDegreeOfCovering_T3() {
        int t = 0;
        int h = 0;
        for (CarDetails c : cars) {
            double intersectedQualifier = and(qualifiers, c);
            double intersectedSummarizer = and(summarizers, c);
            if (intersectedQualifier > 0) {
                h++;
            }
            if (Math.min(intersectedQualifier, intersectedSummarizer) > 0) {
                t++;
            }
        }
        if (h == 0 && t == 0) {
            return 0;
        }
        return (double) t / h;
    }

    public double getDegreeOfAppropriateness_T4() {
        double multiply = 1.0;
        for (Label summarizer : summarizers) {
            double r = 0.0;
            for (CarDetails c : cars) {
                if (summarizer.getFuzzySet().getMembershipDegree(fieldForLabel(summarizer, c)) > 0) {
                    r++;
                }
            }
            multiply *= (r / cars.size());
        }
        return Math.abs(multiply - getDegreeOfCovering_T3());
    }

    public double getDegreeOfSummary_T5() {
        return 2.0 * Math.pow(0.5, summarizers.size());
    }

    public double getDegreeOfQuantifierImprecision_T6() {
        FuzzySet fs = quantifier.getFuzzySet();
        double measure = fs.getSupport().getSize();
        if (quantifier.getClass().equals(AbsoluteQuantifier.class)) {
            return 1.0 - (measure / cars.size());
        } else {
            return 1.0 - measure;
        }
    }

    public double getDegreeOfQuantifierCardinality_T7() {
        FuzzySet fs = quantifier.getFuzzySet();
        double measure = quantifier.getCardinality();
        if (quantifier.getClass().equals(AbsoluteQuantifier.class)) {
            return 1.0 - (measure / cars.size());
        } else {
            return 1.0 - measure;
        }
    }

    public double getDegreeOfSummarizerCardinality_T8() {
        double multiply = 1.0;
        for (Label summarizer : summarizers) {
            multiply = multiply * (summarizer.getFuzzySet().getCardinality(cars.stream()
                    .map(c -> fieldForLabel(summarizer, c)).toList()) / summarizer.getFuzzySet().getUniverseOfDiscourse().getSize());
        }
        multiply = Math.pow(multiply, (double) 1 / summarizers.size());
        return 1.0 - multiply;
    }

    public double getDegreeOfQualifierImprecision_T9() {
        double multiply = 1.0;
        if (qualifiers != null) {
            for (Label qualifier : qualifiers) {
                multiply = multiply * qualifier.getFuzzySet().getDegreeOfFuzziness();
            }
        }
        double res = Math.pow(multiply, (double) 1 / (qualifiers != null ? qualifiers.size() : 1));
        return 1.0 - res;
    }

    public double getDegreeOfQualifierCardinality_T10() {
        double multiply = 1;
        if (qualifiers != null) {
            for (Label qualifier : qualifiers) {
                multiply = multiply * (qualifier.getFuzzySet().getCardinality(cars.stream()
                        .map(c -> fieldForLabel(qualifier, c)).toList()) / qualifier.getFuzzySet().getUniverseOfDiscourse().getSize());
            }
        }

        multiply = Math.pow(multiply, (double) 1 / (qualifiers != null ? qualifiers.size() : 1));
        return 1.0 - multiply;

    }

    public double getLengthOfQualifier_T11() {
        return 2.0 * Math.pow(0.5, qualifiers.size());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(quantifier.getName().toUpperCase(Locale.ROOT)).append(" samochodów będącyh/mających ");
        for (int i = 0; i < qualifiers.size(); i++) {
            Label qualifier = qualifiers.get(i);
            sb.append(qualifier.getName().toUpperCase(Locale.ROOT)).append(" ")
                    .append(qualifier.getLinguisticVariableName().toLowerCase(Locale.ROOT));
            if (i + 1 < qualifiers.size()) {
                sb.append(" i ");
            }
        }
        sb.append(" ma ");
        for (int i = 0; i < summarizers.size(); i++) {
            Label summarizer = summarizers.get(i);
            sb.append(summarizer.getName().toUpperCase(Locale.ROOT)).append(" ")
                    .append(summarizer.getLinguisticVariableName().toLowerCase(Locale.ROOT));
            if (i + 1 < summarizers.size()) {
                sb.append(" i ");
            }
        }
        return sb.toString();
    }
}
