package pl.ksr.view.model;

public record SingleSubjectSummaryDTO(String textValue,
                                      double goodnessOfSummary,
                                      double degreeOfTruth_T1,
                                      double degreeOfImprecision_T2,
                                      double degreeOfCovering_T3,
                                      double degreeOfAppropriateness_T4,
                                      double degreeOfSummary_T5,
                                      double degreeOfQuantifierImprecision_T6,
                                      double degreeOfQuantifierCardinality_T7,
                                      double degreeOfSummarizerCardinality_T8,
                                      double degreeOfQualifierImprecision_T9,
                                      double degreeOfQualifierCardinality_T10,
                                      double lengthOfQualifier_T11) {
}
