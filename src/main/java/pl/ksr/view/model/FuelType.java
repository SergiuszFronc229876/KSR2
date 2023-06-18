package pl.ksr.view.model;

public enum FuelType {
    GASOLINE("Benzyna", "Gasoline"),
    DIESEL("Diesel", "Diesel");

    private final String name;
    private final String dbName;

    FuelType(String name, String dbName) {
        this.name = name;
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }

    @Override
    public String toString() {
        return name;
    }
}
