package com.elainehello.weather.model;

import java.io.Serializable;
import java.util.Locale;

public record Municipio(String id,
                        String nombre,
                        String slug,
                        String provincia,
                        String pais,
                        Coordenadas coordenadas) implements Serializable {


    // Municipio method does receive as parameter an object dto
    // which contains (id, municipio, slug, provincia, paisNombre)
    // parameters living in the class MunicipioDto
    public static Municipio from(MunicipioDto d) {
        return new Municipio(
                d.id,
                d.municipio,
                d.slug,
                d.provincia,
                d.paisNombre == null ? "España" : d.paisNombre,
                new Coordenadas(Double.parseDouble(d.lat), Double.parseDouble(d.lon)));
    }

    public boolean esEspana() {
        return "España".equals(pais);
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s (%s %s) [%.4f, %.4f]",
                nombre, provincia, pais, coordenadas.lat(), coordenadas.lon());
    }

}
