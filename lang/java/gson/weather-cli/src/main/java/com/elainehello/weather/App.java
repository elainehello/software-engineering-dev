package com.elainehello.weather;

import com.elainehello.weather.api.ApiClient;
import com.elainehello.weather.model.Coordenadas;
import com.elainehello.weather.model.Municipio;
import com.elainehello.weather.service.MunicipioService;
import com.elainehello.weather.storage.CacheStore;
import com.elainehello.weather.util.Geo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class App {

    private static final Coordenadas PUERTA_DEL_SOL = new Coordenadas(40.4169, -3.7035);

    public static void main(String[] args) {
        String query = args.length > 0 ? String.join(" ", args) : "madrid";

        try {
            CacheStore cache = new CacheStore(Path.of("cache"));
            ApiClient api = new ApiClient();
            MunicipioService service = new MunicipioService();

            // 1. Get data: cache first, API on a miss
            List<Municipio> results;
            Optional<List<Municipio>> cached = cache.load(query);
            if (cached.isPresent()) {
                System.out.println("[cache] " + cached.get().size() + " results for '" + query + "'");
                results = cached.get();
            } else {
                System.out.println("[api] fetching '" + query + "'...");
                results = api.search(query);
                cache.save(query, results);
            }

            // 2. Overview
            System.out.println("\nResults per country: " + service.contarPorPais(results));

            // 3. Spanish results grouped by province
            System.out.println("\nSpain, by province:");
            service.porProvincia(service.soloEspana(results)).forEach((prov, list) -> {
                System.out.println("  " + prov + ":");
                list.forEach(m -> System.out.println("    - " + m.nombre()));
            });

            // 4. Nearest to Puerta del Sol
            System.out.println("\nNearest to Puerta del Sol:");
            service.masCercanos(service.soloEspana(results), PUERTA_DEL_SOL, 3)
                    .forEach(m -> System.out.printf(Locale.ROOT, "  %s: %.1f km%n",
                            m.nombre(), Geo.km(PUERTA_DEL_SOL, m.coordenadas())));

            // 5. Export with Gson (pretty JSON)
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Files.writeString(Path.of("out.json"), gson.toJson(results));
            System.out.println("\nWrote out.json (" + results.size() + " items)");

        } catch (IOException e) {
            System.err.println("I/O or network error: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupted");
        }
    }
}