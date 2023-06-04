package pl.ksr.logic.summarization;

import pl.ksr.logic.model.CarDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SingleSubjectSummary {
    default Map<String, Double> calculateMeasures() {
        Map<String, Double> measures = new HashMap<>();
        measures.put("T1", getDegreeOfTruth_T1());
        measures.put("T2", getDegreeOfImprecision_T2());
        measures.put("T3", getDegreeOfCovering_T3());
        measures.put("T4", getDegreeOfAppropriateness_T4());
        measures.put("T5", getDegreeOfSummary_T5());
        measures.put("T6", getDegreeOfQuantifierImprecision_T6());
        measures.put("T7", getDegreeOfQuantifierCardinality_T7());
        measures.put("T8", getDegreeOfSummarizerCardinality_T8());
        measures.put("T9", getDegreeOfQualifierImprecision_T9());
        measures.put("T10", getDegreeOfQualifierCardinality_T10());
        measures.put("T11", getLengthOfQualifier_T11());
        return measures;
    }
    double getGoodnessOfSummary();

    double getDegreeOfTruth_T1();

    double getDegreeOfImprecision_T2();

    double getDegreeOfCovering_T3();

    double getDegreeOfAppropriateness_T4();

    double getDegreeOfSummary_T5();

    double getDegreeOfQuantifierImprecision_T6();

    double getDegreeOfQuantifierCardinality_T7();

    double getDegreeOfSummarizerCardinality_T8();

    double getDegreeOfQualifierImprecision_T9();

    double getDegreeOfQualifierCardinality_T10();

    double getLengthOfQualifier_T11();

    default double fieldForLabel(Label l, CarDetails c) {
        return switch (l.getLinguisticVariableName()) {
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

    default double and(List<Label> labels, CarDetails car) {
        if (labels == null) {
            return 1;
        }
        double min = 1.0;
        for (Label label : labels) {
            double degreeOfMembership = label.getFuzzySet().getMembershipDegree(fieldForLabel(label, car));
            if (degreeOfMembership < min) {
                min = degreeOfMembership;
            }
        }
        return min;
    }
    String toString();
}
