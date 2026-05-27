package lsp.birds;

// ── LSP CORRECT ───────────────────────────────────────────────────────────────
// Ostrich extends Bird directly — it neither flies nor swims.
// It adds its own capability (run) without inheriting anything it can't honour.
// No UnsupportedOperationException, no broken contract anywhere.
public class Ostrich extends Bird {

    private double topSpeedKmh;

    public Ostrich(String name, double topSpeedKmh) {
        super(name, "Struthio camelus");
        this.topSpeedKmh = topSpeedKmh;
    }

    // Ostrich-specific capability — not forced into any hierarchy it doesn't fit
    public String run() {
        return String.format("  🦤 %s sprints at %.0f km/h!", getName(), topSpeedKmh);
    }

    @Override
    public String makeSound() {
        return String.format("  %s booms: BOOM BOOM!", getName());
    }
}