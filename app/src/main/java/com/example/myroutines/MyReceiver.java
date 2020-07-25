package com.example.myroutines;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myroutines.app.Constant;
import com.example.myroutines.app.NotificationHelper;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int action = intent.getIntExtra("ACTION", Constant.ERROR_RECEIVER);
        switch (action){
            case Constant.NOTIFICATION_RECEIVER:
                String title = intent.getStringExtra("TITLE");
                String content = intent.getStringExtra("CONTENT");
                notification(context, title, content);
                break;
            case Constant.REQUEST_RECEIVER:
                request(context);
                break;
            case Constant.WIFI_RECEIVER:
                int state = intent.getIntExtra("STATE", Constant.WIFI_OFF);
                wifi(context, state);
                break;
            case Constant.ERROR_RECEIVER:
                notification(context, "ERROR", "RECEIVER");
                break;
        }
    }

    private void notification(Context context, String title, String content){
        NotificationHelper notificationHelper = new NotificationHelper(context);
        notificationHelper.createNotification(title, content);
    }

    private void request (Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://dms.fatanugraha.xyz/pull";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Intent myIntent = new Intent(context, MyReceiver.class);
                    myIntent.setAction("com.example.myIntent");
                    myIntent.putExtra("ACTION", Constant.NOTIFICATION_RECEIVER);
                    myIntent.putExtra("TITLE", "SUCCESS");
                    myIntent.putExtra("CONTENT", response);
                    context.sendBroadcast(myIntent);
                },
                error -> {
                    Intent myIntent = new Intent(context, MyReceiver.class);
                    myIntent.setAction("com.example.myIntent");
                    myIntent.putExtra("ACTION", Constant.NOTIFICATION_RECEIVER);
                    myIntent.putExtra("TITLE", "FAILED");
                    myIntent.putExtra("CONTENT", error);
                    context.sendBroadcast(myIntent);
                }
        );
        queue.add(stringRequest);
    }

    private void wifi(Context context, int state){
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        Intent myIntent = new Intent(context, MyReceiver.class);
        myIntent.setAction("com.example.myIntent");
        myIntent.putExtra("ACTION", Constant.NOTIFICATION_RECEIVER);
        if (state == Constant.WIFI_ON){
            wifiManager.setWifiEnabled(true);
            myIntent.putExtra("TITLE", context.getApplicationContext().getString(R.string.wifi_on));
        }
        else{
            wifiManager.setWifiEnabled(false);
            myIntent.putExtra("TITLE", context.getApplicationContext().getString(R.string.wifi_off));
        }
        context.sendBroadcast(myIntent);
    }
}
