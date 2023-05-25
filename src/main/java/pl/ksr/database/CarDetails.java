package pl.ksr.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
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
