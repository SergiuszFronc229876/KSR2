package pl.ksr.view.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import pl.ksr.logic.calculation.functions.GaussianFunction;
import pl.ksr.logic.calculation.functions.MembershipFunction;
import pl.ksr.logic.calculation.functions.TrapezoidalFunction;
import pl.ksr.logic.calculation.functions.TriangularFunction;
import pl.ksr.logic.calculation.sets.ContinuousSet;
import pl.ksr.logic.calculation.sets.FuzzySet;
import pl.ksr.logic.summarization.Label;
import pl.ksr.logic.summarization.LinguisticVariable;
import pl.ksr.view.Data;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class AdvancedViewController implements Initializable {

    @FXML
    private ComboBox<String> labelTypeCB;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        membershipFunctionPane.setVisible(false);
        dText.setVisible(false);
        dTF.setVisible(false);
        labelTypeCB.getItems().addAll(List.of("Kwantyfikator", "Zmienna lingwistyczna"));
        functionCB.getItems().addAll(List.of("Trójkątna", "Trapezoidalna", "Gaussa"));
    }

    @FXML
    private void labelTypeCB_onAction() {
        optionCB.getItems().clear();
        switch (labelTypeCB.getValue()) {
            case "Kwantyfikator" -> optionCB.getItems().addAll(List.of("absolutny", "względny"));
            case "Zmienna lingwistyczna" ->
                    optionCB.getItems().addAll(Data.linguisticVariables.stream().map(LinguisticVariable::getName).toList());
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
            case "Zmienna lingwistyczna" -> {
                LinguisticVariable variable = Data.linguisticVariables.stream()
                        .filter(var -> var.getName().equals(optionCB.getValue()))
                        .findFirst()
                        .orElseThrow();

                ContinuousSet tempSet = ((ContinuousSet) variable.getLabels().get(0).getFuzzySet().getUniverseOfDiscourse());
                FuzzySet fuzzySet = new FuzzySet(function, new ContinuousSet(tempSet.getBeginOfUniverse(), tempSet.getEndOfUniverse()));
                Label label = new Label(labelNameTF.getText(), variable.getName(), fuzzySet);

                variable.getLabels().add(label);
            }
        }
    }
}
