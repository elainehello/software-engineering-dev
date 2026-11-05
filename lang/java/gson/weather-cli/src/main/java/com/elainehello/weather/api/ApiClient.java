package com.elainehello.weather.api;

import com.elainehello.weather.model.Municipio;
import com.elainehello.weather.model.MunicipioDto;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ApiClient {

    private static final String BASE = "https://eltiempoen.net/api";

    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    private final Gson gson = new Gson();

    // Search Method
    public List<Municipio> search(String query) throws IOException, InterruptedException {
        URI uri = URI.create(
                BASE + "/search?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8));

        // build the request
        HttpRequest request = HttpRequest.newBuilder(uri)
                .timeout(Duration.ofSeconds(10))
                .header("Accept", "application/json")
                .headers("User-Agent", "weather-cli-learning/0.1")
                .GET()
                .build();

        // response ack
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("HTTP" + response.statusCode() + " for " + uri);
        }

        try {
            MunicipioDto[] dtos = gson.fromJson(response.body(), MunicipioDto[].class);
            if (dtos == null) {
                return List.of(); // empty body makes Gson return null
            }
            return Arrays.stream(dtos).map(Municipio::from).toList();
        } catch (JsonSyntaxException | NumberFormatException e) {
            throw new IOException("Unexpected response format", e);
        }

    }
}
