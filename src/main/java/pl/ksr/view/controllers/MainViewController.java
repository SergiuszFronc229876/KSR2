package pl.ksr.view.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.ksr.logic.calculation.functions.GaussianFunction;
import pl.ksr.logic.calculation.functions.TrapezoidalFunction;
import pl.ksr.logic.calculation.functions.TriangularFunction;
import pl.ksr.logic.calculation.functions.UnionMembershipFunction;
import pl.ksr.logic.calculation.sets.ContinuousSet;
import pl.ksr.logic.calculation.sets.FuzzySet;
import pl.ksr.logic.model.CarDetails;
import pl.ksr.logic.summarization.Label;
import pl.ksr.logic.summarization.*;
import pl.ksr.logic.utils.CarDetailsReader;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class MainViewController implements Initializable {

    public TableColumn TableSummaryNo;
    public TableColumn TableSummary;
    public TableColumn TableSummaryT;
    public Button GenerateBtn;
    public TextField weightT1TF;
    public TextField weightT2TF;
    public TextField weightT10TF;
    public TextField weightT3TF;
    public TextField weightT4TF;
    public TextField weightT5TF;
    public TextField weightT6TF;
    public TextField weightT7TF;
    public TextField weightT8TF;
    public TextField weightT9TF;
    public TextField weightT11TF;
    public ComboBox<Quantifier> quantifierCB;
    public TableView summaryTable;

    @FXML
    private TableView<CarDetails> carDetailsTable;
    @FXML
    private TreeView qualifiersTreeView;
    @FXML
    private TreeView summarizersTreeView;

    private final List<CarDetails> carDetailsList;

    public MainViewController() {
        carDetailsList = CarDetailsReader.readDataCsv();
        initData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillDatabaseTable();
        fillQualifiersTreeView();
        fillSummarizersTreeView();
        fillQuantifiersComboBox();
        fillWeights();
    }

    private void fillWeights() {
        weightT1TF.textProperty().set(String.valueOf(measureWeights.getWeights().get("T1")));
        weightT2TF.textProperty().set(String.valueOf(measureWeights.getWeights().get("T2")));
        weightT3TF.textProperty().set(String.valueOf(measureWeights.getWeights().get("T3")));
        weightT4TF.textProperty().set(String.valueOf(measureWeights.getWeights().get("T4")));
        weightT5TF.textProperty().set(String.valueOf(measureWeights.getWeights().get("T5")));
        weightT6TF.textProperty().set(String.valueOf(measureWeights.getWeights().get("T6")));
        weightT7TF.textProperty().set(String.valueOf(measureWeights.getWeights().get("T7")));
        weightT8TF.textProperty().set(String.valueOf(measureWeights.getWeights().get("T8")));
        weightT9TF.textProperty().set(String.valueOf(measureWeights.getWeights().get("T9")));
        weightT10TF.textProperty().set(String.valueOf(measureWeights.getWeights().get("T10")));
        weightT11TF.textProperty().set(String.valueOf(measureWeights.getWeights().get("T11")));
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
        carDetailsTable.getItems().clear();
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
        column13.setCellValueFactory(new PropertyValueFactory<>("FuelEconomy"));

        carDetailsTable.getColumns().addAll(List.of(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13));
        carDetailsTable.getItems().addAll(carDetailsList);
    }

    private void fillQualifiersTreeView() {
        CheckBoxTreeItem<String> root = new CheckBoxTreeItem<>("Kwalifikatory");
        root.setExpanded(true);
        for (LinguisticVariable var : linguisticVariables) {
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
        for (LinguisticVariable var : linguisticVariables) {
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

    private Quantifier checkedQuantifier = null;

    private void fillQuantifiersComboBox() {
        quantifierCB.getItems().addAll(predefinedQuantifiers);
    }

    private MeasureWeights measureWeights;
    private List<LinguisticVariable> linguisticVariables;
    private List<AbsoluteQuantifier> absoluteQuantifiers;
    private List<RelativeQuantifier> relativeQuantifiers;
    private List<Quantifier> predefinedQuantifiers;

    private void initData() {
        Map<String, Double> measures = new HashMap<>();
        measures.put("T1", 0.1);
        measures.put("T2", 0.1);
        measures.put("T3", 0.1);
        measures.put("T4", 0.1);
        measures.put("T5", 0.1);
        measures.put("T6", 0.1);
        measures.put("T7", 0.1);
        measures.put("T8", 0.1);
        measures.put("T9", 0.1);
        measures.put("T10", 0.05);
        measures.put("T11", 0.05);
        measureWeights = new MeasureWeights(measures);

        //Cena
        Label najtansze = new Label("najtańsze", "Cena", new FuzzySet(new TrapezoidalFunction(1, 1, 3000, 5000), new ContinuousSet(1, 1_000_000)));
        Label popularne = new Label("popularne", "Cena", new FuzzySet(new TrapezoidalFunction(3000, 5000, 8000, 10000), new ContinuousSet(1, 1_000_000)));
        Label luksusowe = new Label("luksusowe", "Cena", new FuzzySet(new TrapezoidalFunction(8000, 10000, 17000, 25000), new ContinuousSet(1, 1_000_000)));
        Label superLuksusowe = new Label("super-luksusowe", "Cena", new FuzzySet(new TrapezoidalFunction(17000, 25000, 80000, 100000), new ContinuousSet(1, 1_000_000)));
        Label sportowe = new Label("sportowe", "Cena", new FuzzySet(new TrapezoidalFunction(80000, 100000, 200000, 250000), new ContinuousSet(1, 1_000_000)));
        Label superSportowe = new Label("super-sportowe", "Cena", new FuzzySet(new TrapezoidalFunction(200000, 250000, 1_000_000, 1_000_000), new ContinuousSet(1, 1_000_000)));
        List<Label> labelsCena = List.of(najtansze, popularne, luksusowe, superLuksusowe, sportowe, superSportowe);
        LinguisticVariable cena = new LinguisticVariable("Cena", labelsCena);

        //Przebieg
        Label do_25_000 = new Label("do 25 000 km", "Przebieg", new FuzzySet(new TrapezoidalFunction(0, 0, 10_000, 25_0000), new ContinuousSet(1, 500_000)));
        Label miedzy_10000_75000 = new Label("między 10 000 km a 75 000 km", "Przebieg", new FuzzySet(new TrapezoidalFunction(10_000, 25_000, 50_000, 75_000), new ContinuousSet(1, 500_000)));
        Label miedzy_50000_125000 = new Label("między 50 000 km a 125 000 km", "Przebieg", new FuzzySet(new TrapezoidalFunction(50_000, 75_0000, 100_000, 125_000), new ContinuousSet(1, 500_000)));
        Label miedzy_100000_200000 = new Label("między 100 000 km a 200 000 km", "Przebieg", new FuzzySet(new TrapezoidalFunction(100_000, 125_000, 175_000, 200_000), new ContinuousSet(1, 500_000)));
        Label miedzy_175000_300000 = new Label("między 175 000 km a 300 000 km", "Przebieg", new FuzzySet(new TrapezoidalFunction(175_000, 200_000, 275_000, 300_000), new ContinuousSet(1, 500_000)));
        Label miedzy_275000_375000 = new Label("między 275 000 km a 375 000 km", "Przebieg", new FuzzySet(new TrapezoidalFunction(275_000, 300_000, 350_000, 375_000), new ContinuousSet(1, 500_000)));
        Label ponad_375000 = new Label("ponad 375 000 km", "Przebieg", new FuzzySet(new TrapezoidalFunction(350_000, 375_000, 500_000, 500_000), new ContinuousSet(1, 500_000)));
        List<Label> labelsPrzebieg = List.of(do_25_000, miedzy_10000_75000, miedzy_50000_125000, miedzy_100000_200000, miedzy_175000_300000, miedzy_275000_375000, ponad_375000);
        LinguisticVariable przebieg = new LinguisticVariable("Przebieg", labelsPrzebieg);

        //Moc silnika
        Label slaba = new Label("słaba", "Moc silnika", new FuzzySet(new TrapezoidalFunction(20, 20, 90, 110), new ContinuousSet(20, 1000)));
        Label umiarkowana = new Label("umiarkowana", "Moc silnika", new FuzzySet(new TriangularFunction(60, 140, 220), new ContinuousSet(20, 1000)));
        Label mocna = new Label("mocna", "Moc silnika", new FuzzySet(new TriangularFunction(140, 220, 300), new ContinuousSet(20, 1000)));
        Label bardzoMocna = new Label("bardzo mocna", "Moc silnika", new FuzzySet(new TrapezoidalFunction(220, 300, 800, 800), new ContinuousSet(20, 1000)));
        List<Label> labelsMoc = List.of(slaba, umiarkowana, mocna, bardzoMocna);
        LinguisticVariable mocSilnika = new LinguisticVariable("Moc silnika", labelsMoc);

        //Zużycie paliwa
        Label bardzoEkonomiczne = new Label("bardzo ekonomiczne", "Zużycie paliwa na 100 km", new FuzzySet(new TrapezoidalFunction(4.0, 4.0, 4.2, 5.0), new ContinuousSet(4.0, 21.2)));
        Label ekonomiczne = new Label("ekonomiczne", "Zużycie paliwa na 100 km", new FuzzySet(new TrapezoidalFunction(4.2, 5.0, 7.6, 8.4), new ContinuousSet(4.0, 21.2)));
        Label umiarkowanieEkonomiczne = new Label("umiarkowanie ekonomiczne", "Zużycie paliwa na 100 km", new FuzzySet(new TrapezoidalFunction(7.6, 8.4, 10.2, 11.6), new ContinuousSet(4.0, 21.2)));
        Label nieekonomiczne = new Label("nieekonomiczne", "Zużycie paliwa na 100 km", new FuzzySet(new TrapezoidalFunction(10.2, 11.6, 21.2, 21.2), new ContinuousSet(4.0, 21.2)));
        List<Label> labelsZuzycie = List.of(bardzoEkonomiczne, ekonomiczne, umiarkowanieEkonomiczne, nieekonomiczne);
        LinguisticVariable zuzyciePaliwa = new LinguisticVariable("Zużycie paliwa na 100 km ", labelsZuzycie);

        //Pojemność silnika
        Label do1500 = new Label("do 1500 cm3", "Pojemność silnika", new FuzzySet(new TrapezoidalFunction(700, 700, 1100, 1500), new ContinuousSet(700, 9100)));
        Label miedzy1300_2100 = new Label("miedzy 1300 cm3 a 2100 cm3", "Pojemność silnika", new FuzzySet(new TrapezoidalFunction(1300, 1600, 1800, 2100), new ContinuousSet(700, 9100)));
        Label miedzy1800_2700 = new Label("miedzy 1800 cm3 a 2700 cm3", "Pojemność silnika", new FuzzySet(new TrapezoidalFunction(1800, 2100, 2400, 2700), new ContinuousSet(700, 9100)));
        Label miedzy2400_3500 = new Label("miedzy 2400 cm3 a 3500 cm3", "Pojemność silnika", new FuzzySet(new TrapezoidalFunction(2400, 2700, 3200, 3500), new ContinuousSet(700, 9100)));
        Label miedzy3200_4700 = new Label("miedzy 3200 cm3 a 4700 cm3", "Pojemność silnika", new FuzzySet(new TrapezoidalFunction(3200, 3500, 4400, 4700), new ContinuousSet(700, 9100)));
        Label miedzy4400_6200 = new Label("miedzy 4400 cm3 a 6200 cm3", "Pojemność silnika", new FuzzySet(new TrapezoidalFunction(4400, 4700, 5900, 6200), new ContinuousSet(700, 9100)));
        Label powyzej5900 = new Label("powyżej 5900 cm3", "Pojemność silnika", new FuzzySet(new TrapezoidalFunction(5900, 6200, 9100, 9100), new ContinuousSet(700, 9100)));
        List<Label> labelsPojemnosc = List.of(do1500, miedzy1300_2100, miedzy1800_2700, miedzy2400_3500, miedzy3200_4700, miedzy4400_6200, powyzej5900);
        LinguisticVariable pojemnoscSilnika = new LinguisticVariable("Pojemność silnika", labelsPojemnosc);

        //Długość
        Label mikrosamochód = new Label("mikrosamochód", "Długość", new FuzzySet(new TrapezoidalFunction(2.4, 2.4, 3.1, 3.5), new ContinuousSet(2.4, 6.6)));
        Label miejski = new Label("samochód miejski", "Długość", new FuzzySet(new TrapezoidalFunction(3.1, 3.5, 4.0, 4.2), new ContinuousSet(2.4, 6.6)));
        Label kompaktowy = new Label("samochód kompaktowy", "Długość", new FuzzySet(new TrapezoidalFunction(4.0, 4.2, 4.5, 4.7), new ContinuousSet(2.4, 6.6)));
        Label klasySredniej = new Label("samochód klasy średniej", "Długość", new FuzzySet(new TrapezoidalFunction(4.5, 4.7, 4.9, 5.1), new ContinuousSet(2.4, 6.6)));
        Label klasySredniejWyzszej = new Label("samochód klasy średniej wyższej", "Długość", new FuzzySet(new TrapezoidalFunction(4.9, 5.1, 5.3, 5.5), new ContinuousSet(2.4, 6.6)));
        Label wyzszej = new Label("samochód klasy wyższej", "Długość", new FuzzySet(new TrapezoidalFunction(5.3, 5.5, 6.6, 6.6), new ContinuousSet(2.4, 6.6)));
        List<Label> labelsDlugosc = List.of(mikrosamochód, miejski, kompaktowy, klasySredniej, klasySredniejWyzszej, wyzszej);
        LinguisticVariable dlugosc = new LinguisticVariable("Długość", labelsDlugosc);

        //Zbiornik Paliwa
        Label mala = new Label("pozwalająca przejechać około 500 km bez tankowania Volkswagen Golfem 5 z silnikiem o pojemności 1400 cm3", "Pojemność zbiornika paliwa", new FuzzySet(new UnionMembershipFunction(new TrapezoidalFunction(20, 20, 35, 35), new GaussianFunction(35, 4, 35, 77)), new ContinuousSet(20, 160)));
        Label standardowa = new Label("pozwalająca przejechać około 1000 km bez tankowania Volkswagen Golfem 5 z silnikiem o pojemności 1400 cm3", "Pojemność zbiornika paliwa", new FuzzySet(new GaussianFunction(65, 4, 28, 111), new ContinuousSet(20, 160)));
        Label spora = new Label("pozwalająca przejechać około 1500 km bez tankowania Volkswagen Golfem 5 z silnikiem o pojemności 1400 cm3", "Pojemność zbiornika paliwa", new FuzzySet(new GaussianFunction(108, 4, 53, 108), new ContinuousSet(20, 160)));
        List<Label> labelsZbiornik = List.of(mala, standardowa, spora);
        LinguisticVariable pojemnoscZbiornikaPaliwa = new LinguisticVariable("Pojemność zbiornika paliwa", labelsZbiornik);

        //Rozstaw osi
        Label krotki = new Label("krótki", "Rozstaw osi", new FuzzySet(new TrapezoidalFunction(1.6, 1.6, 1.8, 2.4), new ContinuousSet(1.6, 4.2)));
        Label sredni = new Label("średni", "Rozstaw osi", new FuzzySet(new TrapezoidalFunction(1.8, 2.4, 2.8, 3.2), new ContinuousSet(1.6, 4.2)));
        Label dlugi = new Label("długi", "Rozstaw osi", new FuzzySet(new TrapezoidalFunction(2.8, 3.2, 4.2, 4.2), new ContinuousSet(1.6, 4.2)));
        List<Label> labelsRozstawOsi = List.of(krotki, sredni, dlugi);
        LinguisticVariable rozstawOsi = new LinguisticVariable("Rozstaw osi", labelsRozstawOsi);

        //Moment obrotowy
        Label niski = new Label("niski", "Moment obrotowy", new FuzzySet(new TrapezoidalFunction(100, 100, 180, 230), new ContinuousSet(100, 1100)));
        Label normalny = new Label("normalny", "Moment obrotowy", new FuzzySet(new TriangularFunction(150, 255, 380), new ContinuousSet(100, 1100)));
        Label wysoki = new Label("wysoki", "Moment obrotowy", new FuzzySet(new TriangularFunction(260, 365, 470), new ContinuousSet(100, 1100)));
        Label chipTuning = new Label("chip tuning", "Moment obrotowy", new FuzzySet(new TrapezoidalFunction(430, 510, 1100, 1100), new ContinuousSet(100, 1100)));
        List<Label> labelsMomentObrotowy = List.of(niski, normalny, wysoki, chipTuning);
        LinguisticVariable momentObrotowy = new LinguisticVariable("Moment obrotowy", labelsMomentObrotowy);

        //Szerokosc
        Label waski = new Label("węższy od Volkswagena Passata B5", "Szerokość", new FuzzySet(new TrapezoidalFunction(1.50, 1.50, 1.60, 1.70), new ContinuousSet(1.50, 2.60)));
        Label zwyczajny = new Label("szerokość Volkswagena Passata B5", "Szerokość", new FuzzySet(new TrapezoidalFunction(1.60, 1.70, 1.80, 2.00), new ContinuousSet(1.50, 2.60)));
        Label szeroki = new Label("szerszy od Volkswagena Passata B5 o 20 cm", "Szerokość", new FuzzySet(new TrapezoidalFunction(1.80, 2.00, 2.20, 2.30), new ContinuousSet(1.50, 2.60)));
        Label bardzoSzeroki = new Label("szerszy od Volkswagena Passata B5 o 60 cm", "Szerokość", new FuzzySet(new TrapezoidalFunction(2.20, 2.30, 2.60, 2.60), new ContinuousSet(1.50, 2.60)));
        List<Label> labelsSzerokosc = List.of(waski, zwyczajny, szeroki, bardzoSzeroki);
        LinguisticVariable szerokosc = new LinguisticVariable("Szerokość", labelsSzerokosc);

        //Zmienne lingwistyczne
        linguisticVariables = List.of(cena, przebieg, mocSilnika, zuzyciePaliwa, pojemnoscSilnika, dlugosc, pojemnoscZbiornikaPaliwa, rozstawOsi, momentObrotowy, szerokosc);

        //Kwantyfikatory Względne
        RelativeQuantifier prawieZaden = new RelativeQuantifier("Prawie żaden", new FuzzySet(new TrapezoidalFunction(0.00, 0.00, 0.10, 0.20), new ContinuousSet(0, 1)));
        RelativeQuantifier niewiele = new RelativeQuantifier("Niewiele", new FuzzySet(new TrapezoidalFunction(0.10, 0.20, 0.25, 0.30), new ContinuousSet(0, 1)));
        RelativeQuantifier mniejNizPolowa = new RelativeQuantifier("Mniej niż połowa", new FuzzySet(new TrapezoidalFunction(0.25, 0.30, 0.40, 0.45), new ContinuousSet(0, 1)));
        RelativeQuantifier okoloPolowa = new RelativeQuantifier("Około połowa", new FuzzySet(new TrapezoidalFunction(0.40, 0.45, 0.55, 0.60), new ContinuousSet(0, 1)));
        RelativeQuantifier sporaCzesc = new RelativeQuantifier("Spora część", new FuzzySet(new TrapezoidalFunction(0.55, 0.60, 0.65, 0.75), new ContinuousSet(0, 1)));
        RelativeQuantifier niemalWszystkie = new RelativeQuantifier("Niemal wszystkie", new FuzzySet(new TrapezoidalFunction(0.65, 0.75, 1.00, 1.00), new ContinuousSet(0, 1)));
        relativeQuantifiers = List.of(prawieZaden, niewiele, mniejNizPolowa, okoloPolowa, sporaCzesc, niemalWszystkie);

        //Kwantyfikatory Absolutne
        AbsoluteQuantifier mniejNiz2000 = new AbsoluteQuantifier("Mniej niż 2000", new FuzzySet(new TrapezoidalFunction(0, 0, 1000, 2000), new ContinuousSet(0, 10_000)));
        AbsoluteQuantifier okolo3000 = new AbsoluteQuantifier("Około 3000", new FuzzySet(new TrapezoidalFunction(1000, 2000, 3000, 4000), new ContinuousSet(0, 10_000)));
        AbsoluteQuantifier miedzy3000_7000 = new AbsoluteQuantifier("Między 3000 a 7000", new FuzzySet(new TrapezoidalFunction(3000, 4000, 6000, 7000), new ContinuousSet(0, 10_000)));
        AbsoluteQuantifier blisko7000 = new AbsoluteQuantifier("Blisko 7000", new FuzzySet(new TrapezoidalFunction(6000, 7000, 7500, 8500), new ContinuousSet(0, 10_000)));
        AbsoluteQuantifier ponad8000 = new AbsoluteQuantifier("Ponad 8000", new FuzzySet(new TrapezoidalFunction(7500, 8000, 10000, 10000), new ContinuousSet(0, 10_000)));
        absoluteQuantifiers = List.of(mniejNiz2000, okolo3000, miedzy3000_7000, blisko7000, ponad8000);

        predefinedQuantifiers = new ArrayList<>();
        predefinedQuantifiers.addAll(absoluteQuantifiers);
        predefinedQuantifiers.addAll(relativeQuantifiers);
    }

    public void generateSummaryBtn_onAction(ActionEvent actionEvent) throws UnsupportedEncodingException {
        measureWeights = retrieveWeights();
        if (!measureWeights.areCorrect()) {
            throw new RuntimeException("Measure weights are incorrect");
        }

        if (quantifierCB.getValue() == null) {
            System.out.println("No quantifier");
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

        List<Quantifier> quantifiers = new ArrayList<>();

        Quantifier selectedQuantifier = quantifierCB.getValue();

        //If quantifier selected, use it as summary quantifier, if not use all predefined quantifiers
        if (selectedQuantifier == null) {
            quantifiers = new ArrayList<>(absoluteQuantifiers);
        } else {
            quantifiers.add(selectedQuantifier);
        }

        if (qualifiers.size() == 0) {
            generateSummariesFirstForm(quantifiers, qualifiers, summarizers);
        } else {
            generateSummariesSecondForm(quantifiers, qualifiers, summarizers);
        }

        fillSummaryTable();
    }

    private void fillSummaryTable() throws UnsupportedEncodingException {
//        // Create the columns
//        TableColumn<Summary, String> summaryColumn = new TableColumn<>("Summary");
//        summaryColumn.setCellValueFactory(new PropertyValueFactory<>("summaryToText"));
//
//        TableColumn<Summary, Double> degreeOfTruthColumn = new TableColumn<>("T1");
//        degreeOfTruthColumn.setCellValueFactory(new PropertyValueFactory<>("degreeOfTruth_T1"));
//        degreeOfTruthColumn.setCellFactory(column -> new RoundedTableCell<>());
//
//        TableColumn<Summary, Double> degreeOfImprecisionColumn = new TableColumn<>("T2");
//        degreeOfImprecisionColumn.setCellValueFactory(new PropertyValueFactory<>("degreeOfImprecision_T2"));
//        degreeOfImprecisionColumn.setCellFactory(column -> new RoundedTableCell<>());
//
//        TableColumn<Summary, Double> degreeOfCoveringColumn = new TableColumn<>("T3");
//        degreeOfCoveringColumn.setCellValueFactory(new PropertyValueFactory<>("degreeOfCovering_T3"));
//        degreeOfCoveringColumn.setCellFactory(column -> new RoundedTableCell<>());
//
//        TableColumn<Summary, Double> degreeOfAppropriatenessColumn = new TableColumn<>("T4");
//        degreeOfAppropriatenessColumn.setCellValueFactory(new PropertyValueFactory<>("degreeOfAppropriateness_T4"));
//        degreeOfAppropriatenessColumn.setCellFactory(column -> new RoundedTableCell<>());
//
//        TableColumn<Summary, Double> degreeOfSummaryColumn = new TableColumn<>("T5");
//        degreeOfSummaryColumn.setCellValueFactory(new PropertyValueFactory<>("degreeOfSummary_T5"));
//        degreeOfSummaryColumn.setCellFactory(column -> new RoundedTableCell<>());
//
//        TableColumn<Summary, Double> degreeOfQuantifierImprecisionColumn = new TableColumn<>("T6");
//        degreeOfQuantifierImprecisionColumn.setCellValueFactory(new PropertyValueFactory<>("degreeOfQuantifierImprecision_T6"));
//
//        TableColumn<Summary, Double> degreeOfQuantifierCardinalityColumn = new TableColumn<>("T7");
//        degreeOfQuantifierCardinalityColumn.setCellValueFactory(new PropertyValueFactory<>("degreeOfQuantifierCardinality_T7"));
//
//        TableColumn<Summary, Double> degreeOfSummarizerCardinalityColumn = new TableColumn<>("T8");
//        degreeOfSummarizerCardinalityColumn.setCellValueFactory(new PropertyValueFactory<>("degreeOfSummarizerCardinality_T8"));
//
//        TableColumn<Summary, Double> degreeOfQualifierImprecisionColumn = new TableColumn<>("T9");
//        degreeOfQualifierImprecisionColumn.setCellValueFactory(new PropertyValueFactory<>("degreeOfQualifierImprecision_T9"));
//
//        TableColumn<Summary, Double> degreeOfQualifierCardinalityColumn = new TableColumn<>("T10");
//        degreeOfQualifierCardinalityColumn.setCellValueFactory(new PropertyValueFactory<>("degreeOfQualifierCardinality_T10"));
//
//        TableColumn<Summary, Double> lengthOfQualifierColumn = new TableColumn<>("T11");
//        lengthOfQualifierColumn.setCellValueFactory(new PropertyValueFactory<>("lengthOfQualifier_T11"));
//
//        // Add the columns to the TableView
//        summaryTable.getColumns().addAll(
//                summaryColumn,
//                degreeOfTruthColumn,
//                degreeOfImprecisionColumn,
//                degreeOfCoveringColumn,
//                degreeOfAppropriatenessColumn,
//                degreeOfSummaryColumn,
//                degreeOfQuantifierImprecisionColumn,
//                degreeOfQuantifierCardinalityColumn,
//                degreeOfSummarizerCardinalityColumn,
//                degreeOfQualifierImprecisionColumn,
//                degreeOfQualifierCardinalityColumn,
//                lengthOfQualifierColumn
//        );

        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        summaries.forEach(summary -> System.out.println(String.format("%s T:%f T1: %f T2: %f T3: %f T4: %f T5: %f T6: %f T7: %f T8: %f T9: %f T10: %f T11: %f",
                summaryToText(summary),
                summary.calculateQuality(),
                summary.getDegreeOfTruth_T1(),
                summary.getDegreeOfImprecision_T2(),
                summary.getDegreeOfCovering_T3(),
                summary.getDegreeOfAppropriateness_T4(),
                summary.getDegreeOfSummary_T5(),
                summary.getDegreeOfQuantifierImprecision_T6(),
                summary.getDegreeOfQuantifierCardinality_T7(),
                summary.getDegreeOfOfSummarizerCardinality_T8(),
                summary.getDegreeOfQualifierImprecision_T9(),
                summary.getDegreeOfQualifierCardinality_T10(),
                summary.getLengthOfQualifier_T11())));

//        // Create a list of Summary objects
//        ObservableList<Summary> summaryList = FXCollections.observableArrayList();
//        // Add Summary objects to the list (replace with your actual data)
//        summaryList.addAll(summaries);
//
//        // Set the items of the TableView to the list of Summary objects
//        summaryTable.setItems(summaryList);
    }

    private double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }

    private List<Summary> summaries = new ArrayList<>();

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
                        Summary summary = new Summary(measureWeights, qualifiers, quantifier, tempSumList, carDetailsList);
                        summaries.add(summary);
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
                    Summary summary = new Summary(measureWeights, qualifiers, quantifier, tempSumList, carDetailsList);
                    summaries.add(summary);
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

    public String summaryToText(Summary summary) {
        StringBuilder sb = new StringBuilder();
        sb.append(summary.getQuantifier().getName().toUpperCase(Locale.ROOT)).append(" obiektów ");
        if (!summary.isFirstForm()) {
            sb.append("będąca/mająca ");
            for (int i = 0; i < summary.getQualifiers().size(); i++) {
                Label qualifier = summary.getQualifiers().get(i);
                sb.append(qualifier.getName().toUpperCase(Locale.ROOT)).append(" ")
                        .append(qualifier.getLinguisticVariableName().toLowerCase(Locale.ROOT));
                if (i + 1 < summary.getQualifiers().size()) {
                    sb.append(" i ");
                }
            }
        }
        sb.append(" ma ");
        for (int i = 0; i < summary.getSummarizers().size(); i++) {
            Label summarizer = summary.getSummarizers().get(i);
            sb.append(summarizer.getName().toUpperCase(Locale.ROOT)).append(" ")
                    .append(summarizer.getLinguisticVariableName().toLowerCase(Locale.ROOT));
            if (i + 1 < summary.getSummarizers().size()) {
                sb.append(" i ");
            }
        }
        return sb.toString();
    }

    private Label findLabel(String variableName, String labelName) {
        for (LinguisticVariable var : linguisticVariables) {
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

    private Quantifier findQuantifierByName(String name) {
        for (Quantifier quantifier : predefinedQuantifiers) {
            if (quantifier.getName().equals(name)) {
                return quantifier;
            }
        }
        return null;
    }

    public class RoundedTableCell<S, T> extends TableCell<S, T> {
        private final DecimalFormat decimalFormat;

        public RoundedTableCell() {
            decimalFormat = new DecimalFormat("#.00");
        }

        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setText(null);
            } else {
                setText(decimalFormat.format(item));
            }
        }
    }
}
