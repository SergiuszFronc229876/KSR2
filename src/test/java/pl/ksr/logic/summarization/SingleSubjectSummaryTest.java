package pl.ksr.logic.summarization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.ksr.logic.calculation.functions.TrapezoidalFunction;
import pl.ksr.logic.calculation.functions.TriangularFunction;
import pl.ksr.logic.calculation.sets.ContinuousSet;
import pl.ksr.logic.calculation.sets.DiscreteSet;
import pl.ksr.logic.calculation.sets.FuzzySet;
import pl.ksr.logic.model.CarDetails;
import pl.ksr.logic.summarization.forms.FirstFormSingleSubjectSummary;
import pl.ksr.logic.utils.CarDetailsReader;

import java.util.List;

public class SingleSubjectSummaryTest {

    private final List<CarDetails> carDetailsList = CarDetailsReader.readDataCsv();

    @Test
    public void measure_T1() {
        List<Double> elements = List.of(1500.0, 1100.0, 1900.0, 800.0, 1350.0, 1200.0, 1900.0, 2000.0, 1600.0, 1750.0, 2000.0, 1100.0);
        List<CarDetails> mockCarDetails = elements.stream().map(e -> new CarDetails(0, "", "", e, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList();

        TrapezoidalFunction atLeast1800Function = new TrapezoidalFunction(1800, 1800,3000, 3000);
        ContinuousSet salarySet = new ContinuousSet(0, 3000);
        FuzzySet atLeast1800SalaryFuzzySet = new FuzzySet(atLeast1800Function, salarySet);
        Label averageSalary = new Label("atLeast1800Salary", "Cena", atLeast1800SalaryFuzzySet);

        TrapezoidalFunction someQFunction = new TrapezoidalFunction(0.16, 0.2, 0.334, 0.4);
        ContinuousSet someQSet = new ContinuousSet(0, 1);
        FuzzySet someQFuzzySet = new FuzzySet(someQFunction, someQSet);
        RelativeQuantifier someQ = new RelativeQuantifier("some", someQFuzzySet);

        FirstFormSingleSubjectSummary summary = new FirstFormSingleSubjectSummary(null, someQ, List.of(averageSalary), mockCarDetails);
        double summary_T = summary.getDegreeOfTruth_T1();

        Assertions.assertEquals(1.0, summary_T);
    }

    @Test
    public void measure_T6_relativeQuantifier() {
        TriangularFunction functionQ1 = new TriangularFunction(0, 0.25, 0.5);
        TrapezoidalFunction functionQ2 = new TrapezoidalFunction(0, 0.125, 0.375, 0.5);

        FuzzySet fuzzySetQ1 = new FuzzySet(functionQ1, new ContinuousSet(0, 1));
        FuzzySet fuzzySetQ2 = new FuzzySet(functionQ2, new ContinuousSet(0, 1));

        RelativeQuantifier q1 = new RelativeQuantifier("Q1", fuzzySetQ1);
        RelativeQuantifier q2 = new RelativeQuantifier("Q2", fuzzySetQ2);

        FirstFormSingleSubjectSummary summaryQ1 = new FirstFormSingleSubjectSummary(null, q1, null, null);
        FirstFormSingleSubjectSummary summaryQ2 = new FirstFormSingleSubjectSummary(null, q2, null, null);

        double T6Q1 = summaryQ1.getDegreeOfQuantifierImprecision_T6();
        double T6Q2 = summaryQ2.getDegreeOfQuantifierImprecision_T6();

        Assertions.assertEquals(0.5, T6Q1);
        Assertions.assertEquals(0.5, T6Q2);
    }

    @Test
    public void measure_T7_relativeQuantifier() {
        TriangularFunction functionQ1 = new TriangularFunction(0, 0.25, 0.5);
        TrapezoidalFunction functionQ2 = new TrapezoidalFunction(0, 0.125, 0.375, 0.5);

        FuzzySet fuzzySetQ1 = new FuzzySet(functionQ1, new ContinuousSet(0, 1));
        FuzzySet fuzzySetQ2 = new FuzzySet(functionQ2, new ContinuousSet(0, 1));

        RelativeQuantifier q1 = new RelativeQuantifier("Q1", fuzzySetQ1);
        RelativeQuantifier q2 = new RelativeQuantifier("Q2", fuzzySetQ2);

        FirstFormSingleSubjectSummary summaryQ1 = new FirstFormSingleSubjectSummary(null, q1, null, null);
        FirstFormSingleSubjectSummary summaryQ2 = new FirstFormSingleSubjectSummary(null, q2, null, null);

        double T7Q1 = summaryQ1.getDegreeOfQuantifierCardinality_T7();
        double T7Q2 = summaryQ2.getDegreeOfQuantifierCardinality_T7();

        Assertions.assertEquals(0.75, T7Q1);
        Assertions.assertEquals(0.625, T7Q2);
    }
}
