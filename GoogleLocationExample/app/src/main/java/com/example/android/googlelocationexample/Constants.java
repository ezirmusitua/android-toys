package com.example.android.googlelocationexample;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by jferroal on 2017-05-29.
 */

public final class Constants {
    private Constants() {}

    public static final String PACKAGE_NAME = "com.example.android.googlelocationexample";
    public static final String BROADCAST_ACTION = PACKAGE_NAME + ".BROADCAST_ACTION";
    public static final String ACTIVITY_EXTRA = PACKAGE_NAME + ".ACTIVITY_EXTRA";
    public static final Integer GEO_FENCE_RADIUS_IN_METERS = 1;
    public static final Integer GEO_FENCE_EXPIRATION_IN_MILLISECONDS = 1000;
    public static final HashMap<String, LatLng> BAY_AREA_LANDMARKS = new HashMap<>();
    static {
        BAY_AREA_LANDMARKS.put("HOME", new LatLng(31.281625, 121.4515546));
    }
}
