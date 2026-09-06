package com.adam.darklightwallpaper.trigger;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.adam.darklightwallpaper.R;

public class TimeTriggerService extends Service {

    private static final int NOTIFICATION_ID = 3001;

    public static void start(Context context) {
        context.startForegroundService(new Intent(context, TimeTriggerService.class));
    }

    public static void stop(Context context) {
        context.stopService(new Intent(context, TimeTriggerService.class));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(NOTIFICATION_ID, buildForegroundNotification());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private Notification buildForegroundNotification() {
        return new NotificationCompat.Builder(this, ThemeTrigger.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_oui_wallpaper_outline)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_description))
                .setContentIntent(getNotificationSettingsIntent())
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .build();
    }

    private PendingIntent getNotificationSettingsIntent() {
        return PendingIntent.getActivity(
                this,
                0,
                new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
                        .putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName())
                        .putExtra(Settings.EXTRA_CHANNEL_ID, ThemeTrigger.NOTIFICATION_CHANNEL_ID),
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }
}
