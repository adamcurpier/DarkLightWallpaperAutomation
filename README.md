# Dark & Light Wallpaper Automation

Dark & Light Wallpaper Automation is an Android app for automatically switching between separate light and dark wallpaper sets.

It is designed for people who want their wallpaper to change with either a schedule or the phone's current Light/Dark theme, while keeping separate control of the **Home screen** and **Lock screen** wallpapers.

## What the app does

The app stores four wallpaper selections:

| Mode | Home screen | Lock screen |
|---|---|---|
| Light | Light Home wallpaper | Light Lock wallpaper |
| Dark | Dark Home wallpaper | Dark Lock wallpaper |

The Home and Lock wallpapers **do not need to be the same image**. You may use four different images if you want. Using the same Light image for both Home and Lock, and the same Dark image for both Home and Lock, also works perfectly.

## Main features

- Separate Light and Dark wallpaper sets.
- Separate Home and Lock screen wallpapers.
- **Time** trigger with a custom start/end schedule.
- **System Theme** trigger that follows Android's current Light/Dark theme.
- Exact-alarm scheduling for reliable timed changes.
- Foreground-service support for better background reliability on aggressive Android skins.
- In-app System Dark Mode helper with device-specific guidance.
- Xiaomi / HyperOS guidance built directly into the app.
- Custom Dark & Light user interface and launcher icon.

## Basic setup

1. Open **Dark & Light**.
2. Turn the app **On**.
3. Choose a trigger:
   - **Time** to switch wallpapers on a schedule.
   - **System Theme** to follow the phone's current Light/Dark theme.
4. Open **Light Wallpapers** and choose:
   - one wallpaper for the Home screen;
   - one wallpaper for the Lock screen.
5. Open **Dark Wallpapers** and choose:
   - one wallpaper for the Home screen;
   - one wallpaper for the Lock screen.
6. If using **Time**, choose the schedule you want.

Example tested schedule:

- Dark wallpaper starts: **7:00 PM**
- Light wallpaper returns: **7:00 AM next day**

For a more detailed walkthrough, see [INSTALLATION.md](INSTALLATION.md).

## Matching the phone's system Dark Mode schedule

Dark & Light controls wallpapers. It does **not** force the Android system-wide Light/Dark theme in Time mode.

If you want the entire phone UI and the wallpapers to change together:

1. Set the wallpaper schedule in Dark & Light.
2. Open the **System Dark Mode** card in the app.
3. Tap **Configure System Dark Mode**.
4. Set the phone's native Dark Mode / Dark Theme schedule to the same start and end times.

For example, if Dark & Light is set to **7:00 PM - 7:00 AM**, set the phone's own Dark Mode schedule to **7:00 PM - 7:00 AM** as well.

## Xiaomi / HyperOS setup

Dark & Light has been functionally validated on a **Xiaomi 17T Pro running HyperOS 3 / Android 16**.

For best wallpaper reliability on Xiaomi / HyperOS, enable all three of the following for Dark & Light:

1. **Background autostart: ON**
2. **Battery: No restrictions**
3. **Lock/protect Dark & Light in Recents**

These settings are recommended because HyperOS can aggressively limit background apps.

### HyperOS scheduled Dark Mode behavior

During testing, HyperOS sometimes deferred the visible system Light/Dark change while the phone was actively being used.

If the scheduled system theme change does not appear immediately:

1. Turn the screen off briefly.
2. Wake and unlock the phone.

The scheduled Light/Dark state should then apply immediately.

This affects the phone's **native HyperOS Dark Mode schedule**, not the wallpaper scheduler itself.

## Exact alarm access

Time-based wallpaper changes use Android's exact-alarm system.

On Android versions that require approval, allow Dark & Light to schedule exact alarms when prompted. Without exact-alarm access, Android may delay scheduled wallpaper changes.

## Foreground service notification

For reliable background operation, the app uses a foreground service when needed.

The notification is labeled:

**Dark & Light Wallpaper Automation**  
*Wallpaper automation is active*

This is expected behavior and helps keep the scheduler available on devices that aggressively reclaim background processes.

## Tested behavior

The following behaviors were directly validated on Xiaomi / HyperOS:

- Manual Light/Dark wallpaper switching.
- Scheduled Light -> Dark wallpaper change.
- Scheduled Dark -> Light wallpaper change.
- Correct overnight schedules such as 7:00 PM -> 7:00 AM.
- Scheduled changes while the app is in the background.
- Scheduled changes after the app is removed from the Recents screen.
- Foreground service remains active in Time mode.
- Matching HyperOS system Dark Mode schedule.
- HyperOS system theme applies after a brief screen-off/wake cycle when the UI was still active at the schedule boundary.
- Separate Home and Lock screen wallpaper selections.

## Compatibility

### Validated

- Xiaomi 17T Pro
- HyperOS 3
- Android 16

### Expected but not yet directly validated

- Google Pixel / stock Android
- Samsung Galaxy / One UI
- Other Android devices that support standard WallpaperManager and exact alarms

Behavior may vary by manufacturer because some Android skins impose additional background-process restrictions.

## Normal-use requirements

After installation and setup:

- Root is **not** required.
- Bootloader unlocking is **not** required.
- ADB is **not** required for normal operation.
- Developer Options are **not** required for normal operation.

## Build information

Current Android project configuration:

- Namespace: `com.adam.darklightwallpaper`
- Minimum SDK: 26
- Target SDK: 33
- Compile SDK: 34
- Android Gradle Plugin: 7.4.2
- Gradle: 7.5
- Java compatibility: Java 8

The project currently builds successfully with the development environment used during validation.

## Project history and attribution

Dark & Light Wallpaper Automation is a substantially modified derivative of **DualWallpaper** by **Yanndroid**.

Original project:

https://github.com/Yanndroid/DualWallpaper

The original project was released under the **MIT License**. The original MIT copyright and permission notice are retained in this repository as required by that license.

Major work in this fork includes a redesigned Dark & Light interface, renamed application/package, custom launcher artwork, improved time scheduling, exact-alarm handling, foreground-service reliability work, device-specific System Dark Mode guidance, Xiaomi / HyperOS background-reliability guidance, and extensive real-device validation.

## Documentation

- [Installation and setup](INSTALLATION.md)
- [Troubleshooting](TROUBLESHOOTING.md)
- [MIT License](LICENSE)

## License

This project remains distributed under the MIT License. See [LICENSE](LICENSE).

Original copyright notice:

`Copyright (c) 2022 Yanndroid`

## Disclaimer

Android manufacturers can change background-execution and wallpaper behavior between OS versions. The Xiaomi / HyperOS instructions above reflect real-device testing on the validated configuration and may need adjustment on other devices or future software versions.
