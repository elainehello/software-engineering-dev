package accessmodifiers.parent; // same package as Vehicle.class

public class GarageService {

    public void service(Vehicle vehicle) {
        System.out.println("-- GarageService (same package as Vehicle.class) --");

        // public - always accessible
        System.out.println("  Brand: " + vehicle.brand);

        // protected - accesible from same package
        System.out.println("  Engine: " + vehicle.engineType);

        // package-private - accessible because GarageService.class is in the same package as Vehicle.class
        System.out.println("  Factory code: " + vehicle.factoryCode);

        // private - (X) NOT accessible even from packages
        // System.out.println(vehicle.internalSerialNumber);

        // package-private method - accessible because GarageService.class is in the same package as Vehicle.class
        vehicle.runFactoryCheck();
    }
}
