package accessmodifiers;

import accessmodifiers.child.ElectricCar;
import accessmodifiers.parent.GarageService;
import accessmodifiers.parent.Vehicle;

public class Main {

    public static void main(String[] args) {

        Vehicle car = new Vehicle("Toyota", "V6", "TY-001", "SN-999");

        // ── What Main can access (different package, not a subclass) ─────────
        System.out.println("── Main class (different package, not a subclass) ──");

        // public field
        System.out.println("  Brand: " + car.brand);

        // protected — NOT accessible here (not a subclass)
        // System.out.println(car.engineType);  // compile error

        // package-private — NOT accessible from different package
        // System.out.println(car.factoryCode); // compile error

        // private — never accessible
        // System.out.println(car.internalSerialNumber); // compile error

        // public methods — always accessible
        System.out.println("  Summary: " + car.getSummary());
        car.triggerReset();

        // ── GarageService: same package as Vehicle ───────────────────────────
        System.out.println();
        GarageService garage = new GarageService();
        garage.service(car);

        // ── ElectricCar: different package but subclass ──────────────────────
        System.out.println();
        ElectricCar tesla = new ElectricCar("Tesla", 100);
        tesla.showAccessLevels();
        System.out.println("  Full summary: " + tesla.getSummary());
    }
}