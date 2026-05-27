package lsp.birds;

// -- Base: only what is universally true for ALL birds
// No fly() here - that assumption is exactly what breaks LSP with Penguin
// Every bird eats, sleeps, and makes a sound - nothing more assumed.
// Abstract class because we don't want to create a generic "Bird" - we want to create specific types of birds (Sparrow, Penguin, etc.)
public abstract class Bird {

    private final String name;
    private final String species;

    protected Bird(String name, String species) {
        this.name = name;
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public String getRole() {
        return this.getClass().getSimpleName();
    }

    // Every bird makes a sound - universally true, (safe in base) (abstract)
    public abstract String makeSound();

    // Every bird eats - universally true
    public String eat(String food) {
        return String.format("  %s (%s) eats %s", name, getRole(), food);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s", getRole(), name, species);
    }
}
