package com.example.myroutines.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.myroutines.AccelerometerService;

public class Restarter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startForegroundService(new Intent(context, AccelerometerService.class));
    }
}
