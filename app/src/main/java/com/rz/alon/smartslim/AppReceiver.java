package com.rz.alon.smartslim;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by alonrz on 6/24/16.
 */
public class AppReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("ALON", "AppReceiver");
        Intent newIntent = new Intent(context, TimerActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(newIntent);
    }

    public void setAlarm(Context context, long millisLeft) {
        Log.d("ALON", "setAlarm");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AppReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        //alarmManager.setInexactRepeating(AlarmManager.RTC, Calendar.getInstance().getTimeInMillis() + mMillisLeft, 0, pendingIntent);
        if(millisLeft != 0) {
            alarmManager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis() + millisLeft, 0, pendingIntent);
        }
    }

    public void cancelAlarm(Context context) {
//        Intent intent = new Intent(context, Alarm.class);
//        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.cancel(sender);
    }
}
