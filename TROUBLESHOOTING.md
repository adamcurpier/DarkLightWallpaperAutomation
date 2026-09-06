# Troubleshooting

## Wallpaper did not change at the scheduled time

Check the following:

1. Dark & Light is **On**.
2. Trigger is set to **Time**.
3. The displayed schedule is correct.
4. Exact-alarm access is allowed if your Android version requires it.
5. The foreground-service notification is present while automation is active.

On Xiaomi / HyperOS also confirm:

- Background autostart is enabled.
- Battery is set to **No restrictions**.
- Dark & Light is locked/protected in Recents.

## Wallpaper changed but the phone UI is still Light or Dark

This is expected when using the **Time** trigger.

Dark & Light changes wallpapers. Android's system-wide Light/Dark theme is controlled separately by the phone.

Use **Configure System Dark Mode** and set the phone's native schedule to the same start/end times used by Dark & Light.

## Xiaomi / HyperOS system theme did not visibly switch at the boundary

During real-device testing, HyperOS sometimes deferred the visible native Light/Dark transition while the phone was actively in use.

Briefly turn the screen off, then wake and unlock the phone. The scheduled system theme should apply.

This is a HyperOS native-theme behavior, not a failure of the wallpaper scheduler.

## Home and Lock wallpapers are the same

That is allowed, but not required.

Dark & Light provides four independent slots: Light Home, Light Lock, Dark Home, and Dark Lock. Choose a different image for any slot if desired.

## App was removed from Recents

The validated Xiaomi configuration continued to execute scheduled wallpaper changes after the app was removed from Recents while the foreground service and recommended HyperOS background settings were active.

For best reliability on Xiaomi / HyperOS, keep the app protected/locked in Recents rather than repeatedly clearing it.

## Force Stop

Android's **Force stop** action is different from swiping an app away from Recents. Force stopping an app can prevent scheduled/background work until the user opens the app again. This is normal Android behavior.

## After reinstalling

Open Dark & Light again and verify:

- App is On.
- Correct trigger is selected.
- Schedule is correct.
- All four wallpaper slots are selected.
- Exact-alarm access is still allowed.
- Xiaomi / HyperOS background settings are still enabled if applicable.
