package pl.ksr.logic.summarization.forms;

import pl.ksr.logic.model.CarDetails;
import pl.ksr.logic.summarization.Label;
import pl.ksr.logic.summarization.MultiSubjectSummary;
import pl.ksr.logic.summarization.Quantifier;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class FourthFormMultiSubjectSummary implements MultiSubjectSummary {
    private final List<Label> summarizers;
    private final List<CarDetails> carsForSubject1;
    private final List<CarDetails> carsForSubject2;

    public FourthFormMultiSubjectSummary(List<Label> summarizers, List<CarDetails> carsForSubject1, List<CarDetails> carsForSubject2) {
        this.summarizers = summarizers;
        this.carsForSubject1 = carsForSubject1;
        this.carsForSubject2 = carsForSubject2;
    }

    @Override
    public double getDegreeOfTruth_T1() {
        double implication = 0.0;
        // A zawiera B + B zawiera A: logic 1,1
        // A zawiera B + B nie zawiera A: logic 1,0
        // A nie zawiera B + B  zawiera A: logic 0,1

        List<CarDetails> all = Stream.concat(carsForSubject1.stream(), carsForSubject2.stream()).toList();
        for (CarDetails c : all) {
            double memberShip = and(summarizers, c);
            if (carsForSubject1.contains(c) && carsForSubject2.contains(c)) {
                implication += lukasiewiczImplication(memberShip, memberShip);
            } else if (carsForSubject2.contains(c) && !carsForSubject1.contains(c)) {
                implication += lukasiewiczImplication(memberShip, 0);
            } else if (!carsForSubject2.contains(c) && carsForSubject1.contains(c)) {
                implication += lukasiewiczImplication(0, memberShip);
            }

        }
        return 1 - (implication / all.size());
    }

    private double lukasiewiczImplication(double a, double b) {
        return Math.min(1, 1 - a + b);
    }

    @Override
    public String toString() {
        String subjectName1 = carsForSubject1.get(0).getFuelType();
        String subjectName2 = carsForSubject2.get(0).getFuelType();
        StringBuilder sb = new StringBuilder();
        sb.append("Więcej ")
                .append(" samochodów z silnikiem ")
                .append(subjectName1)
                .append(" niż ")
                .append(" samochodów z silnikiem ")
                .append(subjectName2)
                .append(" jest/ma ");
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