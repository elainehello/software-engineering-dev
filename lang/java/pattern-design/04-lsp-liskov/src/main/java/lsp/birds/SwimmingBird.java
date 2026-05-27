package lsp.birds;

// Capacity Layer: only birds that actually swim extend this
public abstract class SwimmingBird extends Bird {

    private double maxDepthMeters;

    protected SwimmingBird(String name, String species, double maxDepthMeters) {
        super(name, species); // super() does call to Bird constructor to set name and species
        this.maxDepthMeters = maxDepthMeters;
    }

    public double getMaxDepthMeters() {
        return maxDepthMeters;
    }

    // Safe here - every SwimmingBird genuinely swims
    public abstract String swim();
}
