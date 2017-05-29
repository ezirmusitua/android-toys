package com.example.android.serviceexample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ExampleBindService extends Service {
    private final IBinder mBinder = new LocalBinder();
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("J LOG", "bound service");
        return mBinder;
    }

    public void toastBoundMessage() {
        Toast.makeText(this, "Service Bind", Toast.LENGTH_SHORT).show();
    }
    public class LocalBinder extends Binder {
        ExampleBindService getService() {
            // Return this instance of LocalService so clients can call public methods
            return ExampleBindService.this;
        }
    }
}
