package pl.ksr.view.controllers;

import javafx.beans.property.SimpleDoubleProperty;
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
import pl.ksr.logic.summarization.forms.FirstFormSingleSubjectSummary;
import pl.ksr.logic.summarization.forms.SecondFormSingleSubjectSummary;
import pl.ksr.view.Data;
import pl.ksr.view.model.SingleSubjectSummaryDTO;

import java.net.URL;
import java.util.*;

public class MainViewController implements Initializable {
    @FXML
    private TextField weightT1TF;
    @FXML
    private TextField weightT2TF;
    @FXML
    private TextField weightT10TF;
    @FXML
    private TextField weightT3TF;
    @FXML
    private TextField weightT4TF;
    @FXML
    private TextField weightT5TF;
    @FXML
    private TextField weightT6TF;
    @FXML
    private TextField weightT7TF;
    @FXML
    private TextField weightT8TF;
    @FXML
    private TextField weightT9TF;
    @FXML
    private TextField weightT11TF;
    @FXML
    private TableView<SingleSubjectSummaryDTO> summaryTable;
    @FXML
    private TableView<CarDetails> carDetailsTable;
    @FXML
    private TreeView<String> qualifiersTreeView;
    @FXML
    private TreeView<String> summarizersTreeView;

    private final List<SingleSubjectSummary> summaries = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillDatabaseTable();
        fillQualifiersTreeView();
        fillSummarizersTreeView();
        fillWeights();
        initSummaryTableColumns();
    }

    private void fillWeights() {
        weightT1TF.textProperty().set(String.valueOf(Data.measureWeights.getWeights().get("T1")));
        weightT2TF.textProperty().set(String.valueOf(Data.measureWeights.getWeights().get("T2")));
        weightT3TF.textProperty().set(String.valueOf(Data.measureWeights.getWeights().get("T3")));
        weightT4TF.textProperty().set(String.valueOf(Data.measureWeights.getWeights().get("T4")));
        weightT5TF.textProperty().set(String.valueOf(Data.measureWeights.getWeights().get("T5")));
        weightT6TF.textProperty().set(String.valueOf(Data.measureWeights.getWeights().get("T6")));
        weightT7TF.textProperty().set(String.valueOf(Data.measureWeights.getWeights().get("T7")));
        weightT8TF.textProperty().set(String.valueOf(Data.measureWeights.getWeights().get("T8")));
        weightT9TF.textProperty().set(String.valueOf(Data.measureWeights.getWeights().get("T9")));
        weightT10TF.textProperty().set(String.valueOf(Data.measureWeights.getWeights().get("T10")));
        weightT11TF.textProperty().set(String.valueOf(Data.measureWeights.getWeights().get("T11")));
    }

    private MeasureWeights retrieveWeights() {
        HashMap<String, Double> map = new HashMap<>();
        map.put("T1", Double.valueOf(weightT1TF.textProperty().get()));
        map.put("T2", Double.valueOf(weightT2TF.textProperty().get()));
        map.put("T3", Double.valueOf(weightT3TF.textProperty().get()));
        map.put("T4", Double.valueOf(weightT4TF.textProperty().get()));
        map.put("T5", Double.valueOf(weightT5TF.textProperty().get()));
        map.put("T6", Double.valueOf(weightT6TF.textProperty().get()));
        map.put("T7", Double.valueOf(weightT7TF.textProperty().get()));
        map.put("T8", Double.valueOf(weightT8TF.textProperty().get()));
        map.put("T9", Double.valueOf(weightT9TF.textProperty().get()));
        map.put("T10", Double.valueOf(weightT10TF.textProperty().get()));
        map.put("T11", Double.valueOf(weightT11TF.textProperty().get()));
        return new MeasureWeights(map);
    }

    private void fillDatabaseTable() {
        TableColumn<CarDetails, String> column1 = new TableColumn<>("ID");
        TableColumn<CarDetails, String> column2 = new TableColumn<>("Franchise");
        TableColumn<CarDetails, String> column3 = new TableColumn<>("Model");
        TableColumn<CarDetails, String> column4 = new TableColumn<>("Price");
        TableColumn<CarDetails, String> column5 = new TableColumn<>("Mileage");
        TableColumn<CarDetails, String> column6 = new TableColumn<>("Horsepower");
        TableColumn<CarDetails, String> column7 = new TableColumn<>("Eng. disp.");
        TableColumn<CarDetails, String> column8 = new TableColumn<>("Fuel tank vol.");
        TableColumn<CarDetails, String> column9 = new TableColumn<>("Wheelbase");
        TableColumn<CarDetails, String> column10 = new TableColumn<>("Torque");
        TableColumn<CarDetails, String> column11 = new TableColumn<>("Width");
        TableColumn<CarDetails, String> column12 = new TableColumn<>("Length");
        TableColumn<CarDetails, String> column13 = new TableColumn<>("Fuel economy");
        TableColumn<CarDetails, String> column14 = new TableColumn<>("Year");

        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column2.setCellValueFactory(new PropertyValueFactory<>("franchiseName"));
        column3.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        column4.setCellValueFactory(new PropertyValueFactory<>("price"));
        column5.setCellValueFactory(new PropertyValueFactory<>("mileage"));
        column6.setCellValueFactory(new PropertyValueFactory<>("horsepower"));
        column7.setCellValueFactory(new PropertyValueFactory<>("engineDisplacement"));
        column8.setCellValueFactory(new PropertyValueFactory<>("fuelTankVolume"));
        column9.setCellValueFactory(new PropertyValueFactory<>("wheelbase"));
        column10.setCellValueFactory(new PropertyValueFactory<>("torque"));
        column11.setCellValueFactory(new PropertyValueFactory<>("width"));
        column12.setCellValueFactory(new PropertyValueFactory<>("length"));
        column13.setCellValueFactory(new PropertyValueFactory<>("fuelEconomy"));
        column14.setCellValueFactory(new PropertyValueFactory<>("year"));

        carDetailsTable.getColumns().addAll(List.of(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14));
        carDetailsTable.getItems().addAll(Data.carDetailsList);
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
        // set the cell factory
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

    @FXML
    private void generateSummaryBtn_onAction() {
        Data.measureWeights = retrieveWeights();
        if (!Data.measureWeights.areCorrect()) {
            throw new RuntimeException("Measure weights are incorrect");
        }

        Set<String> temp1 = new HashSet<>();
        Set<String> temp2 = new HashSet<>();
        ObservableSet<String> checkedQuantifiers = FXCollections.observableSet(temp1);
        ObservableSet<String> checkedSummarizers = FXCollections.observableSet(temp2);
        findCheckedItems((CheckBoxTreeItem<?>) qualifiersTreeView.getRoot(), checkedQuantifiers);
        findCheckedItems((CheckBoxTreeItem<?>) summarizersTreeView.getRoot(), checkedSummarizers);

        summaries.clear();
        List<Label> qualifiers = new ArrayList<>();
        List<Label> summarizers = new ArrayList<>();

        for (String chosenOption : temp1) {
            String[] names = chosenOption.split(";");
            qualifiers.add(findLabel(names[0], names[1]));
        }
        for (String chosenOption : temp2) {
            String[] names = chosenOption.split(";");
            summarizers.add(findLabel(names[0], names[1]));
        }

        if (qualifiers.size() == 0) { // First form.
            List<Quantifier> quantifiers = new ArrayList<>();
            quantifiers.addAll(Data.relativeQuantifiers);
            quantifiers.addAll(Data.absoluteQuantifiers);
            generateSummariesFirstForm(quantifiers, qualifiers, summarizers);
        } else { // Second form.
            List<Quantifier> quantifiers = new ArrayList<>(Data.relativeQuantifiers);
            generateSummariesSecondForm(quantifiers, qualifiers, summarizers);
        }

        fillSummaryTable();
    }

    @FXML
    private void initSummaryTableColumns() {
        // Create new columns
        TableColumn<SingleSubjectSummaryDTO, String> summaryColumn = new TableColumn<>("Podsumowanie");
        summaryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().textValue()));
        summaryColumn.setPrefWidth(500);

        TableColumn<SingleSubjectSummaryDTO, Double> goodnessOfSummaryColumn = new TableColumn<>("T");
        goodnessOfSummaryColumn.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().goodnessOfSummary()).asObject());
        goodnessOfSummaryColumn.setCellFactory(column -> new RoundedTableCell<>());

        TableColumn<SingleSubjectSummaryDTO, Double> degreeOfTruthColumn = new TableColumn<>("T1");
        degreeOfTruthColumn.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().degreeOfTruth_T1()).asObject());
        degreeOfTruthColumn.setCellFactory(column -> new RoundedTableCell<>());

        TableColumn<SingleSubjectSummaryDTO, Double> degreeOfImprecisionColumn = new TableColumn<>("T2");
        degreeOfImprecisionColumn.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().degreeOfImprecision_T2()).asObject());
        degreeOfImprecisionColumn.setCellFactory(column -> new RoundedTableCell<>());

        TableColumn<SingleSubjectSummaryDTO, Double> degreeOfCoveringColumn = new TableColumn<>("T3");
        degreeOfCoveringColumn.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().degreeOfCovering_T3()).asObject());
        degreeOfCoveringColumn.setCellFactory(column -> new RoundedTableCell<>());

        TableColumn<SingleSubjectSummaryDTO, Double> degreeOfAppropriatenessColumn = new TableColumn<>("T4");
        degreeOfAppropriatenessColumn.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().degreeOfAppropriateness_T4()).asObject());
        degreeOfAppropriatenessColumn.setCellFactory(column -> new RoundedTableCell<>());

        TableColumn<SingleSubjectSummaryDTO, Double> degreeOfSummaryColumn = new TableColumn<>("T5");
        degreeOfSummaryColumn.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().degreeOfSummary_T5()).asObject());
        degreeOfSummaryColumn.setCellFactory(column -> new RoundedTableCell<>());

        TableColumn<SingleSubjectSummaryDTO, Double> degreeOfQuantifierImprecisionColumn = new TableColumn<>("T6");
        degreeOfQuantifierImprecisionColumn.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().degreeOfQuantifierImprecision_T6()).asObject());
        degreeOfQuantifierImprecisionColumn.setCellFactory(column -> new RoundedTableCell<>());

        TableColumn<SingleSubjectSummaryDTO, Double> degreeOfQuantifierCardinalityColumn = new TableColumn<>("T7");
        degreeOfQuantifierCardinalityColumn.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().degreeOfQuantifierCardinality_T7()).asObject());
        degreeOfQuantifierCardinalityColumn.setCellFactory(column -> new RoundedTableCell<>());

        TableColumn<SingleSubjectSummaryDTO, Double> degreeOfSummarizerCardinalityColumn = new TableColumn<>("T8");
        degreeOfSummarizerCardinalityColumn.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().degreeOfSummarizerCardinality_T8()).asObject());
        degreeOfSummarizerCardinalityColumn.setCellFactory(column -> new RoundedTableCell<>());

        TableColumn<SingleSubjectSummaryDTO, Double> degreeOfQualifierImprecisionColumn = new TableColumn<>("T9");
        degreeOfQualifierImprecisionColumn.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().degreeOfQualifierImprecision_T9()).asObject());
        degreeOfQualifierImprecisionColumn.setCellFactory(column -> new RoundedTableCell<>());

        TableColumn<SingleSubjectSummaryDTO, Double> degreeOfQualifierCardinalityColumn = new TableColumn<>("T10");
        degreeOfQualifierCardinalityColumn.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().degreeOfQualifierCardinality_T10()).asObject());
        degreeOfQualifierCardinalityColumn.setCellFactory(column -> new RoundedTableCell<>());

        TableColumn<SingleSubjectSummaryDTO, Double> lengthOfQualifierColumn = new TableColumn<>("T11");
        lengthOfQualifierColumn.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().lengthOfQualifier_T11()).asObject());
        lengthOfQualifierColumn.setCellFactory(column -> new RoundedTableCell<>());

        // Add the columns to the TableView
        summaryTable.getColumns().addAll(
                summaryColumn,
                goodnessOfSummaryColumn,
                degreeOfTruthColumn,
                degreeOfImprecisionColumn,
                degreeOfCoveringColumn,
                degreeOfAppropriatenessColumn,
                degreeOfSummaryColumn,
                degreeOfQuantifierImprecisionColumn,
                degreeOfQuantifierCardinalityColumn,
                degreeOfSummarizerCardinalityColumn,
                degreeOfQualifierImprecisionColumn,
                degreeOfQualifierCardinalityColumn,
                lengthOfQualifierColumn
        );
    }

    private void fillSummaryTable() {
        ArrayList<SingleSubjectSummaryDTO> summariesDTO = new ArrayList<>();
        for (SingleSubjectSummary summary : summaries) {
            summariesDTO.add(new SingleSubjectSummaryDTO(
                    summary.toString(),
                    summary.getGoodnessOfSummary(),
                    summary.getDegreeOfTruth_T1(),
                    summary.getDegreeOfImprecision_T2(),
                    summary.getDegreeOfCovering_T3(),
                    summary.getDegreeOfAppropriateness_T4(),
                    summary.getDegreeOfSummary_T5(),
                    summary.getDegreeOfQuantifierImprecision_T6(),
                    summary.getDegreeOfQuantifierCardinality_T7(),
                    summary.getDegreeOfSummarizerCardinality_T8(),
                    summary.getDegreeOfQualifierImprecision_T9(),
                    summary.getDegreeOfQualifierCardinality_T10(),
                    summary.getLengthOfQualifier_T11()
            ));
        }

        summaryTable.getItems().clear();
        ObservableList<SingleSubjectSummaryDTO> summaryList = FXCollections.observableArrayList();
        summaryList.addAll(summariesDTO);
        summaryTable.setItems(summaryList);
    }

    private void generateSummariesFirstForm(List<Quantifier> quantifiers, List<Label> qualifiers,
                                            List<Label> summarizers) {
        for (int i = 1; i < summarizers.size() + 1; i++) {
            for (int j = 0; j < summarizers.size(); j++) {
                List<Label> tempSumList = new ArrayList<>();
                if (j + i - 1 < summarizers.size()) {
                    for (int k = j; k < j + i; k++) {
                        tempSumList.add(summarizers.get(k));
                    }
                }
                if (tempSumList.size() == i) {
                    for (Quantifier quantifier : quantifiers) {
                        if (quantifier instanceof AbsoluteQuantifier && qualifiers.size() > 0) {
                            continue;
                        }
                        SingleSubjectSummary singleSubjectSummary;
                        if (qualifiers.size() == 0) {
                            singleSubjectSummary = new FirstFormSingleSubjectSummary(Data.measureWeights, quantifier, tempSumList, Data.carDetailsList);
                        } else {
                            singleSubjectSummary = new SecondFormSingleSubjectSummary(Data.measureWeights, quantifier, qualifiers, tempSumList, Data.carDetailsList);
                        }
                        summaries.add(singleSubjectSummary);
                    }
                }
            }

            if (i == 2 && summarizers.size() == 3) {
                List<Label> tempSumList = new ArrayList<>();
                tempSumList.add(summarizers.get(0));
                tempSumList.add(summarizers.get(2));
                for (Quantifier quantifier : quantifiers) {
                    if (quantifier instanceof AbsoluteQuantifier && qualifiers.size() > 0) {
                        continue;
                    }
                    SingleSubjectSummary singleSubjectSummary;
                    if (qualifiers.size() > 0) {
                        singleSubjectSummary = new FirstFormSingleSubjectSummary(Data.measureWeights, quantifier, tempSumList, Data.carDetailsList);
                    } else {
                        singleSubjectSummary = new SecondFormSingleSubjectSummary(Data.measureWeights, quantifier, qualifiers, tempSumList, Data.carDetailsList);
                    }
                    summaries.add(singleSubjectSummary);
                }
            }
        }
    }

    private void generateSummariesSecondForm(List<Quantifier> quantifiers, List<Label> qualifiers,
                                             List<Label> summarizers) {
        for (int i = 1; i < qualifiers.size() + 1; i++) {
            for (int j = 0; j < qualifiers.size(); j++) {
                List<Label> tempQuaList = new ArrayList<>();
                if (j + i - 1 < qualifiers.size()) {
                    for (int k = j; k < j + i; k++) {
                        tempQuaList.add(qualifiers.get(k));
                    }
                }
                if (tempQuaList.size() == i) {
                    generateSummariesFirstForm(quantifiers, tempQuaList, summarizers);
                }
            }
            if (i == 2 && qualifiers.size() == 3) {
                List<Label> tempQuaList = new ArrayList<>();
                tempQuaList.add(qualifiers.get(0));
                tempQuaList.add(qualifiers.get(2));
                generateSummariesFirstForm(quantifiers, tempQuaList, summarizers);
            }
        }
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

    @FXML
    private void refresh() {
        fillQualifiersTreeView();
        fillSummarizersTreeView();
    }

    public static class RoundedTableCell<S, T> extends TableCell<S, T> {
        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setText(null);
            } else {
                if (item instanceof Double) {
                    double value = (double) item;
                    if (value < 0.01 && value != 0) {
                        setText(">0.00");
                    } else if (value > 0.99 && value != 1) {
                        setText("<1.00");
                    } else {
                        setText(String.format("%.2f", value));
                    }
                } else {
                    setText(item.toString());
                }
            }
        }
    }
}
