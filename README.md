# Tetris

A sample Android game of Tetris.
The aim for this project is to provide a template gaming app that you can practice integrating Facebook SDK. For any Facebook developer resources or technical documents, you should use the [Facebook Developers Site](https://developers.facebook.com/apps/).

The main project structure:
- activities: Implementation of the various activities (views) in the game
- game: The main Tetris game logic
- render: Render utilities using GLES

The Facebook integration tutorial process:
- Facebook SDK Install
- Facebook App Events Implementation
- Deep Link Integration

Requirements:
- Android Studio
- A virtual device or Android device for testing

You should be able to open this project in Android Studio and build it and run in the virtual device or Android device of your choice.

# Tasks

To practice the integration, you should do the following:

- Install the Facebook SDK into your app
- Track activate app in order to track install
- Track achievements using App Events
- Verify your App Events
- Integrate deep links
- Verify deep links

# Facebook SDK install

First step of Facebook integration is to create your own app at [Facebook Developers Site](https://developers.facebook.com/apps/).

When creating your app, you can choose the platform to Android. Fill in the information about your app in the setup process. You will get an unique application ID after the creation process. Here are some of the key steps:
- Specify app name, category etc.
- Specify your app's package name and the entry `Activity`'s name
- Specify the keyhash from your dev computer

You can then follow the [Android SDK guide](https://developers.facebook.com/docs/android/getting-started/) to do the following:
- Add the dependencies for Facebook SDK and import it in your project
- Call `FacebookSdk.sdkInitialize` when the app opens
- Update your `AndroidManifest.xml` with your app ID and `INTERNET` permission if you don't have yet


# Facebook App Events Implementation

Following the [Facebook App Events for Android Document](https://developers.facebook.com/docs/app-events/android). On all the long running `Activity` in your app, you should log `activateApp` and `deactivateApp` on the `onResume` and `onPause` callbacks to be able to track user sessions.
Facebook uses the first `activateApp` event from user as the signal of a new app install.

In the case of this training example, you should implement `activateApp` and `deactivateApp` in both `SplashActivity` (since it's the entrance of the app) and the `MainActivity` (the main game screen).

Next, the `MainActivity` class defined two achievements: `ACHIEVEMENT_1_POINTS` and `ACHIEVEMENT_5_POINTS`. You should report these achievements as `EVENT_NAME_UNLOCKED_ACHIEVEMENT` app event described in the App Event document.

## Verifying App Events

After you build the app and run it on a Virtual Device or a test Android phone with internet connection, the new install and achievement events should show up in the [Facebook Analytics](https://www.facebook.com/analytics/) tool after a few moments. Go to Facebook Analytics, select the app you created and click on `App Events` on the left nav. Then click on `Most Recent` and you should be able to see the events received by Facebook.

# Deep Link Integration

Follow the [Deep Linking Document](https://developers.facebook.com/docs/app-ads/deep-linking). The app already accepts the special schema that starts with `sesampletetris://` (as defined in the `AndroidManifest.xml` file). And also there is a utility function `redirectToDeepLink` defined in the `SplashActivity` which redirects users to the `DeepLinkActivity` with the supplied message. You should implement the deep link handling when the app launches in `SplashActivity` and get the target URI in the deep link and call `redirectToDeepLink` with that URI.
The user will be redirected to the `DeepLinkActivity` and the screen will show the received target URI.

## Verifying Deep Links

You can verify your deep link integration in two ways:
- From [App Ads Helper](https://developers.facebook.com/tools/app-ads-helper/), select the app you created then scroll down and click on `Test Deep Link` and send a deep link like `sesampletetris://deeplink/12345`. You will receive a notification containing this deep link in your Facebook app.
- You can browse to `http://sesampleweb.herokuapp.com/deep_link.html` and there is a link as mentioned above. When you click on that link, the app should open and you will be redirected to the `DeepLinkActivity`.
