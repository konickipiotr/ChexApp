package com.chex.utils;

import android.location.Location;

public class Coords {
    public double latitude;
    public double longitude;

    public Coords(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Coords(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    @Override
    public String toString() {
        return "Coords{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
