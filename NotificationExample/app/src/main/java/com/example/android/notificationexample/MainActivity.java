package com.example.android.notificationexample;


import android.os.Bundle;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Integer notificationCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationCount = 0;
    }

    public void onClickCreateNotification(View view) {
        notificationCount += 1;
        switch (view.getId()) {
            case R.id.btn_normal_notification:
                createNotification(100);
                break;
            case R.id.btn_big_notification:
                createNotification(200);
                break;
            default:
                break;
        }
    }

    private void createNotification(Integer category) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.flower_2_you)
                .setContentTitle("Notifications Example")
                .setContentText("This is a test notification");
        Intent notificationIntent = new Intent(this, NotificationTargetActivity.class);
        if (category == 200) {
            builder.setTicker("New message alert!");
            builder.setNumber(notificationCount);
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            String[] events = new String[6];
            events[0] = "This is first line....";
            events[1] = "This is second line...";
            events[2] = "This is third line...";
            events[3] = "This is 4th line...";
            events[4] = "This is 5th line...";
            events[5] = "This is 6th line...";
            // Sets a title for the Inbox style big view
            inboxStyle.setBigContentTitle("Big Title Details:");
            // Moves events into the big view
            for (int i = 0; i < events.length; i++) {
                inboxStyle.addLine(events[i]);
            }
            builder.setStyle(inboxStyle);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(NotificationActivity.class);

            /* Adds the Intent that starts the Activity to the top of the stack */
            stackBuilder.addNextIntentc(notificationIntent);
            PendingIntent contentIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
        } else {
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
        }
        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
