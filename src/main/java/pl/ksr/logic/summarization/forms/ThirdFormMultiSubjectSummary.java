package pl.ksr.logic.summarization.forms;

import pl.ksr.logic.model.CarDetails;
import pl.ksr.logic.summarization.Label;
import pl.ksr.logic.summarization.MultiSubjectSummary;
import pl.ksr.logic.summarization.Quantifier;

import java.util.List;
import java.util.Locale;

public class ThirdFormMultiSubjectSummary implements MultiSubjectSummary {
    private final Quantifier quantifier;
    private final List<Label> summarizers;
    private final List<Label> qualifiers;
    private final List<CarDetails> carsForSubject1;
    private final List<CarDetails> carsForSubject2;

    public ThirdFormMultiSubjectSummary(Quantifier quantifier, List<Label> summarizers, List<Label> qualifiers, List<CarDetails> carsForSubject1, List<CarDetails> carsForSubject2) {
        this.quantifier = quantifier;
        this.summarizers = summarizers;
        this.qualifiers = qualifiers;
        this.carsForSubject1 = carsForSubject1;
        this.carsForSubject2 = carsForSubject2;
    }

    @Override
    public double getDegreeOfTruth_T1() {
        double nfSigmaFirstSubjIntersectionOfSummarizersWithQuaLifiers = Math.min(nfSigmaCount(carsForSubject1, summarizers), nfSigmaCount(carsForSubject1, qualifiers));
        double nfSigmaFirstSubjSumarizers = nfSigmaCount(carsForSubject2, summarizers);
        double m1 = carsForSubject1.size();
        double m2 = carsForSubject2.size();

        return quantifier.getFuzzySet().getMembershipDegree((nfSigmaFirstSubjIntersectionOfSummarizersWithQuaLifiers / m1) / ((nfSigmaFirstSubjIntersectionOfSummarizersWithQuaLifiers / m1) + (nfSigmaFirstSubjSumarizers / m2)));
    }

    @Override
    public String toString() {
        String subjectName1 = subjectName(carsForSubject1.get(0).isNew());
        String subjectName2 = subjectName(carsForSubject2.get(0).isNew());
        StringBuilder sb = new StringBuilder();
        sb.append(quantifier.getName().toUpperCase(Locale.ROOT))
                .append(" samochodów " + subjectName1)
                .append(" które są/mają ");
        for (int i = 0; i < qualifiers.size(); i++) {
            Label qualifier = qualifiers.get(i);
            sb.append(qualifier.getName().toUpperCase(Locale.ROOT)).append(" ").append(qualifier.getLinguisticVariableName().toLowerCase(Locale.ROOT));
            if (i + 1 < qualifiers.size()) {
                sb.append(" i ");
            }
        }
        sb.append(" w porównaniu do tych ").append(" samochodów z silnikiem " + subjectName2);

        sb.append(" jest/ma ");
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