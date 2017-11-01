package com.macbook.hurmat.bitcoin;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Calendar;

import static com.macbook.hurmat.bitcoin.MainActivity_conversion.compareValue;
import static com.macbook.hurmat.bitcoin.MainActivity_conversion.notificationManager;

/**
 * Created by hurmat on 31/10/2017.
 */

public class NotificationService extends Service {

    private final IBinder mBinder = new LocalBinder();


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent notificationIntent = new Intent(this, MainActivity_conversion.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        int pendingIntentId = 0;
        PendingIntent pendindIntent = PendingIntent.getActivity(this,pendingIntentId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification.Builder builder = new Notification.Builder(this)

                .setContentIntent(pendindIntent)
                .setContentTitle("Bitcoin")
                .setContentText("The rate of ETH crossed "+ compareValue)
                .setSmallIcon(R.drawable.notification_bitcoin)
                .setWhen(Calendar.getInstance().getTimeInMillis())
                .setAutoCancel(true);

        Notification notification = builder.build();
        builder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
         notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int notificationId = 1;

       // notificationManager.notify(notificationId,notification);
        startForeground(notificationId,notification);
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }
    public class LocalBinder extends Binder {

        public NotificationService getServiceInstance(){
            return NotificationService.this;
        }
    }


}
