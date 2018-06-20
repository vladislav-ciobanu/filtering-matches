package com.vciobanu.filteringmatches.util;

import com.vciobanu.filteringmatches.model.City;
import org.springframework.stereotype.Component;

@Component
public class GeoUtil {

    public double calculateDistanceInKm(City city1, City city2) {
        return calculateDistance(city1, city2) * 1.609344;
    }

    private double calculateDistance(City city1, City city2) {
        double theta = city1.getLon() - city2.getLon();

        double distance = Math.sin(degToRad(city1.getLat())) * Math.sin(degToRad(city2.getLat()))
                + Math.cos(degToRad(city1.getLat())) * Math.cos(degToRad(city2.getLat())) * Math.cos(degToRad(theta));

        return radToDeg(Math.acos(distance)) * 60 * 1.1515;
    }

    private double degToRad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double radToDeg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
