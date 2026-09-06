package com.adam.darklightwallpaper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.adam.darklightwallpaper.utils.TriggerUtil;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Preferences preferences = new Preferences(context);
        if (preferences.isEnabled()) {
            if (preferences.changeWithTheme()) {
                TriggerUtil.startThemeTrigger(context);
            } else {
                TriggerUtil.startTimeTrigger(context);
            }
        }
    }
}
