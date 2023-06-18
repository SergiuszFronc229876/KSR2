package pl.ksr.view.model;

public enum FuelType {
    GASOLINE("Benzyna"),
    DIESEL("Diesel");

    private final String name;

    FuelType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}