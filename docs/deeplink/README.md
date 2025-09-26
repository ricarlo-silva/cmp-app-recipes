# Deep Linking Guide

## Introduction

Deep links are URLs that take users directly to specific content within your mobile application,
bypassing the app's homepage if necessary. This improves user experience by providing a seamless
transition from other apps, websites, or notifications.

This guide provides commands to test deep links on both Android and iOS platforms.

---

## Types of Deep Links

### 1. Scheme-based Deep Links

* **Description:** Use a custom URL scheme (e.g., `myapp://`).
* **Pros:** Simple to implement.
* **Cons:**
    * Less secure, as other apps can potentially register the same scheme.
    * If multiple apps claim the same scheme, the user might see an app chooser dialog.

### 2. Verified Links (Universal Links on iOS / Android App Links)

* **Description:** Use standard `http` or `https` URLs associated with a website domain that you
  own and have configured to link to your app.
* **Pros:**
    * More secure, as they require domain verification.
    * Provide a fallback to your website if the app isn't installed.
    * Typically offer a smoother user experience (no app chooser dialog if the app is installed and
      correctly configured).
* **Cons:** Requires more setup (website configuration, assetlinks.json for Android,
  apple-app-site-association file for iOS).

---

## Testing Deep Links

### Android

#### Prerequisites

* Android Debug Bridge (ADB) installed and configured.
* An Android device or emulator connected.

#### Scheme-based Deep Link

To test a scheme-based deep link, use the `am start` command with the `-d` flag followed by your
deep link URL:

```shell
adb shell am start -d myapp://br.com.ricarlo.recipes/home
```

* `myapp://`: Your app's custom scheme.
* `br.com.ricarlo.recipes`: Typically your app's package name or a unique host.
* `/home`: The path to the specific content within your app.

#### Android App Link (Universal Link equivalent)

To test an Android App Link, use the `am start` command with the `-d` flag followed by your HTTPS
URL:

```shell
adb shell am start -d https://br.com.ricarlo.recipes/home
```

* `https://br.com.ricarlo.recipes`: Your verified domain.
* `/home`: The path to the specific content.

**Note:** For Android App Links to work correctly without a disambiguation dialog, your app must be
properly configured with an `assetlinks.json` file on your
domain. [Learn more about Android App Links](https://developer.android.com/training/app-links).

---

### iOS

#### Prerequisites

* Xcode Command Line Tools installed (includes `simctl`).
* An iOS simulator booted or a physical device connected (for physical devices, you might need
  third-party tools or test via Safari).

#### Scheme-based Deep Link

To test a scheme-based deep link on a booted iOS simulator:

```shell
xcrun simctl openurl booted "myapp://br.com.ricarlo.recipes/home"
```

* `myapp://`: Your app's custom URL scheme.
* `br.com.ricarlo.recipes/home`: The host and path to the content.

#### Universal Link

To test a Universal Link on a booted iOS simulator:

```shell
xcrun simctl openurl booted "https://br.com.ricarlo.recipes/home"
```

* `https://br.com.ricarlo.recipes/home`: Your HTTPS URL.

**Note:** For Universal Links to work correctly, your app must be properly configured with an
`apple-app-site-association` file on your domain, and your app's entitlements must be set up.

---
