package com.anuradhakelum.istqbmockexam;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by anuradhawijetunga on 3/3/18.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        Intent notificationIntent = new Intent(context, MainActivity.class);


        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(context);
        Boolean switchPref = sharedPref.getBoolean
                (SettingsActivity.KEY_PREF_NOTIFICATION_SWITCH, false);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder.setContentTitle("Are you ready for ISTQB exam")
                .setContentText("Get ready for ISTQB exam. Use Mock exams to improve your knowledge.")
                .setTicker("New  Alert!")
                .setSmallIcon(R.mipmap.ic_launcher_red_round)
                .setContentIntent(pendingIntent).build();


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (switchPref) {
            notificationManager.notify(0, notification);
        }

    }
}
