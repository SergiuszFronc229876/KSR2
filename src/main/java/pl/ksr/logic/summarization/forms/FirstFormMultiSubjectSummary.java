package pl.ksr.logic.summarization.forms;

import pl.ksr.logic.model.CarDetails;
import pl.ksr.logic.summarization.Label;
import pl.ksr.logic.summarization.MultiSubjectSummary;
import pl.ksr.logic.summarization.Quantifier;

import java.util.List;
import java.util.Locale;

public class FirstFormMultiSubjectSummary implements MultiSubjectSummary {
    private final Quantifier quantifier;
    private final List<Label> summarizers;
    private final List<CarDetails> carsForSubject1;
    private final List<CarDetails> carsForSubject2;

    public FirstFormMultiSubjectSummary(Quantifier quantifier, List<Label> summarizers, List<CarDetails> carsForSubject1, List<CarDetails> carsForSubject2) {
        this.quantifier = quantifier;
        this.summarizers = summarizers;
        this.carsForSubject1 = carsForSubject1;
        this.carsForSubject2 = carsForSubject2;
    }

    @Override
    public double getDegreeOfTruth_T1() {
        double nfSigmaFirstSubjSummarizers = nfSigmaCount(carsForSubject1, summarizers);
        double nfSigmaSecondSubjSummarizers = nfSigmaCount(carsForSubject2, summarizers);
        double m1 = carsForSubject1.size();
        double m2 = carsForSubject2.size();

        return quantifier.getFuzzySet().getMembershipDegree((nfSigmaFirstSubjSummarizers / m1) / ((nfSigmaFirstSubjSummarizers / m1) + (nfSigmaSecondSubjSummarizers / m2)));
    }

    @Override
    public String printSummary() {
        //TODO SUBJECTS NAMES
        String subjectName1 = "s1";
        String subjectName2 = "s2";
        StringBuilder sb = new StringBuilder();
        sb.append(quantifier.getName().toUpperCase(Locale.ROOT))
                .append(subjectName1)
                .append(" w porównaniu do ").append(subjectName2)
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
