package com.elainehello.weather.service;

import com.elainehello.weather.model.Coordenadas;
import com.elainehello.weather.model.Municipio;
import com.elainehello.weather.util.Geo;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MunicipioService {

    public List<Municipio> soloEspana(List<Municipio> all) {
        return all.stream().filter(Municipio::esEspana).toList();
    }

    public Map<String, List<Municipio>> porProvincia(List<Municipio> all) {
        return all.stream()
                .collect(Collectors.groupingBy(Municipio::provincia, TreeMap::new, Collectors.toList()));
    }

    public Map<String, Long> contarPorPais(List<Municipio> all) {
        return all.stream()
                .collect(Collectors.groupingBy(Municipio::pais, TreeMap::new, Collectors.counting()));
    }

    public List<Municipio> masCercanos(List<Municipio> all, Coordenadas origen, int limite) {
        return all.stream()
                .sorted(Comparator.comparingDouble(m -> Geo.km(origen, m.coordenadas())))
                .limit(limite)
                .toList();
    }
}