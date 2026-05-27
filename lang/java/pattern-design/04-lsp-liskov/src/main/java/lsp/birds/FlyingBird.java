package lsp.birds;

// -- Capacity Layer: only birds that actually fly extend this
// Penguin and Ostrich will never touch this class - no violation possible
// abstract class because we don't want to create a generic "FlyingBird" - we want to create specific types of flying birds (Sparrow, Eagle, etc.)
public abstract class FlyingBird extends Bird {

    private double maxAltitudeMeters;

    // protected constructor doesn't allow outside code to create a FlyingBird directly - only subclasses can call this constructor (Sparrow, Eagle, etc.)
    protected FlyingBird(String name, String species, double maxAltitudeMeters) {
        super(name, species); // super() does call to Bird constructor to set name and species
        this.maxAltitudeMeters = maxAltitudeMeters;
    }

    public double getMaxAltitudeMeters() {
        return maxAltitudeMeters;
    }

    // Safe to define here - every subclass of FlyingBird genuinely flies
    // abstract method doesn't have an implementation here - each specific flying bird will implement it in its own way (Sparrow might say "Flap flap!", Eagle might say "Soar high!")
    public abstract String fly();
}
