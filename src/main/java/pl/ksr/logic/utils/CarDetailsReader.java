package pl.ksr.logic.utils;

import lombok.experimental.UtilityClass;
import pl.ksr.logic.model.CarDetails;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CarDetailsReader {
    public static List<CarDetails> readDataCsv() {
        List<CarDetails> carDetailsList = new ArrayList<>();

        try {
            // Read the data.csv file from the resources directory
            InputStream inputStream = CarDetailsReader.class.getResourceAsStream("/data.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Skip the header line if it exists
            String headerLine = reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by semicolons and create a CarDetails object
                String[] data = line.split(";");

                long id = Long.parseLong(data[0]);
                String franchiseName = data[1];
                String modelName = data[2];
                double price = Double.parseDouble(data[3].replace(",", "."));
                double mileage = Double.parseDouble(data[4].replace(",", "."));
                double horsepower = Double.parseDouble(data[5].replace(",", "."));
                double engineDisplacement = Double.parseDouble(data[6].replace(",", "."));
                double fuelTankVolume = Double.parseDouble(data[7].replace(",", "."));
                double wheelbase = Double.parseDouble(data[8].replace(",", "."));
                double torque = Double.parseDouble(data[9].replace(",", "."));
                double length = Double.parseDouble(data[10].replace(",", "."));
                double width = Double.parseDouble(data[11].replace(",", "."));
                double fuelEconomy = Double.parseDouble(data[12].replace(",", "."));

                CarDetails carDetails = new CarDetails(id, franchiseName, modelName, price, mileage, horsepower, engineDisplacement,
                        fuelTankVolume, wheelbase, torque, length, width, fuelEconomy);

                carDetailsList.add(carDetails);
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return carDetailsList;
    }
}
