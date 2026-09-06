package com.adam.darklightwallpaper.ui.fragment;

import android.app.AlarmManager;
import android.net.Uri;
import android.os.Build;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.adam.darklightwallpaper.Preferences;
import com.adam.darklightwallpaper.R;
import com.adam.darklightwallpaper.trigger.ThemeTrigger;
import com.adam.darklightwallpaper.ui.adapter.ViewPagerAdapter;
import com.adam.darklightwallpaper.utils.TriggerUtil;
import com.adam.darklightwallpaper.utils.WallpaperUtil;
import dev.oneuiproject.oneui.dialog.StartEndTimePickerDialog;
import dev.oneuiproject.oneui.preference.LayoutPreference;
import dev.oneuiproject.oneui.preference.SwitchBarPreference;

public class PreferencesFragment extends PreferenceFragmentCompat {

    private Context mContext;
    private Preferences mPreferences;
    private WallpaperUtil mWallpaperUtil;
    private ViewPagerAdapter mAdapter;

    public void initFields(ViewPagerAdapter adapter, WallpaperUtil wallpaperUtil) {
        this.mAdapter = adapter;
        this.mWallpaperUtil = wallpaperUtil;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mPreferences = new Preferences(mContext);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        SwitchBarPreference switchBarPreference = findPreference("service_enabled");
        LayoutPreference previewPreference = findPreference("preview");
        LayoutPreference systemDarkModePreference = findPreference("system_dark_mode");
        DropDownPreference modePreference = findPreference("service_mode");
        Preference schedulePreference = findPreference("schedule");
        updateSystemDarkModeCard(systemDarkModePreference);

        switchBarPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            if ((boolean) newValue) {
                if (mPreferences.changeWithTheme()) {
                    TriggerUtil.startThemeTrigger(mContext);
                } else if (ensureExactAlarmAccess()) {
                    TriggerUtil.startTimeTrigger(mContext);
                }
            } else {
                TriggerUtil.stopAll(mContext);
            }
            return true;
        });

        if (mAdapter != null && mWallpaperUtil != null) {
            ViewPager2 viewPager = previewPreference.findViewById(R.id.viewPager);
            viewPager.seslGetListView().setNestedScrollingEnabled(false);
            viewPager.setAdapter(mAdapter);
            viewPager.setOffscreenPageLimit(1);

            TabLayout tabLayout = previewPreference.findViewById(R.id.tabLayout);
            tabLayout.seslSetSubTabStyle();

            tabLayout.setTabTextColors(
                    mContext.getColor(R.color.app_text_secondary),
                    mContext.getColor(R.color.app_text_primary));
            tabLayout.seslSetSubTabSelectedIndicatorColor(mContext.getColor(R.color.app_accent));

            new TabLayoutMediator(tabLayout, viewPager, (tab, position)
                    -> tab.setText(((ViewPagerAdapter) viewPager.getAdapter()).getTitle(position))).attach();
        } else {
            getPreferenceScreen().removePreference(previewPreference);
        }

        modePreference.seslSetSummaryColor(getColoredSummaryColor());
        modePreference.setOnPreferenceChangeListener((preference, newValue) -> {
            schedulePreference.setVisible(newValue.equals("1"));
            if (mPreferences.isEnabled()) {
                if (newValue.equals("0")) {
                    TriggerUtil.startThemeTrigger(mContext);
                } else if (ensureExactAlarmAccess()) {
                    TriggerUtil.startTimeTrigger(mContext);
                }
            }
            return true;
        });

        schedulePreference.setVisible(!mPreferences.changeWithTheme());
        schedulePreference.seslSetSummaryColor(getColoredSummaryColor());
        schedulePreference.setSummary(getScheduleSummary(mContext, mPreferences.getScheduleStart(), mPreferences.getScheduleEnd()));
        schedulePreference.setOnPreferenceClickListener(preference -> {
            new StartEndTimePickerDialog(mContext,
                    mPreferences.getScheduleStart(),
                    mPreferences.getScheduleEnd(),
                    DateFormat.is24HourFormat(mContext),
                    (startTime, endTime) -> {
                        schedulePreference.setSummary(getScheduleSummary(mContext, startTime, endTime));
                        mPreferences.setScheduleTime(startTime, endTime);
                        if (mPreferences.isEnabled() && !mPreferences.changeWithTheme() && ensureExactAlarmAccess())
                            TriggerUtil.startTimeTrigger(mContext);
                    }).show();
            return false;
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView().setBackgroundResource(R.drawable.app_background_gradient);
        getListView().seslSetLastRoundedCorner(false);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mAdapter != null) mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mPreferences.isEnabled() && !mPreferences.changeWithTheme()) {
            AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S
                    || (alarmManager != null && alarmManager.canScheduleExactAlarms())) {
                TriggerUtil.startTimeTrigger(mContext);
            }
        }
    }

    private void updateSystemDarkModeCard(LayoutPreference preference) {
        if (preference == null) return;

        TextView statusView = preference.findViewById(R.id.system_dark_mode_status);
        TextView deviceView = preference.findViewById(R.id.system_dark_mode_device);
        TextView instructionsView = preference.findViewById(R.id.system_dark_mode_instructions);
        Button configureButton = preference.findViewById(R.id.system_dark_mode_configure);

        boolean dark = (getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;

        if (statusView != null) {
            statusView.setText(getString(R.string.system_dark_mode_status,
                    getString(dark ? R.string.theme_dark : R.string.theme_light)));
        }

        String manufacturer = Build.MANUFACTURER == null ? "" : Build.MANUFACTURER;
        String model = Build.MODEL == null ? "" : Build.MODEL;
        String deviceName = (manufacturer + " " + model).trim();

        if (deviceView != null) {
            deviceView.setText(getString(R.string.system_dark_mode_device, deviceName));
        }

        String maker = manufacturer.toLowerCase(Locale.US);
        int instructionsRes;
        if (maker.contains("xiaomi") || maker.contains("redmi") || maker.contains("poco")) {
            instructionsRes = R.string.system_dark_mode_xiaomi;
        } else if (maker.contains("google")) {
            instructionsRes = R.string.system_dark_mode_pixel;
        } else if (maker.contains("samsung")) {
            instructionsRes = R.string.system_dark_mode_samsung;
        } else {
            instructionsRes = R.string.system_dark_mode_generic;
        }

        if (instructionsView != null) {
            instructionsView.setText(instructionsRes);
        }

        if (configureButton != null) {
            configureButton.setOnClickListener(v ->
                    startActivity(new Intent(Settings.ACTION_DISPLAY_SETTINGS)));
        }
    }

    private String getScheduleSummary(Context context, int startTime, int endTime) {
        int startHour = startTime / 60;
        int startMinute = startTime % 60;
        int endHour = endTime / 60;
        int endMinute = endTime % 60;
        StringBuilder sb = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(DateFormat.is24HourFormat(context) ? 11 : 10, startHour);
        calendar.set(12, startMinute);
        sb.append(DateFormat.getTimeFormat(context).format(new Date(calendar.getTimeInMillis())));
        sb.append(" - ");
        calendar.clear();
        calendar.set(DateFormat.is24HourFormat(context) ? 11 : 10, endHour);
        calendar.set(12, endMinute);
        if (startTime >= endTime) {
            sb.append(context.getResources().getString(R.string.preference_time_next_day, DateFormat.getTimeFormat(context).format(new Date(calendar.getTimeInMillis()))));
        } else {
            sb.append(DateFormat.getTimeFormat(context).format(new Date(calendar.getTimeInMillis())));
        }
        return sb.toString();
    }

    private ColorStateList getColoredSummaryColor() {
        TypedValue colorPrimaryDark = new TypedValue();
        mContext.getTheme().resolveAttribute(dev.oneuiproject.oneui.design.R.attr.colorPrimaryDark, colorPrimaryDark, true);
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled},
                new int[]{-android.R.attr.state_enabled}
        };
        int[] colors = new int[]{
                Color.argb(0xff,
                        Color.red(colorPrimaryDark.data),
                        Color.green(colorPrimaryDark.data),
                        Color.blue(colorPrimaryDark.data)),
                Color.argb(0x4d,
                        Color.red(colorPrimaryDark.data),
                        Color.green(colorPrimaryDark.data),
                        Color.blue(colorPrimaryDark.data))
        };
        return new ColorStateList(states, colors);
    }

    private boolean ensureExactAlarmAccess() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return true;

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null || alarmManager.canScheduleExactAlarms()) return true;

        Intent intent = new Intent(
                Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                Uri.parse("package:" + mContext.getPackageName()));
        startActivity(intent);
        return false;
    }
}




