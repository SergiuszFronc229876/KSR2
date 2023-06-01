package pl.ksr.logic.model;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CarDetails {
    private long id;
    private String franchiseName;
    private String modelName;
    private double price;
    private double mileage;
    private double horsepower;
    private double engineDisplacement;
    private double fuelTankVolume;
    private double wheelbase;
    private double torque;
    private double length;
    private double width;
    private double fuelEconomy;
}
