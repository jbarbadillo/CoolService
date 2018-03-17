package com.javirock.coolservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class CoolService extends Service {
    public CoolService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Used only in case if services are bound (Bound Services).
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Logger.i("Received Start Foreground Intent ");
            showNotification();
            Toast.makeText(this, "Service Started!", Toast.LENGTH_SHORT).show();

        } else if (intent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Logger.i("Received Stop Foreground Intent ");
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }
    private void showNotification() {
        // And now, building and attaching the Close button.
        RemoteViews notificationView = new RemoteViews(this.getPackageName(),R.layout.notification);

        Intent buttonCloseIntent = new Intent(this, NotificationCloseButtonHandler.class);
        buttonCloseIntent.putExtra("action", "close");

        PendingIntent buttonClosePendingIntent = pendingIntent.getBroadcast(this, 0, buttonCloseIntent, 0);
        notificationView.setOnClickPendingIntent(R.id.notification_button_close, buttonClosePendingIntent);
        
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);




        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.guitar);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("AndroidGuitar")
                .setTicker("AndroidGuitar")
                .setContentText("Ready to play!")
                .setSmallIcon(R.drawable.guitar)
                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(pendingIntent)
                .setOngoing(true).build();
                //.addAction(android.R.drawable.ic_media_previous, "Previous",
                 //       ppreviousIntent)
                //.addAction(android.R.drawable.ic_media_play, "Play",
                 //       pplayIntent)
                //.addAction(android.R.drawable.ic_media_next, "Next",
                 //       pnextIntent).build();
        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                notification);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.i("inDestroy");
        Toast.makeText(this, "Service Detroyed!", Toast.LENGTH_SHORT).show();
    }
}
