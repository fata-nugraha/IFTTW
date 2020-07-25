package com.example.myroutines;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.myroutines.app.Constant;
import com.example.myroutines.app.Restarter;
import com.example.myroutines.data.Routine;
import com.example.myroutines.data.RoutineHelper;

import java.util.List;

public class AccelerometerService extends IntentService implements SensorEventListener {
    SensorManager sm = null;
    List list;
    Boolean active = true;
    Boolean update = false;
    private List<Routine> routines;

    public AccelerometerService(){
        super("AccelerometerService");
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        float sum = Math.abs(values[0]) + Math.abs(values[1]) + Math.abs(values[2]);
        if (sum > 30){
            for (Routine routine : routines) {
                switch (routine.getAction().getId()){
                    case 1:
                        sendNotification(this,
                                routine.getAction().getTitle(),
                                routine.getAction().getText());
                        break;
                    case 2:
                        sendRequest(this);
                        break;
                    case 3:
                        if (routine.getAction().getWifiState() == Constant.WIFI_ON) {
                            enableWifi(this);
                        } else {
                            disableWifi(this);
                        }
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        NotificationManager mNotifyManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel
                    ("service", getApplicationContext().getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
        startForeground(1337, getNotificationBuilder("SERVICE", "HI").build());

        RoutineHelper db = new RoutineHelper(this);
        routines = db.getAllActiveRoutineCondition(1);

    }

    private NotificationCompat.Builder getNotificationBuilder(String title, String content) {
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity
                (getApplicationContext(), 0, notificationIntent,
                        PendingIntent.FLAG_ONE_SHOT);
        // Build the notification with all of the parameters.
        NotificationCompat.Builder notifyBuilder = new NotificationCompat
                .Builder(getApplicationContext(), "service")
                .setContentTitle(title)
                .setContentText(content)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setSmallIcon(R.drawable.ic_android)
                .setAutoCancel(true).setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        return notifyBuilder;
    }

    protected void onHandleIntent(Intent workIntent){
        update = workIntent.getBooleanExtra("UPDATE", false);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        list = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(list.size()>0){
            sm.registerListener(this,(Sensor)list.get(0),SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            Toast.makeText(getBaseContext(),"Error: No Accelerometer.",Toast.LENGTH_LONG).show();
        }
        while (active);
    }

    @Override
    public void onDestroy() {
        sm.unregisterListener(this);
        Intent reIntent = new Intent(getApplicationContext(), Restarter.class);
        reIntent.setAction("com.example.restarter");
        sendBroadcast(reIntent);
        super.onDestroy();
    }

    public void enableWifi(Context context){
        Intent myIntent = new Intent(context.getApplicationContext(), MyReceiver.class);
        myIntent.setAction("com.example.myIntent");
        myIntent.putExtra("ACTION", Constant.WIFI_RECEIVER);
        myIntent.putExtra("STATE", Constant.WIFI_ON);
        context.getApplicationContext().sendBroadcast(myIntent);
    }

    public void disableWifi(Context context){
        Intent myIntent = new Intent(context.getApplicationContext(), MyReceiver.class);
        myIntent.setAction("com.example.myIntent");
        myIntent.putExtra("ACTION", Constant.WIFI_RECEIVER);
        myIntent.putExtra("STATE", Constant.WIFI_OFF);
        context.getApplicationContext().sendBroadcast(myIntent);
    }

    public void sendRequest(Context context){
        Intent myIntent = new Intent(context.getApplicationContext(), MyReceiver.class);
        myIntent.setAction("com.example.myIntent");
        myIntent.putExtra("ACTION", Constant.REQUEST_RECEIVER);
        context.getApplicationContext().sendBroadcast(myIntent);
    }

    public void sendNotification(Context context, String title, String text) {
        Intent myIntent = new Intent(context.getApplicationContext(), MyReceiver.class);
        myIntent.setAction("com.example.myIntent");
        myIntent.putExtra("ACTION", Constant.NOTIFICATION_RECEIVER);
        myIntent.putExtra("TITLE", title);
        myIntent.putExtra("CONTENT", text);
        context.getApplicationContext().sendBroadcast(myIntent);
    }
}

