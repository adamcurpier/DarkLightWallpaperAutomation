package com.adam.darklightwallpaper.trigger;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

import com.adam.darklightwallpaper.Preferences;
import com.adam.darklightwallpaper.utils.WallpaperUtil;

public class TimeTrigger extends BroadcastReceiver {

    public static final String ACTION_SET_NIGHT = "com.adam.darklightwallpaper.SET_NIGHT";
    public static final String ACTION_SET_DAY = "com.adam.darklightwallpaper.SET_DAY";

    private static final int REQUEST_NIGHT = 1001;
    private static final int REQUEST_DAY = 1002;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == null) return;

        Preferences preferences = new Preferences(context);

        // Ignore stale alarms if automation was disabled or switched back to theme mode.
        if (!preferences.isEnabled() || preferences.changeWithTheme()) return;

        WallpaperUtil wallpaperUtil = new WallpaperUtil(context);

        if (intent.getAction().equals(ACTION_SET_NIGHT)) {
            wallpaperUtil.loadWallpapers(true);
            scheduleOne(context, true, preferences.getScheduleStart());
        } else if (intent.getAction().equals(ACTION_SET_DAY)) {
            wallpaperUtil.loadWallpapers(false);
            scheduleOne(context, false, preferences.getScheduleEnd());
        }
    }

    public static void start(Context context) {
        Preferences preferences = new Preferences(context);

        // Always replace any old alarms with fresh one-shot alarms.
        stop(context);

        scheduleOne(context, true, preferences.getScheduleStart());
        scheduleOne(context, false, preferences.getScheduleEnd());

        // Immediately apply the wallpaper that matches the current time.
        new WallpaperUtil(context).loadWallpapers(isNowDark(preferences));
    }

    public static void stop(Context context) {
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(getTriggerIntent(context, true));
        alarmManager.cancel(getTriggerIntent(context, false));
    }

    public static boolean isNowDark(Preferences preferences) {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay =
                calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
        int startTime = preferences.getScheduleStart();
        int endTime = preferences.getScheduleEnd();

        return (timeOfDay < endTime && startTime < timeOfDay)
                || (endTime < startTime
                && (startTime < timeOfDay || timeOfDay < endTime));
    }

    private static void scheduleOne(
            Context context, boolean night, int timeInMinutes) {

        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        long triggerAtMillis = getNextTimeMillis(timeInMinutes);
        PendingIntent pendingIntent = getTriggerIntent(context, night);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerAtMillis,
                        pendingIntent);
            } else {
                // Safe fallback if Android has not granted exact-alarm access.
                alarmManager.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerAtMillis,
                        pendingIntent);
            }
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent);
        }
    }

    private static long getNextTimeMillis(int time) {
        Calendar calendar = Calendar.getInstance();
        long timeNow = calendar.getTimeInMillis();

        calendar.set(Calendar.HOUR_OF_DAY, time / 60);
        calendar.set(Calendar.MINUTE, time % 60);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (calendar.getTimeInMillis() <= timeNow) {
            calendar.add(Calendar.DATE, 1);
        }

        return calendar.getTimeInMillis();
    }

    private static PendingIntent getTriggerIntent(Context context, boolean night) {
        Intent intent = new Intent(context, TimeTrigger.class);
        intent.setAction(night ? ACTION_SET_NIGHT : ACTION_SET_DAY);

        return PendingIntent.getBroadcast(
                context,
                night ? REQUEST_NIGHT : REQUEST_DAY,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }
}
