package accessmodifiers.child;

import accessmodifiers.parent.Vehicle;

public class ElectricCar extends Vehicle {

    private int batteryCapacityKwh;

    public ElectricCar(String brand, int batteryCapacityKwh) {
        // super() call to the parent (inherited) class
        super(brand, "Electric", "EV-FACTORY", "EV-" + batteryCapacityKwh + "-001");
        this.batteryCapacityKwh = batteryCapacityKwh;
    }

    public void showAccessLevels() {
        System.out.println("── ElectricCar (different package, is a subclass) ──");

        // public — accessible everywhere
        System.out.println("  Brand: " + brand);

        // protected — accessible in subclass even from different package
        System.out.println("  Engine type: " + engineType);

        // package-private — NOT accessible from different package, even in subclass
        // System.out.println(factoryCode);  // compile error

        // (X) private — never accessible outside Vehicle
        // System.out.println(internalSerialNumber);  // compile error

        // protected method — subclass can call it
        System.out.println("  Diagnostics: " + getDiagnostics());

        // (X) package-private method — not accessible from different package
        // runFactoryCheck();  // compile error
    }

    //  overriding a protected method — allowed in subclass
    @Override
    protected String getDiagnostics() {
        return super.getDiagnostics() + " | Battery: " + batteryCapacityKwh + " kWh";
    }

    public int getBatteryCapacity() {
        return batteryCapacityKwh;
    }
}
