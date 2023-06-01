package pl.ksr.logic.summarization.forms;

import pl.ksr.logic.calculation.sets.FuzzySet;
import pl.ksr.logic.model.CarDetails;
import pl.ksr.logic.summarization.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FirstFormSingleSubjectSummary implements SingleSubjectSummary {
    private MeasureWeights weights;
    private Quantifier quantifier;
    private List<Label> summarizers;
    private List<CarDetails> cars;

    public FirstFormSingleSubjectSummary(MeasureWeights weights, Quantifier quantifier, List<Label> summarizers, List<CarDetails> cars) {
        this.weights = weights;
        this.quantifier = quantifier;
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

    @Override
    public double getDegreeOfTruth_T1() {
        double r = 0.0;
        double m = 0.0;
        for (CarDetails c : cars) {
            r += and(summarizers, c);
        }
        if (quantifier.getClass().equals(AbsoluteQuantifier.class)) {
            m = 1;
        } else m = cars.size();
        return quantifier.getFuzzySet().getMembershipDegree(r / m);
    }

    @Override
    public double getDegreeOfImprecision_T2() {
        double multiply = 1.0;
        for (Label summarizer : summarizers) {
            multiply = multiply * summarizer.getFuzzySet().getDegreeOfFuzziness();
        }
        double res = Math.pow(multiply, 1 / (double) summarizers.size());
        return 1.0 - res;
    }

    @Override
    public double getDegreeOfCovering_T3() {
        int t = 0;
        for (CarDetails c : cars) {
            double intersectedSummarizer = and(summarizers, c);
            if (intersectedSummarizer > 0) {
                t++;
            }
        }
        if (t == 0) {
            return 0;
        }
        return (double) t / cars.size();
    }

    @Override
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

    @Override
    public double getDegreeOfSummary_T5() {
        return 2.0 * Math.pow(0.5, summarizers.size());
    }

    @Override
    public double getDegreeOfQuantifierImprecision_T6() {
        FuzzySet fs = quantifier.getFuzzySet();
        double measure = fs.getSupport().getSize();
        if (quantifier.getClass().equals(AbsoluteQuantifier.class)) {
            return 1.0 - (measure / cars.size());
        } else {
            return 1.0 - measure;
        }
    }

    @Override
    public double getDegreeOfQuantifierCardinality_T7() {
        FuzzySet fs = quantifier.getFuzzySet();
        double measure = fs.getCardinality();
        if (quantifier.getClass().equals(AbsoluteQuantifier.class)) {
            return 1.0 - (measure / cars.size());
        } else {
            return 1.0 - measure;
        }
    }

    @Override
    public double getDegreeOfOfSummarizerCardinality_T8() {
        double multiply = 1.0;
        for (Label summarizer : summarizers) {
            multiply = multiply * (summarizer.getFuzzySet().getCardinality() / summarizer.getFuzzySet().getUniverseOfDiscourse().getSize());
        }
        multiply = Math.pow(multiply, (double) 1 / summarizers.size());
        return 1.0 - multiply;
    }

    @Override
    public double getDegreeOfQualifierImprecision_T9() {
        return 0.0;
    }

    @Override
    public double getDegreeOfQualifierCardinality_T10() {
        return 0.0;
    }

    @Override
    public double getLengthOfQualifier_T11() {
        return 0.0;
    }

    @Override
    public String printSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append(quantifier.getName().toUpperCase(Locale.ROOT)).append(" samochodów jest/ma ");
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
