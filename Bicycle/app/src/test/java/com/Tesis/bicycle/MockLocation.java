package com.Tesis.bicycle;

import android.location.Location;

public class MockLocation extends Location {
    public MockLocation(String provider) {
        super(provider);
    }

    @Override
    public double getAltitude() {
        return super.getAltitude();
    }

    @Override
    public void setLatitude(double latitude) {
        super.setLatitude(latitude);
    }

    @Override
    public double getLongitude() {
        return super.getLongitude();
    }

    @Override
    public void setLongitude(double longitude) {
        super.setLongitude(longitude);
    }

    @Override
    public float getSpeed() {
        return super.getSpeed();
    }

    @Override
    public void setSpeed(float speed) {
        super.setSpeed(speed);
    }

    @Override
    public float getAccuracy() {
        return super.getAccuracy();
    }

    @Override
    public void setAccuracy(float horizontalAccuracy) {
        super.setAccuracy(horizontalAccuracy);
    }
}
