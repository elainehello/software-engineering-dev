package com.elainehello.weather.model;


import com.google.gson.annotations.SerializedName;

// Municipio data transfer object
public class MunicipioDto {
    String id;
    String municipio;
    String slug;
    String lat;
    String lon;
    String provincia;

    @SerializedName("pais_slug")
    String paisSlug; // null for Spain

    @SerializedName("pais_nombre")
    String paisNombre;
}
