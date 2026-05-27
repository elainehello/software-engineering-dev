package lsp.birds;

// Sparrow extends FlyingBird — small but genuinely flies
public class Sparrow extends FlyingBird {

    public Sparrow(String name) {
        super(name, "Passer domesticus", 150);
    }

    @Override
    public String fly() {
        return String.format("  🐦 %s flutters up to %.0fm", getName(), getMaxAltitudeMeters());
    }

    @Override
    public String makeSound() {
        return String.format("  %s chirps: tweet tweet!", getName());
    }
}