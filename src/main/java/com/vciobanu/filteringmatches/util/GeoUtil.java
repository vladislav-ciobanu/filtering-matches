package com.vciobanu.filteringmatches.util;

import com.vciobanu.filteringmatches.model.City;

public class GeoUtil {

    public static double calculateDistanceInKm(City city1, City city2) {
        return calculateDistance(city1, city2) * 1.609344;
    }

    private static double calculateDistance(City city1, City city2) {
        double lon1 = city1.getLon();
        double lon2 = city2.getLon();
        double lat1 = city1.getLat();
        double lat2 = city2.getLat();

        double theta = lon1 - lon2;
        double distance = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        distance = Math.acos(distance);
        distance = rad2deg(distance);
        return distance * 60 * 1.1515;
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
