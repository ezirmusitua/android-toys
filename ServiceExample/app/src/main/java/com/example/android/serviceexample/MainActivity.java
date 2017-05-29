package com.example.android.serviceexample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    ExampleBindService serviceToBind;
    Boolean isBound;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ExampleBindService.LocalBinder binder = (ExampleBindService.LocalBinder) service;
            serviceToBind = binder.getService();
            isBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, ExampleBindService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    public void onClickStartService(View view) {
        switch (view.getId()) {
            case R.id.btn_start_service:
                startService(new Intent(getBaseContext(), ExampleService.class));
                break;
            case R.id.btn_start_intent_service:
                startService(new Intent(getBaseContext(), ExampleIntentService.class));
                break;
            case R.id.btn_bind_service:
                this.serviceToBind.toastBoundMessage();
                break;
            default:
                break;
        }
    }

    public void onClickStopService(View view) {
        switch (view.getId()) {
            case R.id.btn_stop_service:
                stopService(new Intent(getBaseContext(), ExampleService.class));
                break;
            case R.id.btn_stop_intent_service:
                stopService(new Intent(getBaseContext(), ExampleIntentService.class));
                break;
            default:
                break;
        }
    }

}
