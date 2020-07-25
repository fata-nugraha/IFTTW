package com.example.myroutines;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myroutines.app.Constant;
import com.example.myroutines.data.Routine;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class AlarmHelper extends AppCompatActivity {

    private LocalDateTime ldt;
    private ZonedDateTime zdt;
    private AlarmManager alarmManager;

    public void setAlarm(Context context, Routine routine){
        alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        int action = routine.getAction().getId();
        Intent myIntent = new Intent(context.getApplicationContext(), MyReceiver.class);
        myIntent.putExtra("ACTION", action);
        switch (action){
            case Constant.NOTIFICATION_RECEIVER:
                myIntent.putExtra("TITLE", routine.getAction().getTitle());
                myIntent.putExtra("CONTENT", routine.getAction().getText());
                break;
            case Constant.REQUEST_RECEIVER:
                break;
            case Constant.WIFI_RECEIVER:
                myIntent.putExtra("STATE", routine.getAction().getWifiState());
                break;
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),
                routine.getId(), myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        ldt = routine.getCond().getLdt();
        zdt = ldt.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));
        switch (routine.getCond().getId()){
            case 2:
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, zdt.toInstant().toEpochMilli(), pendingIntent);
                break;
            case 3:
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, zdt.toInstant().toEpochMilli(), AlarmManager.INTERVAL_DAY, pendingIntent);
                break;
            case 4:
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, zdt.toInstant().toEpochMilli(), AlarmManager.INTERVAL_DAY*7, pendingIntent);
                break;
        }
        Intent mi = new Intent(context, MyReceiver.class);
        mi.setAction("com.example.myIntent");
        mi.putExtra("ACTION", Constant.NOTIFICATION_RECEIVER);
        mi.putExtra("TITLE", "ALARM SET");
        mi.putExtra("CONTENT", ldt.toString() + routine.getId());
        context.sendBroadcast(mi);
        Log.v("contextset", context.getApplicationContext().toString());

    }

    public void killAlarm(Context context, Routine routine){
        alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        int action = routine.getAction().getId();
        Intent myIntent = new Intent(context.getApplicationContext(), MyReceiver.class);
        myIntent.putExtra("ACTION", action);
        switch (action){
            case Constant.NOTIFICATION_RECEIVER:
                myIntent.putExtra("TITLE", routine.getAction().getTitle());
                myIntent.putExtra("CONTENT", routine.getAction().getText());
                break;
            case Constant.REQUEST_RECEIVER:
                break;
            case Constant.WIFI_RECEIVER:
                myIntent.putExtra("STATE", routine.getAction().getWifiState());
                break;
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),
                routine.getId(), myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        Intent mi = new Intent(context, MyReceiver.class);
        mi.setAction("com.example.myIntent");
        mi.putExtra("ACTION", Constant.NOTIFICATION_RECEIVER);
        mi.putExtra("TITLE", "ALARM KILL");
        mi.putExtra("CONTENT", Integer.toString(routine.getId()));
        context.sendBroadcast(mi);
        Log.v("contextkill", context.getApplicationContext().toString());
    }

}
