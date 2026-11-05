package com.elainehello.weather.storage;

import com.elainehello.weather.model.Municipio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CacheStore {

    // const attribute
    private static final Duration MAX_AGE = Duration.ofHours(24);
    private final Path dir;

    // constructor (?) handle creation failure
    public CacheStore(Path dir) throws IOException {
        this.dir = dir;
        Files.createDirectories(dir);
    }

    private Path fileFor(String query) {
        //regex condition
        String safe = query.toLowerCase().replaceAll("[^a-z0-9]", "_");
        return dir.resolve("search-" + safe + ".ser");
    }

    // Wrapped (?) optional return null-safety
    public Optional<List<Municipio>> load(String query) {
        Path file = fileFor(query);
        try {
            if (!Files.exists(file)) return Optional.empty();

            Instant modified = Files.getLastModifiedTime(file).toInstant();
            if (modified.plus(MAX_AGE).isBefore(Instant.now())) return Optional.empty();

            try (var in = new ObjectInputStream(Files.newInputStream(file))) {
                @SuppressWarnings("unchecked")
                List<Municipio> data = (List<Municipio>) in.readObject();
                return Optional.of(data);
            }
        } catch (IOException | ClassNotFoundException e) {
            // Corrupt or incompatible cache: ignore it and refetch
            System.err.println("Cache ignored (" + e.getClass().getSimpleName() + ")");
            return Optional.empty();
        }
    }

    public void save(String query, List<Municipio> data) throws IOException {
        try (var out = new ObjectOutputStream(Files.newOutputStream(fileFor(query)))) {
            out.writeObject(new ArrayList<>(data));   // ArrayList is Serializable
        }
    }
}
