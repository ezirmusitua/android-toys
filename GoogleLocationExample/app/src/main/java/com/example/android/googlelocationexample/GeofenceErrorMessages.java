package com.example.android.googlelocationexample;

import com.google.android.gms.location.GeofenceStatusCodes;

/**
 * Created by jferroal on 2017-05-29.
 */

public final class GeofenceErrorMessages {
    private GeofenceErrorMessages() {}

    public static String getErrorMessage(Integer errorCode) {
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return "Geo fence not available";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return "Geo fence too many geo fences";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return "Geo fence too many pending intents";
            default:
                return "Unknown geo fence error";
        }
    }
}
