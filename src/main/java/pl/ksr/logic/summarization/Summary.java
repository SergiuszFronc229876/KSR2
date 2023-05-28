package pl.ksr.logic.summarization;

import lombok.AllArgsConstructor;
import pl.ksr.logic.calculation.sets.FuzzySet;
import pl.ksr.logic.database.CarDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class Summary {
    private MeasureWeights weights;
    private List<Label> qualifiers;
    private Quantifier quantifier;
    private List<Label> summarizers;
    private final List<CarDetails> cars;
    private final List<CarDetails> cars2;
    private boolean isFirstForm;

    public Map<String, Double> calculateMeasures() {
        Map<String, Double> measures = new HashMap<>();
        measures.put("T1", getDegreeOfTruth_T1());
        measures.put("T2", getDegreeOfImprecision_T2());
        measures.put("T3", getDegreeOfCovering_T3());
        measures.put("T4", getDegreeOfAppropriateness_T4());
        measures.put("T5", getDegreeOfSummary_T5());
        measures.put("T6", getDegreeOfQuantifierImprecision_T6());
        measures.put("T7", getDegreeOfQuantifierCardinality_T7());
        measures.put("T8", getDegreeOfOfSummarizerCardinality_T8());
        measures.put("T9", getDegreeOfQualifierImprecision_T9());
        measures.put("T10", getDegreeOfQualifierCardinality_T10());
        measures.put("T11", getLengthOfQualifier_T11());
        return measures;
    }

    public double calculateQuality() {
        Map<String, Double> measures = calculateMeasures();
        return measures.entrySet().stream()
                .mapToDouble(e -> e.getValue() * weights.getWeights().get(e.getKey()))
                .sum();
    }

    public double getDegreeOfTruth_T1() {
        double r = 0.0;
        double m = 0.0;
        if (isFirstForm) {
            for (CarDetails c : cars) {
                r += and(summarizers, c);
            }
        } else {
            for (CarDetails c : cars) {
                double intersectedQualifiers = and(qualifiers, c);
                r += Math.min(and(summarizers, c), intersectedQualifiers);
                m += intersectedQualifiers;
            }
        }
        if (quantifier.getClass().equals(AbsoluteQuantifier.class)) {
            m = 1;
        } else if (isFirstForm) {
            m = cars.size();
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
        double measure = fs.getCardinality();
        if (quantifier.getClass().equals(AbsoluteQuantifier.class)) {
            return 1.0 - (measure / cars.size());
        } else {
            return 1.0 - measure;
        }
    }

    public double getDegreeOfOfSummarizerCardinality_T8() {
        double multiply = 1.0;
        for (Label summarizer : summarizers) {
            multiply = multiply * (summarizer.getFuzzySet().getCardinality() / summarizer.getFuzzySet().getUniverseOfDiscourse().getSize());
        }
        multiply = Math.pow(multiply, (double) 1 / summarizers.size());
        return 1.0 - multiply;
    }

    public double getDegreeOfQualifierImprecision_T9() {
        double multiply = 1.0;
        for (Label qualifier : qualifiers) {
            multiply = multiply * qualifier.getFuzzySet().getDegreeOfFuzziness();
        }
        if (!isFirstForm) {
            double res = Math.pow(multiply, (double) 1 / qualifiers.size());
            return 1.0 - res;
        } else {
            return 0.0;
        }
    }

    public double getDegreeOfQualifierCardinality_T10() {
        double multiply = 1;
        for (Label qualifier : qualifiers) {
            multiply = multiply * (qualifier.getFuzzySet().getCardinality() / qualifier.getFuzzySet().getUniverseOfDiscourse().getSize());

        }
        if (!isFirstForm) {
            multiply = Math.pow(multiply, (double) 1 / qualifiers.size());
            return 1.0 - multiply;
        } else {
            return 0.0;
        }
    }

    public double getLengthOfQualifier_T11() {
        return 2.0 * Math.pow(0.5, isFirstForm ? 1 : qualifiers.size());
    }

    private double and(List<Label> labels, CarDetails car) {
        double min = 1.0;
        for (Label label : labels) {
            double degreeOfMembership = label.getFuzzySet().getMembershipDegree(fieldForLabel(label, car));
            if (degreeOfMembership < min) {
                min = degreeOfMembership;
            }
        }
        return min;
    }

    private double fieldForLabel(Label l, CarDetails c) {
        return switch (l.getLinguisticVariable()) {
            case "Cena" -> c.getPrice();
            case "Przebieg" -> c.getMileage();
            case "Moc silnika" -> c.getHorsepower();
            case "Zużycie paliwa na 100 km" -> c.getFuelEconomy();
            case "Pojemność silnika" -> c.getEngineDisplacement();
            case "Długość" -> c.getLength();
            case "Pojemność zbiornika paliwa" -> c.getFuelTankVolume();
            case "Rozstaw osi" -> c.getWheelbase();
            case "Moment obrotowy" -> c.getTorque();
            case "Szerokość" -> c.getWidth();
            default -> throw new IllegalStateException(String.format("Wrong label name: %s", l.getName()));
        };
    }
}