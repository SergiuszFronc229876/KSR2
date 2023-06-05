package pl.ksr.logic.summarization;

import pl.ksr.logic.model.CarDetails;

import java.util.List;

public interface MultiSubjectSummary {
    double getDegreeOfTruth_T1();

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

    default double nfSigmaCount(List<CarDetails> cars, List<Label> labels) {
        double result = 0.0;
        for (CarDetails car : cars) {
            result += and(labels, car);
        }
        return result;
    }

    String toString();
}
