package pl.ksr.view.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import pl.ksr.logic.calculation.functions.GaussianFunction;
import pl.ksr.logic.calculation.functions.MembershipFunction;
import pl.ksr.logic.calculation.functions.TrapezoidalFunction;
import pl.ksr.logic.calculation.functions.TriangularFunction;
import pl.ksr.logic.calculation.sets.ContinuousSet;
import pl.ksr.logic.calculation.sets.FuzzySet;
import pl.ksr.logic.summarization.AbsoluteQuantifier;
import pl.ksr.logic.summarization.Label;
import pl.ksr.logic.summarization.LinguisticVariable;
import pl.ksr.logic.summarization.RelativeQuantifier;
import pl.ksr.view.Data;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class AdvancedViewController implements Initializable {
    @FXML
    private ComboBox<String> labelTypeCB;
    @FXML
    private Text objectTypeText;
    @FXML
    private ComboBox<String> optionCB;
    @FXML
    private ComboBox<String> functionCB;
    @FXML
    private AnchorPane membershipFunctionPane;
    @FXML
    private TextField labelNameTF;
    @FXML
    private Text aText;
    @FXML
    private Text bText;
    @FXML
    private Text cText;
    @FXML
    private Text dText;
    @FXML
    private TextField aTF;
    @FXML
    private TextField bTF;
    @FXML
    private TextField cTF;
    @FXML
    private TextField dTF;
    @FXML
    private StackPane stackPaneChart;

    private LineChart<Number, Number> lineChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        membershipFunctionPane.setVisible(false);
        dText.setVisible(false);
        dTF.setVisible(false);
        labelTypeCB.getItems().addAll(List.of("Kwantyfikator", "Etykieta zmiennej"));
        functionCB.getItems().addAll(List.of("Trójkątna", "Trapezoidalna", "Gaussa"));
    }

    @FXML
    private void labelTypeCB_onAction() {
        optionCB.getItems().clear();
        switch (labelTypeCB.getValue()) {
            case "Kwantyfikator" -> {
                objectTypeText.setText("Typ kwantyfikatora");
                optionCB.getItems().addAll(List.of("Absolutny", "Względny"));
            }
            case "Etykieta zmiennej" -> {
                objectTypeText.setText("Zmienna lingwistyczna");
                optionCB.getItems().addAll(Data.linguisticVariables.stream().map(LinguisticVariable::getName).toList());
            }
        }
    }

    @FXML
    private void functionCB_onAction() {
        switch (functionCB.getValue()) {
            case "Trójkątna" -> {
                aText.setText("lewy dolny róg");
                bText.setText("górny róg");
                cText.setText("prawy dolny róg");
                dText.setVisible(false);
                dTF.setVisible(false);
            }
            case "Trapezoidalna" -> {
                aText.setText("lewy dolny róg");
                bText.setText("lewy górny róg");
                cText.setText("prawy górny róg");
                dText.setText("prawy dolny róg");
                dText.setVisible(true);
                dTF.setVisible(true);
            }
            case "Gaussa" -> {
                aText.setText("środek");
                bText.setText("odchylenie standardowe");
                cText.setText("lewy limit");
                dText.setText("prawy limit");
                dText.setVisible(true);
                dTF.setVisible(true);
            }
        }
        membershipFunctionPane.setVisible(true);
    }

    @FXML
    private void addNewLabel_onAction() {
        MembershipFunction function = switch (functionCB.getValue()) {
            case "Trójkątna" ->
                    new TriangularFunction(Double.parseDouble(aTF.getText()), Double.parseDouble(bTF.getText()), Double.parseDouble(cTF.getText()));
            case "Trapezoidalna" ->
                    new TrapezoidalFunction(Double.parseDouble(aTF.getText()), Double.parseDouble(bTF.getText()), Double.parseDouble(cTF.getText()), Double.parseDouble(dTF.getText()));
            case "Gaussa" ->
                    new GaussianFunction(Double.parseDouble(aTF.getText()), Double.parseDouble(bTF.getText()), Double.parseDouble(cTF.getText()), Double.parseDouble(dTF.getText()));
            default -> throw new IllegalStateException("Unexpected value: " + functionCB.getValue());
        };

        switch (labelTypeCB.getValue()) {
            case "Etykieta zmiennej" -> {
                LinguisticVariable variable = Data.linguisticVariables.stream()
                        .filter(var -> var.getName().equals(optionCB.getValue()))
                        .findFirst()
                        .orElseThrow();

                ContinuousSet tempSet = ((ContinuousSet) variable.getLabels().get(0).getFuzzySet().getUniverseOfDiscourse());
                FuzzySet fuzzySet = new FuzzySet(function, new ContinuousSet(tempSet.getBeginOfUniverse(), tempSet.getEndOfUniverse()));
                Label label = new Label(labelNameTF.getText(), variable.getName(), fuzzySet);

                variable.getLabels().add(label);
            }
            case "Kwantyfikator" -> {
                if (optionCB.getValue().equals("Absolutny")) {
                    ContinuousSet tempSet = (ContinuousSet) Data.absoluteQuantifiers.get(0).getFuzzySet().getUniverseOfDiscourse();
                    FuzzySet fuzzySet = new FuzzySet(function, new ContinuousSet(tempSet.getBeginOfUniverse(), tempSet.getEndOfUniverse()));
                    AbsoluteQuantifier quantifier = new AbsoluteQuantifier(labelNameTF.getText(), fuzzySet);
                    Data.absoluteQuantifiers.add(quantifier);
                } else {
                    ContinuousSet tempSet = (ContinuousSet) Data.relativeQuantifiers.get(0).getFuzzySet().getUniverseOfDiscourse();
                    FuzzySet fuzzySet = new FuzzySet(function, new ContinuousSet(tempSet.getBeginOfUniverse(), tempSet.getEndOfUniverse()));
                    RelativeQuantifier quantifier = new RelativeQuantifier(labelNameTF.getText(), fuzzySet);
                    Data.relativeQuantifiers.add(quantifier);
                }
            }
        }
        showChart();
        showInfoAlert(labelNameTF.getText() + " został dodany");
    }

    private void showInfoAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }

    public void showChart() {
        if (lineChart != null) {
            stackPaneChart.getChildren().clear();
        }

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        lineChart = new LineChart<>(xAxis, yAxis);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        if (optionCB.getValue().equals("Absolutny")) {
            xAxis.setLowerBound(0);
            xAxis.setUpperBound(Data.carDetailsList.size());
        } else if (optionCB.getValue().equals("Względny")) {
            xAxis.setLowerBound(0);
            xAxis.setUpperBound(1);
        } else {
            LinguisticVariable variable = Data.linguisticVariables.stream()
                    .filter(var -> var.getName().equals(optionCB.getValue()))
                    .findFirst()
                    .orElseThrow();

            ContinuousSet tempSet = ((ContinuousSet) variable.getLabels().get(0).getFuzzySet().getUniverseOfDiscourse());
            xAxis.setLowerBound(tempSet.getBeginOfUniverse());
            xAxis.setUpperBound(tempSet.getEndOfUniverse());
        }

        lineChart.setTitle("Wykres funkcji przynależności");
        xAxis.setLabel(labelNameTF.getText());
        xAxis.setAutoRanging(false);
        series.setName("Funkcja przynależności");

        double a, b, c;
        a = Double.parseDouble(aTF.getText());
        b = Double.parseDouble(bTF.getText());
        c = Double.parseDouble(cTF.getText());

        switch (functionCB.getValue()) {
            case "Trójkątna" -> {
                series.getData().add(new XYChart.Data<>(a, 0));
                series.getData().add(new XYChart.Data<>(b, 1));
                series.getData().add(new XYChart.Data<>(c, 0));
            }
            case "Trapezoidalna" -> {
                double d = Double.parseDouble(dTF.getText());
                series.getData().add(new XYChart.Data<>(a, 0));
                series.getData().add(new XYChart.Data<>(b, 1));
                series.getData().add(new XYChart.Data<>(c, 1));
                series.getData().add(new XYChart.Data<>(d, 0));
            }
            case "Gaussa" -> {
                double d = Double.parseDouble(dTF.getText());
                lineChart.setCreateSymbols(false);
                double x = c;
                double step = (d-c) / 100.0;
                GaussianFunction function = new GaussianFunction(a, b, c, d);
                while (x <= d) {
                    double val = function.getValue(x);
                    series.getData().add(new XYChart.Data<>(x, val));
                    x += step;
                }
            }
        }
        lineChart.getData().add(series);
        stackPaneChart.getChildren().add(lineChart);
        StackPane.setAlignment(lineChart, Pos.CENTER);
    }

    @FXML
    private void generateNewLabel_onAction() {
        showChart();
    }
}
