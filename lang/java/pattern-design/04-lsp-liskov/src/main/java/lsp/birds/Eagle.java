package lsp.birds;

// Eagle extends FlyingBird — it genuinely flies, substitution is safe
public class Eagle extends FlyingBird {

    private double wingspanCm;

    public Eagle(String name, double wingspanCm) {
        super(name, "Aquila chrysaetos", 3000);
        this.wingspanCm = wingspanCm;
    }

    @Override
    public String fly() {
        return String.format("  🦅 %s soars up to %.0fm — wingspan: %.0fcm",
                getName(), getMaxAltitudeMeters(), wingspanCm);
    }

    @Override
    public String makeSound() {
        return String.format("  %s screams: KREEEE!", getName());
    }
}