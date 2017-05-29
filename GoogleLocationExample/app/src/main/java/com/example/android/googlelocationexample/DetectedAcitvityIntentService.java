package com.example.android.googlelocationexample;

import android.app.IntentService;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;

/**
 * Created by jferroal on 2017-05-29.
 */

public class DetectedAcitvityIntentService extends IntentService{
    protected static final String TAG = "J_ACTIVITY_REC";

    public DetectedAcitvityIntentService() {
        super(TAG);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        ActivityRecognitionResult arr = ActivityRecognitionResult.extractResult(intent);
        Intent localIntent = new Intent(Constants.BROADCAST_ACTION);

        ArrayList<DetectedActivity> detectedActivities = (ArrayList) arr.getProbableActivities();
        localIntent.putExtra(Constants.ACTIVITY_EXTRA, detectedActivities);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}
