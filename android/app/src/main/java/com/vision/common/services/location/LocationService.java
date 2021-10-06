package com.vision.common.services.location;

public class LocationService {
    private static LocationService sInstance;

    private LocationService() {

    }

    public static synchronized LocationService get() {
        if (sInstance == null) {
            sInstance = new LocationService();
        }

        return sInstance;
    }

    public void startTracking() {

    }

    public void stopTracking() {
        
    }
}
