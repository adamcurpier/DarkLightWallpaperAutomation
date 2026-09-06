package com.adam.darklightwallpaper.utils;

import android.content.Context;

import com.adam.darklightwallpaper.trigger.ThemeTrigger;
import com.adam.darklightwallpaper.trigger.TimeTrigger;
import com.adam.darklightwallpaper.trigger.TimeTriggerService;

public class TriggerUtil {

    public static void startThemeTrigger(Context context) {
        stopTimeTrigger(context);

        ThemeTrigger.start(context);
    }

    public static void startTimeTrigger(Context context) {
        stopThemeTrigger(context);

        TimeTriggerService.start(context);
        TimeTrigger.start(context);
    }

    public static void stopAll(Context context) {
        stopThemeTrigger(context);
        stopTimeTrigger(context);
    }

    private static void stopThemeTrigger(Context context) {
        ThemeTrigger.stop(context);
    }

    private static void stopTimeTrigger(Context context) {
        TimeTriggerService.stop(context);
        TimeTrigger.stop(context);
    }


}
