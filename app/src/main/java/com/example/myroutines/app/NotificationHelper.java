package com.example.myroutines.app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import com.example.myroutines.MainActivity;
import com.example.myroutines.R;

import java.util.Date;

public class NotificationHelper {

    private Context mContext;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotifyManager;

    public NotificationHelper(Context context) {
        mContext = context;
    }

    public void createNotification(String title, String content)
    {
        mNotifyManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                    mContext.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder(title, content);
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        mNotifyManager.notify(m, notifyBuilder.build());
    }

    private NotificationCompat.Builder getNotificationBuilder(String title, String content) {
        Intent notificationIntent = new Intent(mContext, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity
                (mContext, 0, notificationIntent,
                        PendingIntent.FLAG_ONE_SHOT);
        // Build the notification with all of the parameters.
        NotificationCompat.Builder notifyBuilder = new NotificationCompat
                .Builder(mContext, PRIMARY_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setSmallIcon(R.drawable.ic_android)
                .setAutoCancel(true).setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        return notifyBuilder;
    }
}
