package com.example.android.googlelocationexample;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

public class GeoFenceTransitionIntentService extends IntentService {
    protected static final String TAG = "J_GEO_FENCE";

    public GeoFenceTransitionIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMsg = GeofenceErrorMessages.getErrorMessage(geofencingEvent.getErrorCode());
            Log.d(TAG, errorMsg);
            return;
        }

        int geoFenceTransition = geofencingEvent.getGeofenceTransition();
        Boolean isValidGeoFenceTransition = geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT;
        if (isValidGeoFenceTransition) {
            List<Geofence> triggeringGeoFences = geofencingEvent.getTriggeringGeofences();
            // Get the transition details as a String.
            String geoFenceTransitionDetails = getGeoFenceTransitionDetails(geoFenceTransition, triggeringGeoFences
            );

            // Send notification and log the transition details.
            sendNotification(geoFenceTransitionDetails);
        }
    }

    private String getGeoFenceTransitionDetails(int geoFenceTransition, List<Geofence> triggeringGeoFences) {

        String geoFenceTransitionString = getTransitionString(geoFenceTransition);

        ArrayList<String> triggeringGeoFencesIdsList = new ArrayList<>();
        for (Geofence geofence : triggeringGeoFences) {
            triggeringGeoFencesIdsList.add(geofence.getRequestId());
        }
        String triggeringGeoFencesIdsString = TextUtils.join(", ", triggeringGeoFencesIdsList);

        return geoFenceTransitionString + ": " + triggeringGeoFencesIdsString;
    }


    private void sendNotification(String notificationDetails) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(MainActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.fat_cat)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.fat_cat))
                .setColor(Color.RED)
                .setContentTitle(notificationDetails)
                .setContentText("Geo fence notification text")
                .setContentIntent(notificationPendingIntent);

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
    }

    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return "Geo fence transition enter";
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return "Geo fence transition exit";
            default:
                return "Unknown geo fence transition";
        }
    }
}
