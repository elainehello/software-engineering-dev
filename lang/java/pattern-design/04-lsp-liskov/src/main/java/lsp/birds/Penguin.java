package lsp.birds;

// ── LSP CORRECT ───────────────────────────────────────────────────────────────
// Penguin extends SwimmingBird — NOT FlyingBird.
// No throw, no empty override, no lie. It simply never enters the flying hierarchy.
// Any code working with FlyingBird will never receive a Penguin — by design.
public class Penguin extends SwimmingBird {

    private double topSpeedKmh;

    public Penguin(String name, double topSpeedKmh) {
        super(name, "Spheniscidae", 535);
        this.topSpeedKmh = topSpeedKmh;
    }

    @Override
    public String swim() {
        return String.format("  🐧 %s dives to %.0fm deep at %.0f km/h",
                getName(), getMaxDepthMeters(), topSpeedKmh);
    }

    @Override
    public String makeSound() {
        return String.format("  %s brays: BWAAA!", getName());
    }
}