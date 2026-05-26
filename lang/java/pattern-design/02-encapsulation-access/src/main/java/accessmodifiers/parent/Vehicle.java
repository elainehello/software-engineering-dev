package accessmodifiers.parent;

public class Vehicle {

    // public - accesible from everywhere
    public String brand;

    // protected - accessible within the same package and subclasses
    protected String engineType;

    // package-private (default) - accessible only within the same package
    String factoryCode;

    // private - accessible only within the same class
    private String internalSerialNumber;

    // Constructor
    public Vehicle(String brand, String engineType, String factoryCode, String internalSerialNumber) {
        this.brand = brand;
        this.engineType = engineType;
        this.factoryCode = factoryCode;
        this.internalSerialNumber = internalSerialNumber;
    }

    // -- public method -- anyone can access/call this method
    public String getSummary() {
        return String.format("Brand: %s | Engine: %s", brand, engineType);
    }

    // -- protected method -- only subclasses and classes in the same package can access/call/override this method
    protected String getDiagnostics() {
        return String.format("Factory: %s | Serial: %s", factoryCode, internalSerialNumber);
    }

    // -- package-private method - only classes in accessmodifiers.parent
    void runFactoryCheck() {
        System.out.println("  [Factory check] Serial: " + internalSerialNumber);
    }

    // -- private method -- only this class (Vehicle) can access/call this method
    private void resetInternalSerial() {
        this.internalSerialNumber = "RESET-000";
        System.out.println("  [Internal] Serial reset.");
    }

    // -- public method that internally delegates to private method
    public void triggerReset() {
        System.out.println("Triggering internal reset...");
        resetInternalSerial(); // -- private method called from within same class
    }
}
