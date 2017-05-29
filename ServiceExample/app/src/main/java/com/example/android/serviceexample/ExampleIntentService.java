package com.example.android.serviceexample;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ExampleIntentService extends IntentService {
    public ExampleIntentService() {
        super("ExampleIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("J LOG", "intent service");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
