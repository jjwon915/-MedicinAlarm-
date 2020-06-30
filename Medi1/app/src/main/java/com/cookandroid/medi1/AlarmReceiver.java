package com.cookandroid.medi1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    String CHANNEL_ID = "DDingDDing";

    @Override
    public void onReceive(Context context, Intent intent) {
        int req = intent.getExtras().getInt("req", 0);

        //푸시 알람 설정
        PendingIntent pendingIntent = PendingIntent.getActivity(context, req, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.medicine_img).setContentTitle("DDingDDing").setContentText("약 먹을 시간입니다.")
                .setWhen(Calendar.getInstance().getTimeInMillis()).setContentIntent(pendingIntent).setAutoCancel(true);

        notificationManager.notify(0, builder.build());
    }
}