package pl.ksr.view.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.ksr.logic.model.CarDetails;
import pl.ksr.logic.utils.CarDetailsReader;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    public TableView<CarDetails> carDetailsTable;

    private final List<CarDetails> carDetailsList;

    public MainViewController() {
        carDetailsList = CarDetailsReader.readDataCsv();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillTable();
    }

    private void fillTable() {
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
}
