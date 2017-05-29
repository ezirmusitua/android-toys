package com.example.android.googlelocationexample;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private final String LOG_TAG = "J_LOCATION_EXAMPLE";
    private TextView tv_location;
    private GoogleApiClient ggApiClient;
    private LocationRequest ggLocationReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.ggApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        this.tv_location = (TextView) findViewById(R.id.text_location);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        this.ggApiClient.connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Boolean noPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        if (noPermission) {
            String[] needPermissions = {android.Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, needPermissions, 1);
        }
    }

    @Override
    protected void onStop() {
        this.ggApiClient.disconnect();
        super.onStop();
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
        this.ggLocationReq = LocationRequest.create();
        this.ggLocationReq.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        this.ggLocationReq.setInterval(1000);
        LocationServices.FusedLocationApi.requestLocationUpdates(this.ggApiClient,
                this.ggLocationReq, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(this.LOG_TAG, location.toString());
        String latitude = Double.toString(location.getLatitude());
        this.tv_location.setText(latitude);
    }
}
