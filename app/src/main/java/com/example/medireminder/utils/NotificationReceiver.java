package com.example.medireminder.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String medicineName = intent.getStringExtra("medicineName");
        String dosage = intent.getStringExtra("dosage");
        
        NotificationHelper notificationHelper = new NotificationHelper(context);
        notificationHelper.showNotification(
                "Time to take your medicine!",
                medicineName + " - " + dosage
        );
    }
}

