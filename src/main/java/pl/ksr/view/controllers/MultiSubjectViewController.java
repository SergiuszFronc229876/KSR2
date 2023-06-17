package pl.ksr.view.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.ksr.logic.model.CarDetails;
import pl.ksr.logic.summarization.Label;
import pl.ksr.logic.summarization.*;
import pl.ksr.logic.summarization.forms.FirstFormMultiSubjectSummary;
import pl.ksr.logic.summarization.forms.FourthFormMultiSubjectSummary;
import pl.ksr.logic.summarization.forms.SecondFormMultiSubjectSummary;
import pl.ksr.logic.summarization.forms.ThirdFormMultiSubjectSummary;
import pl.ksr.view.Data;
import pl.ksr.view.FuelType;

import java.net.URL;
import java.util.*;

import static pl.ksr.view.FuelType.DIESEL;
import static pl.ksr.view.FuelType.GASOLINE;

public class MultiSubjectViewController implements Initializable {
    private final List<MultiSubjectSummary> summaries = new ArrayList<>();
    @FXML
    private ComboBox<FuelType> firstSubject_CB;
    @FXML
    private ComboBox<FuelType> secondSubject_CB;
    @FXML
    private TreeView<String> summarizersTreeView;
    @FXML
    private TreeView<String> qualifiersTreeView;
    @FXML
    private TableView<MultiSubjectSummary> summaryTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initSummaryTableColumns();
        fillQualifiersTreeView();
        fillSummarizersTreeView();

        firstSubject_CB.getItems().addAll(List.of(GASOLINE, DIESEL));
        secondSubject_CB.getItems().addAll(List.of(GASOLINE, DIESEL));

        firstSubject_CB.setOnAction(event -> {
            FuelType selectedValue = firstSubject_CB.getValue();
            if (selectedValue.equals(DIESEL)) {
                secondSubject_CB.setValue(GASOLINE);
            } else if (selectedValue.equals(GASOLINE)) {
                secondSubject_CB.setValue(DIESEL);
            }
        });
        secondSubject_CB.setOnAction(event -> {
            FuelType selectedValue = secondSubject_CB.getValue();
            if (selectedValue.equals(DIESEL)) {
                firstSubject_CB.setValue(GASOLINE);
            } else if (selectedValue.equals(GASOLINE)) {
                firstSubject_CB.setValue(DIESEL);
            }
        });
    }

    private void fillQualifiersTreeView() {
        CheckBoxTreeItem<String> root = new CheckBoxTreeItem<>("Kwalifikatory");
        root.setExpanded(true);
        for (LinguisticVariable var : Data.linguisticVariables) {
            CheckBoxTreeItem<String> variableTreeItem = new CheckBoxTreeItem<>(var.getName());
            variableTreeItem.setExpanded(true);
            for (Label label : var.getLabels()) {
                CheckBoxTreeItem<String> labelTreeItem = new CheckBoxTreeItem<>(label.getName());
                variableTreeItem.getChildren().add(labelTreeItem);
            }
            root.getChildren().add(variableTreeItem);
        }
        qualifiersTreeView.setRoot(root);
        qualifiersTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
    }

    private void fillSummarizersTreeView() {
        CheckBoxTreeItem<String> root = new CheckBoxTreeItem<>("Sumaryzatory");
        root.setExpanded(true);
        for (LinguisticVariable var : Data.linguisticVariables) {
            CheckBoxTreeItem<String> variableTreeItem = new CheckBoxTreeItem<>(var.getName());
            variableTreeItem.setExpanded(true);
            for (Label label : var.getLabels()) {
                CheckBoxTreeItem<String> labelTreeItem = new CheckBoxTreeItem<>(label.getName());
                variableTreeItem.getChildren().add(labelTreeItem);
            }
            root.getChildren().add(variableTreeItem);
        }
        summarizersTreeView.setRoot(root);
        // set the cell factory
        summarizersTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
    }

    public void initSummaryTableColumns() {
        // Create new columns
        TableColumn<MultiSubjectSummary, String> formNumber = new TableColumn<>("Numer formy");
        formNumber.setCellValueFactory(cellData -> {
            if (cellData.getValue().getClass().equals(FirstFormMultiSubjectSummary.class)) {
                return new SimpleStringProperty("1");
            } else if (cellData.getValue().getClass().equals(SecondFormMultiSubjectSummary.class)) {
                return new SimpleStringProperty("2");
            } else if (cellData.getValue().getClass().equals(ThirdFormMultiSubjectSummary.class)) {
                return new SimpleStringProperty("3");
            } else {
                return new SimpleStringProperty("4");
            }
        });

        TableColumn<MultiSubjectSummary, String> summaryColumn = new TableColumn<>("Podsumowanie");
        summaryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));
        summaryColumn.setPrefWidth(1200);

        TableColumn<MultiSubjectSummary, Double> degreeOfTruthColumn = new TableColumn<>("T1");
        degreeOfTruthColumn.setCellValueFactory(new PropertyValueFactory<>("degreeOfTruth_T1"));
        degreeOfTruthColumn.setCellFactory(column -> new MainViewController.RoundedTableCell<>());

        // Add the columns to the TableView
        summaryTable.getColumns().addAll(
                formNumber,
                summaryColumn,
                degreeOfTruthColumn
        );
    }

    private void fillSummaryTable() {
        summaryTable.getItems().clear();
        ObservableList<MultiSubjectSummary> summaryList = FXCollections.observableArrayList();
        summaryList.addAll(summaries);
        summaryTable.setItems(summaryList);
    }

    public void generateButton_OnAction() {
        Set<String> temp1 = new HashSet<>();
        Set<String> temp3 = new HashSet<>();
        ObservableSet<String> checkedQuantifiers1 = FXCollections.observableSet(temp1);
        ObservableSet<String> checkedSummarizers = FXCollections.observableSet(temp3);
        findCheckedItems((CheckBoxTreeItem<?>) qualifiersTreeView.getRoot(), checkedQuantifiers1);
        findCheckedItems((CheckBoxTreeItem<?>) summarizersTreeView.getRoot(), checkedSummarizers);
        if (firstSubject_CB.getValue() == null) {
            throw new RuntimeException("Należy wybrać podmiot");
        }
        if (checkedQuantifiers1.size() > 3) {
            throw new RuntimeException("Wybrano zbyt wiele kwalifikatorów dla podmiotu");
        }
        if (checkedSummarizers.size() > 3 || checkedSummarizers.size() == 0) {
            throw new RuntimeException("Błędna liczba sumaryzatorów. Należy wybrać od 1 do 3 sumaryzatorów");
        }
        summaries.clear();
        List<Label> qualifiers1 = new ArrayList<>();
        List<Label> summarizers = new ArrayList<>();

        for (String chosenOption : temp1) {
            String[] names = chosenOption.split(";");
            qualifiers1.add(findLabel(names[0], names[1]));
        }
        for (String chosenOption : temp3) {
            String[] names = chosenOption.split(";");
            summarizers.add(findLabel(names[0], names[1]));
        }

        ArrayList<Quantifier> quantifiers = new ArrayList<>();
        quantifiers.addAll(Data.absoluteQuantifiers);
        quantifiers.addAll(Data.relativeQuantifiers);
        generateSummariesMultiObject(quantifiers, qualifiers1, summarizers);
        fillSummaryTable();
    }

    private void generateSummariesMultiObject(List<Quantifier> quantifiers, List<Label> qualifiers,
                                              List<Label> summarizers) {
        List<CarDetails> objects1;
        List<CarDetails> objects2;

        if (firstSubject_CB.getValue().equals(GASOLINE)) {
            objects1 = Data.carDetailsList.stream().filter(carDetails -> carDetails.getFuelType().equals(GASOLINE.toString())).toList();
            objects2 = Data.carDetailsList.stream().filter(carDetails -> carDetails.getFuelType().equals(DIESEL.toString())).toList();
        } else {
            objects1 = Data.carDetailsList.stream().filter(carDetails -> carDetails.getFuelType().equals(DIESEL.toString())).toList();
            objects2 = Data.carDetailsList.stream().filter(carDetails -> carDetails.getFuelType().equals(GASOLINE.toString())).toList();
        }

        for (int i = 1; i < summarizers.size() + 1; i++) {
            for (int j = 0; j < summarizers.size(); j++) {
                List<Label> tempSumList = new ArrayList<>();
                if (j + i - 1 < summarizers.size()) {
                    for (int k = j; k < j + i; k++) {
                        tempSumList.add(summarizers.get(k));
                    }
                }
                if (tempSumList.size() == i) {
                    MultiSubjectSummary summary2 = new FourthFormMultiSubjectSummary(tempSumList, objects1, objects2);
                    summaries.add(summary2);
                    for (Quantifier quantifier : quantifiers) {
                        if (quantifier.getClass().equals(AbsoluteQuantifier.class)) {
                            continue;
                        }
                        MultiSubjectSummary summary1 = new FirstFormMultiSubjectSummary(quantifier, tempSumList, objects1, objects2);
                        summaries.add(summary1);
                        generateSecondAndThirdForm(quantifier, qualifiers, tempSumList, objects1, objects2);
                    }
                }
            }
            if (i == 2 && summarizers.size() == 3) {
                List<Label> tempSumList = new ArrayList<>();
                tempSumList.add(summarizers.get(0));
                tempSumList.add(summarizers.get(2));
                MultiSubjectSummary summary2 = new FourthFormMultiSubjectSummary(tempSumList, objects1, objects2);
                summaries.add(summary2);
                for (Quantifier quantifier : quantifiers) {
                    if (quantifier.getClass().equals(AbsoluteQuantifier.class)) {
                        continue;
                    }
                    MultiSubjectSummary summary1 = new FirstFormMultiSubjectSummary(quantifier, tempSumList, objects1, objects2);
                    summaries.add(summary1);
                    generateSecondAndThirdForm(quantifier, qualifiers, tempSumList, objects1, objects2);
                }
            }
        }
    }

    private void generateSecondAndThirdForm(Quantifier quantifier, List<Label> qualifiers,
                                            List<Label> tempSumList, List<CarDetails> objects1,
                                            List<CarDetails> objects2) {
        for (int i = 1; i < qualifiers.size() + 1; i++) {
            for (int j = 0; j < qualifiers.size(); j++) {
                List<Label> tempQuaList = new ArrayList<>();
                if (j + i - 1 < qualifiers.size()) {
                    for (int k = j; k < j + i; k++) {
                        tempQuaList.add(qualifiers.get(k));
                    }
                }
                if (tempQuaList.size() == i) {
                    MultiSubjectSummary summary1 = new SecondFormMultiSubjectSummary(quantifier, tempSumList, tempQuaList, objects1, objects2);
                    MultiSubjectSummary summary2 = new ThirdFormMultiSubjectSummary(quantifier, tempSumList, tempQuaList, objects1, objects2);
                    summaries.add(summary1);
                    summaries.add(summary2);
                }
            }
            if (i == 2 && qualifiers.size() == 3) {
                List<Label> tempQuaList = new ArrayList<>();
                tempQuaList.add(qualifiers.get(0));
                tempQuaList.add(qualifiers.get(2));
                MultiSubjectSummary summary1 = new SecondFormMultiSubjectSummary(quantifier, tempSumList, tempQuaList, objects1, objects2);
                MultiSubjectSummary summary2 = new ThirdFormMultiSubjectSummary(quantifier, tempSumList, tempQuaList, objects1, objects2);
                summaries.add(summary1);
                summaries.add(summary2);
            }
        }
    }

    private Label findLabel(String variableName, String labelName) {
        for (LinguisticVariable var : Data.linguisticVariables) {
            if (var.getName().equals(variableName)) {
                for (Label label : var.getLabels()) {
                    if (label.getName().equals(labelName)) {
                        return label;
                    }
                }
            }
        }
        return null;
    }

    private void findCheckedItems(CheckBoxTreeItem<?> item, ObservableSet<String> checkedItems) {
        if (item.isSelected()) {
            if (item.getParent() != null && item.getChildren().size() == 0) {
                checkedItems.add(item.getParent().getValue().toString() + ";" + item.getValue().toString());
            }
        }
        for (TreeItem<?> child : item.getChildren()) {
            findCheckedItems((CheckBoxTreeItem<?>) child, checkedItems);
        }
    }

    @FXML
    private void refresh() {
        fillQualifiersTreeView();
        fillSummarizersTreeView();
    }
}
