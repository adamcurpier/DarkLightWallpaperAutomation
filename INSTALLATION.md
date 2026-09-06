# Installation and Setup

## Install the app

Install the Dark & Light Wallpaper Automation APK on your Android phone, then open **Dark & Light**.

If Android blocks installation from the source you used to download the APK, allow installation from that source in Android settings and retry.

## Choose the four wallpaper slots

Dark & Light keeps four independent wallpaper selections:

1. **Light Home**
2. **Light Lock**
3. **Dark Home**
4. **Dark Lock**

The four images can all be different. The Home and Lock images are not required to match.

A simple setup is to use one image for both Light Home and Light Lock, and a second image for both Dark Home and Dark Lock.

## Choose a trigger

### Time

Use **Time** when you want Dark & Light to change wallpapers on its own schedule.

1. Select **Time**.
2. Tap **Set Schedule**.
3. Choose the Dark start time and Light return time.
4. Allow exact-alarm access if Android requests it.

An overnight schedule is supported. For example:

- Start: **7:00 PM**
- End: **7:00 AM next day**

### System Theme

Use **System Theme** when you want wallpaper changes to follow Android's current Light/Dark theme instead of an independent clock schedule.

## Match the phone UI to the wallpaper schedule

When using the Time trigger, Dark & Light changes the wallpapers but does not force the phone's global system theme.

To keep both synchronized:

1. Set the desired schedule in Dark & Light.
2. Tap **Configure System Dark Mode**.
3. Set Android's native Dark Mode / Dark Theme schedule to the exact same times.

## Xiaomi / HyperOS

For best reliability on Xiaomi, Redmi, and POCO devices running HyperOS:

- Enable **Background autostart** for Dark & Light.
- Set **Battery** to **No restrictions**.
- Lock/protect **Dark & Light** in the Recents screen.

HyperOS may defer a scheduled native system Light/Dark change while the phone is actively being used. If that occurs, briefly turn the screen off, then wake and unlock it. During validation the scheduled system state applied immediately after that screen-off/wake cycle.

## Normal operation

After setup, normal use does not require:

- ADB
- USB debugging
- Wireless debugging
- Developer Options
- Root
- Bootloader unlocking

The app may show a low-priority foreground-service notification while wallpaper automation is active. This is expected and improves scheduler reliability on devices with aggressive background-process management.
