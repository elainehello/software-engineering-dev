package lsp;

import lsp.birds.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // ── Build the flock ───────────────────────────────────────────────────
        Eagle   eagle   = new Eagle("Eddie", 220);
        Sparrow sparrow = new Sparrow("Sam");
        Penguin penguin = new Penguin("Pete", 36);
        Ostrich ostrich = new Ostrich("Oscar", 72);

        // ── All birds share the base contract safely ──────────────────────────
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       ALL BIRDS — base contract      ║");
        System.out.println("╚══════════════════════════════════════╝");

        List<Bird> allBirds = List.of(eagle, sparrow, penguin, ostrich);
        allBirds.forEach(b -> {
            System.out.println("  " + b);
            System.out.println(b.makeSound());
            System.out.println(b.eat("their favourite food"));
        });

        // ── Flying birds — Penguin and Ostrich never enter this list ──────────
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       FLYING BIRDS only              ║");
        System.out.println("╚══════════════════════════════════════╝");

        List<FlyingBird> flyingBirds = List.of(eagle, sparrow);
        flyingBirds.forEach(b -> System.out.println(b.fly()));

        // ── Swimming birds — Eagle and Ostrich never enter this list ──────────
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       SWIMMING BIRDS only            ║");
        System.out.println("╚══════════════════════════════════════╝");

        List<SwimmingBird> swimmingBirds = List.of(penguin);
        swimmingBirds.forEach(b -> System.out.println(b.swim()));

        // ── Ostrich runs — its own capability, no hierarchy abuse ─────────────
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       OSTRICH — unique capability    ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println(ostrich.run());

        // ── The LSP guarantee ─────────────────────────────────────────────────
        System.out.println("\n── LSP holds because: ──────────────────");
        System.out.println("  ✓ Every Bird substitutes safely in allBirds");
        System.out.println("  ✓ Every FlyingBird genuinely flies — no throws");
        System.out.println("  ✓ Penguin never enters flyingBirds — impossible by type");
        System.out.println("  ✓ Ostrich never enters flyingBirds or swimmingBirds");
        System.out.println("  ✓ Zero instanceof checks, zero runtime surprises");
    }
}