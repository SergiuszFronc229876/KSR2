package pl.ksr.logic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class CarDetails {
    @Id
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
