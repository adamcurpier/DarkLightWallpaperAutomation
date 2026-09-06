# Dark & Light Wallpaper Automation - Privacy Policy

Dark & Light Wallpaper Automation is an open-source Android application for automatically switching between user-selected light and dark wallpapers.

## Data collection

Dark & Light Wallpaper Automation does not collect, store, sell, share, or transmit personal information.

The app contains no advertising, analytics, telemetry, tracking SDKs, or user accounts.

The current application does not request the Android INTERNET permission. Wallpaper images and application settings remain on the device.

## Wallpaper and local file access

Wallpaper images selected by the user are processed locally on the device and are used only to apply the chosen Home screen and Lock screen wallpapers.

On older Android versions, the app may request storage access so the user can select local image files. This access is not used to upload or transmit those files.

## Permissions

The app may use the following Android permissions for its core functions:

- SET_WALLPAPER - applies the selected wallpapers.
- SCHEDULE_EXACT_ALARM - schedules precise Light/Dark wallpaper changes.
- FOREGROUND_SERVICE - helps keep scheduled wallpaper automation reliable in the background.
- POST_NOTIFICATIONS - displays the foreground-service notification where required by Android.
- RECEIVE_BOOT_COMPLETED - restores enabled wallpaper automation after the device restarts.
- READ_EXTERNAL_STORAGE on Android 12 and earlier - allows selection of local wallpaper images.
- ACCESS_NETWORK_STATE - allows the app to inspect network connectivity state. This permission does not provide Internet access.

## Background operation

When wallpaper automation is enabled, Android may display a persistent notification indicating that Dark & Light Wallpaper Automation is active. This supports reliable scheduled operation on devices that aggressively restrict background applications.

## Third parties

Dark & Light Wallpaper Automation does not send information to third-party services.

## Open-source project

This project is a substantially modified derivative of DualWallpaper by Yanndroid and is distributed under the MIT License. Source code is available in the project GitHub repository.

## Contact

Questions, bug reports, and privacy concerns may be submitted through the GitHub repository issue tracker:

https://github.com/adamcurpier/DarkLightWallpaperAutomation/issues

## Changes

This privacy policy may be updated if the application permissions or data-handling behavior change in a future release.
