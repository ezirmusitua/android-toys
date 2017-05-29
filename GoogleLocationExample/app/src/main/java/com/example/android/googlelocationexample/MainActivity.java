package com.example.android.googlelocationexample;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback,
        ResultCallback<Status>,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private final String LOG_TAG = "J_LOCATION_EXAMPLE";
    private ArrayList<Geofence> geoFences;
    private TextView tvLocation;
    private TextView tvActivityRecognize;
    private GoogleApiClient locationApiClient;
    private GoogleApiClient activityRecognizeClient;
    private ActivityDetectionBroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.locationApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        this.activityRecognizeClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        this.tvLocation = (TextView) findViewById(R.id.text_location);
        this.tvActivityRecognize = (TextView) findViewById(R.id.text_activity_recognize);
        this.broadcastReceiver = new ActivityDetectionBroadcastReceiver();

        this.geoFences = new ArrayList<>();
        populateGeoFences();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        this.locationApiClient.connect();
        this.locationApiClient.connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Boolean noPermission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        if (noPermission) {
            String[] needPermissions = { android.Manifest.permission.ACCESS_FINE_LOCATION };
            ActivityCompat.requestPermissions(this, needPermissions, 0);
        } else {
            this.locationApiClient.connect();
            this.activityRecognizeClient.connect();
        }
    }

    @Override
    protected void onStop() {
        this.locationApiClient.disconnect();
        this.activityRecognizeClient.disconnect();
        super.onStop();
    }


    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.broadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(this.broadcastReceiver, new IntentFilter(Constants.BROADCAST_ACTION));
        super.onResume();
    }

    @Override
    public void onResult(Status status) {
        // do nothing
        if (status.isSuccess()) {
            Toast.makeText(this, "Geo fences Added", Toast.LENGTH_SHORT).show();
        } else {
            // Get the status code for the error and log it using a user-friendly message.
            String errorMessage = GeofenceErrorMessages.getErrorMessage(status.getStatusCode());
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(this.LOG_TAG, "connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult cr) {
        Log.i(this.LOG_TAG, "connection failed");
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationRequest ggLocationReq = LocationRequest.create();
        ggLocationReq.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        ggLocationReq.setInterval(1000);
        if (this.locationApiClient.isConnected()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(this.locationApiClient,
                    ggLocationReq, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(this.LOG_TAG, location.toString());
        String latitude = Double.toString(location.getLatitude());
        String longitude = Double.toString(location.getLongitude());
        String text = latitude + "|" + longitude;
        this.tvLocation.setText(text);
    }

    public void requestActivityUpdates(View view) {
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(
                this.activityRecognizeClient, 5000, getActivityDetectionPendingIntent())
                .setResultCallback(this);
    }

    public void removeActivityUpdates(View view) {
        ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(
                this.activityRecognizeClient, getActivityDetectionPendingIntent())
                .setResultCallback(this);
    }

    public void addGeoFence(View view) {
        if (!this.locationApiClient.isConnected()) {
            Toast.makeText(this, "Google location api not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            LocationServices.GeofencingApi.addGeofences(
                    this.locationApiClient,
                    getGeoFencingRequest(),
                    getGeoFencePendingIntent()
            ).setResultCallback(this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            Log.d(this.LOG_TAG, securityException.toString());
        }
    }

    private PendingIntent getActivityDetectionPendingIntent() {
        Intent intent = new Intent(this, DetectedActivityIntentService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getGeoFencePendingIntent() {
        Intent intent = new Intent(this, GeoFenceTransitionIntentService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void populateGeoFences() {
        for (Map.Entry<String, LatLng> entry : Constants.BAY_AREA_LANDMARKS.entrySet()) {
            this.geoFences.add(new Geofence.Builder()
                    .setRequestId(entry.getKey())
                    .setCircularRegion(
                            entry.getValue().latitude,
                            entry.getValue().longitude,
                            Constants.GEO_FENCE_RADIUS_IN_METERS
                    )
                    .setExpirationDuration(Constants.GEO_FENCE_EXPIRATION_IN_MILLISECONDS)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());
        }
    }

    private GeofencingRequest getGeoFencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(this.geoFences);
        return builder.build();
    }

    private class ActivityDetectionBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<DetectedActivity> detectedActivities = intent.getParcelableArrayListExtra(Constants.ACTIVITY_EXTRA);
            String resultStr = "";
            for (DetectedActivity activity : detectedActivities) {
                resultStr += convertTypeToName(activity.getType()) + "::" + activity.getConfidence() + "\n";
            }
            tvActivityRecognize.setText(resultStr);
        }

        private String convertTypeToName(Integer typeCode) {
            String typeName = "";
            switch (typeCode) {
                case DetectedActivity.IN_VEHICLE:
                    typeName = "In Vehicle";
                    break;
                case DetectedActivity.ON_BICYCLE:
                    typeName = "On Bicycle";
                    break;
                case DetectedActivity.ON_FOOT:
                    typeName = "On Foot";
                    break;
                case DetectedActivity.RUNNING:
                    typeName = "Running";
                    break;
                case DetectedActivity.STILL:
                    typeName = "Still";
                    break;
                case DetectedActivity.TILTING:
                    typeName = "Tilting";
                    break;
                case DetectedActivity.WALKING:
                    typeName = "WALKING";
                    break;
                case DetectedActivity.UNKNOWN:
                    typeName = "Unknown:";
                    break;
            }
            return typeName;
        }
    }
}
