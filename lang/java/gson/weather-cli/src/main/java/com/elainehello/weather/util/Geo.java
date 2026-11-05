package com.elainehello.weather.util;

import com.elainehello.weather.model.Coordenadas;

public final class Geo {

    private static final double EARTH_RADIUS_KM = 6371.0;

    private Geo() {}

    /** Haversine distance in kilometres. */
    public static double km(Coordenadas a, Coordenadas b) {
        double dLat = Math.toRadians(b.lat() - a.lat());
        double dLon = Math.toRadians(b.lon() - a.lon());
        double h = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(Math.toRadians(a.lat())) * Math.cos(Math.toRadians(b.lat()))
                * Math.pow(Math.sin(dLon / 2), 2);
        return 2 * EARTH_RADIUS_KM * Math.asin(Math.sqrt(h));
    }
}
