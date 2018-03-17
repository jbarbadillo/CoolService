package com.javirock.coolservice;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class CoolService extends Service implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int PERM_REQUEST_LOCATION = 1;
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
        Logger.i("onCreate");

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.i("onStartCommand");
        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Logger.i("Received Start Foreground Intent ");


            /*PermissionHelper.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_ADMIN},
                    PERM_REQUEST_LOCATION, "Location", "Needed", android.R.drawable.ic_secure);*/


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
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0,
                notificationIntent, 0);

        // And now, building and attaching the Close button.
        Intent buttonCloseIntent = new Intent(this, CoolService.class);
        buttonCloseIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent buttonClosePendingIntent = pendingIntent.getService(this, 0, buttonCloseIntent, 0);

        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.guitar);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("AndroidGuitar")
                .setTicker("AndroidGuitar")
                .setContentText("Ready to play!")
                .setSmallIcon(R.drawable.guitar)
                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .addAction(android.R.drawable.ic_menu_close_clear_cancel,"close", buttonClosePendingIntent)
                .build();

        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                notification);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.i("inDestroy");
        Toast.makeText(this, "Service Detroyed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isGranted = false;
        for (int i = 0; i < grantResults.length; i++) {
            if (permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION) && (grantResults[i] == PackageManager.PERMISSION_GRANTED)) {
                isGranted = true;
            }else if(permissions[i].equals(Manifest.permission.BLUETOOTH) && (grantResults[i] == PackageManager.PERMISSION_GRANTED)){
                isGranted = true;
            }
        }
        if (isGranted) {
            Logger.i("PERMISSIONS GRANTED");
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            adapter.enable();
        }
        else
            Logger.i("ACCESS_FINE_LOCATION permission not granted. Location notifications will not be available.");

    }
}
